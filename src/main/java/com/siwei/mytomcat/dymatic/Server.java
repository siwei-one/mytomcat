package com.siwei.mytomcat.dymatic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 模拟服务端
 * 这个版本可以发送静态和动态，下个版本进行一些封装
 * 
 * @author Yuer
 *
 */
@SuppressWarnings("rawtypes")
public class Server {

	// 代表静态资源路径
	private static String WEB_ROOT = System.getProperty("user.dir") + "\\" + "src\\main\\webapp";

	// 代表请求的文件路径
	private static String url = "";

	// 还要将配置文件中的信息读取到map中
	private static Map<String, String> map = new HashMap<String, String>();

	// 服务器启动之前初始化配置信息到map中
	static {
		// 使用javaAPI读取配置文件信息
		Properties prop = new Properties();

		try {
			// 先加载
			prop.load(new FileInputStream(WEB_ROOT + "\\conf.properties"));

			// 将配置文件中的数据读取到map中、
			Set set = prop.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = prop.getProperty(key);
				map.put(key, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

				// 先判断是否是/或没有 则直接访问web.xml中的欢迎页
				// 这里还没做。。。。
				

				// 判断本次请求的是静态页面还是servlet程序
				if (null != url) {
					// 看是否存在. 即是否访问静态
					if (url.indexOf(".") != -1) {
						// 发送静态资源给客户端
						sendStatic(ops);
					} else {
						// 发送动态的
						sendDynamic(is, ops);
						
					}
				}
				
				// 这里不知道为什么要关闭且不能变为null
				// 关闭会立马释放资源，设置为null，只是说明我不再指向这个对象
				socket.close();
//				socket = null;

				

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

		// 做测试
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
		// 这里没考虑啥都没有的情况，即localhost:8080 或local host：8080/(游览器自动省略/)
		str = temp.substring(temp.indexOf(" ") + 2);
		url = str.substring(0, str.indexOf(" "));

		// 做测试
		System.out.println(url);
		System.out.println(url + "-----");
	}
	
	/**
	 * 发送动态资源
	 * @param ops
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private static void sendDynamic(InputStream is, OutputStream ops) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		// 接下来判断这次请求的url是否存在于map中
		if (map.containsKey(url)) {
			
			// 先发送响应行和响应头
			// 响应行
			ops.write("HTTP/1.1 200 OK\n".getBytes());
			// 响应头
			ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
			ops.write("Server:Yuer\n".getBytes());
			// 响应体上的换行
			ops.write("\n".getBytes());
			
			// 如果包含指定的key,获取到map中key对应的value部分
			String value = map.get(url);
			
			// 通过反射调用对应的Servlet 这里后面通过web.xml或者注解
			Class clazz = Class.forName(value);
			Servlet servlet = (Servlet) clazz.newInstance();
		
			// 执行init方法
			servlet.init();
			// 执行service
			servlet.service(is, ops);
			// 执行销毁 销毁不知道是在什么时候
//			servlet.destroy();
			
		} else {
			// 响应行
			ops.write("HTTP/1.1 404 NotFound\n".getBytes());
			// 响应头
			ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
			ops.write("Server:Yuer\n".getBytes());
			// 响应体上的换行
			ops.write("\n".getBytes());

			ops.write("reuqest is bad".getBytes());
		}
		
	}
	

	/**
	 * 发送静态资源给客户端
	 * 
	 * @param ops
	 * @throws IOException
	 */
	private static void sendStatic(OutputStream ops) {
		FileInputStream fis = null;
		File file = new File(WEB_ROOT,url);
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
			
			// 这里不确定是否要刷新
			ops.flush();

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
