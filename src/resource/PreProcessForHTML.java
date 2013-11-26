package resource;

import java.util.LinkedList;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class PreProcessForHTML {		//~~~learned

	private String ult;			//~~~���ݽ�����html�ļ��ľ���·��
	private String S_area;
	private LinkedList<String> S_tag =new LinkedList();
	private LinkedList<String> S_time=new LinkedList();
	private String S_product;
	private LinkedList<String> S_title=new LinkedList();
	private LinkedList<String> S_address=new LinkedList();
	
	/**
	 * ������ ����·��s��������˽�б���ult
	 * */
	public PreProcessForHTML(String s)
	{
		ult=s;
	}
	
	/**
	 * �Դ�����·���µ�html�ļ����д���
	 * �Ѵ���html�ı���õ���tag�ڵ㣬area�ڵ㣬time�ڵ�ȵȷֱ��¼�ڰ�
	 * ͨ�������get()�������������	
	 * */
	public void PreProcess() throws ParserException{
		Parser parser=new Parser(ult);		//~~~htmlparser.Parser�ǿ�����������ʱ����HTMLҳ�洦��ĺܺ��õ�һ������
		parser.setEncoding("utf8");			//~~~org.htmlparser.Parserû���ҵ�������
		NodeFilter title=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","bookmark"));	//~~~AndFilter���԰�����Filter������ϣ�ֻ��ͬʱ����������Node�Żᱻ���ˡ�����˵������ѡ��tgaΪa�����������а���rel��bookmark
		NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasChildFilter(new TagNameFilter("strong")));	//~~~HasChildFilter�Ƿ����з��������ӽڵ�Ľڵ㣬ʵ���Ϸ��ص��Ǹ��׽ڵ㣬���ĺ�����Ҫ����tag = strong
		//NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("id","content"));
		NodeFilter tag=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","tag"));
		NodeFilter time=new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("class","time"));
		System.out.println(ult);
		NodeList nodelist=parser.parse(area);	//~~~��������area�����нڵ㼯�ϣ�����
		Node node_area=nodelist.elementAt(0);
		S_area=node_area.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getText();
		//S_area=node_area.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getText();
		parser.reset();
		
		nodelist=parser.parse(time);
		for(int i=0;i<nodelist.size();i++)
		{
			Node node_time=nodelist.elementAt(i);
			System.out.println(node_time.getFirstChild().toString());	
			S_time.add(node_time.getFirstChild().toString());		//~~~��÷���time�����Ľڵ�ĺ���
		}
		
		parser.reset();
		nodelist=parser.parse(tag);
		for(int i=0;i<nodelist.size();i++)
		{	
			Node node_tag=nodelist.elementAt(i);
			System.out.println(node_tag.getFirstChild().toString());
			S_tag.add(node_tag.getFirstChild().toString());
		}
		parser.reset();
		nodelist=parser.parse(title);
		//Node node_title=nodelist.elementAt(0);
		for(int i=0;i<nodelist.size();i++)
		{
			Node node_title=nodelist.elementAt(i);
			String str=node_title.getFirstChild().toString();
			String address=node_title.toString();
			S_address.add(address.substring(address.indexOf(":")+2,address.indexOf(";")));	//~~~�����ַaddress��Ϣ
			System.out.println(address.substring(address.indexOf(":")+2,address.indexOf(";")));
			int index1=str.indexOf(":");
			str=str.substring(index1);
			str=str.substring(2);
			MethodForText h=new MethodForText();
			StringBuffer s=new StringBuffer(h.fiterString(str,":| |\"|��|��|��|��|&#8221|;|��|%|&#8220|&#822|��|��|��|��|��|&#"));
			str=s.toString();
			str=str.replace("��","7");
			str=str.replace("��", "6");
			str=str.replace("��", "5");
			str=str.replace("��", "8");
			str=str.replace("��", "4");
			str=str.replace("��", "3");
			//System.out.println(str);
			S_title.add(str);
		}
		parser.reset();
		//System.out.println(S_area);
		//System.out.println(S_tag);
		//System.out.println(S_time);
		//System.out.println(S_title);
		int index=S_area.indexOf("-");
		S_product=S_area.substring(index);
		S_product=S_product.substring(2);
		S_area=S_area.substring(0, index);
		//System.out.println(S_product);
		index=S_title.indexOf(":");
	}
	public  LinkedList<String> getTitle()
	{
		return S_title;
	}
	public String getProduct()
	{
		return S_product;
	}
	public String getArea()
	{
		return S_area;
	}
	public LinkedList<String> getTime()
	{
		return S_time;
	}
	public LinkedList<String> getTag()
	{
		return S_tag;
	}
	public  LinkedList<String> getAddress()
	{
		return S_address;
	}
}
