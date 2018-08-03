package nl.zoethout.grot.util;

import java.io.IOException;
import java.io.Serializable;

public class TextUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String addPreZeros(int number, int size) {
		String str = "" + number;
		while (str.length() < size) {
			str = "0" + str;
		}
		return str;
	}

	public static String toProperCase(String str){
		String changeStr = "";
		try {
			changeStr = makeProperCase(str);
		} catch (IOException e) {
			changeStr = "";
		}
		return changeStr;
	}
	
	private static String makeProperCase(String theString)
			throws java.io.IOException {

		java.io.StringReader in = new java.io.StringReader(
				theString.toLowerCase());
		boolean precededBySpace = true;
		StringBuffer properCase = new StringBuffer();
		while (true) {
			int i = in.read();
			if (i == -1)
				break;
			char c = (char) i;
			if (c == ' ' || c == '-' || c == '"' || c == '(' || c == '.'
					|| c == '/' || c == '\\' || c == ',') {
				properCase.append(c);
				precededBySpace = true;
			} else {
				if (precededBySpace) {
					properCase.append(Character.toUpperCase(c));
				} else {
					properCase.append(c);
				}
				precededBySpace = false;
			}
		}

		return properCase.toString();

	}

}
