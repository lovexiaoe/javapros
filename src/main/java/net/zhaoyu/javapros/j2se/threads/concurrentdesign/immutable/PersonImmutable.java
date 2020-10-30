package net.zhaoyu.javapros.j2se.threads.concurrentdesign.immutable;

import java.util.Date;

/**
 * 不可变对象，在并发时，有如下两个好处：
 * 1.不需要使用不同机制。
 * 2.不会有数据不一致的问题。
 *
 * 不可变对象有如下几个方面：
 * 1. 将class设置为final，不能被其他类扩展。
 * 2. 所有的属性设置为 private final。
 * 3. 不提供对属性的修改方法，在constructor中一次初始化。
 * 4. 如果有字段的值对象时可变的，（如Date），那么在getter方法中总是返回一个copy。
 * 5. 不要在constructor中泄露this引用。如下，会this泄露出去。
 * <pre> {@code
 * 		public NotSoImmutable(int x, Observable o) {
 *         this.x = x;
 *            o.registerListener(this);
 *          }
 * }</pre>
 */
public final class PersonImmutable {
	
	final private String firstName;
	final private String lastName;
	final private Date birthDate;
	
	public PersonImmutable (String firstName, String lastName, String address, Date birthDate) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.birthDate=birthDate;
	}

	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}
	
	public Date getBirthDate() {
		//值对象可能改变的字段，返回copy。
		return new Date(birthDate.getTime());
	}
}
