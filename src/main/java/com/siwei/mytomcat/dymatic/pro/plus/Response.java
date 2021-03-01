package com.siwei.mytomcat.dymatic.pro.plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.siwei.mytomcat.util.CloseAll;

/**
 * 响应请求读取文件并写回到浏览器
 * 
 * @author Yuer
 *
 */
@SuppressWarnings("rawtypes")
public class Response {

	private OutputStream ops = null;

	private Request request = null;
	// 代表静态资源路径
	private static String WEB_ROOT = System.getProperty("user.dir") + "\\" + "src\\main\\webapp";

	// 还要将配置文件中的信息读取到map中
	private  Map<String, String> map = new HashMap<String, String>();
	

	public Response() {

	}

	public Response(Request request, OutputStream ops) {
		this.request = request;
		this.ops = ops;
		this.map = ParseXml.getMap();
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public OutputStream getOps() {
		return ops;
	}

	public void setOps(OutputStream ops) {
		this.ops = ops;
	}
	
	
	/**
	 * 重定向
	 * @throws Exception 
	 */
	public void sendRedirect() throws Exception {
		
		String url = request.getUrl();
		// 先判断是否是/或没有 则直接访问my.xml中的欢迎页
		if (url == null || "".equals(url)) {
			sendStatic(true);
		} else { // 判断本次请求的是静态页面还是servlet程序
			// 看是否存在. 即是否访问静态
			if (url.indexOf(".") != -1) {
				// 发送静态资源给客户端
				sendStatic(false);
			} else {
				// 没有数据的情况下
				if (request.getData_map().isEmpty()) {
					// 发送动态的
					sendDynamic(false);
					
				} else { // 有数据
					sendDynamic(true);
				}
				
			}
		}
		
	}

	/**
	 * 发送动态资源
	 * 
	 * @param ops
	 * @throws Exception 
	 */
	public void sendDynamic(boolean flag)
			throws Exception {

		String url = request.getUrl();

		// 接下来判断这次请求的url是否存在于map中
		if (map.containsKey(url)) {
			
			// 如果包含指定的key,获取到map中key对应的value部分
			String value = map.get(url);

			// 通过反射调用对应的Servlet 这里后面通过web.xml或者注解
			Class clazz = Class.forName(value);
			Servlet servlet = (Servlet) clazz.newInstance();
			
			if (flag) {
				servlet.init();
				// 执行service
				servlet.service(request, this);
			} else {

				// 先发送响应行和响应头
				// 响应行
				ops.write("HTTP/1.1 200 OK\n".getBytes());
				// 响应头
				ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
				ops.write("Server:Yuer\n".getBytes());
				// 响应体上的换行(响应头或请求头到后面的数据部分数据之前必须有空的一行，所以这里加了一个\n表示一个空行)
				ops.write("\n".getBytes());
	
				// 执行init方法
				servlet.init();
				// 执行service
				servlet.service(request, this);
				// 执行销毁 销毁不知道是在什么时候
	//			servlet.destroy();
			
			}

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
	 * @param flag 代表是否是默认
	 */
	public void sendStatic(Boolean flag) {
		String url = "";
		// 是默认即地址栏啥都没写
		if (flag) {
			// 读取欢迎页
			url = ParseXml.getWelcome();
		} else {
			url = request.getUrl();
		}

		FileInputStream fis = null;
		File file = new File(WEB_ROOT, url);
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
			CloseAll.close(fis);
		}
	}
	

}
