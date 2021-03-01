package com.siwei.mytomcat.dymatic.pro.plus;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件
 * @author Yuer
 *
 */
public class ReadConfig extends Properties{
	private static final long serialVersionUID = 4896442440078075125L;
	private static ReadConfig instance = new ReadConfig();
	
	private ReadConfig() {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");) {
			this.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ReadConfig getInstance() {
		return instance;
	}
}

