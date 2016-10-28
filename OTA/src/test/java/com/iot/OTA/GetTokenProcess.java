package com.iot.OTA;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTokenProcess extends HttpServlet{
	private static final long serialVersionUID = -4012838481920999524L;  
	  
    /** 
     * 写在这里的代码都是 POST 请求 
     */  
    @Override  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException { 
    	BufferedReader bodyContent = request.getReader();
    	String str, theWholeStr="";
    	while((str = bodyContent.readLine())!=null)
    	{
    		theWholeStr +=str;
    	}
    	System.out.println(theWholeStr);
        //String query = request.getParameter("query");  
        String result = "{\"accesstoken\":\"fromTheThirdParty\",\"expirein\":7200, \"statuscode\":0}";    
        ResultToClient.printToJson(result, response);  
    }  
  
    /** 
     * 写在这里的代码都是 GET 请求 
     */  
    @Override  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        String query = request.getParameter("query");  
        String result = "welcome to my server. It's a GET request.";  
        if (null != query && !query.trim().equals("")) {  
            result = query + ", " + result;  
        }  
        ResultToClient.printToJson(result, response);  
    }  

}
