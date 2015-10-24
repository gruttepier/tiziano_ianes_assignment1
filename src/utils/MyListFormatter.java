package utils;

import java.util.ArrayList;

/**
 * Contains methods for personalized formatting and printing of listss
 * @author Tiziano Ianes
 *
 */
public class MyListFormatter {
	/**
	 * Given a List of Strings, it prints each element on the same line, with a
	 * comma separating each element and an 'or' before the last one
	 * 
	 * @param toPrint The list to be printed
	 */
	public void printListCommaOr(ArrayList<String> toPrint) {
		int i = 0;
		for (String o : toPrint) {
			i++;
			if (i > 1 && i < toPrint.size())
				System.out.print(", ");
			if (i == toPrint.size())
				System.out.print(" or ");
			System.out.print(o);
		}
	}
}
