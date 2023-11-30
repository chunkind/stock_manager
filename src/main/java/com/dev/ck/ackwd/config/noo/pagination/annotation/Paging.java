/*
 * Copyright (c) 2010-2011 NOO. All Rights Reserved.
 * [Id:Pagation.java  2011-11-23 下午8:37 poplar.yfyang ]
 */
package com.dev.ck.ackwd.config.noo.pagination.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 페이징 노트, 페이징 처리 기관 에 대한 필요성 이 그것을 설정할 때 
 * 단지 클래스 선언 에 설정 지원
 */
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Paging {
	/**
	 * 페이지 매김 객체 속성 이름 ,
	 * 매개 변수 전달 개체를 설정해야 할 필요가있을 때 즉{@link org.noo.pagination.model.PaginationSupport}  속성이름
	 *
	 * @return 매김 오브젝트 속성 이름 ,기본 페이지
	 */
	String field() default "paging";
}