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

	private String ult;			//~~~传递进来的html文件的绝对路径
	private String S_area;
	private LinkedList<String> S_tag =new LinkedList();
	private LinkedList<String> S_time=new LinkedList();
	private String S_product;
	private LinkedList<String> S_title=new LinkedList();
	private LinkedList<String> S_address=new LinkedList();
	
	/**
	 * 将参数 绝对路径s传给类中私有变量ult
	 * */
	public PreProcessForHTML(String s)
	{
		ult=s;
	}
	
	/**
	 * 对传过来路径下的html文件进行处理
	 * 把处理html文本后得到的tag节点，area节点，time节点等等分别记录在案
	 * 通过后面的get()函数来返回输出	
	 * */
	public void PreProcess() throws ParserException{
		Parser parser=new Parser(ult);		//~~~htmlparser.Parser是开发搜索引擎时，对HTML页面处理的很好用的一个东西
		parser.setEncoding("utf8");			//~~~org.htmlparser.Parser没有找到？？？
		NodeFilter title=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","bookmark"));	//~~~AndFilter可以把两种Filter进行组合，只有同时满足条件的Node才会被过滤。比如说这里是选择tga为a，并且属性中包括rel和bookmark
		NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasChildFilter(new TagNameFilter("strong")));	//~~~HasChildFilter是返回有符合条件子节点的节点，实际上返回的是父亲节点，它的孩子需要满足tag = strong
		//NodeFilter area=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("id","content"));
		NodeFilter tag=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("rel","tag"));
		NodeFilter time=new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("class","time"));
		System.out.println(ult);
		NodeList nodelist=parser.parse(area);	//~~~满足条件area的所有节点集合？？？
		Node node_area=nodelist.elementAt(0);
		S_area=node_area.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getText();
		//S_area=node_area.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getText();
		parser.reset();
		
		nodelist=parser.parse(time);
		for(int i=0;i<nodelist.size();i++)
		{
			Node node_time=nodelist.elementAt(i);
			System.out.println(node_time.getFirstChild().toString());	
			S_time.add(node_time.getFirstChild().toString());		//~~~获得符合time条件的节点的孩子
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
			S_address.add(address.substring(address.indexOf(":")+2,address.indexOf(";")));	//~~~加入地址address信息
			System.out.println(address.substring(address.indexOf(":")+2,address.indexOf(";")));
			int index1=str.indexOf(":");
			str=str.substring(index1);
			str=str.substring(2);
			MethodForText h=new MethodForText();
			StringBuffer s=new StringBuffer(h.fiterString(str,":| |\"|、|：|“|”|&#8221|;|；|%|&#8220|&#822|！|Ⅱ|％|”|“|&#"));
			str=s.toString();
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
