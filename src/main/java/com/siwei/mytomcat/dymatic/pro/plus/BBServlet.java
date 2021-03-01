package com.yuer.mytomcat.dymatic.pro.plus;

import java.io.IOException;

public class BBServlet implements Servlet {

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void service(Request request, Response response) {
		try {
			response.getOps().write("我是BB Servlet".getBytes());
			// 这里不确定是否要刷新
			response.getOps().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
