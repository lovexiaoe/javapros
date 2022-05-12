package net.zhaoyu.javapros.j2se.designpattern.creational;

//内部静态类方法是线程安全的
public class Singleton {
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Singleton instance = new Singleton();
    }
    /**
     * 私有化构造方法
     */
    private Singleton(){
    }

    /**
     * 这里提供静态方法，因为如果提供实例方法，意味着Singleton类可以创建实例，那么久达不到单例的效果。
     * @return
     */
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
