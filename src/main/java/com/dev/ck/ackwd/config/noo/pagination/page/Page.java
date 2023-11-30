package com.dev.ck.ackwd.config.noo.pagination.page;

import java.io.Serializable;

/**
 * 페이징 인터페이스 .
 */
public interface Page extends Serializable {
	int getCurrentPage(); //현재 페이지 번호
	boolean isNext(); //현제 페이지가 마지막 페이지가 아니여서 다음 페이지가 있으면  true 를 반환
	boolean isPrevious(); //현재 페이지가 처음 페이지가 아니여서 이전 페이지가 있으면  true 를 반환
	int getPageEndRow(); //현제 페이지에서 마지막 아이템의
	int getPageSize(); // 한페이지 안에 포함할 최대 아이템 갯수
	int getPageStartRow();
	int getTotalPages();
	int getTotalRows();
	void setTotalPages(int i);
	void setCurrentPage(int i);
	void setNext(boolean b);
	void setPrevious(boolean b);
	void setPageEndRow(int i);
	void setPageSize(int i);
	void setPageStartRow(int i);
	void setTotalRows(int i);
	void init(int rows, int pageSize, int currentPage);
}
