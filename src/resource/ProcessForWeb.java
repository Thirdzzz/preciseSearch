package resource;
import java.io.*;

import org.ictclas4j.utility.GFString;
public class ProcessForWeb {		//~~~使用了HtmlReader。java	HtmlRegexpUtil。java		MethodForText。java    learned
	public ProcessForWeb(){	
	}
	
	/**
	 * 读取路径文件的全部内容
	 * */
	public StringBuffer ReadFile(String storepos)				//~~~获取的是路径storepos下html的所有内容
	{
		HtmlReader reader=new HtmlReader();
		StringBuffer s=new StringBuffer(reader.readHtml(storepos));			//~~~storepos是filepath文件路径
		return s;
	}
	/**
	 * 将内容写入到目标路径文件中
	 * */
	public boolean WriteFile(String content,String storepos)		//~~~将内容content写入到路径storepos下的文件
	{
		try{
			FileWriter fw=new FileWriter(storepos);			//~~~写入目的路径文件storepos
			fw.write(content);
			fw.flush();
			return true;
	}
		catch (IOException e)
		{
			return false;
		}
	}
	
	/**
	 * //~~~怎么看着这个好像把目标文本中的所有<>都剔除了？？？
	 * */
	public StringBuffer SubContentBufferAll(StringBuffer contentSub )
	{
		HtmlRegexpUtil h=new HtmlRegexpUtil();
		StringBuffer s=new StringBuffer(h.replaceHtmlTag(contentSub.toString(), "title", "<重点", ">"));
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/title", "</重点", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "h", "<重点", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "/h", "</重点", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "strong", "<重点", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/strong", "</重点", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "a", "<重点", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "/a", "</重点", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "p", "<重点", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/p", "</重点", ">");
		contentSub=s;
		s=h.replaceHtmlTag1(contentSub.toString(), "meta","content","<重点>", "</重点>");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "meta", "</重点", ">");
		//	new StringBuffer(h.filterHtml(contentSub.toString()));
		s=h.fiterHtmlTag(s.toString(), "([^重点]*)");
		s=h.filterHtml(s.toString());					//~~~怎么看着这个好像把目标文本中的所有<>都剔除了？？？
		return s;
	
	}
	
	/**
	 * //~~~这个意思应该是把字符串中带符号的都给去除了
	 * */
	public StringBuffer DelOther(StringBuffer content)
	{
		MethodForText h=new MethodForText();
		StringBuffer s=new StringBuffer(h.fiterString(content.toString(),"([!-~]|，|。|、|-|（|）|－|《|》|？|/?|：|；|：)"));
		return s;															//~~~这个意思应该是把字符串中带符号的都给去除了
	}
	
	/**
	 * ~~~看着像是去除字符串中的空格符的意思？？？没看太懂
	 * */
	public StringBuffer DelSpace(StringBuffer any)
	{
		MethodForText h=new MethodForText();
		StringBuffer s=new StringBuffer(h.fiterString(any.toString()," |/?|	"));
		GFString g=new GFString();
		StringBuffer str=new StringBuffer(g.removeSpace(s.toString()));
		return str;
	}
	
}
