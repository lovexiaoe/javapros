package net.zhaoyu.javapros.j2se.designpattern.structure.Filter;

import java.util.List;

public interface Criteria {
	public List<Person> meetCriteria(List<Person> persons);
}
