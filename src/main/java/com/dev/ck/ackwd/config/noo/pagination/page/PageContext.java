package com.dev.ck.ackwd.config.noo.pagination.page;

/**
 * 페이징 컨텍스트 매개 변수 .
 */
public class PageContext extends Pagination {
	/**
	 *  직렬화  ID
	 */
	private static final long serialVersionUID = -3294902812084550562L;

	/**
	 * 페이징 파라미터 콘텍스트
	 */
	private static final ThreadLocal<PageContext> PAGE_CONTEXT_THREAD_LOCAL = new ThreadLocal<PageContext>();

	/**
	 * 페이징 파라미터 의현재 컨텍스트 를 위젯
	 *
	 * @return 페이징 파라미터 콘텍스트
	 */
	public static PageContext getPageContext() {
		PageContext ci = PAGE_CONTEXT_THREAD_LOCAL.get();
		if (ci == null) {
			ci = new PageContext();
			PAGE_CONTEXT_THREAD_LOCAL.set(ci);
		}
		return ci;
	}

	/**
	 * 정리 페이징 컨텍스트 매개 변수
	 */
	public static void removeContext() {
		PAGE_CONTEXT_THREAD_LOCAL.remove();
	}
}