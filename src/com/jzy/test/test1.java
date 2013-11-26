package com.jzy.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import cpdetector.io.ASCIIDetector;
import cpdetector.io.ByteOrderMarkDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
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
public class test1 {

	

public static String delHTMLTag(String htmlStr) { 	  //删除标签
    String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
    String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
     
    Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
    Matcher m_script=p_script.matcher(htmlStr); 
    htmlStr=m_script.replaceAll(""); //过滤script标签 
     
    Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
    Matcher m_style=p_style.matcher(htmlStr); 
    htmlStr=m_style.replaceAll(""); //过滤style标签 
     
    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
    Matcher m_html=p_html.matcher(htmlStr); 
    htmlStr=m_html.replaceAll(""); //过滤html标签 

    return htmlStr.trim(); //返回文本字符串 
} 
public static void FlagGuolv1(String path2,String Contexpath) throws IOException
{
   
}
public static String FlagGuolv(String website) throws IOException
{
	
		  
		 
		  Document doc=Jsoup.connect(website).timeout(60000).get();
			
			//输出htmlClass.class.getClass().getResource("/").getPath()
		  File file=new File("");
     
		  String title=doc.title().toString();
		  Element body=doc.body();
		  String sff="";//去掉script标签的html文本
		  String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
		  Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
		  Matcher m_script=p_script.matcher(body.toString()); 
		  sff=m_script.replaceAll(""); //过滤script标签 
		  
		
		   extractInfo it=new extractInfo();
		
		    
		    it.executeExtract(sff,"gbk");
		    String strpaper=it.getStrContext();
  
             
             return strpaper;
             
	
}
        
        


}
