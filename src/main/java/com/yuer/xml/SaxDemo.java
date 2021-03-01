package com.yuer.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 使用SAX解析xml
 * 优点和缺点：
 * 		SAX处理的优点非常类似于流媒体的优点。分析能够立即开始，而不是等待所有的数据被处理。
 * 		而且，由于应用程序只是在读取数据时检查数据，因此不需要将数据存储在内存中。这对于大型文档来说是个巨大的优点
 * 		
 * 	
 * 		缺点：不能修改文件，且不支持文件的随意读取
 * 		
 * 
 * @author Yuer
 *
 */
public class SaxDemo {

	public static void main(String[] args) throws Exception {
		// 1.或去SAXParserFactory实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2.获取SAXparser实例
		SAXParser saxParser = factory.newSAXParser();
		// 创建Handel对象
		SAXDemoHandel handel = new SAXDemoHandel();
		saxParser.parse("src/main/resources/demo.xml", handel);
	}
}

class SAXDemoHandel extends DefaultHandler {
	// 遍历xml文件开始标签
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("sax解析开始");
	}

	// 遍历xml文件结束标签
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("sax解析结束");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("student")) {
			System.out.println("============开始遍历student=============");
			// System.out.println(attributes.getValue("rollno"));
		} else if (!qName.equals("student") && !qName.equals("class")) {
			System.out.print("节点名称:" + qName + "----");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equals("student")) {
			System.out.println(qName + "遍历结束");
			System.out.println("============结束遍历student=============");
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String value = new String(ch, start, length).trim();
		if (!value.equals("")) {
			System.out.println(value);
		}
	}
}