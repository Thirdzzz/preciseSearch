package baidutupian;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;



import org.jsoup.Jsoup;
import org.mira.lucene.analysis.IK_CAnalyzer;


import spider.DBoperate;


public class indextest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		
		File de=new File("D:\\test\\index");
		File[] defiles = de.listFiles();
		for(int i=0;i<defiles.length;i++)
		{
			File file = defiles[i];
			file.delete();
		}
		
		String path = "D:\\test\\index";//Ë÷ÒýÄ¿Â¼
		   
	         Analyzer analyzer = new MMAnalyzer();
	 
	         IndexWriter indexWriter = new IndexWriter(path,analyzer,true);
	 
	         
		     File dir = new File("D:\\test\\imag");
		     File[] files = dir.listFiles();
		     for(int i=0;i<files.length;i++)
			 {
			      Document doc = new Document();
			      File file = files[i];
			      
				  
				  
				  System.out.println("path:"+file.getPath()+";content:"+file.getName());
				  
				  
	              doc.add(new Field("path",file.getPath(),Field.Store.YES,Field.Index.NO));
	              
	            //  doc.add(new Field("website",website,Field.Store.YES,Field.Index.TOKENIZED));
	              
	              doc.add(new Field("content",file.getName(),Field.Store.YES,Field.Index.TOKENIZED));
	              
	             // doc.add(new Field("path",file.getPath(),Field.Store.YES,Field.Index.TOKENIZED));
	              
	              indexWriter.addDocument(doc);
		     }
		     indexWriter.optimize();
				indexWriter.close();
	}

}

