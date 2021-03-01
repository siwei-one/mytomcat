package com.yuer.mytomcat.yc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXml {
	 private static Map<String, String> typeMap = new HashMap<>(); 
	 public ParseXml(){
		 try {
			init();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	 }
	 
	
	@SuppressWarnings("unchecked")
	private void init() throws DocumentException {
		SAXReader read = new SAXReader();
		Document doc = null;
		doc = read.read(this.getClass().getClassLoader().getResourceAsStream("web.xml"));
		
		List<Element> mimes = doc.selectNodes("//mime-mapping"); 
		for(Element el : mimes) {
			typeMap.put(el.selectSingleNode("extension").getText().trim(), el.selectSingleNode("mime-type").getText().trim());
		}
	}
	
	public static String getContentType(String extension) {
		return typeMap.getOrDefault(extension, "text/html");
	}
}
