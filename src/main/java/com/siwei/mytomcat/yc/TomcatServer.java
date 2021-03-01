package com.yuer.mytomcat.yc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 元辰的后面借鉴
 * @author Yuer
 *
 */
public class TomcatServer {
	public static void main(String[] args) {
		try {
			new TomcatServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void start() throws IOException {
		//从配置文件获取端口
		int port = Integer.parseInt(ReadConfig.getInstance().getProperty("port"));
		
		//启动服务器
		ServerSocket ssk = new ServerSocket(port);
		
		new ParseXml();
		
		System.out.println("服务器启动成功，占用端口" + port);
		
		//创建一个线程池，大小为20
		ExecutorService service = Executors.newFixedThreadPool(20);
		
		//循环监客户端的请求
		Socket sk = null;
		
		while(true) {
			sk = ssk.accept();
			
			//创建一个会话来处理这个请求
			service.submit(new HttpSession(sk)); //将这个会话放到线程池中
		}
	}
}
