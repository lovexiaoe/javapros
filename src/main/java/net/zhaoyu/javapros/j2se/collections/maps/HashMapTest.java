package net.zhaoyu.javapros.j2se.collections.maps;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 在java中，散列表用链表数组实现，每个链表都被称为桶（bucket）。想要查找表中对应的位置，就要先计算它的hashcode，
 * 然后与桶的总数取余，所得到的结果就是保存这个元素的桶的索引。
 * 如果某个对象的hashcode为76268，并且有128个桶，对象就应该保存在第108个桶中（76268除以128余108）。如果这个桶没有满。
 * 那么就将这个对象存到桶中。当然，有时候会遇到桶被占满的情况，这种情况是不可避免的，被称为散列冲突（hash collision）。
 *
 * 如果大致知道有多少个元素要被插入到散列表中，就可以设置桶数。通常，将桶数设置为预计元素个数的75%~150%。
 * 且最好设置成一个素数，以防键的集聚(hash碰撞)。标准类库使用的数组的长度是2的幂，默认值为16.
 *
 * 为什么数组长度必须是2的n次方呢，“取余（%）操作中如果除数是2的幂次，则等价于与其除数减一的与（&）操作，也就是说hash%length=hash&(length-1)”。
 * 这样提高了运算效率。
 *
 * 当然不可能总知道需要存储的元素个数 ，如果散列表太满，就需要再散列（rehashed）
 * 装填因子(load factor)决定何时对表进行rehash。默认的是0.75，如果Hashtable的容量超过
 *（当前存放的对象的最大数量）/（装填因子）,那么就会rehash。创建一个桶更多的表（大约是当前表的两倍）
 * ，并将原表的数据移到这个新表中。原来的数据会被移动到新表中并且位置根据hash发生变化。
 *
 * hashMap可以使用null的key或者value。不同步，继承自AbstractMap，可以使用Collections.synchronizedMap方法包装一个hashMap。
 *
 * hashMap在多线程下会导致死循环问题，
 * 原因在于并发下的 Rehash 会造成元素之间会形成一个循环链表，导致get操作死循环。不过，jdk 1.8 后解决了这个问题
 *
 * 在1.8以后对相同hashcode的链表做了查询优化，在数组长度大于8时，使用树结构(红黑树)存储，查找的时间复杂度降低。
 *
 *
 * 可能获得map的视图，一共有3个视图
 * Set<K> keySet()
 * Collection<K> values()
 * Set<Map.Entry<K,V>> entrySet()
 *
 */
public class HashMapTest {
	public static void main(String[] args) {

		Map<String, Employee> staff = new HashMap<String, Employee>();
		staff.put("001", new Employee("张三"));
		staff.put("002", new Employee("张四"));
		staff.put("003", new Employee("张五"));
		staff.put("004", new Employee("张六"));

		//java8初始化
		Map<String, Integer> map = new HashMap<String,Integer>(){{
			put("A", 10);
			put("B", 11);
			put("C", 12);
		}};

		System.out.println(staff);

		// 删除元素
		staff.remove("002");
		// 添加已存在的元素。
		staff.put("004", new Employee("张七"));
		// 遍历key-value。
		for (Map.Entry<String, Employee> entry : staff.entrySet()) {
			String key = entry.getKey();
			Employee value = entry.getValue();
			System.out.println("key=" + key + ",value" + value);
		}
	}
}

class Employee {
	private String name;
	private double salary;

	public Employee(String n) {
		name = n;
		salary = 0;
	}

	@Override
	public String toString() {
		return "[name=" + name + ",salary=" + salary + "]";
	}
}
