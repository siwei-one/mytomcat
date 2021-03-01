package com.yuer.xml;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * jsoup主要用来解析HTML，但是也可用于解析xml主要是很简便 也可用于爬虫
 * 
 * @author Yuer
 *
 */
public class TestJsoup {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		parseXml();
	}

	public static void parseXml() throws IOException {
//		int i = 0;
//		StringBuffer buff = new StringBuffer();
//		FileInputStream fis = new FileInputStream(new File("src/main/resources/demo.xml"));
//
//		while ((i = fis.read()) != -1) {
//			buff.append((char) i);
//		}

		// 获取Document的方式有三种，第一种字符串，第二种文件(先封装为File，还可以指定编码格式如utf-8)，第三种网址(需要先封装为URL)
//		Document doc = Jsoup.parse(buff.toString());
		Document doc = Jsoup.parse(new File("src/main/resources/demo.xml"),"utf-8");

//		System.out.println(doc); // 这个会将整个xml打印和直接打印buff的效果一致

		// 获取Elements的方式有很多种：根据id,class，tag，属性(好像是看是否存在这个属性)都可
		Elements as = doc.getElementsByTag("firstname");
		for (Element e : as) {
			// 标签的文本值
			System.out.println(e.text());

		}
		as = doc.getElementsByTag("student");
		for (Element e : as) {
			// 标签的对应属性的值 这里是rollno属性的值
			System.out.println(e.attr("rollno"));

			// 获取该标签的所有值 打印的值类似于这种格式：rollno="593" e="ee"
			System.out.println(e.attributes());

			// 还有一些在html中常用的获取id等等不写了这里主要展示分析xml的方法
			// 还有如jquery的选择器的这里省略，在爬虫那里仔细介绍

		}

		// jsoup和DOM都类似，都采用的建立文档树，可以接受增删改属性,这里好像失效了
		// 这里对元素的修改好像确实修改了，但是没有同步到xml中
		Element e = doc.getElementsByTag("firstname").last();

		System.out.println(doc);
		// 这个可以修改一个Element，也可以直接修改多个如Elements，但是不知道为什么这里失效了
//		e.attr("class", "class1");
		// 增加移除属性 这里好像只支持class
		// 修改文本值 不传值即是获取，传值即是修改
		e.text(" Hello JSoup");
		System.out.println(doc);

//		e.appendText(" Hello JSoup");
	}

	

}
