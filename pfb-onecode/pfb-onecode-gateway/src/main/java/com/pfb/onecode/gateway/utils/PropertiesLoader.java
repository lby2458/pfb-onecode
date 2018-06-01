package com.pfb.onecode.gateway.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class PropertiesLoader {
	
	private Properties properties = null;
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	public PropertiesLoader() {
		loadResource(new String[]{"app.properties"});
	}


	public PropertiesLoader(String... properties) {
		loadResource(properties);
	}
	
	public String getProperty(String key){
		return null == properties.getProperty(key)? "" : properties.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue){
		return null == properties.getProperty(key)? defaultValue : properties.getProperty(key);
	}
	
	private void loadResource(String... proper) {
		properties = new Properties();
		Resource resource = null;
		InputStream inputStream = null;
		for (String name : proper) {
			try {
				resource = resourceLoader.getResource(name);
				inputStream = resource.getInputStream();
				properties.load(inputStream);
//				properties.load(new FileInputStream(name));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(null != inputStream){
					try {
						inputStream.close();
						inputStream = null;
					} catch (IOException e) {
					}
				}
			}
				
		}
	}
	
}
