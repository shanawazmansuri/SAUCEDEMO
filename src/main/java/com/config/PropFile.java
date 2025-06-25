package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropFile {

	public static Properties prop;

	public PropFile() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					"./src/main/java/com/config/Prop.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}