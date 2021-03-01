package com.siwei.mytomcat.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟服务端
 * @author Yuer
 *
 */
public class TestServer {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		OutputStream ops = null;
		
		try {
			// 第一步，创建ServerSocket对象，监听本机的8080端口号
			serverSocket = new ServerSocket(8080);
			
			while (true) {
				// 等待来自客户端的请求和获取客户端对象对应的Socket对象
				socket = serverSocket.accept();
				
				// 通过获取到的Socket对象获取输出流对象
				ops = socket.getOutputStream();
				
				// 通过获取到的输出流对象将HTTP协议的响应部分发送到客户端
				// 响应行
				ops.write("HTTP/1.1 200 OK\n".getBytes());
				// 响应头
				ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
				ops.write("Server:Yuer\n".getBytes());
				// 响应体上的换行(两个换行？)
				ops.write("\n\n".getBytes());
				// 响应体
				StringBuffer buff = new StringBuffer();
				buff.append("<html>");
				buff.append("<head><title>myTomcat</title></head>");
				buff.append("<body>");
				buff.append("<h1> I am header 1</h1>");
				buff.append("<a href='http://www.baidu.com'>百度</a>");
				buff.append("</body>");
				buff.append("</html>");
				ops.write(buff.toString().getBytes());
			}
			
			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (null != ops) {
				try {
					ops.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
