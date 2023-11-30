package com.dev.ck.code;

import java.util.ArrayList;
import java.util.List;

import com.dev.ck.code.vo.Apple;
import com.dev.ck.code.vo.Color;

public class TestCase02 {
	public static final Color GREEN = Color.GREEN;
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	
	public static List<Apple> filterGreenApples(List<Apple> inventory, Color color){
		List<Apple> result = new ArrayList<Apple>();
		for(Apple apple: inventory) {
			if(color.equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<Apple>();
		inventory.add(new Apple(Color.GREEN));
		inventory.add(new Apple(Color.RED));
		inventory.add(new Apple(Color.BLUE));
		inventory.add(new Apple(Color.YELLOW));
		inventory.add(new Apple(Color.WHITE));
		inventory.add(new Apple(Color.BLACK));
		
		List<Apple> result = filterGreenApples(inventory, GREEN);
		System.out.println(result.toString());
	}
}