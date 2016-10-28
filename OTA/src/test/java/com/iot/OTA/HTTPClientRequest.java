package com.iot.OTA;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.ByteArrayBuffer;

public class HTTPClientRequest {
	public static void main(String[] args) throws Exception { 
		
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);  
 
		  
		ContentExchange exchange = new ContentExchange();
		exchange.setMethod("POST");
		String requestBody = "{\"userId\":\"3B5A64FB49409165E05AC10A3B203F5D\"}";
        exchange.setRequestContent(new ByteArrayBuffer(requestBody.getBytes("UTF-8")));
		exchange.setURL("http://10.1.64.224:8080/lwsi/cuxcc/getToken");
		System.out.println("====Sending POST Request====");
		httpClient.send(exchange);
		exchange.waitForDone();
	
	}
	
	public static void RequestToPlatform() throws Exception{
		
		HttpClient httpclient = new HttpClient();
		httpclient.start();
		httpclient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);  
 
		  
		ContentExchange exchange = new ContentExchange();
		exchange.setMethod("POST");
		String requestBody = "{\"userId\":\"3B5A64FB49409165E05AC10A3B203F5D\"}";
        exchange.setRequestContent(new ByteArrayBuffer(requestBody.getBytes("UTF-8")));
		exchange.setURL("http://10.1.64.224:8080/lwsi/cuxcc/getToken");
		System.out.println("====Sending POST Request====");
		httpclient.send(exchange);
		exchange.waitForDone();
		
	}
}
