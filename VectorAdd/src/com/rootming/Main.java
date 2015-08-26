package com.rootming;

class Vector{
    public int x;
    public int y;

    Vector()
    {
        this.x = 0;
        this.y = 0;
    }

    Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    static public Vector add(Vector first, Vector second)
    {
        return new Vector(first.x + second.x, first.y + second.y);
    }

    public Vector add(Vector second)
    {
        return new Vector(this.x + second.x, this.y + second.y);
    }

    static public Vector subtract(Vector first, Vector second)
    {
        return new Vector(first.x - second.x, first.y - second.y);
    }

    public Vector subtract(Vector second)
    {
        return new Vector(this.x - second.x, this.y - second.y);
    }

    static public void display(Vector vec)
    {
        System.out.println("x = " + vec.x + " ,y = " + vec.y);
    }

    public void display()
    {
        System.out.println("x = " + this.x + " ,y = " + this.y);
    }
}


public class Main {

    public static void main(String[] args) {
	// write your code here
        Vector test = new Vector();
        test.display();
        Vector.display(test);
    }
}
