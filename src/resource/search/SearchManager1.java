package resource.search;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpSession;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.queryParser.*;
import org.ictclas4j.segment.SegTag;
import org.ictclas4j.utility.GFString;
import org.ictclas4j.utility.StringTools;
import org.mira.lucene.analysis.IK_CAnalyzer;


import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

import resource.index.IndexManager;

public class SearchManager1 {			//~~~搜索引擎		lucene有待进一步深入的学习		learned
	private String queryStr = null;//待搜索的字符串
	private IndexManager indexManager = null;//将从索引管理器中取得索引数据
	private Analyzer analyzer = null;
	private JenaOWLModel owlModel;
	
	/**
	 * 初始化
	 * @param queryStr
	 * @param owl
	 */

	
	
	/**
	 * //搜索方法，返回值为List集合对象
	 * @param searchResult
	 * @return
	 * @throws InvalidTokenOffsetsException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException 
	 */
	public static void main(String args[]) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException
	{
		SearchManager1 it=new SearchManager1();
		it.search("牛奶");
	}
	public void search(String name) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException{
		//泛型编程，实现类型安全
		IndexSearcher indexSearcher = null;		//~~~索引搜索
		BooleanQuery q1=new BooleanQuery();		//~~~逻辑搜索
		//~~~Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。
			//创建一个搜索器，以只读方式打开一个索引
		    
			indexSearcher = new IndexSearcher("D://index");
			QueryParser queryParser = new QueryParser("contents",new MMAnalyzer());
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query q2=queryParser.parse(name);
			
			
			
			
			
		
		if(q1 != null && indexSearcher != null){
			try{
				
				
				
				
				Analyzer analyzer = new IK_CAnalyzer();//设定分词器
				
				Hits hits = indexSearcher.search(q2);
				for(int i=0;i<hits.length();i++){
					
				
					
					System.out.println(hits.doc(i).get("title"));
					System.out.println(hits.doc(i).get("website"));
					System.out.println(hits.doc(i).get("date"));
				   
				    
					
					
				   
				    
						
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		
	}
}
