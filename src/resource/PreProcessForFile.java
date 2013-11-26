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

public class PreProcessForFile {		//~~~类似于PreProcessForHTML。java	将传过来的路径下文件处理出来 tag，time，area等信息		learned
	private String ult;
	private String S_area;
	private LinkedList<String> S_tag =new LinkedList();
	private LinkedList<String> S_time=new LinkedList();
	private LinkedList<String> S_title=new LinkedList();
	private LinkedList<String> S_address=new LinkedList();
	public PreProcessForFile(String s)
	{
		ult=s;
	}
	public void PreProcess() throws ParserException{
		Parser parser=new Parser(ult);
		parser.setEncoding("utf8");
		NodeFilter title=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","bookmark"));
		NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasChildFilter(new TagNameFilter("strong")));
		//NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("id","content"));
		NodeFilter tag=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","tag"));
		NodeFilter time=new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("class","time"));
		System.out.println(ult);
		NodeList nodelist=parser.parse(area);
		Node node_area=nodelist.elementAt(0);
		S_area=node_area.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getText();
		//S_area=node_area.getText();
		S_area=S_area.replace("&ldquo;", "");
		S_area=S_area.replace("&rdquo;", "");
		MethodForText h=new MethodForText();
		StringBuffer s=new StringBuffer(h.fiterString(S_area,":| |\"|、|：|“|”|&#8221|;|；|%|&#8220|&#822|！|Ⅱ|％|”|“|&#"));
		S_area=s.toString();
		System.out.println(S_area);
		parser.reset();
		
		nodelist=parser.parse(time);
		for(int i=0;i<nodelist.size();i++)
		{
			Node node_time=nodelist.elementAt(i);
			System.out.println(node_time.getFirstChild().toString());
			S_time.add(node_time.getFirstChild().toString());
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
			int index1=str.indexOf(":");
			String address=node_title.toString();
			S_address.add(address.substring(address.indexOf(":")+2,address.indexOf(";")));
			System.out.println(address.substring(address.indexOf(":")+2,address.indexOf(";")));
			str=str.substring(index1);
			str=str.substring(2);
			MethodForText h1=new MethodForText();
			StringBuffer s1=new StringBuffer(h1.fiterString(str,":| |\"|、|：|“|”|&#8221|;|；|%|&#8220|&#822|！|Ⅱ|％|”|“|&#"));
			str=s1.toString();
			str=str.replace("７","7");
			str=str.replace("６", "6");
			str=str.replace("５", "5");
			str=str.replace("８", "8");
			str=str.replace("４", "4");
			str=str.replace("３", "3");
			//System.out.println(str);
			S_title.add(str);
		}
		parser.reset();
		//System.out.println(S_area);
		//System.out.println(S_tag);
		//System.out.println(S_time);
		//System.out.println(S_title);
		//System.out.println(S_product);
	}
	public  LinkedList<String> getTitle()
	{
		return S_title;
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
