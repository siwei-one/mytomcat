package com.yuer.mytomcat.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  模拟游览器向服务端发请求
 *  可能还需要对传来的字符进行编码处理
 * @author Yuer
 *
 */
public class TestClient {
	
	
	public static void main(String[] args) {
		Socket socket =  null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
			// 第一步，先建立socket对象，连接至itcast.cn的80端口
			socket = new Socket("www.itcast.cn",80);
			
			// 第二步，获取输入流
			in = socket.getInputStream();
			
			
			// 第三步，获取输出流
			out = socket.getOutputStream();
			
			// 第四步，将http协议的请求部分发送到客户端   /subject/about/index.html
			// 先发请求行，按顺序来是先请求方式，再资源路径 再协议和版本
			out.write("GET /subject/about/index.html HTTP/1.1\n".getBytes());
			// 再就是请求头 这里只是模仿，只发一个关键的HOST
			out.write("HOST:www.itcast.cn\n".getBytes());
			// 再就是请求体，这里没有数据，
			out.write("\n".getBytes());
			
			// 第五步，读取来自服务端的响应数据打印到控制台
			int i = 0;
			
			while ((i = in.read()) != -1) {
				System.out.print((char)i); // 这里为啥转为char？
//				System.out.print(i); // 这里为啥转为char？
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			// 最后，释放资源
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket = null;
			}
			
		}
		
		
		
		
	}

}
