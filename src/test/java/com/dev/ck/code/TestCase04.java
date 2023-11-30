package com.dev.ck.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dev.ck.code.vo.Apple;
import com.dev.ck.code.vo.Color;

/**
 * 가능한 모든 속성으로 필터링 
 * 색이 GREEN이거나 무게가 100 이상
 */
public class TestCase04 {
	public static final Color GREEN = Color.GREEN;
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;

	public static List<Apple> filterGreenApples(List<Apple> inventory, Color color, int weight, boolean flag){
		List<Apple> result = new ArrayList<Apple>();
		for(Apple apple: inventory) {
			if((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
				result.add(apple);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(
			 new Apple(Color.GREEN, 10)
			,new Apple(Color.RED, 50)
			,new Apple(Color.BLUE, 100)
			,new Apple(Color.YELLOW, 150)
			,new Apple(Color.WHITE, 200)
			,new Apple(Color.BLACK, 100)
			,new Apple(Color.GREEN, 100)
			,new Apple(Color.GREEN, 150)
		);
		
		List<Apple> result = filterGreenApples(inventory, GREEN, 0, true);
		List<Apple> result2 = filterGreenApples(inventory, null, 150, false);
		System.out.println(result.toString());
		System.out.println(result2.toString());
	}
}