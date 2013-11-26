package baidutupian;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.wltea.analyzer.lucene.IKAnalyzer;

import spider.DBoperate;


public class CreatIndex1 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		
		
	
		
		String path = "E:\\test\\index";//索引目录
		   Directory directory = FSDirectory.open(new File(path));
	         Analyzer analyzer = new IKAnalyzer(true);
	 
	         IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
	 
	         config.setOpenMode(OpenMode.CREATE); 
	         IndexWriter indexWriter = new IndexWriter(directory, config);
		     File dir = new File("D:\\test\\test1");//目标文件夹
		     int count=0;
			 File[] dataFiles = dir.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
			 
				 if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
						
						String content=loadFileToString(dataFiles[i]);
						
						
						int start=content.indexOf("@");//1-2 网站   2-3  时间   3-4 题目
						int start1=content.indexOf("@",start+1);
						int start2=content.indexOf("@",start1+1);
						int start3=content.indexOf("@",start2+1);
						String website=content.substring(start+1,start1);
						String date=content.substring(start1+1,start2);
						String title=content.substring(start2+1,start3);
						String contents=content.substring(start3+1);
						System.out.println("count:"+count);
						title=title.replaceAll("&nbsp","");
						title=title.replaceAll(".txt","");
						contents.replaceAll("&nbsp","");
						
						System.out.println("website:"+website);
						System.out.println("date:"+date);
						System.out.println("title:"+title);
						
						Document document = new Document();
				  
						document.add(new Field("contents",contents,Field.Store.YES,Field.Index.ANALYZED));
	              
						document.add(new Field("title",title,Field.Store.YES,Field.Index.ANALYZED));
	              
						document.add(new Field("date",date,Field.Store.YES,Field.Index.ANALYZED));
	              
						document.add(new Field("website",website,Field.Store.YES,Field.Index.ANALYZED));
	              
	              indexWriter.addDocument(document);
		     }
			}
				 
		      indexWriter.close();
	}
	public static String loadFileToString(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

