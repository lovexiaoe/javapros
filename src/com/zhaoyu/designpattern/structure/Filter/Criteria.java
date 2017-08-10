package com.zhaoyu.designpattern.structure.Filter;

import java.util.List;

public interface Criteria {
	public List<Person> meetCriteria(List<Person> persons);
}
