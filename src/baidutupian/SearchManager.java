package baidutupian;

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

public class SearchManager {			//~~~搜索引擎		lucene有待进一步深入的学习		learned
	private String queryStr = null;//待搜索的字符串
	private IndexManager indexManager = null;//将从索引管理器中取得索引数据
	private Analyzer analyzer = null;
	private JenaOWLModel owlModel;
	
	/**
	 * 初始化
	 * @param queryStr
	 * @param owl
	 * @throws InvalidTokenOffsetsException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	
	
	public static void main(String args[]) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException
	{
		SearchManager it=new SearchManager();
		List<ResultBean> its=new ArrayList<ResultBean>();
		it.search(its);
	}
	/**
	 * //搜索方法，返回值为List集合对象
	 * @param searchResult
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException 
	 */
	public List search(List<ResultBean> searchResult) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException{
		//泛型编程，实现类型安全
		IndexSearcher indexSearcher = null;		//~~~索引搜索
		BooleanQuery q1=new BooleanQuery();		//~~~逻辑搜索
		//~~~Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。
			//创建一个搜索器，以只读方式打开一个索引
		    
			indexSearcher = new IndexSearcher("D:\\test\\index");
			QueryParser queryParser = new QueryParser("contents",new MMAnalyzer());
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			int iu=0;
		
					Query q2=queryParser.parse("猪肉");
					q1.add(q2, BooleanClause.Occur.SHOULD);
		
			
			
			
			
		
		if(q1 != null && indexSearcher != null){
			try{
				
				
				
				
				Analyzer analyzer = new IK_CAnalyzer();//设定分词器
				SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
				Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
				highlighter.setTextFragmenter(new SimpleFragmenter(10));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
				Hits hits = indexSearcher.search(q1);
				for(int i=0;i<hits.length();i++){
					
				
					String str=null;
				
					
					
					
					
					ResultBean resultBean = new ResultBean();
					//取得搜索结果，存放在ResultBean结果Bean中
					
					
					String content=hits.doc(i).get("contents");
					if(content!=null)
					{
						
						
			            Document doc = hits.doc(i); 	
						
					TokenStream tokenStream = analyzer.tokenStream("",new StringReader(doc.get("contents")));
					str = highlighter.getBestFragment(tokenStream, doc.get("contents"));
					}
				
					
					
				   
				    
					
					//添加进List集合对象
					searchResult.add(resultBean);				
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	
		return searchResult;
	}
}
