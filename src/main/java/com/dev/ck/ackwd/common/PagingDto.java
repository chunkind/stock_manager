package com.dev.ck.ackwd.common;

public class PagingDto {
	private int page;
	private int perRows;
	
	public PagingDto() {
		this.page = 0;
		this.perRows = 5;
	}

	public void setStrPage(String page) {
		if(null == page || "".equals(page) || "null".equals(page.toLowerCase()))
			this.page = 1;
		else
			this.page = Integer.parseInt(page);
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setPerRows(int perRows) {
		this.perRows = perRows;
	}
	
	public int getPage() {
		return (page - 1) * perRows;
	}
	public int getPerRows() {
		return page * perRows;
	}
}