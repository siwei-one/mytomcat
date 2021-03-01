package com.yuer.mytomcat.dymatic;

import java.io.InputStream;
import java.io.OutputStream;

public interface Servlet {
	
	
	public void init();
	
	public void service(InputStream is, OutputStream ops);
	
	public void destroy();
	
	

}
