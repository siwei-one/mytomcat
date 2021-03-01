package com.siwei.mytomcat.dymatic.pro;

public interface Servlet {
	
	
	public void init();
	
	public void service(Request request, Response response);
	
	public void destroy();
	
	

}
