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
		  
			//lpath记录相对路径
			  
			 
			 BooleanQuery q1=new BooleanQuery();		//~~~逻辑搜索
			//~~~Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。
				//创建一个搜索器，以只读方式打开一个索引
			
			 Directory directory = FSDirectory.open(new File("D:\\test\\index"));
				
		     DirectoryReader directoryReader = DirectoryReader.open(directory);
		
		     IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		 
			
			
		     Analyzer analyzer = new IKAnalyzer(true);//设定分词器
				Iterator j = name.iterator();//先迭代出来  
		          
		        while(j.hasNext()){//遍历  
		        	String s=j.next().toString();
		        	QueryParser parser = new QueryParser(Version.LUCENE_41, "content", analyzer);
		        	//MultiFieldQueryParser.Parse("Java",new string[]{"Title","Author"},BooleanClause.Occur[],analyzer);
			        Query query = parser.parse(s);
		        	System.out.println("~~~:"+s);
					q1.add(query, Occur.SHOULD);
		        }  
			
			
			
			
			SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
			Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
			highlighter.setTextFragmenter(new SimpleFragmenter(10));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
			ScoreDoc[] hits = indexSearcher.search(q1, null, 1000).scoreDocs;
			System.out.println("changdu:"+hits.length);
		       
            for(int i = 0; i < hits.length; i++){
	       
	                   
            	ScoreDoc hit = hits[i];
                itmode oo=new itmode();//插入类中
                
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
		name.add("猪肉");
		High it=new High();
		it.num(name);
			
	}
}
