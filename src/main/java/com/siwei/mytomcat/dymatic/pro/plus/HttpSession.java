package com.yuer.mytomcat.dymatic.pro.plus;

import java.net.Socket;

/**
 * 用来作为线程封装每个游览器访问的动作
 * @author Yuer
 *
 */
public class HttpSession  implements Runnable{
	
	private Socket socket = null;
	
	
	public HttpSession(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Request request;
		Response response;
		try {
			request = new Request(socket.getInputStream());
			response = new Response(request, socket.getOutputStream());
			response.sendRedirect();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

}
