package com.siwei.mytomcat.dymatic.pro;

import java.io.IOException;
import java.io.InputStream;

/**
 * 获取HTTP的请求头所有信息并截取URL地址返回
 * 
 * @author Yuer
 *
 */
public class Request {

	// 代表请求的文件路径
	private  String url = "";
	
	private InputStream is = null;
	
	public Request() {
		
	}
	
	public Request(InputStream is) {
		this.is = is;
		try {
			parse();//解析请求
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	/**
	 * 获取请求部分中的文件路径并赋值给url
	 * 
	 * @param is
	 * @throws IOException
	 */
	public  void parse() throws IOException {
		// 这里读取完的时间是在游览器停止后会导致报错
		// 这里read是返回下一个字节，而read(byte[] b)则是返回读入缓冲区b的总字节数
//		int i = 0;
//		StringBuffer temp = new StringBuffer();
//
//		while ((i = is.read()) != -1) {
//			temp.append((char) i);
//		}
		
		/*OutputStream out = new BufferedOutputStream(response.getOutputStream());
        byte[] bytes = new byte[1024];
        int len;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        in.close();
        out.flush();
        out.close();*/
		StringBuffer content = new StringBuffer();

		// 这里为什么是2048且一次性读取完（不怕文件大读不完？这里不是读文件而是请求信息）
		// get 是通过URL提交数据，因此GET可提交的数据量就跟URL所能达到的最大长度有直接关系。很多文章都说GET方式提交的数据最多只能是1024字节，而 实际上，URL不存在参数上限的问题，HTTP协议规范也没有对URL长度进行限制。这个限制是特定的浏览器及服务器对它的限制。IE对URL长度的限制 是2083字节(2K+35字节)。对于其他浏览器，如FireFox，Netscape等，则没有长度限制，
		// 这个时候其限制取决于服务器的操作系统。即 如果url太长，服务器可能会因为安全方面的设置从而拒绝请求或者发生不完整的数据请求。
		// 所以一般而言请求体的信息2048应该够了，直接一次读取即可
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
	private  void parseUrl(String temp) {
		String str = "";
		// 这里没考虑啥都没有的情况，即localhost:8080 或local host：8080/(游览器自动省略/)
		str = temp.substring(temp.indexOf(" ") + 2);
		url = str.substring(0, str.indexOf(" "));

		// 做测试
		System.out.println(url);
		System.out.println(url + "-----");
	}
}
