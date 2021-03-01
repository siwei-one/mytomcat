package com.yuer.mytomcat.dymatic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BBServlet implements Servlet {

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void service(InputStream is, OutputStream ops) {
		try {
			ops.write("我是BB Servlet".getBytes());
			// 这里不确定是否要刷新
			ops.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
