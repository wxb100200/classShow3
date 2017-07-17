package com.hz.school.http;

public abstract class GenericServlet {

	public void service(Request request,Response response) {
		this.doGet(request, response);
		this.doPost(request, response);
	}
	
	public abstract void doGet(Request request,Response response);
	public abstract void doPost(Request request,Response response);
}
