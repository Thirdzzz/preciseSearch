package com.jzy.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File input=new File("resource/dizhen.htm");
		String baseUri="http://zh.wikipedia.org/";
		//Document doc=Jsoup.parse(input,"UTF-8",baseUri);
		Document doc=Jsoup.connect("http://zh.wikipedia.org/zh-cn/地震").get();
		
		doc.select("div[class=body],div[class=portal],div[id=footer],div[id=mw-navigation]").remove();
		//doc.select("img").remove();//如要保留图片,可删除此行
		
		Elements linkList=doc.select("a[href]");
		for(Element e:linkList){
			e.attr("href", e.attr("abs:href"));			
		}
		Elements imgList=doc.select("img[src]");
		for(Element e:imgList){
			e.attr("src","http:"+e.attr("src"));
		}
		
		//输出html
		Element body=doc.body();
		
		String regex= "<!--[\\s\\S]*?-->|<[sS][cC][rR][iI][pP][tT][\\s\\S]*?</[sS][cC][rR][iI][pP][tT]>";//过滤的正则表达式换成这个 
        System.out.println(body.html().replaceAll(regex," "));
		File output=new File("resource/output.htm");
		FileWriter fw=new FileWriter(output);
		fw.write(body.html());
		fw.close();
		//System.out.println(body.html());
		
		
	}

}
