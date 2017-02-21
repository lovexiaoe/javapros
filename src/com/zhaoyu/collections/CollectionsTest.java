package com.zhaoyu.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Collections�����ṩ�˴�������ͼ�������磬asList,nCopies��subList��subSet�ȡ�
 * ͨ������ͼ��һ���ľ����ԣ�������ֻ���Զ����޷��ı��С ��ֻ֧��ɾ������֧�ֲ��룬
 * ������������������Ƶ���ͼ�ͻ��׳�һ��UnsupportedOperationException�쳣��
 *
 * @author xiaoE
 *
 */
public class CollectionsTest {
	public static void main(String[] args) {

		String[] str2 = new String[12];
		// Arrays.asList��û�и���һ�����飬���Ƿ�����str2��һ����ͼ�����¶�li2�ĸı��ͬ����str2��
		List<String> li2 = Arrays.asList(str2);
		li2.set(0, "element 0");
		System.out.println(str2[0]);

		// ֱ��ʹ��asList
		List<String> li3 = Arrays.asList("obj1", "obj2", "obj3");

		// ʹ��Collections.nCopies,����һ��100���ַ�����Default�����б�
		List<String> settings = Collections.nCopies(100, "Default");
		System.out.println(settings.size());

		/**
		 * �ӷ�Χ��ͼ
		 */
		// ����һ��settings������ͼ����һ�������������ڣ��ڶ�����������������
		List<String> staff = new ArrayList<String>();
		staff.add("staff1");
		staff.add("staff2");
		staff.add("staff3");
		staff.add("staff4");
		staff.add("staff5");
		List group2 = staff.subList(1, 3);
		// clear��ɾ��group2Ԫ�أ���ɾ��staff�ж�Ӧ��Ԫ�ء�staff��Ԫ�ػ���١�
		// ���ܻ���ͼ���õ��б�����ӷ�Χ��������settings��li3�����б�Ͳ�����ա�
		group2.clear();
		System.out.println("���鳤��" + staff.size() + " || ������Ԫ��" + staff.get(2));

		/**
		 * ���Ϻ������ת��
		 */
		// ��һ������ת��Ϊ����
		String[] values = new String[4];
		values[3] = "4444";
		HashSet<String> set1 = new HashSet<String>(Arrays.asList(values));

		// ������ת��Ϊ����
		Object[] values1 = set1.toArray();
		// ���ڴӼ���ת���Ķ�����Object���޷��ı������ͣ����Բ��ܽ���ǿ��ת����������ʱ�ᱨ��
		// String[] values2 = (String[]) set1.toArray(); // run error��

		// ��Ҫʹ����һ��toArray���������������Ϊ��ϣ����Ԫ�������ҳ���Ϊ0�����顣
		// ���ܽ�����ָ��Ϊ���٣�����������£���û�д���һ���µ����� ��
		String[] values3 = set1.toArray(new String[0]);
		System.out.println("ת����ĳ���" + values3.length + ", �ڶ���Ԫ��Ϊ��" + values3[1]);

		// Ҳ���Թ���һ��ָ����С�����顣
		String[] values4 = set1.toArray(new String[set1.size()]);
		System.out.println("ת����ĳ���" + values4.length + ", �ڶ���Ԫ��Ϊ��" + values4[1]);

		/**
		 * ����,����ʹ���Զ����Comparator��Ϊ������
		 */
		Collections.sort(staff);
		// ��������
		Collections.sort(staff, Collections.reverseOrder());

		/**
		 * ������Ĺ����෴�Ļ���������shuffle
		 */
		Collections.shuffle(staff);

		/**
		 * ����ͬ����ͼ
		 */
		Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());

		/**
		 * ���ϵĶ��ַ����ң�����ʵ��Comparator�ӿڵĶ��󣬿���ʹ�ö��ַ����Ҷ������û��ʵ��Comparator���󣬿����Զ���
		 * һ��Comparator���� ��
		 *
		 * ���ַ����Ҷ��б�����������ҡ�ʱ�临�Ӷ�ΪO(log2 n)��
		 *
		 * ���ֲ���ֻ�ж�������ʵ����ݽṹ����˼�����������ṹ�ģ�Ҫ�������һ������м�λ�á���ô���ַ���ʧȥ���ơ�
		 * ����һ������õ��б����û�в��ҵ�ĳ��Ԫ�ء�����i,��ô���Խ����Ԫ�ز��뵽-i-1��λ�ã������б��������ȷ��
		 */
		int i = Collections.binarySearch(staff, "staff4");
		int i1 = Collections.binarySearch(staff, "staff4", Collections.reverseOrder());
	}
}
