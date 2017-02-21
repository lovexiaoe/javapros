package com.zhaoyu.collections.sets;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ������һ�����򼯺ϡ���������˳��Ԫ�ز��뵽�����С�������ʹ�����ṹ��ʵ�ֵģ�
 * ��ǰʹ�õ��Ǻ����������ʱ�临�Ӷ�Ϊlog2 n�η���
 *
 * treeSet֪����ô���������ٶ�Ԫ��ʵ����Comparable�ӿڡ�
 * Ȼ����ʹ��Comparable�ӿڶ���������������ԣ�����һ���������ֻ࣬��ʵ�������һ�Ρ����һ��������Ҫ���ղ�ͬ��
 * �����������ǾͲ���ʵ�֡�
 * ���������ǿ��Խ�Comparator�Ķ��󴫵ݸ�TreeSet�ȼ��ϵĹ��췽��������������ϰ���Comparator������ıȽϷ�ʽ
 * ���бȽϡ�
 *
 * @author xiaoE
 *
 */
public class TreeSetTest {
	public static void main(String[] args) {
		ItemComparator comp = new ItemComparator();
		// ����Ҳ����ʹ��ItemComparator �������ඨ�� ��
		SortedSet<String> sortedSet = new TreeSet<String>(comp);
	}
}

class ItemComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

}
