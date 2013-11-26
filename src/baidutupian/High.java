package baidutupian;




import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class High {

	public class itmode
	{
		public String name;
		
		public String content;
		
		public String website;
		
		public int size;

	}

	@SuppressWarnings("deprecation")
	public List<itmode> num(SortedSet<String> name)throws IOException, ParseException, InvalidTokenOffsetsException 
	{
		
	
		 List<itmode>ga=new ArrayList<itmode>();
		  
			//lpath��¼���·��
			  
			 
			 BooleanQuery q1=new BooleanQuery();		//~~~�߼�����
			//~~~Lucene��һ������ȫ�ļ�������Ѱ�Ŀ�Դ��ʽ�⣬��Apache��������֧�ֺ��ṩ��Lucene�ṩ��һ����ȴǿ���Ӧ�ó�ʽ�ӿڣ��ܹ���ȫ����������Ѱ��
				//����һ������������ֻ����ʽ��һ������
			
			 Directory directory = FSDirectory.open(new File("D:\\test\\index"));
				
		     DirectoryReader directoryReader = DirectoryReader.open(directory);
		
		     IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		 
			
			
		     Analyzer analyzer = new IKAnalyzer(true);//�趨�ִ���
				Iterator j = name.iterator();//�ȵ�������  
		          
		        while(j.hasNext()){//����  
		        	String s=j.next().toString();
		        	QueryParser parser = new QueryParser(Version.LUCENE_41, "content", analyzer);
		        	//MultiFieldQueryParser.Parse("Java",new string[]{"Title","Author"},BooleanClause.Occur[],analyzer);
			        Query query = parser.parse(s);
		        	System.out.println("~~~:"+s);
					q1.add(query, Occur.SHOULD);
		        }  
			
			
			
			
			SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//�趨������ʾ�ĸ�ʽ��Ҳ���ǶԸ�����ʾ�Ĵ������ǰ׺��׺
			Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
			highlighter.setTextFragmenter(new SimpleFragmenter(10));//����ÿ�η��ص��ַ���.��ش����ʹ�����������ʱ��Ҳû��һ����ȫ������չʾ�����ɣ���Ȼ����Ҳ���趨ֻչʾ��������
			ScoreDoc[] hits = indexSearcher.search(q1, null, 1000).scoreDocs;
			System.out.println("changdu:"+hits.length);
		       
            for(int i = 0; i < hits.length; i++){
	       
	                   
            	ScoreDoc hit = hits[i];
                itmode oo=new itmode();//��������
                
                Document coDocument = indexSearcher.doc(hit.doc);
						TokenStream tokenStream = analyzer.tokenStream("",new StringReader(coDocument.get("content").toString()));
						String str = highlighter.getBestFragment(tokenStream, coDocument.get("content").toString());
					
	                    System.out.println(str);
	                    
	      			    oo.name=coDocument.get("name");
	      			    oo.website=coDocument.get("website");
	      			    oo.content=str;
	      			    ga.add(oo);
	      		  
  					    
	       
	                }
		
		    
		    return ga;
	}
	
	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		SortedSet<String> name=new TreeSet<String>();
		name.add("����");
		High it=new High();
		it.num(name);
			
	}
}
