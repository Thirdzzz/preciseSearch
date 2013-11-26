package resource;
import java.io.*;

import org.ictclas4j.utility.GFString;
public class ProcessForWeb {		//~~~ʹ����HtmlReader��java	HtmlRegexpUtil��java		MethodForText��java    learned
	public ProcessForWeb(){	
	}
	
	/**
	 * ��ȡ·���ļ���ȫ������
	 * */
	public StringBuffer ReadFile(String storepos)				//~~~��ȡ����·��storepos��html����������
	{
		HtmlReader reader=new HtmlReader();
		StringBuffer s=new StringBuffer(reader.readHtml(storepos));			//~~~storepos��filepath�ļ�·��
		return s;
	}
	/**
	 * ������д�뵽Ŀ��·���ļ���
	 * */
	public boolean WriteFile(String content,String storepos)		//~~~������contentд�뵽·��storepos�µ��ļ�
	{
		try{
			FileWriter fw=new FileWriter(storepos);			//~~~д��Ŀ��·���ļ�storepos
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
	 * //~~~��ô������������Ŀ���ı��е�����<>���޳��ˣ�����
	 * */
	public StringBuffer SubContentBufferAll(StringBuffer contentSub )
	{
		HtmlRegexpUtil h=new HtmlRegexpUtil();
		StringBuffer s=new StringBuffer(h.replaceHtmlTag(contentSub.toString(), "title", "<�ص�", ">"));
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/title", "</�ص�", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "h", "<�ص�", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "/h", "</�ص�", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "strong", "<�ص�", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/strong", "</�ص�", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "a", "<�ص�", ">");
		//contentSub=s;
		//s=h.replaceHtmlTag(contentSub.toString(), "/a", "</�ص�", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "p", "<�ص�", ">");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "/p", "</�ص�", ">");
		contentSub=s;
		s=h.replaceHtmlTag1(contentSub.toString(), "meta","content","<�ص�>", "</�ص�>");
		contentSub=s;
		s=h.replaceHtmlTag(contentSub.toString(), "meta", "</�ص�", ">");
		//	new StringBuffer(h.filterHtml(contentSub.toString()));
		s=h.fiterHtmlTag(s.toString(), "([^�ص�]*)");
		s=h.filterHtml(s.toString());					//~~~��ô������������Ŀ���ı��е�����<>���޳��ˣ�����
		return s;
	
	}
	
	/**
	 * //~~~�����˼Ӧ���ǰ��ַ����д����ŵĶ���ȥ����
	 * */
	public StringBuffer DelOther(StringBuffer content)
	{
		MethodForText h=new MethodForText();
		StringBuffer s=new StringBuffer(h.fiterString(content.toString(),"([!-~]|��|��|��|-|��|��|��|��|��|��|/?|��|��|��)"));
		return s;															//~~~�����˼Ӧ���ǰ��ַ����д����ŵĶ���ȥ����
	}
	
	/**
	 * ~~~��������ȥ���ַ����еĿո������˼������û��̫��
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
