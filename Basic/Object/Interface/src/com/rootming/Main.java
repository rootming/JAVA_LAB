package com.rootming;


interface TreeInterface{
    int high = 0;
    void setName(String name);
    void setHigh(int high);
}

interface TreeInterface2{
    int high2 = 0;
    void setName2(String name);
    void setHigh2(int high);
}

abstract class TreeClass{
    int high3 = 0;
    abstract void setName3(String name);
    abstract void setHigh3(int high);
    /* public 或者 protected 能够被派生类继承 */

    static void mothed(){
        System.out.println("Interface can't do it.");
    }
}

class TestTree extends TreeClass implements TreeInterface, TreeInterface2 {

    public void setName(String name){

    }


    public void setHigh(int high){

    }

    public void setName2(String name){

    }

    public void setHigh2(int high){

    }

    public void setName3(String name){

    }

    public void setHigh3(int high){

    }

}

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}
