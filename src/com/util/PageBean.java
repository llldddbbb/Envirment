package com.util;

public class PageBean {
	
	private int page;//当前页
	private int pageSize;//页面大小
	@SuppressWarnings("unused")
	private int start;//起始数
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStart() {
		return (page-1)*pageSize;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public PageBean() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	
}
