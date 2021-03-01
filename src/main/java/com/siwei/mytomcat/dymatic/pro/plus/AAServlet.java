package com.yuer.mytomcat.dymatic.pro.plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import com.yuer.mytomcat.util.FinalString;

public class AAServlet implements Servlet {

	@Override
	public void init() {

	}

	@Override
	public void service(Request request, Response response) throws Exception {
		if ("GET".equals(request.getMethod())) {
			doGet(request,response);
		} else {
			doPost(request,response);
		}


	}


	@Override
	public void doGet(Request req, Response rep) throws Exception {
		Map<String,String> map = req.getData_map();
		OutputStream ops = rep.getOps();
		FileInputStream fis = null;
		// 如果带有请求数据
		if (!map.isEmpty()) {
			String userName = "";
			String password = "";
			
			userName = map.get("userName".toLowerCase());
			password = map.get("password".toLowerCase());
			
			// 响应行
			ops.write("HTTP/1.1 200 OK\n".getBytes());
			// 响应头
			ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
			ops.write("Server:Yuer\n".getBytes());
			// 响应体上的换行
			ops.write("\n".getBytes());
			
			// 模拟从数据库中取数据进行判断
			if ("lsy".equals(userName) && "404221".equals(password)) { // 密码正确，登录成功
				
				int i = 0;
				StringBuffer buff = new StringBuffer();
				fis = new FileInputStream(new File(FinalString.WEB_ROOT, "index.html"));

				while ((i = fis.read()) != -1) {
					buff.append((char) i);
				}

				// 响应体
				ops.write(buff.toString().getBytes());
				ops.flush();
				
			} else { // 密码错误，显示密码错误
				ops.write("Error,GoBack".getBytes());
				ops.flush();
			}
			
		}
		
		
		
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		
		rep.getOps().write("我是AA Servlet".getBytes());
		// 这里不确定是否要刷新
		rep.getOps().flush();
		
	}
	
	@Override
	public void destroy() {
		
	}

}
