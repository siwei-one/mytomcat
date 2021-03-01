package com.siwei.mytomcat.yc;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 会话类，处理请求
*@author 苏雨晨
*@version 创建时间：2020年2月13日下午4:54:41
**/
public class HttpSession implements Runnable {
	private Socket sk = null;
	private Request request = null;
	private Response response = null;
	
	public HttpSession(Socket sk) {
		this.sk = sk;
	}

	@Override
	public void run() {
		try(InputStream is = sk.getInputStream()) {
			request = new Request(is); //处理请求
			
			String url = request.getUrl(); //请求路径
			response = new Response(sk.getOutputStream()); //响应
			response.sendRedirect(url); //重定向到指定的资源
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

