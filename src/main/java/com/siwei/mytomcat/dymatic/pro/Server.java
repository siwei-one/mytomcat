package com.yuer.mytomcat.dymatic.pro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yuer.mytomcat.util.CloseAll;

/**
 * 模拟服务端
 * 这个版本可以发送静态和动态，并将一些操作封装到request和response
 * 
 * @author Yuer
 *
 */
public class Server {


	public static void main(String[] args) throws IOException {
		new Server().start();
	}
	
	
	private void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			Integer port = Integer.parseInt(ReadConfig.getInstance().getProperty("port"));
			// 第一步，创建ServerSocket对象
			serverSocket = new ServerSocket(port);
			
			//创建一个线程池，大小为20
			ExecutorService service = Executors.newFixedThreadPool(20);

			while (true) {
				// 等待来自客户端的请求和获取客户端对象对应的Socket对象
				socket = serverSocket.accept();

				service.execute(new HttpSession(socket));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			CloseAll.close(socket);
		}

	}

	

	

	
	
	

}
