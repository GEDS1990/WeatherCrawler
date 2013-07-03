package com.baca.test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");
		String b = a.get(1);
		a.remove(1);
		System.out.println(a.toString());
		System.out.println(a.toString());
		System.out.println(b);

	}

}
