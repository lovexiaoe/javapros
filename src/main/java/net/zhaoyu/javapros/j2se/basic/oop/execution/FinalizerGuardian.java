package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 终结器监控者模式。解决子类忘记调用父类finalizer的问题。
 * 当我们在父类重写finalizer，子类重写finalizer时，子类的使用后，回收，是不会调用父类的finalizer的。
 * 通过在父类中声明一个监控者对象，监控者对象重写finalizer。那么在子类回收后，父类的finalizer也会被调用。
 */
public class FinalizerGuardian {
    public static void main(final String[] args) throws Exception {
        Parent.doIt();
        System.gc();
        Thread.sleep(5000); //  5 sec sleep
    }
}
class Parent {

    /**
     * 当Parent类不可达时，guardian对象也不不可达，这时会调用guardian的finalize方法。
     */
    @SuppressWarnings("unused")
    private final Object guardian = new Object() {
        @Override protected void finalize() {
            doFinalize();
        }
    };

    //如果子类没有显式调用super.finalize()，那么父类的finalize方法不会被调用，所以需要定义上面的guardian。
    /*@Override protected void finalize() {
        doFinalize();
    }*/

    private void doFinalize() {
        System.out.println("Finalize of class Parent");
    }

    public static void doIt() {
        Child c = new Child();
        System.out.println(c);
    }

}

class Child extends Parent {
    @Override protected void finalize() {
//        super.finalize();
        System.out.println("Finalize of class Child");
    }

}