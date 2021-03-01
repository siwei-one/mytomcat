package com.yuer.mytomcat.dymatic.pro;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 这里使用jsoup解析xml
 * 
 * @author Yuer
 *
 */
public class ParseXml {

	// 还要将配置文件中的信息读取到map中
	private static Map<String, String> map = new HashMap<String, String>();
	
	// 这里将配置文件中的欢迎页读取出来
	private static String welcome = "";

	
	// 使用之前就初始化好了
	static {
		Document doc;
		Elements as;
		
		try {
			doc = Jsoup.parse(new File("src/main/webapp/my.xml"), "utf-8");
			
			// 先将欢迎页初始化
			Element ele = doc.getElementsByTag("welcome-file").first();
			welcome = ele.text();
			
			// 再将请求与类路径进行映射
			// 获取Elements的方式有很多种：根据id,class，tag，属性(好像是看是否存在这个属性)都可
			as = doc.getElementsByTag("servlet-mapping");
			for (Element e : as) {
				// 这个是请求路径
				Element a = e.child(2);
				Element b = e.child(1);
				map.put(a.text(), b.text());

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
	}

	public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		ParseXml.map = map;
	}

	public static String getWelcome() {
		return welcome;
	}

	public static void setWelcome(String welcome) {
		ParseXml.welcome = welcome;
	}

}
