package org.net5ijy.commons.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {

	private Properties prop = new Properties();

	public PropertiesHolder(String propertiesName) {
		try {
			prop.load(IOUtils.resourceInputStream(propertiesName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
