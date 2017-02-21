package com.zhaoyu.collections.maps;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * ��map����Щֵ��ʹ�ú󲻻���ȥ���� �����������ջ��Ʋ��ܴӱ���ɾ��������Ҫ�ɳ�����ӻ��map��ɾ������
 * ����ʹ��WeakHashMap���������顣
 * WeakHashMapʹ�������ã�weak references���������������������weak keys�����ٱ�ʹ��(û��������������)ʱ��
 * �������������л���������л��գ����պ�������ļ�ֵ�Ի��Զ���ɾ����
 *
 * ��map����key �� valueΪ�գ��̲߳���ȫ��
 *
 * @author xiaoE
 *
 */
public class WeakHashMapTest {
	public static void main(String[] args) {
		Map<String, Object> map = new WeakHashMap();
		map.put("111", new Integer(2));
		map.put("111", new Integer(3));
		map.put("222", new Integer(4));
		System.out.println(map.get("222"));
		System.out.println("���ȣ�" + map.size() + ", ����" + map);
	}
}
