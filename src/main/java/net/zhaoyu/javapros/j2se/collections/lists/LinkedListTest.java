package net.zhaoyu.javapros.j2se.collections.lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 在java中所有的链表都是双向链接的。
 *
 * 接口Iterator中没有add方法。它的子接口ListIterator包含了add方法。
 * 另外ListIterator还有两个方法，可以用来反向遍历链表。
 *
 * interface ListIterator<E> extends Iterator<E>
 * {
 * void add(E element); //add方法在迭代器位置之前添加 一个新对象 。
 * ...
 * E previous()
 * boolean hasPrevious()
 * }
 *
 * @author xiaoE
 *
 */
public class LinkedListTest {
    public static void main(String[] args) {

        List<Integer> list = new LinkedList<Integer>();
        list.add(2);
        list.add(1);
        System.out.println(list.get(0));

        TestModificationException();
    }

    /**
     * iterator能检测到元素的修改，如果iterator发现它的集合被另一个iterator或者集合自身修改，就会抛出
     * ModificationException异常。
     *
     */
    public static void TestModificationException() {
        List<String> li = new ArrayList<String>();
        li.add("1111");
        li.add("2222");
        li.add("3333");
        li.add("4444");
        // contains方法可以检测和某个值相等(equals)的对象是否出现在列表中。
        System.out.println(li.contains("4444"));
        li.add("4444");
        System.out.println(li);
        ListIterator<String> i1 = li.listIterator();
        ListIterator<String> i2 = li.listIterator();
        i1.next();
        i1.remove();
        i2.next(); // throws java.util.ConcurrentModificationException
    }
}
