package net.zhaoyu.javapros.j2se.oop.extend;


//继承是is-a的关系，当一个子类继承自一个父类的时候，如果父类变量占用1M,子类对象占用0.5M，那么在新建一个子类的时候。
//在内存模型中，会首先创建一个1M的父类对象（包括了所有的方法，变量等（也包括私有成员）），然后，在创建一个0.5M的子类对象，
//再创建一个影藏的引用super指向父类对象，总共加起来为1.5M，虽然子类不能直接使用父类的私有成员。但是子类是拥有父类的私有成员的。

public class ExtendTest {
    public static void main(String[] args) {
        //这里会首先初始化一个父类Father，包括所有成员，然后创建son这个子类，拥有一个指向父类的指针。
        // 这里son是拥有父类的私有成员i的，但是不能直接访问。所以调用print方法时，会打印出2.
        Son son=new Son();
        son.print();
    }
    static class Father{
        private int i=1;
        void print(){
            System.out.println(++i);
        }
    }

    static class Son extends  Father{
        void print(){
            super.print();
        }
    }

}

