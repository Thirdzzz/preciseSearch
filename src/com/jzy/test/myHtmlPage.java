package com.jzy.test;

/**
 * 
 */


/**
 * @author Oliver
 *
 */
import org.htmlparser.visitors.HtmlPage;
import org.htmlparser.*;
import org.htmlparser.nodes.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.*;
import org.htmlparser.visitors.*;

public class myHtmlPage extends HtmlPage {

	/**
	 * @param args
	 */
	private String strSummery = ""; 
	public myHtmlPage(Parser parser){
		super(parser);
	}
	public void visitTag(Tag tag) {
		if(tag instanceof MetaTag) {
        	//MetaTag tag1 = (MetaTag)tag;
			String metaName = tag.getAttribute("name");
			if (metaName != null) {
				if (metaName.toLowerCase().equals("description")) {
					strSummery = tag.getAttribute("content");
				}
			}
        
        }
//		//如果有唯一的子节点且为textnode，可以考虑是不是summery或title
//		NodeList NL = tag.getChildren(); 
//		if(NL != null && NL.size() ==1
//				&& NL.elementAt(0) instanceof TextNode){
//			String temp = NL.toHtml().toLowerCase();
//			if(temp.indexOf("intro")>0 || temp.indexOf("summery")>0){
//				strSummery = NL.elementAt(0).getText();
//			}
//		}

		super.visitTag(tag);
    }
   
	public String getSummery(){
		return strSummery;
	}
}
