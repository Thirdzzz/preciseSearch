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

public class SearchManager1 {			//~~~��������		lucene�д���һ�������ѧϰ		learned
	private String queryStr = null;//���������ַ���
	private IndexManager indexManager = null;//����������������ȡ����������
	private Analyzer analyzer = null;
	private JenaOWLModel owlModel;
	
	/**
	 * ��ʼ��
	 * @param queryStr
	 * @param owl
	 */

	
	
	/**
	 * //��������������ֵΪList���϶���
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
		it.search("ţ��");
	}
	public void search(String name) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException{
		//���ͱ�̣�ʵ�����Ͱ�ȫ
		IndexSearcher indexSearcher = null;		//~~~��������
		BooleanQuery q1=new BooleanQuery();		//~~~�߼�����
		//~~~Lucene��һ������ȫ�ļ�������Ѱ�Ŀ�Դ��ʽ�⣬��Apache��������֧�ֺ��ṩ��Lucene�ṩ��һ����ȴǿ���Ӧ�ó�ʽ�ӿڣ��ܹ���ȫ����������Ѱ��
			//����һ������������ֻ����ʽ��һ������
		    
			indexSearcher = new IndexSearcher("D://index");
			QueryParser queryParser = new QueryParser("contents",new MMAnalyzer());
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query q2=queryParser.parse(name);
			
			
			
			
			
		
		if(q1 != null && indexSearcher != null){
			try{
				
				
				
				
				Analyzer analyzer = new IK_CAnalyzer();//�趨�ִ���
				
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
