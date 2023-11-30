package com.dev.ck.code.pred.impl;

import com.dev.ck.code.TestCase05;
import com.dev.ck.code.pred.ApplePredicate;
import com.dev.ck.code.vo.Apple;
import com.dev.ck.code.vo.Color;

public class AppleGreenColorPredicate implements ApplePredicate{
	@Override
	public boolean test(Apple apple) {
		return TestCase05.GREEN.equals(apple.getColor());
	}
}