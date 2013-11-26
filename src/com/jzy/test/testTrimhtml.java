package com.jzy.test;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class testTrimhtml {
	

	public static boolean creatTxtFile(String path) throws IOException { //����TXT
		
		boolean flag = false; 
		
		File filename = new File(path); 
		if (!filename.exists()) { 
		filename.createNewFile(); 
		flag = true; 
		} 
		return flag; 
		} 

	
	    public static String delHTMLTag(String htmlStr) { 	  //ɾ����ǩ
	        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //����script��������ʽ 
	        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //����style��������ʽ 
	        String regEx_html="<[^>]+>"; //����HTML��ǩ��������ʽ 
	         
	        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	        Matcher m_script=p_script.matcher(htmlStr); 
	        htmlStr=m_script.replaceAll(""); //����script��ǩ 
	         
	        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	        Matcher m_style=p_style.matcher(htmlStr); 
	        htmlStr=m_style.replaceAll(""); //����style��ǩ 
	         
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll(""); //����html��ǩ 

	        return htmlStr.trim(); //�����ı��ַ��� 
	    } 
	    public static void FlagGuolv(String path1,String path2)
	    {//"E:\\Workspace\\heritrix1\\jobs\\1-20130927060002058\\mirror\\baike.baidu.com\\index.html"
	    	try {
		    	 
		    	  InputStreamReader isr = new InputStreamReader(new FileInputStream(path1), "gbk");
			  	  BufferedReader br = new BufferedReader(isr);
			  	  String s ="";
			  	  String Paper="";
			  	  
					while((s = br.readLine()) != null)
					  {					
						Paper+=s;				  
					  }				
					 String str=delHTMLTag(Paper);
					 File M=new File(path2);
					 PrintStream ps = new PrintStream(new FileOutputStream(M));
                                         ps.println(str);
                     br.close();
                     isr.close();
                     
				} catch (Exception e) {
					e.printStackTrace();
				}	
	    }
	    
	    public static void main(String[] args) throws Exception {
	    	
	    	 String path1="E:\\Workspace\\heritrix1\\jobs\\1-20131010041039879\\mirror\\open.baidu.com\\coop\\commonweal.html";
	    	
	    	 String path2="D:\\1.txt";
	    	 FlagGuolv(path1,path2);
	    	 
	       
		}
	    
	   
}


