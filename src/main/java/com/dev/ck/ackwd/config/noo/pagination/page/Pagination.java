package com.dev.ck.ackwd.config.noo.pagination.page;

/**
 * 쿼리 매개 변수를 설정합니다.
 */
public class Pagination implements Page {
	/**
	 * 직렬화 ID
	 */
	private static final long serialVersionUID = 8919076199499894558L;
	/**
	 * 기본 데이터 페이지 (10)
	 */
	protected int pageSize = 10;
	/**
	 * 현재 페이지
	 */
	protected int currentPage = 1;
	/**
	 * 총 페이지
	 */
	protected int totalPages = 0;
	/**
	 * 데이터의 총 개수
	 */
	protected int totalRows = 0;
	/**
	 * 페이지 당 라인의 시작 번호
	 */
	protected int pageStartRow = 0;
	/**
	 * 페이지 데이터 종단의 행의 수를 나타낸다
	 */
	protected int pageEndRow = 0;
	/**
	 * 다음 페이지가  있을떄 
	 */
	boolean next = false;
	/**
	 * 이전 페이지가 있을때
	 */
	boolean previous = false;

	public Pagination(int rows, int pageSize) {
		this.init(rows, pageSize);
	}
	
	public Pagination() {

	}

	/**
	 * 페이징 초기화 파라미터 : 당신은 totalRows 를 설정해야  한다. 
	 */
	public void init(int rows, int pageSize) {
		this.pageSize = pageSize;
		this.totalRows = rows;
		if ((totalRows % pageSize) == 0) {
			totalPages = totalRows / pageSize;
		} else {
			totalPages = totalRows / pageSize + 1;
		}
	}

	@Override
	public void init(int rows, int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.totalRows = rows;
		if ((totalRows % pageSize) == 0) {
			totalPages = totalRows / pageSize;
		} else {
			totalPages = totalRows / pageSize + 1;
		}
		if (totalPages != 0)
			gotoPage(currentPage);
	}

	/**
	 * 현재 페이지 범위 를 계산 : pageStartRow 및 pageEndRow 을
	 */
	private void calculatePage() {
		previous = (currentPage - 1) > 0;
		next = currentPage < totalPages;
		if (currentPage * pageSize < totalRows) { //마지막 여부를 결정
			pageEndRow = currentPage * pageSize;
			pageStartRow = pageEndRow - pageSize;
		} else {
			pageEndRow = totalRows;
			pageStartRow = pageSize * (totalPages - 1);
		}
	}

	/**
	 * 특정 페이지 번호로 직접 이동할
	 */
	public void gotoPage(int page) {
		currentPage = page;
		calculatePage();
		// debug1();
	}

	public String debugString() {
		return "데이터의 총 수:" + totalRows + "총 페이지 수:" + totalPages + "  현재 페이지:"
				+ currentPage + "이전 여부:" + previous + "다음 여부:"
				+ next + "시작 행수:" + pageStartRow + "종료 행수:" + pageEndRow;
	}
	
	public String toString() {
		return "데이터의 총 수:" + totalRows + "총 페이지 수:" + totalPages + "  현재 페이지:"
				+ currentPage + "이전 여부:" + previous + "다음 여부:"
				+ next + "시작 행수:" + pageStartRow + "종료 행수:" + pageEndRow;
	}

	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	@Override
	public boolean isNext() {
		return next;
	}

	@Override
	public boolean isPrevious() {
		return previous;
	}

	@Override
	public int getPageEndRow() {
		return pageEndRow;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getPageStartRow() {
		return pageStartRow;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public int getTotalRows() {
		return totalRows;
	}

	@Override
	public void setTotalPages(int i) {
		totalPages = i;
	}

	@Override
	public void setCurrentPage(int i) {
		currentPage = i;
	}

	@Override
	public void setNext(boolean b) {
		next = b;
	}

	@Override
	public void setPrevious(boolean b) {
		previous = b;
	}

	@Override
	public void setPageEndRow(int i) {
		pageEndRow = i;
	}

	@Override
	public void setPageSize(int i) {
		pageSize = i;
	}

	@Override
	public void setPageStartRow(int i) {
		pageStartRow = i;
	}

	@Override
	public void setTotalRows(int i) {
		totalRows = i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + pageSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagination other = (Pagination) obj;
		if (currentPage != other.currentPage)
			return false;
		if (pageSize != other.pageSize)
			return false;
		return true;
	}
}