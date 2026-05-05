package com.andreina.ushi.model;

import java.util.ArrayList;
import java.util.List;

public class Results<T> {
	
	private List<T> pageResults = null;
	private int total = 0;

	public Results() {
		pageResults = new ArrayList<T>();
	}

	public List<T> getPageResults() {
		return pageResults;
	}

	public void setPageResults(List<T> pageResults) {
		this.pageResults = pageResults;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
