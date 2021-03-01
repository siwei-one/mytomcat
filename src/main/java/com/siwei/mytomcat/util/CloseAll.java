package com.yuer.mytomcat.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭传递过来的值
 * @author Yuer
 *
 */
public class CloseAll {
	
	/**
	 * 使用可变参数
	 * @param closeable
	 */
	public static void close(Closeable ...closeables) {
		
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
