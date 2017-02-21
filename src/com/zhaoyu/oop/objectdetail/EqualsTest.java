package com.zhaoyu.oop.objectdetail;

/**
 * java���Ե�equals���������������ԣ�
 * 1���Է��ԣ��ǿ�����x,x.equals(x) Ӧ�÷���true��
 * 2���Գ��ԣ��ǿ�����x��y,x.equals(y)����true,��y.equals(x)ҲӦ������true.
 * 3�������ԣ��ǿ�����x,y,z�����x.equals(y)����true��y.equals(z)����true,x.equals(z)ҲӦ�÷���true��
 * 4��һ���ԣ����x,y���õĶ���û�з����仯����ô����������x.equals(y)Ӧ�÷�����ͬ�Ľ�� ��
 * 5����������ǿ�����x��x.equals(null)Ӧ�÷���false��
 *
 * java��дequals�Ĳ��裺�����Ҫ�Ƚϵ�����ΪclassName,���Ƚϲ�������ΪotherObject,��
 * 1�����this��otherObject�Ƿ�����ͬһ������if(this==otherObject) return true;
 * ���������һ���Ż������ ��ͬһ������ ���Ͳ���Ҫ ���жԱ������ˡ�
 * 2,���otherObject�Ƿ�Ϊnull�����Ϊnull,����false��if(null==otherObject) return false;
 * 3,�Ƚ�otherObject��this�Ƿ�����ͬһ�ࡣ���equals�������ڸ��������������ı䣬��ʹ��getClass��⣺
 * if(getClass()!=otherObject.getClass)return false;
 * ������е����඼ӵ��ͳһ��equals���壬��ʹ��instanceof��⡣
 * if(!(otherObject instanceof ClassName)) return false��
 * 4,��otherObjectת������Ӧ�������ͱ�����
 * className other=(className)otherObject;
 * 5,����������������¶���equals����Ҫ�����а�������super.equals(other)��
 * 6,ʹ��==�Ƚϻ��������ֶΣ�ʹ��equals�Ƚ������ֶΡ�������е��ֶζ�ƥ�䣬�򷵻�true,���򣬷���false��
 *
 *
 * @author xiaoe
 *
 */
public class EqualsTest {
	public static void main(String[] args) {
		System.out.println("equals��Object�ķ�����");
	}
}
