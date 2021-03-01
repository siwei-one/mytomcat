package com.yuer.mytomcat.yc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Request{
	private InputStream is = null;
	private String method = null; //请求方式
	private String url = null; //请求资源的地址
	private String protocolVersion; //请求协议的版本
	private Map<String, String[]> params = new HashMap<>(); //请求地址的参数
	
	public Request(InputStream is) {
		this.is = is;
		
		try {
			parse();//解析请求
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void parse() throws IOException {
		//请求头中一行就是报头信息，那么可以一行一行读取
		BufferedReader read = new BufferedReader(new InputStreamReader(is));
		String line = null;
		int flag = 0;
		while((line = read.readLine()) != null) {
			if("".equals(line)) { //说明读到了空行
				break;
			}
			if(flag == 0) { //说明是起始行
				String[] info = line.split(" ");
				method = info[0];
				this.protocolVersion = info[2];
				System.out.println(Arrays.toString(info));
				
				//判断请求方式
				if("GET".equals(method)) {
					doGet(info[1]);
				} else if("POST".equals(method)) {
					
				}
			} else{
				//System.out.println(line);
			}
			flag ++;
		}
	}

	/**
	 * GET 请求的处理方式
	 * @param str
	 */
	private void doGet(String str) {
		if(!str.contains("?")) { //说明请求路径没有带参数
			this.url = str;
			return;
		}
		
		//处理请求参数
		this.url = str.substring(0, str.indexOf("?"));
	}

	public InputStream getIs() {
		return is;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public Map<String, String[]> getParams() {
		return params;
	}
}
