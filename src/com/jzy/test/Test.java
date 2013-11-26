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
		Document doc=Jsoup.connect("http://zh.wikipedia.org/zh-cn/����").get();
		
		doc.select("div[class=body],div[class=portal],div[id=footer],div[id=mw-navigation]").remove();
		//doc.select("img").remove();//��Ҫ����ͼƬ,��ɾ������
		
		Elements linkList=doc.select("a[href]");
		for(Element e:linkList){
			e.attr("href", e.attr("abs:href"));			
		}
		Elements imgList=doc.select("img[src]");
		for(Element e:imgList){
			e.attr("src","http:"+e.attr("src"));
		}
		
		//���html
		Element body=doc.body();
		
		String regex= "<!--[\\s\\S]*?-->|<[sS][cC][rR][iI][pP][tT][\\s\\S]*?</[sS][cC][rR][iI][pP][tT]>";//���˵�������ʽ������� 
        System.out.println(body.html().replaceAll(regex," "));
		File output=new File("resource/output.htm");
		FileWriter fw=new FileWriter(output);
		fw.write(body.html());
		fw.close();
		//System.out.println(body.html());
		
		
	}

}
