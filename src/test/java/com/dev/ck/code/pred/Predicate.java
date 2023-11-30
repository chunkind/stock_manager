package com.dev.ck.code.pred;

import org.apache.poi.ss.formula.functions.T;

public interface Predicate {
	boolean test(T t);
}
