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
	//���Ŀ���Ϣ��
	
	private static String[][] FilterChars = { { "<", "&lt;" },
			{ ">", "&gt;" }, { " ", "&nbsp+" }, { "\"", "&quot;" },
			{ "&", "&amp;" }, { "/", "&#47;" }, { "\\", "&#92;" },
			{ "\n", "<br>" } ,{"'","&#39;"},{" ","&aacute;"},
			{"'","&ldquo;"},{"","&#[0-9]{2,4}?;"},{" ","\t| {2,9}"}};
	
	private static int ContextBlockSize = 300;//�ı����С

	private String strTitle = "";

	private String strSummery = "";

	private String strContext = "";

	private String strNewsDate = "";

	private String strUrl = "";
	
	private Parser myParser = null;//����ҳ�����
	
	private Block blockcontent=null;//������ȡ��Ϣ����
	
	//���˷Ƿ��ַ�
	public static void main(String args[]) throws IOException
	{
		
		
		//File input = new File("/tmp/input.html");
		
		//Document doc1 = Jsoup.parse(input, "UTF-8", "http://example.com/");
	
      Document doc=Jsoup.connect("http://news.sohu.com/20130521/n376635580.shtml").timeout(60000).get();
		
		//���htmlClass.class.getClass().getResource("/").getPath()
	  
      
	  String title=doc.title().toString();
	  Element body=doc.body();
	  System.out.println(body);
	  String sff="";
	  String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
	  Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	    Matcher m_script=p_script.matcher(body.toString()); 
	    sff=m_script.replaceAll(""); //����script��ǩ 
	    System.out.println("***********************************************************");
	    System.out.println(sff);
	  extractInfo it=new extractInfo();
	  //System.out.println(body);
	  System.out.println("***********************************************************");
	  it.executeExtract(sff,"gbk");
	  System.out.println(it.getStrContext());
	  System.out.println("���⣺"+it.getStrNewsDate());
	}
	
	public String formatString(String str) {
		for(int i = 0;i<13;i++){
			str = str.replaceAll(FilterChars[i][1],FilterChars[i][0]);
		}

		return str;
		
	}
	
	//constructor

	public extractInfo() //һ�㹹�캯��
	{
		this.myParser=new Parser();
		this.blockcontent=new Block();
	}
	
	//ִ��ҳ����ȡ����
	public void executeExtract(String strHtml, String enCoding)
	{
		try {
			myParser.setInputHTML(strHtml);
			// ���ñ���
			//myParser.getEncoding();
			myParser.setEncoding(enCoding);
			
			myHtmlPage visitor = new myHtmlPage(myParser);
			myParser.visitAllNodesWith(visitor);
			// ����title��һ����ȡ����
			strTitle = visitor.getTitle();
			
			// ����meta��ȡժҪ
			strSummery = visitor.getSummery();
			
			// body���ݽ���
			if(visitor.getBody() != null){
				
				Node[] nodes = visitor.getBody().toNodeArray();
				Node body = null;
				//�õ�Body�ڵ�
				if(nodes!=null && nodes.length>0 && nodes[0] != null)
				{
					
					body = nodes[0].getParent();
					//System.out.println(body);
				}
				
				blockcontent = extractNode(body);
				if(blockcontent != null)
					strContext = blockcontent.str.toString();
				
				if(blockcontent!=null)
				blockcontent.clear();  //���blockcontent
			}
		

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//@return 1:�ڵ㺬�е�������Ϣ��
	//@return 2:�ڵ���������Ŀ���Link��Ŀ֮��;
	public Block extractNode(Node nodeP) throws Exception {
		if(nodeP == null)
			return null;
		Block bl = new Block();//����ֵ
		if (nodeP instanceof TextNode) {
			String temp = nodeP.getText().trim().replaceAll("&nbsp;+", " ");
			if (temp.length() > 0) {
				bl.str.append(temp);
				Node parent = nodeP.getParent();
				//������ȡtitle
				if(parent != null && parent instanceof HeadingTag){
					bl.str.append("\r\n");//����
					if(parent.getText().toLowerCase().equals("h1"))
						strTitle = temp;
				}
			}
			return bl;
		}
		String tt = nodeP.getText().toLowerCase();

		//����<br> �����ϼ��뻻��
		if (nodeP instanceof TagNode && tt.indexOf("br") == 0) {
			bl.str.append("\r\n");// ����
			return bl;
		}
		
		// ����<a>;<a>��ǩ(�˴����Լ���ɸѡ���ֵܽڵ㲻��textnode���������ݲ�Ҫ����bl��
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
			//�˴�����DIV��SPAN��TABLE��TableColumn��һ��node�ڵ�ֳ����ɸ��ı����
			for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
				Node node = (Node) e.nextNode();
				//�ֿ鴦��Div...�ȸ���Ϊһ���ı��飻����֮ǰ��֮�䡢֮�������Ϊһ���ı��飩
				if (node instanceof Div || node instanceof Span
						|| node instanceof TableTag || node instanceof TableRow
						|| node instanceof TableColumn) {
					//����ı���̫С�ͺ���
					if(node.toPlainTextString().length() < ContextBlockSize)
						continue;
					if (blTmp.str.length() > 0) {// ������һ���ı���
						// ��� (link / length) > 1/80�����жϲ�������
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
			if (blTmp.str.length() > 0) {// �������һ���ı���
				//������ڵ������£����ʾ��ǰ�����˸��ڵ�����һ���ı���
				if (nodeP instanceof Div || nodeP instanceof BodyTag
						|| nodeP instanceof Span
						|| nodeP instanceof TableColumn) {
					// ��� (link / length) > 1/80�����жϲ�������
					if (blTmp.linkNum * 80 < blTmp.str.length()) {
						bl.add(blTmp);
					}
				}else
					bl.add(blTmp);
			}
			if (bl.str.length() > 0) {
				// <p>�����ϼ��뻻��
				if (nodeP instanceof ParagraphTag) {
					bl.str.append("\r\n");// ���У�����������ַ�
				}
				// ��DIV TD SPAN�����ı�������ȵ������ж�(���ڿ��Լ��Ϻ���������Ե��ж�
				if (nodeP instanceof Div || nodeP instanceof Span
						|| nodeP instanceof TableColumn) {			
					String temp = bl.str.toString().trim();
					temp = formatString(temp);
					// ͨ���������������Ŀ�
					if (temp.length() < ContextBlockSize) {
						// 1.���ǲ���ʱ��
						// 2.���ǲ���<a>���������Һ����Ŀ��ͬ���ڵ�
						// 3.�����title��summery�����ƶ�
						// 4.���ǲ���title
						return null;// ���䳤��̫С����������

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
		//�޳�copyright�Ժ󲿷�
		String regex = "\\(?[cC][oO][pP][yY][rR][iI][gG][hH][tT]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(strContext);
		if (matcher.find()) {
			strContext = strContext.substring(0, matcher.start());
		} else
			//System.out.println("not found");
			;
		//�Թ��໻�з�����
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

