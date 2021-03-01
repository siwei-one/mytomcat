package com.yuer.mytomcat.dymatic.pro.plus;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取HTTP的请求头所有信息并截取URL地址返回
 * 
 * 这次还要封装请求方式和请求体
 * 
 * @author Yuer
 *
 */
public class Request {

	// 代表请求的文件路径
	private  String url = "";
	
	// 请求方式 默认为get
	private String method = "GET";
	
	// 提取get请求方式的信息和post请求方式中请求体的信息
	// get请求的信息格式为x=uuu&y=444(地址栏为localhost:8080/index.html?x=uuu&y=444)
	// post请求的信息格式为username=we&password=we(信息来源于表单提交的请求)
	private String data = "";
	
	private Map<String,String> data_map = new HashMap<String,String>();
	
	private InputStream is = null;
	
	public Request() {
		
	}
	
	public Request(InputStream is) {
		this.is = is;
		try {
			parse();//解析请求
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 获取请求部分中的文件路径并赋值给url
	 * 
	 * @param is
	 * @throws IOException
	 */
	public  void parse() throws IOException {
		
		StringBuffer content = new StringBuffer();

		
		byte[] buffer = new byte[2048];

		int i = -1;

		i = is.read(buffer);

		for (int j = 0; j < i; j++) {
			content.append((char) buffer[j]);
		}

		// 做测试
		System.out.println(content.toString());

		// 接下来进行请求部分的解析
		parseData(content.toString());

	}
	
	
	/**
	 * 将请求部分中的请求行的资源路径解析出来
	 * 还要请求方式，信息等解析出来
	 * 先完成get的登录
	 * 
	 * @param temp
	 */
	private  void parseData(String temp) {
		String str = "";
		
		method = temp.substring(0,temp.indexOf(" "));
		
		str = temp.substring(temp.indexOf(" ") + 2);
		url = str.substring(0, str.indexOf(" "));
		
		if (method != null && !"".equals(method)) {
			if ("GET".equals(method)) {
				if (url.indexOf("?") != -1) { // 先查看url中是否有？
					data = url.substring(url.indexOf("?") + 1);
					url = url.substring(0,url.indexOf("?"));
					
					// 将data中数据转为map
					while(true) {
						String tmp = data.substring(0,data.indexOf("="));
						if (data.indexOf("&") != -1) { // 后面还有数据
							String tmp_1 = data.substring(data.indexOf("=") + 1, data.indexOf("&"));
							data_map.put(tmp, tmp_1);
							
							data = data.substring(data.indexOf("&") + 1);
						} else {
							String tmp_1 = data.substring(data.indexOf("=") + 1);
							data_map.put(tmp, tmp_1);
							return;
						}
					}
				}
				
			} else { // post
				
			}
				
		} 
		

		// 做测试
		System.out.println(url + "-----");
		System.out.println(method + "-----");
		System.out.println(data + "-----");
	}

	public Map<String, String> getData_map() {
		return data_map;
	}

	public void setData_map(Map<String, String> data_map) {
		this.data_map = data_map;
	}

	
}
