package com.biz.score.config;

public class stLines {
	public static String dLine = "";
	public static String sLine = "";
	static {

		for (int i = 0; i < 100; i++) {
			dLine += "=";
			sLine += "-";
		}
	}

}
