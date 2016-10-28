package com.iot.OTA;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.iot.OTA.ResultToClient;

public class ICallProcess extends HttpServlet{
		    private static final long serialVersionUID = -4012838481920999524L;  
		    private static String contentEncode="";
		    private static String afterEncode="";
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
		    	JSONObject jsonbase = JSONObject.fromObject(theWholeStr);
		    	contentEncode = jsonbase.getString("content");
		    	try {
		    		afterEncode = DecodeContentMsg.AESDecode(contentEncode);
		    		System.out.println("afterEncode:" + afterEncode);
		    		JSONObject jsonparse = JSONObject.fromObject(afterEncode);
		    		createDataBytes.writedata("Icall Post body to the thirdParty：" + afterEncode + "\r\n");
		    		if(jsonparse.getString("callType").contains("I-CALL") && jsonparse.getString("vin").equals("RHHHQ678900000000"))
					{
						createDataBytes.writedata("Passed!" + "\r\n");
					}else
					{
						createDataBytes.writedata("Failed!" + "\r\n");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	String result = "{\"codeMsg\":\"Success\"}"; 
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
