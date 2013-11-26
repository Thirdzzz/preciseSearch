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

public class PreProcessForASP {		//~~~类似于PreProcessForHTML。java	将传过来的路径下文件asp类型处理出来 tag，time，area等信息		learned
	private String url;
	private String S_area;
	private LinkedList<String> S_title =new LinkedList();
	private LinkedList<String> S_address=new LinkedList();
	/**
	 * 类同于PreProcessForHTML,也是讲页面信息分别采集到不同的集合中
	 * */
	public PreProcessForASP(String s)
	{
		url=s;
	}
	public void Process() throws ParserException
	{
		Parser parser=new Parser(url);
		parser.setEncoding("GB2312");
		NodeFilter area=new AndFilter( new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("width","97%")),new HasChildFilter(new TagNameFilter("a")));
		NodeFilter title=new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("target","_blank"));
		NodeList nodelist=parser.parse(area);
		Node node_area=nodelist.elementAt(0);
		S_area=node_area.getFirstChild().getFirstChild().getNextSibling().getNextSibling().toHtml();
		parser.reset();
		nodelist=parser.parse(title);
		//Node node_title=nodelist.elementAt(0);
		for(int i=0;nodelist.elementAt(i)!=null;i++)
		{
			Node node_title=nodelist.elementAt(i);
			String str=node_title.getFirstChild().toString();
			String address=node_title.toString();
			int index1=str.indexOf("&nbsp;");
			if(index1!=-1)
			{
				str=str.substring(0,index1);
			}
			System.out.println(str);
			S_title.add(str);
		
			}
		parser.reset();
	}
	public  LinkedList<String> getTitle()
	{
		return S_title;
	}
	public String getArea()
	{
		return S_area;
	}
}
