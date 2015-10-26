package com.ramonli.sandbox.springplaceholder;

import java.util.Properties;

import org.springframework.util.PropertyPlaceholderHelper;

public class PlaceholderParser {

	public static void main(String[] args) {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", null, false);
		// helper.
		Properties prop = new Properties();
		prop.setProperty("name", "ramon");
		prop.setProperty("age", "37");
		prop.setProperty("nickname", "gulu");
		String value = helper.replacePlaceholders("'${name}' is ${age}", prop);
		System.out.println(value);
	}

}
