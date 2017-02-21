package com.zhaoyu.collections.maps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap��LinkedHashSet������¼����Ԫ�����˳��
 * ����ɢ�б��÷���˳�򣬶����ǲ���˳�򣬶�ӳ�����Ŀ���� ������ ÿ�ε���get����put��
 * �ܵ�Ӱ�����Ŀ���ӵ�ǰ��λ��ɾ�������ŵ������β������Ӧ��Ͱ����仯��ֻ�ǵڸ�Ԫ����Ͱ�е�λ�÷����仯����
 * ʵ����"�������ʹ��"ԭ��
 *
 * �������Թ���һ�����ٻ���LinkedHashMap��
 *
 * @author xiaoE
 *
 */
public class LinkedHashMapTest {
	public static void main(String[] args) {
		Map staff = new LinkedHashMap();
		staff.put("001", "��һ");
		staff.put("002", "���");
		staff.put("003", "����");
		staff.put("004", "����");
		Iterator<String> it = staff.keySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		// ���湹��һ�����Դ���100��Ԫ�صĸ��ٻ��档
		Map cache = new LinkedHashMap(128, 0.75F, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return size() > 100;
			}
		};
	}
}
