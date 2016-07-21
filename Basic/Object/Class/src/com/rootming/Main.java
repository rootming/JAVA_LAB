package com.rootming;

class Tree {
    protected double high;
    protected String name;

/*
* 为了让派生类能访问而已
*/

    Tree(){
        System.out.println("Default");
        /* 派生类会调用默认构造函数 */
    }

    public Tree(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void printName(){
        System.out.println("(Father)Tree name is :" + name);
    }

}


class ChildTree1 extends Tree {
    private String secondName;
    void setName(String first, String second){
        this.name = first;
        this.secondName = second;
    }
    public void printName(){
        super.printName();
        System.out.println("Tree name is :" + name + secondName);
    }


}

/*
*  protected 继承与C++类似, 派生类可以访问基类
*  在这里不能把子类设置为public, 在Java中文件名与该文件中public类名字相同
 */

class Forest  {
    private int count;
}



public class Main {

    public static void main(String[] args) {
        Tree tree1 = new Tree("Test Tree1");
        ChildTree1 tree2 = new ChildTree1();
        Tree tree3 = new ChildTree1();
        Main2 test = new Main2();
        tree1.printName();
        test.show();
        tree2.setName("Child", "Tree");
        tree2.printName();
        tree3.setName("Child");
        tree3.printName();
        /* 子类重写了父类函数, 这与C++有所不同, 加上final可以防止被重写*/
	// write your code here
    }
}

class Main2 extends Main{
    void show(){
        System.out.println("I am MainChild");
    }
}