package com.siwei.mytomcat.yc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
*@author 苏雨晨
*@version 创建时间：2020年2月14日下午2:26:13
**/
public class Response {
	private OutputStream os = null;
	private String basePath = null; //基址路径
	private String defaultPage = null;
	
	public Response(OutputStream os) {
		this.os = os;
		basePath = ReadConfig.getInstance().getProperty("path");
		defaultPage = ReadConfig.getInstance().getProperty("default");
	}

	/**
	 * 重定向到指定资源的方法
	 * @param url
	 */
	public void sendRedirect(String url) {
		if(url == null || "".equals(url)) { //如果没有指定具体的资源，则直接返回
			return;
		}
		
		//根据用户的请求资源地址，到本地路径中查找是否有这个资源
		String source = basePath + url.replace("/", "\\");
		File fl = new File(source);
		
		if(!fl.exists()) { //资源不存在
			//报错404
			error404(url);
			return;
		}
		if(fl.isFile()) { //说明是一个资源文件
			byte[] bt = this.readFile(fl);
			String ext = url.substring(url.lastIndexOf(".") + 1); //截取请求资源的后缀
			this.send200(bt, ParseXml.getContentType(ext.toLowerCase()));
		} else if(fl.isDirectory()) { //如果是一个目录
			if(!source.endsWith("\\")) {
				send302(url);
				return;
			} 
			
			source += defaultPage;
			
			fl = new File(source);
			if(!fl.exists()) { //资源不存在
				//报错404
				error404(url);
				return;
			}
			
			byte[] bt = this.readFile(fl);
			String ext = url.substring(url.lastIndexOf(".") + 1); //截取请求资源的后缀
			this.send200(bt, ParseXml.getContentType(ext.toLowerCase()));
		}
	}

	/**
	 * 处理200的响应
	 * @param bt
	 * @param contentType
	 */
	private void send200(byte[] bt, String contentType) {
		try {
			String protocol = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nContent-Length: " + bt.length + "\r\n\r\n";
			os.write(protocol.getBytes());
			os.write(bt);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 处理302的响应
	 * @param bt
	 * @param contentType
	 */
	private void send302(String url) {
		try {
			String protocol = "HTTP/1.1 302 Moved Temprarily\r\nContent-Type: text/html\r\nLocation: " + url + "/\r\n\r\n";
			os.write(protocol.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 处理404的响应
	 * @param bt
	 * @param contentType
	 */
	private void error404(String url) {
		try {
			String errorInfo = "<h1>HTTP Status 404 - " + url + "<h1/>";
			String protocol = "HTTP/1.1 200 File Not Fount\r\nContent-Type: text/html\r\nContent-Length: " + errorInfo.getBytes().length + "\r\n\r\n";
			os.write(protocol.getBytes());
			os.write(errorInfo.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 将指定得到文件读取到字节数组中返回
	 * @param fl
	 * @return
	 */
	private byte[] readFile(File fl) {
		try(FileInputStream fis = new FileInputStream(fl)) {
			byte[] bt = new byte[fis.available()];
			fis.read(bt);
			return bt;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
