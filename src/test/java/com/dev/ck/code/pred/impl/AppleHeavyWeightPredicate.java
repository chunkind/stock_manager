package com.dev.ck.code.pred.impl;

import com.dev.ck.code.pred.ApplePredicate;
import com.dev.ck.code.vo.Apple;

public class AppleHeavyWeightPredicate implements ApplePredicate{
	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() > 150;
	}
}