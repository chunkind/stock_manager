package com.dev.ck.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.dev.ck.code.vo.Apple;
import com.dev.ck.code.vo.Color;

/**
 * 익명클레스
 */
public class TestCase07 {
	public static final Color GREEN = Color.GREEN;
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	
	public static <T> List<T> filter(List<T> list, Predicate<T> p){
		List<T> result = new ArrayList<T>();
		for(T e: list) {
			if(p.test(e)) {
				result.add(e);
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
		List<Integer> numbers = Arrays.asList(
			1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
		);
		
		List<Apple> result = filter(inventory, (Apple apple) -> apple.getWeight() > 150);
		List<Apple> result2 = filter(inventory, (Apple apple) -> GREEN.equals(apple.getColor()));
		
		List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
		
		System.out.println(result.toString());
		System.out.println(result2.toString());
		System.out.println(evenNumbers.toString());
	}
}