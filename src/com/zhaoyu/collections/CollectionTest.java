package com.zhaoyu.collections;

/**
 * ��java����У�������Ļ����ӿڶ���Collection��
 * Collection��������������
 * public interface Collection<E>
 * {
 * boolean add(E element)
 * Iterator<E> iterator();
 * }
 *
 * iterator��������һ��ʵ����Iterator�ӿڵĶ���Iterator����������������
 * public interface Iterator{
 * E next();
 * boolean hasNext();
 * void remove();
 * }
 *
 * Iterable�ӿڷ���һ��Iterator������ֻ��һ��������
 * public interface Iterable<E>{
 * Iterator<E> iterator();
 * }
 * foreachѭ�����Բ����κ�ʵ��Iterable�ӿڵĶ���
 *
 * @author xiaoE
 *
 */

/**
 * Collection�ӿڵķ����ڶ࣬����빹��һ��ʵ������ӿڵĹ����࣬��ô����һ�����˵����� ��
 * java����ṩ��һ����AbstractCollection,����������size��iterator���������ˣ������ṩ���������з�����
 *
 */
public class CollectionTest {

}
