package com.zhaoyu.collections.lists;

import java.util.ArrayList;

/**
 * ��Ȼ����Ҳ����������ʱȷ������Ĵ�С�������������ʼ������ı�Ͳ�̫�����ˣ�
 * ArrayList�����ӻ���ɾ��Ԫ��ʱ�������Զ��������������Ĺ��� ��������ҪΪ�˱�д�κδ��롣
 *
 * ArrayList�Ĳ����ɾ������Ҫ�ƶ�������Ԫ�أ������޸�Ч�ʵ͡�
 *
 * @author xiaoe
 *
 */
public class ArryList {
	public static void main(String[] args) {
		// ArrayList�����ڳ�ʼ��ʱ���ó�ʼ���� ��
		ArrayList<Integer> list = new ArrayList<Integer>(100);
		list.add(1);
		list.add(3);
		list.add(8);
		// size()�������ؽ��Ϊ3
		System.out.println(list.size());

		// ���ȷ�������б�Ĵ�С���ٷ����仯�����Ե���trimToSize���������б�δռ�õĿռ���ա�
		// ������������޸��б�����Ҫ���ѽ϶���Դ��
		// list.trimToSize();

		// ����ʹ��get�����õ��б��е�Ԫ�ء��±��0��ʼ
		System.out.println(list.get(1));

		// ʹ��set����Ԫ�ص�ֵ ��
		list.set(0, 23);
		System.out.println(list.get(0));

		// ������β����������֮�⣬��������ָ�������в��롣
		list.add(0, 11);
		// ArrayList��д��toString������
		System.out.println(list);

		// Ҳ����ɾ��ָ����Ԫ��
		list.remove(1);
		System.out.println(list);

		// subListʹ�á�
		list.clear();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		System.out.println(list.subList(2, 4));
	}

}
