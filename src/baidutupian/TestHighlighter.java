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
		String path = "D:\\test\\index1";//����Ŀ¼
		
		BooleanQuery q1=new BooleanQuery();		
		
		Directory dir = FSDirectory.open(new File(path));
		IndexSearcher search = new IndexSearcher(dir);
		
		
		Term term = new Term("content","����");
		Query query = new TermQuery(term);
		term = new Term("content","����");
		Query query1 = new TermQuery(term);
		
		
		
		q1.add(query, BooleanClause.Occur.SHOULD);
		q1.add(query1, BooleanClause.Occur.SHOULD);
		
		Hits hits = search.search(q1);
		
		
		//���������Ĳ�ѯ
	
		//��������
		Analyzer analyzer = new IK_CAnalyzer();//�趨�ִ���
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//�趨������ʾ�ĸ�ʽ��Ҳ���ǶԸ�����ʾ�Ĵ������ǰ׺��׺
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
		highlighter.setTextFragmenter(new SimpleFragmenter(10));//����ÿ�η��ص��ַ���.��ش����ʹ�����������ʱ��Ҳû��һ����ȫ������չʾ�����ɣ���Ȼ����Ҳ���趨ֻչʾ��������
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
