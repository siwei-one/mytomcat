package com.yuer.mytomcat.dymatic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AAServlet implements Servlet {

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void service(InputStream is, OutputStream ops) {
		try {
			ops.write("我是AA Servlet".getBytes());
			ops.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
