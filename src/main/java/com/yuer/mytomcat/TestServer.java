package com.yuer.mytomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟服务端
 * 这个版本只能发送静态文件
 * 
 * @author Yuer
 *
 */
public class TestServer {

	// 代表静态资源路径
	private static String WEB_ROOT = System.getProperty("user.dir") + "\\" + "src\\main\\webapp";

	// 代表请求的文件路径
	private static String url = "";

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		OutputStream ops = null;
		InputStream is = null;

		try {
			// 第一步，创建ServerSocket对象，监听本机的8080端口号
			serverSocket = new ServerSocket(8080);

			while (true) {
				// 等待来自客户端的请求和获取客户端对象对应的Socket对象
				socket = serverSocket.accept();

				// 通过获取到的Socket对象获取输出流对象和输入流对象
				ops = socket.getOutputStream();
				is = socket.getInputStream();

				// 获取请求体中的文件路径并赋值给url
				parse(is);

				// 发送静态资源给客户端
				sendStatic(ops);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (null != ops) {
				ops.close();
				ops = null;

			}
			// 有可能是这里的输入没有关闭导致请求一直出问题
			if (null != is) {
				is.close();
				is = null;
			}
			if (null != socket) {
				socket.close();
				socket = null;
			}
		}

	}

	/**
	 * 获取请求部分中的文件路径并赋值给url
	 * 
	 * @param is
	 * @throws IOException
	 */
	private static void parse(InputStream is) throws IOException {
		// 这里读取完的时间是在游览器停止后会导致报错
//		int i = 0;
//		StringBuffer temp = new StringBuffer();
//
//		while ((i = is.read()) != -1) {
//			temp.append((char) i);
//		}
		StringBuffer content = new StringBuffer();

		byte[] buffer = new byte[2048];

		int i = -1;

		i = is.read(buffer);

		for (int j = 0; j < i; j++) {
			content.append((char) buffer[j]);
		}

		System.out.println(content.toString());

		// 接下来进行请求部分的解析
		parseUrl(content.toString());

	}

	/**
	 * 将请求部分中的请求行的资源路径解析出来
	 * 
	 * @param temp
	 */
	private static void parseUrl(String temp) {
		String str = "";
		str = temp.substring(temp.indexOf(" ") + 1);
		url = str.substring(0, str.indexOf(" "));

		System.out.println(url);
	}

	/**
	 * 发送静态资源给客户端
	 * 
	 * @param ops
	 * @throws IOException
	 */
	private static void sendStatic(OutputStream ops) {
		FileInputStream fis = null;
		File file = new File(WEB_ROOT + url);
		try {

			if (file.exists()) {
				// 响应行
				ops.write("HTTP/1.1 200 OK\n".getBytes());
				// 响应头
				ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
				ops.write("Server:Yuer\n".getBytes());
				// 响应体上的换行
				ops.write("\n".getBytes());

				int i = 0;
				StringBuffer buff = new StringBuffer();
				fis = new FileInputStream(file);

				while ((i = fis.read()) != -1) {
					buff.append((char) i);
				}

				// 响应体
				ops.write(buff.toString().getBytes());

			} else {
				// 响应行
				ops.write("HTTP/1.1 404 NotFound\n".getBytes());
				// 响应头
				ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
				ops.write("Server:Yuer\n".getBytes());
				// 响应体上的换行
				ops.write("\n".getBytes());

				ops.write("file not found".getBytes());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
