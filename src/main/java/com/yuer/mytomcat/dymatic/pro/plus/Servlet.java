package com.yuer.mytomcat.dymatic.pro.plus;

public interface Servlet {
	
	
	public abstract void init();
	
	public abstract  void service(Request request, Response response) throws Exception;
	public abstract void doGet(Request req,Response rep) throws Exception;
	public abstract void doPost(Request req,Response rep) throws Exception;
	
	public abstract void destroy();
	
	
	
	

}
