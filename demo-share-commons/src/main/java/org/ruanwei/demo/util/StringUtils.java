package org.ruanwei.demo.util;


public class StringUtils {
	public static void main(String[] args) {
		
	}

	public static boolean isDigit(String input) {
		if (input != null) {
			return input.matches("[+-]?\\d+");
		}
		return false;
	}
}
