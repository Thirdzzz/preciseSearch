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


public class MethodForInput {			//~~~用到了MethodForProtege		learned
	private MethodForProtege m;
	private List<String> Key;
	
	/**
	 * 输入一个JenaOWLModel p
	 * 经过MethodForProtege给MethodForProtege 类的 m(class)参数赋值
	 * m.owlModel = p
	 * */
	public MethodForInput(JenaOWLModel p) throws Exception
	{
		m=new MethodForProtege(p);
	}
	/**
	 * 如果radio == “word”，则认为s是单个的单词，将s扔入到SearchProtege()函数中操作，输出操作得到的ResultBean的集合内容
	 * 否则，认为s是一个句子或者文本，先将s分词，逐个按照扔入到SearchProtege()函数中操作，输出操作得到的ResultBean的集合内容
	 * */
	public List Process(String s,String radio) throws Exception
	{ 
		System.out.println(radio);
		List<ResultBean> l=new LinkedList();
		if(radio.equals((String)("word")))
		{
			l=m.SearchProtege(s,l);
			Key=m.GetKeyWord();
			System.out.println("按词搜索！查询本体库");
		}
		else
		{
		MMAnalyzer analyzer = new MMAnalyzer();		//~~~分词器
		BufferedReader reader=new BufferedReader(new FileReader("D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\foodsafety\\WebRoot\\WEB-INF\\2.txt"));
		//MMAnalyzer.addWord("迈克尔雷第");
		MMAnalyzer.addDictionary(reader);			//~~~将原始文本中的一些语料加入到分词器字典中
		String sr=analyzer.segment(s, "/");			//~~~如果s不是单个的词，就用/把词语分开
		while(sr.contains("/"))
		{
			int index=sr.indexOf("/");
			String temp=null;
			temp=sr.substring(0,index);
			sr=sr.substring(index+1);
			l=m.SearchProtege(temp,l);				//~~~一个一个单独的单词分别进行SearchProtege()操作
			//~~~每个单词s进入到protege方法中，反正经历了一些操作，返回的ResultBean的集合内容
		}
		Key=m.GetKeyWord();
		}
		//JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI("file:///c:/Users/he/Desktop/实验室/本体/新建文件夹/foodsafety.owl");
		return l;
	}
	/**
	 * 得到关键词集合中的第i个
	 * */
	public String GetKeyWord(int i)
	{
		return Key.get(i);
	}
}
