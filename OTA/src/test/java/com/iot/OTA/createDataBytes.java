package com.iot.OTA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class createDataBytes {
	
	static String file ="src/test/resources/requestData.dat";
	public static void main(String[] args) {
		//数据构造原则，只关心上行命令，下行不做测试。
		//发送心跳消息20个字节长度
		String heartbeatString = "2E2E04fe000000000605040302010000010000";
		createData(heartbeatString);
		//登录消息
		String logonString = "2E 2E 06 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 2D 61 62 63 64 65 66 67 68 69 6A 6B 6C 31 32 33 34 35 36 37 38 39 30 30 30 30 30 30 30 30 31 2E 30 30 61 62 63 64 30 30 30 30 30 30 00 01";
		logonString = logonString.replace(" ", "");
		createData(logonString);
		//I-CALL消息
		String ICall = "2E 2E 02 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 24 10 0A 0B 0A 20 00 85 08 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 86 00 1F 40";
		ICall = ICall.replace(" ", "");
		createData(ICall);
		//E-Call消息
		String ECall ="2E 2E 02 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 26 10 0A 0B 0A 20 00 85 09 04 00 10 0A 0B 0A 20 00 01 D6 D2 03  06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 00 00 00 00 01";
		ECall = ECall.replace(" ", "");
		createData(ECall);
		//点火上报
		String startEngine ="2E 2E 02 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 30 10 0A 0B 0A 20 00 85 0E 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 09 06 00 1F 40 30 20 40 00 80 02 30 09 01";
		startEngine = startEngine.replace(" ", "");
		createData(startEngine);
		//定时上报
		String reportOntime ="2E 2E 02 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 30 10 0A 0B 0A 20 00 85 01 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 09 06 00 1F 40 30 20 40 00 80 02 30 09 01";
		reportOntime= reportOntime.replace(" ","");
		createData(reportOntime);
		//熄火上报
		String endEngine ="2E 2E 02 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 30 10 0A 0B 0A 20 00 85 01 04 00 10 0A 0B 0A 20 00 01 D6 D2 03 06 30 37 7E 04 B0 00 B4 01 F4 00 00 80 01 09 06 00 1F 40 30 20 40 00 80 02 30 09 01";
		endEngine = endEngine.replace(" ","");
		createData(endEngine);
		//登出
		String logout = "2E 2E 07 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 00";
		logout = logout.replace(" ", "");
		createData(logout);
		//链路连接
		String link ="2E 2E 83 FE 00 00 00 00 06 05 04 03 02 01 00 00 01 00 30 10 0A 0B 0A 20 00 00 10 0A 0B 0A 20 00 01 D6 D2 03  06 30 37 7E 04 B0 00 B4 01 F4 00 00 30 30 30 30 30 30 31 32 33 34 35 36 37 38 39 30 30 30 30";
		link = link.replace(" ", "");
		createData(link);
		
		
		
	}
   public static void createData(String inputString)
   {
	   char[] sc = inputString.toCharArray();
		System.out.println("sc length: " + sc.length/2);
       byte[] ba = new byte[sc.length / 2];
       for (int i = 0; i < ba.length; i++) {
           int nibble0 = Character.digit(sc[i * 2], 16);
           int nibble1 = Character.digit(sc[i * 2 + 1], 16);
           if (nibble0 == -1 || nibble1 == -1){
               throw new IllegalArgumentException(
               "Hex-encoded binary string contains an invalid hex digit in '"+sc[i * 2]+sc[i * 2 + 1]+"'");
           }
           ba[i] = (byte) ((nibble0 << 4) | (nibble1));
       }
       //System.out.println(Arrays.toString(ba));
		byte[] heartbeat = new byte[sc.length/2 + 1];
		byte checksum = ba[2];
		heartbeat[0] = ba[0];
		heartbeat[1] = ba[1];
		heartbeat[2] = ba[2];
		for(int i=3; i<ba.length; i++)
		{	
			checksum = (byte) (checksum^ba[i]);
			System.out.println(checksum);
			heartbeat[i] = ba[i];
		}

		heartbeat[ba.length]=(byte)(checksum & 0xff);
		
		String request = bytesToStringFunc(heartbeat);
		System.out.println("request: " + request);
		writedata(request + "\r\n");
   }
   
   public static byte[] requestBytes(String inputString)
   {
	   inputString = inputString.replace(" ", "");
	   char[] sc = inputString.toCharArray();
		//System.out.println("sc length: " + sc.length/2);
       byte[] ba = new byte[sc.length / 2];
       for (int i = 0; i < ba.length; i++) {
           int nibble0 = Character.digit(sc[i * 2], 16);
           int nibble1 = Character.digit(sc[i * 2 + 1], 16);
           if (nibble0 == -1 || nibble1 == -1){
               throw new IllegalArgumentException(
               "Hex-encoded binary string contains an invalid hex digit in '"+sc[i * 2]+sc[i * 2 + 1]+"'");
           }
           ba[i] = (byte) ((nibble0 << 4) | (nibble1));
       }
       //System.out.println(Arrays.toString(ba));
		byte[] heartbeat = new byte[sc.length/2 + 1];
		byte checksum = ba[2];
		heartbeat[0] = ba[0];
		heartbeat[1] = ba[1];
		heartbeat[2] = ba[2];
		for(int i=3; i<ba.length; i++)
		{	
			checksum = (byte) (checksum^ba[i]);
			//System.out.println(checksum);
			heartbeat[i] = ba[i];
		}

		heartbeat[ba.length]=(byte)(checksum & 0xff);
		
		return heartbeat;
   }
   public static String bytesToStringFunc(byte[] b)
   {   
	   StringBuffer buffer = new StringBuffer();
	   for (int i = 0; i < b.length; ++i){
		   buffer.append(toHexString1(b[i]));
	   }
	   return buffer.toString();
	   
   }
   public static String toHexString1(byte b){
	   String s = Integer.toHexString(b & 0xFF);
	   if (s.length() == 1){
		   return "0" + s;
		   }
	   else{
		   return s;
	   }
	   
   }
   public static void flush() throws IOException
   {
	   FileWriter fw = new FileWriter(file);
	   fw.flush();
	   fw.close();
   }
   public static void writedata(String inputData)
   {
	    
	   BufferedWriter out = null;   
	     try {   
	         out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));   
	         out.write(inputData);   
	     } catch (Exception e) {   
	         e.printStackTrace();   
	     } finally {   
	         try {   
	         	if(out != null){
	         		out.close();   
	             }
	         } catch (IOException e) {   
	             e.printStackTrace();   
	         }   
	     }    
   }
   public static boolean readlinesfromtestresultfile(String expectationfailedString)throws IOException{
	 
   	   BufferedReader dataReader = null;
       boolean isFailed = true;
       try{
       dataReader = new BufferedReader(new FileReader(file));
       String line = dataReader.readLine();
       if(line == null)
       {
    	   isFailed =false;
       }
       while(true)
       {
	        line = dataReader.readLine();
	        if (line == null) {
	            //throw new IOException(filename + ": unable to read line");
	        	break;
	        }
	        if(line.contains(expectationfailedString))
	         {
	        	isFailed = false;
	        	//System.out.println("=======###Found###======");
	         	break;
	            }
       
       }
   }catch (Exception e) {   
        e.printStackTrace();   
    } finally {   
        try {   
        	if(dataReader != null){
        		dataReader.close();   
            }
        } catch (IOException e) {   
            e.printStackTrace();   
        } 
    }
    return isFailed;
  }
}

