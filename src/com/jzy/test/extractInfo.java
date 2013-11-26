package com.jzy.test;
/**
 * 
 */


/**
 * @author Oliver
 *
 */

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.*;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.regex.*;


public class extractInfo {
	//正文块信息类
	
	private static String[][] FilterChars = { { "<", "&lt;" },
			{ ">", "&gt;" }, { " ", "&nbsp+" }, { "\"", "&quot;" },
			{ "&", "&amp;" }, { "/", "&#47;" }, { "\\", "&#92;" },
			{ "\n", "<br>" } ,{"'","&#39;"},{" ","&aacute;"},
			{"'","&ldquo;"},{"","&#[0-9]{2,4}?;"},{" ","\t| {2,9}"}};
	
	private static int ContextBlockSize = 300;//文本块大小

	private String strTitle = "";

	private String strSummery = "";

	private String strContext = "";

	private String strNewsDate = "";

	private String strUrl = "";
	
	private Parser myParser = null;//解析页面对象
	
	private Block blockcontent=null;//分析获取信息对象
	
	//过滤非法字符
	public static void main(String args[]) throws IOException
	{
		
		
		//File input = new File("/tmp/input.html");
		
		//Document doc1 = Jsoup.parse(input, "UTF-8", "http://example.com/");
	
      Document doc=Jsoup.connect("http://news.sohu.com/20130521/n376635580.shtml").timeout(60000).get();
		
		//输出htmlClass.class.getClass().getResource("/").getPath()
	  
      
	  String title=doc.title().toString();
	  Element body=doc.body();
	  System.out.println(body);
	  String sff="";
	  String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
	  Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	    Matcher m_script=p_script.matcher(body.toString()); 
	    sff=m_script.replaceAll(""); //过滤script标签 
	    System.out.println("***********************************************************");
	    System.out.println(sff);
	  extractInfo it=new extractInfo();
	  //System.out.println(body);
	  System.out.println("***********************************************************");
	  it.executeExtract(sff,"gbk");
	  System.out.println(it.getStrContext());
	  System.out.println("标题："+it.getStrNewsDate());
	}
	
	public String formatString(String str) {
		for(int i = 0;i<13;i++){
			str = str.replaceAll(FilterChars[i][1],FilterChars[i][0]);
		}

		return str;
		
	}
	
	//constructor

	public extractInfo() //一般构造函数
	{
		this.myParser=new Parser();
		this.blockcontent=new Block();
	}
	
	//执行页面析取操作
	public void executeExtract(String strHtml, String enCoding)
	{
		try {
			myParser.setInputHTML(strHtml);
			// 设置编码
			//myParser.getEncoding();
			myParser.setEncoding(enCoding);
			
			myHtmlPage visitor = new myHtmlPage(myParser);
			myParser.visitAllNodesWith(visitor);
			// 根据title第一次提取标题
			strTitle = visitor.getTitle();
			
			// 根据meta提取摘要
			strSummery = visitor.getSummery();
			
			// body内容解析
			if(visitor.getBody() != null){
				
				Node[] nodes = visitor.getBody().toNodeArray();
				Node body = null;
				//得到Body节点
				if(nodes!=null && nodes.length>0 && nodes[0] != null)
				{
					
					body = nodes[0].getParent();
					//System.out.println(body);
				}
				
				blockcontent = extractNode(body);
				if(blockcontent != null)
					strContext = blockcontent.str.toString();
				
				if(blockcontent!=null)
				blockcontent.clear();  //清空blockcontent
			}
		

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//@return 1:节点含有的正文信息；
	//@return 2:节点包含的正文块中Link数目之和;
	public Block extractNode(Node nodeP) throws Exception {
		if(nodeP == null)
			return null;
		Block bl = new Block();//返回值
		if (nodeP instanceof TextNode) {
			String temp = nodeP.getText().trim().replaceAll("&nbsp;+", " ");
			if (temp.length() > 0) {
				bl.str.append(temp);
				Node parent = nodeP.getParent();
				//重新提取title
				if(parent != null && parent instanceof HeadingTag){
					bl.str.append("\r\n");//换行
					if(parent.getText().toLowerCase().equals("h1"))
						strTitle = temp;
				}
			}
			return bl;
		}
		String tt = nodeP.getText().toLowerCase();

		//处理<br> 内容上加入换行
		if (nodeP instanceof TagNode && tt.indexOf("br") == 0) {
			bl.str.append("\r\n");// 换行
			return bl;
		}
		
		// 处理<a>;<a>标签(此处可以加以筛选，兄弟节点不是textnode的链接内容不要放入bl中
		if (nodeP instanceof LinkTag ) {
			bl.linkNum++;
			if (tt.indexOf("a") == 0) {
					String tmp = nodeP.toPlainTextString().trim();
					if (tmp.length() > 0) {
						bl.str.append(tmp);
						bl.linkLen += tmp.length();
					}
				
			}
			return bl;
		}
		
		if (nodeP instanceof Div || nodeP instanceof BodyTag
				|| nodeP instanceof TableTag || nodeP instanceof FormTag
				|| nodeP instanceof Span || nodeP instanceof ParagraphTag
				|| nodeP instanceof TableColumn || nodeP instanceof TableRow
				||nodeP instanceof HeadingTag) {
			Block blTmp = new Block();
			NodeList nodeList = nodeP.getChildren();
			// is empty
			if ((nodeList == null) || (nodeList.size() == 0)) {
				return null;
			}
			//此处根据DIV、SPAN、TABLE、TableColumn将一个node节点分成若干个文本块儿
			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
				Node node = (Node) e.nextNode();
				//分块处理（Div...等各自为一个文本块；它们之前和之间、之后合起来为一个文本块）
				if (node instanceof Div || node instanceof Span
						|| node instanceof TableTag || node instanceof TableRow
						|| node instanceof TableColumn) {
					//如果文本块太小就忽略
					if(node.toPlainTextString().length() < ContextBlockSize)
						continue;
					if (blTmp.str.length() > 0) {// 处理上一个文本块
						// 如果 (link / length) > 1/80，则判断不是正文
						if (blTmp.linkNum * 80 < blTmp.str.length()) {
							bl.add(blTmp);
						}
						blTmp.clear();
					}
					Block blTmp1 = extractNode(node);
					if (blTmp1 != null) {
						bl.add(blTmp1);//<Div>
					}

				} else {
					Block blTmp1 = extractNode(node);
					if (blTmp1 != null) {
						blTmp.add(blTmp1);// <P>
					}
				}
			}
			if (blTmp.str.length() > 0) {// 处理最后一个文本块
				//如果父节点是如下，则表示当前遇到了父节点的最后一个文本块
				if (nodeP instanceof Div || nodeP instanceof BodyTag
						|| nodeP instanceof Span
						|| nodeP instanceof TableColumn) {
					// 如果 (link / length) > 1/80，则判断不是正文
					if (blTmp.linkNum * 80 < blTmp.str.length()) {
						bl.add(blTmp);
					}
				}else
					bl.add(blTmp);
			}
			if (bl.str.length() > 0) {
				// <p>内容上加入换行
				if (nodeP instanceof ParagraphTag) {
					bl.str.append("\r\n");// 换行，并左空两个字符
				}
				// 对DIV TD SPAN进行文本块儿长度的正文判断(后期可以加上和主题相关性的判断
				if (nodeP instanceof Div || nodeP instanceof Span
						|| nodeP instanceof TableColumn) {			
					String temp = bl.str.toString().trim();
					temp = formatString(temp);
					// 通过长度来区别正文块
					if (temp.length() < ContextBlockSize) {
						// 1.看是不是时间
						// 2.看是不是<a>链接内容且和正文块儿同父节点
						// 3.计算和title或summery的相似度
						// 4.看是不是title
						return null;// 段落长度太小，不是正文

					}
					else
					{
						bl.str.delete(0,bl.str.length()); 
						bl.str.append(temp);
					}
				}
				return bl;
			}
		}
		return null;
	}
	public String getStrContext() {
		//剔除copyright以后部分
		String regex = "\\(?[cC][oO][pP][yY][rR][iI][gG][hH][tT]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(strContext);
		if (matcher.find()) {
			strContext = strContext.substring(0, matcher.start());
		} else
			//System.out.println("not found");
			;
		//对过多换行符过滤
		strContext = strContext.replaceAll("[\r\n]{2,100}","\r\n");
		return strContext;
	}

	public String getStrNewsDate() {
		return strNewsDate;
	}

	public String getStrSummery() {
		if(strSummery.length()>50)
			return strSummery;
		else{
			String tmp = null;
			if(strContext.length() > 100){
				tmp = strContext.substring(0,100);
			}else
				tmp =strContext.toString();
			
			
			if(strSummery.length()> 0)
				tmp = strSummery +"."+ tmp;
			else
				tmp = strTitle +"." + tmp;
			tmp = tmp.replaceAll("[\r\n]{2,100}",".");
			//tmp += "......";
			return tmp;
		}
	}

	public String getStrTitle() {
		return strTitle;
	}

	public String getStrUrl() {
		return strUrl;
	}

}

