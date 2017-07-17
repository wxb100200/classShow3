package com.hz.school.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * <p>Title:HttpServer</p>
 * <p>Description:手写http服务器</p>
 * @author rhwayfun
 * @date Sep 16, 2015 6:23:49 PM
 * @version 1.0
 */
public class HttpServer {

	//是否关闭
	private boolean isShutDowm = false; 
	//服务器连接
	private ServerSocket server;
	
	public void start(){
		try {
			//1、创建http server
			start(8080);
			//2、等待客户端连接，由于使用TCP协议，所以这里的客户端就是浏览器
			this.handle();
		} catch (IOException e) {
			try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void start(int port){
		try {
			//1、创建http server
			server = new ServerSocket(port);
			//2、等待客户端连接，由于使用TCP协议，所以这里的客户端就是浏览器
			while(!isShutDowm){
				this.handle();
			}
		} catch (IOException e) {
			try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//获取连接信息
	private void handle() throws IOException{
		try {
			Socket client = server.accept();
			//对每一个客户端都启动一个线程处理
			new Thread(new ServletDisPatcher(client)).start();
		} catch (IOException e) {
			stop();
		}
	}
	
	//关闭服务器
	public void stop() throws IOException{
		isShutDowm = true;
		server.close();
	}
	public static void main(String[] args) {
		new HttpServer().start();
	}
}
