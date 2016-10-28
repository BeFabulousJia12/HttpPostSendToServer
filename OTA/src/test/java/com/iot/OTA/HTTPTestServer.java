package com.iot.OTA;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.iot.OTA.ECallProcess;

public class HTTPTestServer {
	
//	public static void main(String[] args) {  
//        try {  
//            Server server = new Server(8080);  
//  
//            ServletContextHandler context = new ServletContextHandler(  
//                    ServletContextHandler.SESSIONS);  
//            context.setContextPath("/");
//            server.setHandler(context);
//  
//            context.addServlet(new ServletHolder(new ECallProcess()), "/Ecall"); 
//            context.addServlet(new ServletHolder(new GetTokenProcess()), "/GetToken");  
//            System.out.println("Server is starting...");
//            server.start();  
//            server.join();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//  
//    }
	
	public static void HTTPServerToProcessMsg(){
		
		 try {  
	            Server server = new Server(8080);  
	  
	            ServletContextHandler context = new ServletContextHandler(  
	                    ServletContextHandler.SESSIONS);  
	            context.setContextPath("/");
	            server.setHandler(context);
	  
	            context.addServlet(new ServletHolder(new ECallProcess()), "/Ecall");
	            context.addServlet(new ServletHolder(new ICallProcess()), "/Icall");
	            context.addServlet(new ServletHolder(new GetTokenProcess()), "/GetToken");  
	            System.out.println("Server is starting...");
	            server.start();  
	            server.join();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  		
	}

}
