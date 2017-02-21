package com.zhaoyu.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Collections类中提供了大量的视图操作，如，asList,nCopies，subList，subSet等。
 * 通常，视图有一定的局限性，即可能只可以读，无法改变大小 ，只支持删除而不支持插入，
 * 如果操作不当，受限制的视图就会抛出一个UnsupportedOperationException异常。
 *
 * @author xiaoE
 *
 */
public class CollectionsTest {
	public static void main(String[] args) {

		String[] str2 = new String[12];
		// Arrays.asList并没有复制一个数组，而是返回了str2的一个视图，如下对li2的改变会同步到str2。
		List<String> li2 = Arrays.asList(str2);
		li2.set(0, "element 0");
		System.out.println(str2[0]);

		// 直接使用asList
		List<String> li3 = Arrays.asList("obj1", "obj2", "obj3");

		// 使用Collections.nCopies,返回一个100个字符串“Default”的列表。
		List<String> settings = Collections.nCopies(100, "Default");
		System.out.println(settings.size());

		/**
		 * 子范围视图
		 */
		// 返回一个settings的子视图，第一个索引包含在内，第二个索引不包含在内
		List<String> staff = new ArrayList<String>();
		staff.add("staff1");
		staff.add("staff2");
		staff.add("staff3");
		staff.add("staff4");
		staff.add("staff5");
		List group2 = staff.subList(1, 3);
		// clear会删除group2元素，并删除staff中对应的元素。staff的元素会变少。
		// 不能会视图所得的列表进行子范围操作，如settings，li3的子列表就不能清空。
		group2.clear();
		System.out.println("数组长度" + staff.size() + " || 第三个元素" + staff.get(2));

		/**
		 * 集合和数组的转换
		 */
		// 将一个数组转换为集合
		String[] values = new String[4];
		values[3] = "4444";
		HashSet<String> set1 = new HashSet<String>(Arrays.asList(values));

		// 将集合转换为数组
		Object[] values1 = set1.toArray();
		// 由于从集合转换的对象都是Object，无法改变其类型，所以不能进行强制转换，在运行时会报错。
		// String[] values2 = (String[]) set1.toArray(); // run error。

		// 需要使用另一种toArray方法，并将其设计为所希望的元素类型且长度为0的数组。
		// 不管将长度指定为多少，在这种情况下，并没有创建一个新的数组 。
		String[] values3 = set1.toArray(new String[0]);
		System.out.println("转换后的长度" + values3.length + ", 第二个元素为：" + values3[1]);

		// 也可以构造一个指定大小的数组。
		String[] values4 = set1.toArray(new String[set1.size()]);
		System.out.println("转换后的长度" + values4.length + ", 第二个元素为：" + values4[1]);

		/**
		 * 排序,可以使用自定义的Comparator作为参数。
		 */
		Collections.sort(staff);
		// 反向排序
		Collections.sort(staff, Collections.reverseOrder());

		/**
		 * 和排序的功能相反的混排乱序函数shuffle
		 */
		Collections.shuffle(staff);

		/**
		 * 创建同步视图
		 */
		Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());

		/**
		 * 集合的二分法查找，对于实现Comparator接口的对象，可以使用二分法查找对象。如果没有实现Comparator对象，可以自定义
		 * 一个Comparator对象 。
		 *
		 * 二分法查找对列表进行排序后查找。时间复杂度为O(log2 n)。
		 *
		 * 二分查找只有对随机访问的数据结构有意思，如果是链表结构的，要遍历表的一半查找中间位置。那么二分法会失去优势。
		 * 对于一个排序好的列表，如果没有查找到某个元素。返回i,那么可以将这个元素插入到-i-1的位置，保持列表的排序正确。
		 */
		int i = Collections.binarySearch(staff, "staff4");
		int i1 = Collections.binarySearch(staff, "staff4", Collections.reverseOrder());
	}
}
