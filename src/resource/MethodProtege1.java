package resource;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hp.hpl.jena.util.FileUtils;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;

public class MethodProtege1 extends HttpServlet {	//~~~略同MethodProtege.java	learned		

	/**
	 * Constructor of the object.
	 */
	public MethodProtege1() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");
		try {
			String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
			//System.out.println(curPath);
			//JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI(curPath + "../../ontology/foodsafety.owl");
			//JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI("file:///c:/Users/he/Desktop/实验室/本体/新建文件夹/foodsafety.owl");
			HttpSession session=request.getSession(true);
			JenaOWLModel owlModel=(JenaOWLModel)session.getAttribute("Protege");
			List<String>l=(List)session.getAttribute("memory");
			for(int k=0;k<l.size();k++)
			{
				String path=l.get(k);
				File fs =new File(path);
				if(fs.getName().toLowerCase().endsWith(".html"))
				{
					PreProcessForHTML h1=new PreProcessForHTML(fs.getAbsolutePath());
					h1.PreProcess();
					OWLNamedClass t = owlModel.getOWLNamedClass("foodsafety:食品安全事件");
					OWLNamedClass t1 = owlModel.getOWLNamedClass("foodsafety:问题食品");
					OWLDatatypeProperty hasContactProperty8 = owlModel.getOWLDatatypeProperty("foodsafety:详情");
					OWLIndividual individual1;
					OWLIndividual individual2;
					int station1=1;
					if(owlModel.getOWLIndividual(h1.getProduct())==null)
					{
						System.out.println(t1);
						individual1 = t1.createOWLIndividual(h1.getProduct());
					}
					else
					{
						individual1 = owlModel.getOWLIndividual(h1.getProduct());
						station1=0;
					}
					int j=0;
					for(int i=0;i<h1.getTitle().size();i++)
					{
						int station2=1;
						String s=h1.getTitle().get(i).toString();
						int b=s.indexOf(":");
						if(owlModel.getOWLIndividual(s)==null)
						{
							individual2 = t.createOWLIndividual(s);
						}
						else
						{
							individual2 = owlModel.getOWLIndividual(s);
							station2=0;
							System.out.println("出现重复！");
						}
						OWLObjectProperty hasContactProperty = owlModel.getOWLObjectProperty("foodsafety:causedby");
						OWLDatatypeProperty hasContactProperty1 = owlModel.getOWLDatatypeProperty("foodsafety:地点");
						OWLDatatypeProperty hasContactProperty2 = owlModel.getOWLDatatypeProperty("foodsafety:日期");
						OWLDatatypeProperty hasContactProperty3 = owlModel.getOWLDatatypeProperty("foodsafety:关键词");
						OWLDatatypeProperty hasContactProperty5 = owlModel.getOWLDatatypeProperty("foodsafety:名称");
						OWLDatatypeProperty hasContactProperty6 = owlModel.getOWLDatatypeProperty("foodsafety:相关单位");
						OWLDatatypeProperty hasContactProperty7 = owlModel.getOWLDatatypeProperty("foodsafety:应对");
						
						OWLObjectProperty hasContactProperty4=owlModel.getOWLObjectProperty("foodsafety:cause");
							individual1.addPropertyValue(hasContactProperty4, individual2);
						System.out.println(station2);
							if(station2==1)
						{
						individual2.setPropertyValue(hasContactProperty, individual1);
						individual2.setPropertyValue(hasContactProperty1, h1.getArea());
						individual2.setPropertyValue(hasContactProperty5, s);
						individual2.setPropertyValue(hasContactProperty6, "无");
						individual2.setPropertyValue(hasContactProperty7, "无");
						individual2.setPropertyValue(hasContactProperty8, h1.getAddress().get(i));
						s=h1.getTime().get(i).toString();
						b=s.indexOf(":");
						s=s.substring(b);
						s=s.substring(2);
						System.out.println(s);
						individual2.setPropertyValue(hasContactProperty2,s);
						int station=0;
						for(;j<h1.getTag().size();j++)
						{	    	
							String str=h1.getTag().get(j).toString();
							int b1=str.indexOf(":");
							str=str.substring(b1);
							str=str.substring(2);
							if(str.contains("网")||str.contains("报")||str.contains("新浪")||str.contains("搜狐")||str.contains("新闻")||str.contains("网友补充")||str.contains("在线")||str.contains("CCTV"))
							{
								j++;
								j++;
								break;
							}
							else
							{
								if(station!=0)
								{
									System.out.print("  "+str);
									individual2.addPropertyValue(hasContactProperty3,str);
								}
								else{
									System.out.print("  "+str);
									individual2.setPropertyValue(hasContactProperty3,str);
									station=1;
								}
							}	
						}
					}
					else
					{
						individual2.addPropertyValue(hasContactProperty, individual1);
					}
					}	
			    } 
				else if(fs.getName().toLowerCase().endsWith(".asp"))
				{
					
					PreProcessForASP h1=new PreProcessForASP(fs.getAbsolutePath());
					h1.Process();
					OWLNamedClass t = owlModel.getOWLNamedClass("foodsafety:食品安全事件");
					OWLNamedClass t1 = owlModel.getOWLNamedClass("foodsafety:致毒物质");
					OWLDatatypeProperty hasContactProperty2 = owlModel.getOWLDatatypeProperty("foodsafety:危害");
					OWLDatatypeProperty hasContactProperty3 = owlModel.getOWLDatatypeProperty("foodsafety:基本信息");
					OWLDatatypeProperty hasContactProperty4 = owlModel.getOWLDatatypeProperty("foodsafety:用途");
					OWLDatatypeProperty hasContactProperty5 = owlModel.getOWLDatatypeProperty("foodsafety:简介");
					OWLDatatypeProperty hasContactProperty6 = owlModel.getOWLDatatypeProperty("foodsafety:急救治疗");
					OWLDatatypeProperty hasContactProperty7 = owlModel.getOWLDatatypeProperty("foodsafety:类别");
					OWLDatatypeProperty hasContactProperty8 = owlModel.getOWLDatatypeProperty("foodsafety:检测方法");
					OWLDatatypeProperty hasContactProperty9 = owlModel.getOWLDatatypeProperty("foodsafety:识别");
					OWLDatatypeProperty hasContactProperty10 = owlModel.getOWLDatatypeProperty("foodsafety:概述");
					OWLDatatypeProperty hasContactProperty11 = owlModel.getOWLDatatypeProperty("foodsafety:预防");
					OWLDatatypeProperty hasContactProperty12 = owlModel.getOWLDatatypeProperty("foodsafety:临床表现");
					OWLDatatypeProperty hasContactProperty13 = owlModel.getOWLDatatypeProperty("foodsafety:物质名称");
					OWLIndividual individual1;
					OWLIndividual individual2;
					if(owlModel.getOWLIndividual(h1.getArea())==null)
					{
						individual1 = t1.createOWLIndividual(h1.getArea());
						individual1.setPropertyValue(hasContactProperty2, "无");
						individual1.setPropertyValue(hasContactProperty3, "无");
						individual1.setPropertyValue(hasContactProperty4, "无");
						individual1.setPropertyValue(hasContactProperty5, "无");
						individual1.setPropertyValue(hasContactProperty6, "无");
						individual1.setPropertyValue(hasContactProperty7, "无");
						individual1.setPropertyValue(hasContactProperty8, "无");
						individual1.setPropertyValue(hasContactProperty9, "无");
						individual1.setPropertyValue(hasContactProperty10, "无");
						individual1.setPropertyValue(hasContactProperty11, "无");
						individual1.setPropertyValue(hasContactProperty12, "无");
						individual1.setPropertyValue(hasContactProperty13, h1.getArea());
					}
					else
						individual1 = owlModel.getOWLIndividual(h1.getArea());
					for(int i=0;i<h1.getTitle().size();i++)
					{
						String s=h1.getTitle().get(i).toString();
			    	int b=s.indexOf(":");
			    	s=s.substring(b);
			    	s=s.substring(2);
			    	MethodForText h=new MethodForText();
					StringBuffer s1=new StringBuffer(h.fiterString(s,":| |\"|、|：|“|”|&quot|;|；|&nbs|%|/?|？|Ⅱ|Ⅱ"));
					s=s1.toString();
			    	System.out.println(s);
			    	OWLDatatypeProperty hasContactProperty14 = owlModel.getOWLDatatypeProperty("foodsafety:地点");
					OWLDatatypeProperty hasContactProperty15 = owlModel.getOWLDatatypeProperty("foodsafety:日期");
					OWLDatatypeProperty hasContactProperty16 = owlModel.getOWLDatatypeProperty("foodsafety:关键词");
					OWLDatatypeProperty hasContactProperty17 = owlModel.getOWLDatatypeProperty("foodsafety:名称");
					OWLDatatypeProperty hasContactProperty18 = owlModel.getOWLDatatypeProperty("foodsafety:相关单位");
					OWLDatatypeProperty hasContactProperty19 = owlModel.getOWLDatatypeProperty("foodsafety:应对");
					OWLDatatypeProperty hasContactProperty20 = owlModel.getOWLDatatypeProperty("foodsafety:详情");
					
			    	if(owlModel.getOWLIndividual(s)==null)
			    	{
			    		individual2 = t.createOWLIndividual(s);
			    		individual2.setPropertyValue(hasContactProperty14, "无");
			    		individual2.setPropertyValue(hasContactProperty15, "无");
			    		individual2.setPropertyValue(hasContactProperty16, "无");
			    		individual2.setPropertyValue(hasContactProperty17, s);
			    		individual2.setPropertyValue(hasContactProperty18, "无");
			    		individual2.setPropertyValue(hasContactProperty19, "无");
			    		individual2.setPropertyValue(hasContactProperty20, "无");
			    	}
			    	else
			    		individual2 = owlModel.getOWLIndividual(s);
			    	OWLObjectProperty hasContactProperty = owlModel.getOWLObjectProperty("foodsafety:relatedto");
			    	OWLObjectProperty hasContactProperty1 = owlModel.getOWLObjectProperty("foodsafety:relate");
			    	individual2.addPropertyValue(hasContactProperty, individual1);
			    	individual1.addPropertyValue(hasContactProperty1, individual2);
					}
				}
				else if(fs.getName().toLowerCase().endsWith(".htm"))
				{
					PreProcessForFile h1=new PreProcessForFile(fs.getAbsolutePath());
					h1.PreProcess();
					OWLNamedClass t = owlModel.getOWLNamedClass("foodsafety:食品安全事件");
					OWLNamedClass t1 = owlModel.getOWLNamedClass("foodsafety:致毒物质");
					OWLDatatypeProperty hasContactProperty22 = owlModel.getOWLDatatypeProperty("foodsafety:危害");
					OWLDatatypeProperty hasContactProperty23 = owlModel.getOWLDatatypeProperty("foodsafety:基本信息");
					OWLDatatypeProperty hasContactProperty24 = owlModel.getOWLDatatypeProperty("foodsafety:用途");
					OWLDatatypeProperty hasContactProperty5 = owlModel.getOWLDatatypeProperty("foodsafety:简介");
					OWLDatatypeProperty hasContactProperty6 = owlModel.getOWLDatatypeProperty("foodsafety:急救治疗");
					OWLDatatypeProperty hasContactProperty7 = owlModel.getOWLDatatypeProperty("foodsafety:类别");
					OWLDatatypeProperty hasContactProperty8 = owlModel.getOWLDatatypeProperty("foodsafety:检测方法");
					OWLDatatypeProperty hasContactProperty9 = owlModel.getOWLDatatypeProperty("foodsafety:识别");
					OWLDatatypeProperty hasContactProperty10 = owlModel.getOWLDatatypeProperty("foodsafety:概述");
					OWLDatatypeProperty hasContactProperty11 = owlModel.getOWLDatatypeProperty("foodsafety:预防");
					OWLDatatypeProperty hasContactProperty12 = owlModel.getOWLDatatypeProperty("foodsafety:临床表现");
					OWLDatatypeProperty hasContactProperty13 = owlModel.getOWLDatatypeProperty("foodsafety:物质名称");
					OWLIndividual individual1;
					OWLIndividual individual2;
					int station3=1;
					if(owlModel.getOWLIndividual(h1.getArea())==null)
					{
						System.out.println(t1);
						individual1 = t1.createOWLIndividual(h1.getArea());
						individual1.setPropertyValue(hasContactProperty22, "无");
						individual1.setPropertyValue(hasContactProperty23, "无");
						individual1.setPropertyValue(hasContactProperty24, "无");
						individual1.setPropertyValue(hasContactProperty5, "无");
						individual1.setPropertyValue(hasContactProperty6, "无");
						individual1.setPropertyValue(hasContactProperty7, "无");
						individual1.setPropertyValue(hasContactProperty8, "无");
						individual1.setPropertyValue(hasContactProperty9, "无");
						individual1.setPropertyValue(hasContactProperty10, "无");
						individual1.setPropertyValue(hasContactProperty11, "无");
						individual1.setPropertyValue(hasContactProperty12, "无");
						individual1.setPropertyValue(hasContactProperty13, h1.getArea());
						station3=0;
					}
					else
						individual1 = owlModel.getOWLIndividual(h1.getArea());
					int j=0;
					for(int i=0;i<h1.getTitle().size();i++)
					{
						int station1=1;
						int station=0;
						String s=h1.getTitle().get(i).toString();
						int b=s.indexOf(":");
						if(owlModel.getOWLIndividual(s)==null)
						{
							individual2 = t.createOWLIndividual(s);
						}
						else
						{
							individual2 = owlModel.getOWLIndividual(s);
							station1=0;
							station=1;
						}
					
						OWLObjectProperty hasContactProperty = owlModel.getOWLObjectProperty("foodsafety:relatedto");
						//OWLObjectProperty hasContactProperty4 = owlModel.getOWLObjectProperty("foodsafety:相关");
						OWLDatatypeProperty hasContactProperty2 = owlModel.getOWLDatatypeProperty("foodsafety:日期");
						OWLDatatypeProperty hasContactProperty3 = owlModel.getOWLDatatypeProperty("foodsafety:关键词");
						OWLDatatypeProperty hasContactProperty14 = owlModel.getOWLDatatypeProperty("foodsafety:地点");
						OWLDatatypeProperty hasContactProperty17 = owlModel.getOWLDatatypeProperty("foodsafety:名称");
						OWLDatatypeProperty hasContactProperty18 = owlModel.getOWLDatatypeProperty("foodsafety:相关单位");
						OWLDatatypeProperty hasContactProperty19 = owlModel.getOWLDatatypeProperty("foodsafety:应对");
						OWLDatatypeProperty hasContactProperty20 = owlModel.getOWLDatatypeProperty("foodsafety:详情");
						if(station1==1)
						{
							individual2.setPropertyValue(hasContactProperty14, "无");
							individual2.setPropertyValue(hasContactProperty17, s);
							individual2.setPropertyValue(hasContactProperty18, "无");
							individual2.setPropertyValue(hasContactProperty19, "无");
							individual2.setPropertyValue(hasContactProperty20, h1.getAddress().get(i));
							individual2.setPropertyValue(hasContactProperty, individual1);
						}
						else
							individual2.addPropertyValue(hasContactProperty, individual1);
						
						s=h1.getTime().get(i).toString();
						b=s.indexOf(":");
						s=s.substring(b);
						s=s.substring(2);
						System.out.println(s);
						individual2.setPropertyValue(hasContactProperty2, s);
						/*if(station3==0){
							individual1.setPropertyValue(hasContactProperty4, individual2);
							
							station3=1;
						}
						else
							individual1.addPropertyValue(hasContactProperty4, individual2);
						*/
						for(;j<h1.getTag().size();j++)
						{	    	
							String str=h1.getTag().get(j).toString();
							int b1=str.indexOf(":");
							str=str.substring(b1);
							str=str.substring(2);
							if(str.contains("网")||str.contains("报")||str.contains("新浪")||str.contains("搜狐")||str.contains("新闻")||str.contains("网友补充")||str.contains("在线")||str.contains("CCTV"))
							{
								j++;
								j++;
								break;
							}
							else
							{
								if(station!=0)
								{
									System.out.print("  "+str);
									individual2.addPropertyValue(hasContactProperty3,str);
									
								}
								else{
									System.out.print("  "+str);
									individual2.setPropertyValue(hasContactProperty3,str);
									station=1;
								}
							}	
						}
					}
				}
				}	
			Collection errors = new ArrayList();
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("GB2312");
			out
					.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.print("    扩展成功！ ");
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
			URI uri = new URI (curPath + "../../ontology/foodsafety.owl");
		    owlModel.save(uri, FileUtils.langXMLAbbrev,errors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
