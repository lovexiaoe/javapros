package com.zhaoyu.collections.sets;

import java.util.HashSet;
import java.util.Set;

/**
 * 在java中，散列表用链表数组实现，每个链表都被称为桶（bucket）。想要查找表中对应的位置，就要先计算它的hashcode，
 * 然后与桶的总数取余，所得到的结果就是保存这个元素的桶的索引。
 * 如果某个对象的hashcode为76268，并且有128个桶，对象就应该保存在第108个桶中（76268除以128余108）。如果这个桶没有满。
 * 那么就将这个对象存到桶中。当然，有时候会遇到桶被占满的情况，这种情况是不可避免的，被称为散列冲突（hash collision）。
 *
 * 如果大致知道有多少个元素要被插入到散列表中，就可以设置桶数。通常，将桶数设置为预计元素个数的75%~150%。
 * 且最好设置成一个素数，以防键的集聚。标准类库使用的桶是2的幂，默认值为16.
 *
 * 当然不可能总知道需要存储的元素个数 ，如果散列表太满，就需要再散列（rehashed）。创建一个桶更多的表，并将原表的数据移到
 * 这个新表中。装填因子(load factor)决定何时对表进行rehash。默认的是0.75。
 *
 * hash表可以实现几个重要类型的数组结构。hashset是一个没有重复元素的集合。
 *
 *
 * @author xiaoE
 *
 */
public class HashSetTest {
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("1111");
		set.add("2222");
		set.add("1111");// 重复添加，覆盖原来的1111。集合中仍然有两个元素。
		System.out.println(set.size());
	}
}
