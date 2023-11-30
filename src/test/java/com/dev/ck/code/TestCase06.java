package com.dev.ck.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dev.ck.code.pred.ApplePredicate;
import com.dev.ck.code.vo.Apple;
import com.dev.ck.code.vo.Color;

/**
 * 익명클레스
 */
public class TestCase06 {
	public static final Color GREEN = Color.GREEN;
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	
	public static List<Apple> filterGreenApples(List<Apple> inventory, ApplePredicate p){
		List<Apple> result = new ArrayList<Apple>();
		for(Apple apple: inventory) {
			if(p.test(apple)) {
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
		
		//익명 클레스 사용
		/*List<Apple> result = filterGreenApples(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return apple.getWeight() > 150;
			}
		});
		List<Apple> result2 = filterGreenApples(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return GREEN.equals(apple.getColor());
			}
		});*/
		
		//람다식 사용
		List<Apple> result = filterGreenApples(inventory, (Apple apple) -> apple.getWeight() > 150);
		List<Apple> result2 = filterGreenApples(inventory, (Apple apple) -> GREEN.equals(apple.getColor()));
		
		System.out.println(result.toString());
		System.out.println(result2.toString());
	}
}