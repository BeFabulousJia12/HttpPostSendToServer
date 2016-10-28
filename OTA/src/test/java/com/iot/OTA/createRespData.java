package com.iot.OTA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.iot.OTA.createDataBytes;
import com.iot.OTA.HTTPClientRequest;
import com.iot.OTA.HTTPTestServer;

public class createRespData {
	
	//发送心跳消息20个字节长度
	static String heartbeatString = "2E 2E 04 fe 00 00 00 00 01 90 73 61 73 35 00 00 01 00 00";
	static String heartbeatResp = "2E 2E 04 01 00 00 00 00 01 90 73 61 73 35 00 00 01 00 00";
	static String logonString = "2E 2E 06 FE 00 00 00 00 01 90 73 61 73 35 00 00 02 00 2D 61 62 63 64 65 66 67 68 69 6A 6B 6C 52 48 48 48 51 36 37 38 39 30 30 30 30 30 30 30 30 31 2E 30 30 61 62 63 64 30 30 30 30 30 30 00 01";
	//static String logonString = "2E 2E 06 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 2D 61 62 63 64 65 66 67 68 69 6A 6B 6C 31 32 33 34 35 36 37 38 39 30 30 30 30 30 30 30 30 31 2E 30 30 61 62 63 64 30 30 30 30 30 30 00 01";
	static String logonResp = "2E 2E 06 01 00 00 00 00 01 90 73 61 73 35 00 00 02 00 08 00 00 00 00 00 00 00 00";
	static String ICall = "2E 2E 02 FE 00 00 00 00 01 90 73 61 73 35 00 00 03 00 24 10 0A 0B 0A 20 00 85 08 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 86 00 1F 40";
	static String ECall ="2E 2E 02 FE 00 00 00 00 01 90 73 61 73 35 00 00 03 00 26 10 0A 0B 0A 20 00 85 09 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 00 00 00 00 01";
	 
	//点火上报
	static String startEngine ="2E 2E 02 FE 00 00 00 00 01 90 73 61 73 35 00 00 03 00 36 10 0A 0B 0A 20 00 85 0E 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 00 00 00 00 86 0A 07 00 08 01 38 80 46 07 D0 00 78 01 2C 16";
    //定时上报
	static String reportOntime ="2E 2E 02 FE 00 00 00 00 01 90 73 61 73 35 00 00 03 00 36 10 0A 0B 0A 20 00 85 01 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 00 00 00 00 86 0A 07 00 08 01 38 80 46 07 D0 00 78 01 2C 16";
	//熄火上报
	static String endEngine ="2E 2E 02 FE 00 00 00 00 01 90 73 61 73 35 00 00 03 00 36 10 0A 0B 0A 20 00 85 0F 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 00 00 00 00 86 0A 07 00 08 01 38 80 46 07 D0 00 78 01 2C 16";
	//链路连接
	static String link ="2E 2E 83 FE 00 00 00 00 01 90 73 61 73 35 00 00 04 00 30 10 0A 0B 0A 20 00 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 00 00 00 00 00 00 00 00 00 00 00 31 30 31 35 49 31 4D 67";
	static String linkResp ="2E 2E 83 01 00 00 00 00 01 90 73 61 73 35 00 00 04 00 01 00";
	//实时信息上报消息
	static String reportResp ="2E2E0201000000000190736173350000030000C5";
	//登出
	static String logout = "2E 2E 07 FE 00 00 00 00 01 90 73 61 73 35 00 00 05 00 00";
	static String logoutResp = "2E 2E 07 01 00 00 00 00 01 90 73 61 73 35 00 00 05 00 00";
	//对服务端发起连接请求 
	public static Socket createTCP() throws UnknownHostException, IOException{
		Socket socket=null;
		socket = new Socket("10.1.64.224", 9001);
		//socket = new Socket("10.1.64.224", 9001);
		//socket = new Socket("125.69.151.39", 8856);
		return socket;
	}
	public class HttpThread extends Thread{
    	public void run()
    	{
			//Start Http Server
			HTTPTestServer.HTTPServerToProcessMsg();
    	}
			
    	}
	@BeforeTest
	public void setup() {
		
		try {
			createDataBytes.flush();
			//启动线程开启http Server
			HttpThread httpThread = new HttpThread();
			httpThread.start();
			//Start Http Request
			HTTPClientRequest.RequestToPlatform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Test
	public static void OTAtest() throws UnknownHostException, IOException
	{
		Socket socket = createTCP();
		
	    try {
			Tcpresponse(socket);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public static void PostCommandsValidation() throws UnknownHostException, IOException
	{
		//查询result里面是否有错误消息
    	Assert.assertTrue(createDataBytes.readlinesfromtestresultfile("Failed"));
	}
	public static String Tcpresponse(Socket socket) throws InterruptedException
	{
		//建立TCP连接
				String responseString = null;
				try {  
				//给服务端发送响应信息 
				OutputStream os=socket.getOutputStream();
				List<String> arry = new ArrayList<String>();
				arry.add(heartbeatString);
				arry.add(logonString);
				arry.add(link);
				arry.add(ICall);
				arry.add(ECall);
				arry.add(startEngine);
				arry.add(reportOntime);
				arry.add(endEngine);
				arry.add(logout);
			
				for(int i=0; i<arry.size();i++)
				{
					
					byte[] request = createDataBytes.requestBytes(arry.get(i));
					os.write(request);
					//接受服务端消息并打印 
					InputStream is=socket.getInputStream(); 
					byte[] b=new byte[1024];
					int len = 0;
			        if((len=(is.read(b)))>0){
			          byte[] respBytes = new byte[len];
			          for(int j=0; j<len; j++)
			          {
			        	  respBytes[j] = b[j];
			          }
			          responseString = createDataBytes.bytesToStringFunc(respBytes);
			          validation(respBytes);
			          System.out.println("!!!!"  + createDataBytes.bytesToStringFunc(respBytes));
			        }else
			        {
			        	Assert.fail("request msg: " +arry.get(i) + "No Response");
			        }
				}
				os.close();
				}
				catch (IOException e) { 
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
				return responseString;
				
	}
	public static void validation(byte[] respBytes)
	{
		String responseString = null;
		byte[] b = new byte[1];
		//首先判断第三个字节
		switch(respBytes[2]){
		case 0x04:
			//处理heartbeat回应消息
			byte[] heartbeatRespBytes = createDataBytes.requestBytes(heartbeatResp);
			heartbeatResp= createDataBytes.bytesToStringFunc(heartbeatRespBytes).toUpperCase();
			responseString = createDataBytes.bytesToStringFunc(respBytes).toUpperCase();
			System.out.println("心跳期望的值：" + heartbeatResp);
			System.out.println("心跳实际的值：" + responseString);
			Assert.assertEquals(heartbeatResp, responseString);
			break;
		case 0x06:
			byte[] logonRespBytes = createDataBytes.requestBytes(logonResp);
			byte[] temp = new byte[logonRespBytes.length-1];
			for(int i=0; i<logonRespBytes.length-1; i++)
			{
				temp[i] = logonRespBytes[i];
			}
			//替换登录时间的内容
			for(int i=19; i<25; i++)
			{
				temp[i] = respBytes[i];
			}
			logonResp= createDataBytes.bytesToStringFunc(temp);
			logonRespBytes = createDataBytes.requestBytes(logonResp);
			logonResp= createDataBytes.bytesToStringFunc(logonRespBytes).toUpperCase();
			responseString = createDataBytes.bytesToStringFunc(respBytes).toUpperCase();
			System.out.println("登录消息期望的值：" + logonResp);
			System.out.println("登录消息实际的值：" + responseString);
			Assert.assertEquals(logonResp, responseString);
			break;
		case (byte) 0x83:
			byte[] linkRespBytes = createDataBytes.requestBytes(linkResp);
			linkResp= createDataBytes.bytesToStringFunc(linkRespBytes).toUpperCase();
			responseString = createDataBytes.bytesToStringFunc(respBytes).toUpperCase();
			System.out.println("链路连接期望的值：" + linkResp);
			System.out.println("链路连接实际的值：" + responseString);
			Assert.assertEquals(linkResp, responseString);
			break;
		case 0x02:
			responseString = createDataBytes.bytesToStringFunc(respBytes).toUpperCase();
			System.out.println("信息上报期望的值：" + reportResp);
			System.out.println("信息上报实际的值：" + responseString);
			Assert.assertEquals(reportResp, responseString);
			break;
		case 0x07:
			//logout
			byte[] logoutRespBytes = createDataBytes.requestBytes(logoutResp);
			logoutResp = createDataBytes.bytesToStringFunc(logoutRespBytes).toUpperCase();
			responseString = createDataBytes.bytesToStringFunc(respBytes).toUpperCase();
			System.out.println("logout期望的值：" + logoutResp);
			System.out.println("logout实际的值：" + responseString);
			Assert.assertEquals(logoutResp, responseString);
			break;	
		}	
	}

}
