package net.zhaoyu.javapros.j2se.designpattern.creational.builder;


/**
 * 建造者（Builder）模式：将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示。
 * 使用案例：lombok的builder，及mybatis的Environment中的Builder
 *
 * 实现由产品，建造者（再抽象化可细分：抽象建造者和具体建造者），指挥者组成。
 *
 * 好处：构建和表示分离，进一步降低耦合。
 * 缺点：产品和构造的组成部分必须相同，产品发生变化，构造也需要变化。
 */
public class Demo {

    public static void main(String[] args) {
        Builder builder = new Builder();
        builder.name("王二");
        builder.tel("18088880000");
        builder.address("乱星海黑岩城。");
        Product product = builder.build();
    }
}
