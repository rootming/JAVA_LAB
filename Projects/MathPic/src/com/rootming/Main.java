package com.rootming;


import static java.lang.Math.pow;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Graphics;

class MathPic {

    class Post
    {
        int x, y;
        Post() {

        }
        Post(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    private int length, unitLength;
    private int threadCount;
    private byte image[];
    private DrawUnit threads[];
    DrawScreen frame;

    Queue<Post>posList;

    MathPic(int length, int unitLength, int num){

        this.length = length;
        this.unitLength = unitLength;
        this.threadCount = num;
        posList = new LinkedList<Post>();
        image = new byte[length * length * 3];
        threads = new DrawUnit[threadCount];
        frame = new DrawScreen();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    void make(){

        calcUnitPosition();

        for(int i = 0; i < threadCount; i++){
            threads[i] = new DrawUnit(i);
            threads[i].start();
        }
        for(int i = 0; i < threadCount; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void drawPixel(int x, int y)
    {
        double a = 0.0, b = 0.0, c, d, n = 0;
        while ((c = a * a) + (d = b * b) < 4 && n++ < 880) {
            b = 2 * a * b + x * 8e-9 - .645411;
            a = c - d + y * 8e-9 + .356888;
        }
        image[3 * (length * y + x)] = (byte)(255 * pow((n - 80) / 800, 3.));
        image[3 * (length * y + x) + 1] = (byte)(255 * pow((n - 80) / 800, .7));
        image[3 * (length * y + x) + 2] = (byte)(255 * pow((n - 80) / 800, .5));
    }

    private void drawUnit(Post pos){
        int startX, startY, endX, endY;
        startX = pos.x;
        startY = pos.y;
        endX = length > startX + unitLength ? startX + unitLength : length;
        endY = length > startY + unitLength ? startY + unitLength : length;
        for (int y = startY; y < endY; y++)
            for (int x = startX; x < endX; x++)
                drawPixel(x, y);
    }

    void calcUnitPosition()
    {
        System.out.println("Calculating postion...");
        int count = (length + unitLength - 1) / unitLength;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                posList.offer(new Post(i * unitLength, j * unitLength));
            }
        }
        System.out.println("Calculate finished.");
    }

    void showPosts(){
        Post tmp;
        tmp = posList.poll();
        while(tmp != null) {
            System.out.println("X: " + tmp.x + " " + "Y: " + tmp.y);
            tmp = posList.poll();
        }
    }

    void save(String filename) {
        String head = new String("P6\n" + length + " " + length + "\n255\n");
        try {
            FileOutputStream file = new FileOutputStream(filename);
            file.write(head.getBytes());
            file.write(image);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void show() {
        frame.start();
    }

    class DrawUnit extends Thread {

        int number;
        @Override
        public void run(){
            Post temp;
            System.out.println("Thread:" + number + " is running...");
            for (;;) {
                temp = getPost();
                if(temp == null)
                    break;
                drawUnit(temp);
            }
            System.out.println("Thread:" + number + " finished.");
        }


        DrawUnit(int number) {
            this.number = number;
        }

        synchronized Post getPost() {
            return posList.poll();
        }


    }

    class DrawScreen extends JFrame {
        BufferedImage buffer = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
        boolean finish = true;
        public DrawScreen() {
            setTitle("画弧形");
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            finish = false;
            byte image2[] = image.clone();
            for(int r = 0; r < length; r++)
                for(int c = 0; c < length; c++) {
                    int index = r * length + c;
                    int tmp;
                    tmp = image2[index];
                    tmp |= image2[index + 1] << 8;
                    tmp |= image2[index + 2] << 16;
                    buffer.setRGB(c, r, tmp);
                }
            System.out.println("Called draw.");
            g.drawImage(buffer, 0, 0, length, length, null);
            finish = true;
        }

        void start() {
            new AutoRefresh().start();
        }

        class AutoRefresh extends Thread {
            @Override
            public void run() {
                while(true){
                    if(finish)
                        repaint();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}





public class Main {



    public static void main(String[] args) {
	// write your code here
        long timeStart=System.currentTimeMillis();
        MathPic myImage = new MathPic(800, 128, 1);
//        myImage.calcUnitPosition();
//        myImage.showPosts();
        myImage.show();
        myImage.make();
        myImage.save("MathPic.ppm");
        System.out.println("Write finished");
        System.out.println("Using time: "+(System.currentTimeMillis()-timeStart) + " ms.");
    }


}
