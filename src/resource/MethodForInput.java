package resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import jeasy.analysis.MMAnalyzer;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;
import org.ictclas4j.utility.GFString;

import resource.search.ResultBean;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;


public class MethodForInput {			//~~~�õ���MethodForProtege		learned
	private MethodForProtege m;
	private List<String> Key;
	
	/**
	 * ����һ��JenaOWLModel p
	 * ����MethodForProtege��MethodForProtege ��� m(class)������ֵ
	 * m.owlModel = p
	 * */
	public MethodForInput(JenaOWLModel p) throws Exception
	{
		m=new MethodForProtege(p);
	}
	/**
	 * ���radio == ��word��������Ϊs�ǵ����ĵ��ʣ���s���뵽SearchProtege()�����в�������������õ���ResultBean�ļ�������
	 * ������Ϊs��һ�����ӻ����ı����Ƚ�s�ִʣ�����������뵽SearchProtege()�����в�������������õ���ResultBean�ļ�������
	 * */
	public List Process(String s,String radio) throws Exception
	{ 
		System.out.println(radio);
		List<ResultBean> l=new LinkedList();
		if(radio.equals((String)("word")))
		{
			l=m.SearchProtege(s,l);
			Key=m.GetKeyWord();
			System.out.println("������������ѯ�����");
		}
		else
		{
		MMAnalyzer analyzer = new MMAnalyzer();		//~~~�ִ���
		BufferedReader reader=new BufferedReader(new FileReader("D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\foodsafety\\WebRoot\\WEB-INF\\2.txt"));
		//MMAnalyzer.addWord("���˶��׵�");
		MMAnalyzer.addDictionary(reader);			//~~~��ԭʼ�ı��е�һЩ���ϼ��뵽�ִ����ֵ���
		String sr=analyzer.segment(s, "/");			//~~~���s���ǵ����Ĵʣ�����/�Ѵ���ֿ�
		while(sr.contains("/"))
		{
			int index=sr.indexOf("/");
			String temp=null;
			temp=sr.substring(0,index);
			sr=sr.substring(index+1);
			l=m.SearchProtege(temp,l);				//~~~һ��һ�������ĵ��ʷֱ����SearchProtege()����
			//~~~ÿ������s���뵽protege�����У�����������һЩ���������ص�ResultBean�ļ�������
		}
		Key=m.GetKeyWord();
		}
		//JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI("file:///c:/Users/he/Desktop/ʵ����/����/�½��ļ���/foodsafety.owl");
		return l;
	}
	/**
	 * �õ��ؼ��ʼ����еĵ�i��
	 * */
	public String GetKeyWord(int i)
	{
		return Key.get(i);
	}
}
