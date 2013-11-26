package baidutupian;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.mira.lucene.analysis.IK_CAnalyzer;


public class TestHighlighter {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, InvalidTokenOffsetsException {
		String path = "D:\\test\\index1";//索引目录
		
		BooleanQuery q1=new BooleanQuery();		
		
		Directory dir = FSDirectory.open(new File(path));
		IndexSearcher search = new IndexSearcher(dir);
		
		
		Term term = new Term("content","猪肉");
		Query query = new TermQuery(term);
		term = new Term("content","新浪");
		Query query1 = new TermQuery(term);
		
		
		
		q1.add(query, BooleanClause.Occur.SHOULD);
		q1.add(query1, BooleanClause.Occur.SHOULD);
		
		Hits hits = search.search(q1);
		
		
		//正常产生的查询
	
		//高亮设置
		Analyzer analyzer = new IK_CAnalyzer();//设定分词器
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
		highlighter.setTextFragmenter(new SimpleFragmenter(10));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
		System.out.println(hits.length());
		for(int i=0;i<hits.length();i++){
			
			
			Document doc = hits.doc(i);
			TokenStream tokenStream = analyzer.tokenStream("",new StringReader(doc.get("content")));
			String str = highlighter.getBestFragment(tokenStream, doc.get("content"));
			System.out.println(str);
			//System.err.println("aaaaaaaaa");
		}
	}

}
