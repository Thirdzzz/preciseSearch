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

public class SearchManager {			//~~~��������		lucene�д���һ�������ѧϰ		learned
	private String queryStr = null;//���������ַ���
	private IndexManager indexManager = null;//����������������ȡ����������
	private Analyzer analyzer = null;
	private JenaOWLModel owlModel;
	
	/**
	 * ��ʼ��
	 * @param queryStr
	 * @param owl
	 */
	public SearchManager(String queryStr,JenaOWLModel owl){
		this.queryStr = queryStr;
		this.indexManager = new IndexManager(null);
		this.analyzer = new MMAnalyzer();
		owlModel=owl;
	}
	
	
	/**
	 * //��������������ֵΪList���϶���
	 * @param searchResult
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException 
	 */
	public List search(List<ResultBean> searchResult) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException{
		//���ͱ�̣�ʵ�����Ͱ�ȫ
		IndexSearcher indexSearcher = null;		//~~~��������
		BooleanQuery q1=new BooleanQuery();		//~~~�߼�����
		//~~~Lucene��һ������ȫ�ļ�������Ѱ�Ŀ�Դ��ʽ�⣬��Apache��������֧�ֺ��ṩ��Lucene�ṩ��һ����ȴǿ���Ӧ�ó�ʽ�ӿڣ��ܹ���ȫ����������Ѱ��
			//����һ������������ֻ����ʽ��һ������
		   
			indexSearcher = new IndexSearcher(indexManager.getIndexDir());
			QueryParser queryParser = new QueryParser("contents",new MMAnalyzer());
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			int iu=0;
			for(int i=0;i<searchResult.size();i++)
			{
				ResultBean l=searchResult.get(i);
				List<String>food=l.getHtmlFood();
				List<String>material=l.getHtmlMaterial();
				
				
				
				for(int j=0;j<food.size();j++)
				{
					String temp=food.get(j);
					Query q2=queryParser.parse(temp);
					q1.add(q2, BooleanClause.Occur.SHOULD);
				}
				for(int j=0;j<material.size();j++)
				{
					String temp=material.get(j);
					Query q2=queryParser.parse(temp);
					q1.add(q2, BooleanClause.Occur.SHOULD);
				}
			
			}
			
		
			
			
			
		
		if(q1 != null && indexSearcher != null){
			try{
				
				
				
				
				Analyzer analyzer = new IK_CAnalyzer();//�趨�ִ���
				SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<strong><font color=\"red\">","</font></strong>");//�趨������ʾ�ĸ�ʽ��Ҳ���ǶԸ�����ʾ�Ĵ������ǰ׺��׺
				Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(q1));
				highlighter.setTextFragmenter(new SimpleFragmenter(200));//����ÿ�η��ص��ַ���.��ش����ʹ�����������ʱ��Ҳû��һ����ȫ������չʾ�����ɣ���Ȼ����Ҳ���趨ֻչʾ��������
				Hits hits = indexSearcher.search(q1);
				for(int i=0;i<hits.length();i++){
					
					ResultBean resultBean = new ResultBean();
					//ȡ����������������ResultBean���Bean��
				
				    
				
					
				
						
						
			        Document doc = hits.doc(i); 	
						
					TokenStream tokenStream = analyzer.tokenStream("",new StringReader(doc.get("contents")));
					String str = highlighter.getBestFragment(tokenStream, doc.get("contents"));
					resultBean.setHtmlContent(str);
					resultBean.setHtmlTitle(doc.get("title"));
					resultBean.sethtmlWebsite(doc.get("website"));
					resultBean.sethtmlDate(doc.get("date"));
					
					
				    
					
					
				   
				    
					
					//��ӽ�List���϶���
					searchResult.add(resultBean);				
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	
		return searchResult;
	}
}
