package com.siwei.mytomcat.dymatic.pro;

import java.io.IOException;

public class AAServlet implements Servlet {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void service(Request request, Response response) {
		try {
			response.getOps().write("我是AA Servlet".getBytes());
			// 这里不确定是否要刷新
			response.getOps().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
