package com.jzy.test;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class GetUrlStrForHttp {
	public static String GetURLstr(String strUrl)
	{
	   InputStream in = null;
	   OutputStream out = null;
	   String strdata = "";
	   try
	   {
	    URL url = new URL(strUrl); // 创建 URL
	    in = url.openStream(); // 打开到这个URL的流
	    out = System.out;

	    // 复制字节到输出流
	    byte[] buffer = new byte[4096];
	    int bytes_read;
	    while ((bytes_read = in.read(buffer)) != -1)
	    {
	     String reads = new String(buffer, 0, bytes_read, "UTF-8");
	     strdata = strdata + reads;
	     out.write(buffer, 0, bytes_read);
	    }
	    in.close();
	    out.close();
	    return strdata;
	   }

	   catch (Exception e)
	   {
	    System.err.println(e);
	    System.err.println("Usage: java GetURL <URL> [<filename>]");
	    return strdata;
	   }
	}
	
	public static void main(String []args){
		String str = GetURLstr("http://www.test.com/tools/getFileName.jsp");
		System.out.println(str);
	}
}
