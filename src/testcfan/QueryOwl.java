package testcfan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.stanford.smi.protegex.owl.inference.dig.translator.DIGVocabulary.Language;

public class QueryOwl {
	RDFNode node1 = null;

	public QueryOwl() {
	}

	String relationshipUri = "http://www.domain2.com";
	String filename = "E:/workspace/foodsafety/本体库备份/foodsafety.owl";

	// String fileurl="file:///";
	
	/**
	 * // 读取本体owl文件
	 * 从filename = "E:/workspace/foodsafety/本体库备份/foodsafety.owl";中获得
	 * @return
	 */
	public OntModel readOwl() {
		// ObjectProperty
		// belongto=ontModel.createObjectProperty(relationshipUri);
		
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM_MICRO_RULE_INF, null);
		try {
			ontModel.read(new FileInputStream(filename), null);
		} catch (IOException ioe) {
			System.err.println(ioe.toString());
		}

		return ontModel;
	}

	/**
	 * //列出当前本体库中的所有的类
	 * 将本题库中包含“http://www.domain2.com#”的类收集起来
	 * 并做一个处理：将http://www.domain2.com#用""代替，即删除
	 * @param owlModel
	 * @return
	 * 返回的字符串集合  LinkedList<String>()
	 */
	public LinkedList listAllClass(OntModel ontModel) {
		LinkedList rescls = new LinkedList<String>();
		Iterator iter = ontModel.listNamedClasses();
		while (iter.hasNext()) {
			String tempstr = iter.next().toString();
			// 将无用的节点类全部去掉 不输出
			if (tempstr.contains("http://www.domain2.com")) {
				tempstr = tempstr.replace("http://www.domain2.com#", "");
				rescls.add(tempstr);
			}
		}
		return rescls;
		// //列出thing节点下的所有的子类，所查询的有用类也在此节点下
		// OntClass
		// ontclass=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		// Iterator iter=ontclass.listSubClasses();
		// while(iter.hasNext())
		// {
		// System.out.println(iter.next().toString());
		// }
	}

	
	/**
	 * // 初始化要查询的类
	 * @param ontModel
	 * @param uri	uri是要查询类的uri
	 * @return	也就是从ontModel中找到uri对应的类，并且返回
	 */
	public OntClass iniClass(OntModel ontModel, String uri) {
		// uri为要查询的类的uri"http://www.domain2.com#食品安全事件"
		OntClass cls = ontModel.getOntClass(uri);
		return cls;
	}

	
	/**
	 * // 获取当前类下的所有实例
	 * 返回的是当前类中实例做了简化的uri
	 * 删除所有“：”
	 * @param ontclass
	 * @return	以简化实例uri的字符串集合LinkedList<String>()作为返回类型
	 */
	public LinkedList getIns(OntClass ontclass) {
		LinkedList resIns = new LinkedList<String>();
		String tempstr;
		try {
			Iterator iter = ontclass.listInstances();

			while (iter.hasNext()) {
				Resource resTemp = (Resource) iter.next();
				tempstr = resTemp.getModel().getGraph().getPrefixMapping()
						.shortForm(resTemp.getURI()).toString();	//~~~获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
				tempstr = tempstr.replace(":", "");
				// System.out.println(tempstr);
				resIns.add(tempstr);
				// System.out.println(resTemp.getModel().getGraph().getPrefixMapping().shortForm(resTemp.getURI()));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return resIns;
	}

	
	/**
	 * // 获取当前类的所有属性
	 * @param ontclass
	 * 简化uri属性中的“foodsafety”被处理掉
	 * 如果属性中包括“相关”，或者匹配了"[a-zA-Z]*"都不能加入到输出属性
	 * @return
	 */
	public LinkedList getPro(OntClass ontclass) {
		LinkedList prols = new LinkedList<String>();
		try {
			for (Iterator i1 = ontclass.listDeclaredProperties(); i1.hasNext();) {
				OntProperty prop = (OntProperty) i1.next();
				String tempPro = prop.getModel().getGraph().getPrefixMapping()
						.shortForm(prop.getURI()).toString();
				tempPro = tempPro.replace("foodsafety:", "");
				if(!(tempPro.contains("相关")||tempPro.matches("[a-zA-Z]*")))
				{
				//System.out.println(tempPro);
				prols.add(tempPro);}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return prols;
	}
	
	/**
	 * //获取指定实例的所有属性
	 * @param ontModel
	 * @param insUri	insUri为所要提取的实例的Uri
	 * @return
	 * //利用正则表达式去掉英文属性，即对象属性，只保留数据属性,去掉type属性
	 */
	public LinkedList getInsPro(OntModel ontModel,String insUri)
	{
		/**
		 * insUri为所要提取的实例的Uri
		 * */
		String temp=null;
		OntResource insRes=ontModel.getOntResource(insUri);
		LinkedList listInsPro = new LinkedList<String>();
		for (Iterator i1 = insRes.listProperties(); i1.hasNext();) 
		{
//		方法1，读出三元组后分词得到属性以及属性的值	
			String tempPro=i1.next().toString();
			//System.out.println("----");
			//System.out.println(tempPro);
			String[] aa=tempPro.split(",");		//将RDF三元组分开后取出中间的属性列放入listInsPro中
			//System.out.println(aa[1]);
			temp=aa[1].replace("http://www.domain2.com#","");	//~~~将RDF三元组中的谓词如果包括"http://www.domain2.com#"，则删去"http://www.domain2.com#"
			temp=temp.trim();
			//利用正则表达式去掉英文属性，即对象属性，只保留数据属性,去掉type属性
			if(!(temp.matches("[a-zA-Z]*")||temp.contains("相关")||temp.contains("type")))
			{
				listInsPro.add(aa[1]);		//~~~how？？为什么aa[1]就是数据属性了，不是说好的作为谓词么》？？？？
			}	
//			if(!(aa[1].contains("相关")||aa[1].contains("[a-zA-Z]*")))//将type属性忽略掉,将对象属性忽略掉(英文属性，相关属性),只保留数据属性
//			{
//			listInsPro.add(aa[1]);
//		
//			}
//			//方法2，直接利用getPredicate方法获取中间的属性
//			Statement st1=(Statement) i1.next();//将该节点赋值给一个statement
//			Resource sub=st1.getSubject();//获取该实例名称
//			Property pre=st1.getPredicate();//获取该实例的第i个属性 
//			RDFNode obj=st1.getObject();//获取对应的属性的值
//			 System.out.print(sub+"    ");
//	         System.out.println(pre);
//	         System.out.println(obj.toString());
			
		}
		return listInsPro;
	}
	
	/**
	 * //获取指定实例的指定属性的值
	 * @param ontModel
	 * @param insUri	insUri表示实例的uri
	 * @param proUri	proUri表示指定属性的uri
	 * @return
	 */
	public LinkedList getProVal(OntModel ontModel,String insUri,String proUri)
	{
		/**
		 * insUri表示实例的uri
		 * proUri表示指定属性的uri
		 * */
		//Property pro = ontModel.getProperty("http://www.domain2.com#cause");
	
		Property pro=ontModel.getProperty(proUri);
		OntResource insRes=ontModel.getOntResource(insUri);
		LinkedList proVal=new LinkedList<String>();
		String tempVal=null;
		for(NodeIterator iterators= insRes.listPropertyValues(pro);iterators.hasNext();)
	      {
	       RDFNode iterator=iterators.nextNode();//对应的单一值
	       tempVal=iterator.toString();
	      // System.out.println(tempVal);
	       if(tempVal.contains("string"))//对于属性值为字符串的情况，把属性的多余描述去掉
	       {
	    	  tempVal=tempVal.replace("^^http://www.w3.org/2001/XMLSchema#string","");
	    	  tempVal=tempVal.trim();
	    	 //  System.out.println(tempVal);
	       }
	       if(tempVal.contains("date"))//对于属性值为date的情况，把多余的属性描述去掉
	       {
	    	   tempVal=tempVal.replace("^^http://www.w3.org/2001/XMLSchema#date","");
	    	   tempVal=tempVal.trim();
	       }
	       else{
	       tempVal=tempVal.replace("http://www.owl-ontologies.com/unnamed.owl#", "");
	       tempVal=tempVal.trim();
	       }
	       proVal.add(tempVal);
	      
	      }
	//	System.out.println(proVal.get(0));
	      return proVal;
	}
	
	/**
	 * //添加实例的属性
	 * @param ontModel
	 * @param proN		proN为第N个属性的名称
	 * @param proNval	proNval为第N个属性的值
	 * @param insName	insName为实例的名称
	 */
	public void addInsPro(OntModel ontModel,String proN,String proNval,String insName)
	{
		//proN为第N个属性的名称
		//proNval为第N个属性的值
		//insName为实例的名称
		String proURI="http://www.domain2.com#"+proN;//获取所要添加的属性的URI
		proURI=proURI.trim();
		Property pro = ontModel.getProperty(proURI);//获取要添加的属性
		String insURI="http://www.owl-ontologies.com/unnamed.owl#"+insName;//获取要添加属性的实例名称
		insURI=insURI.trim();
	
		OntResource insRes=ontModel.getOntResource(insURI);//获取要添加属性的实例
		RDFNode rdfNode = null;
		//不同类型的属性设置不同的属性值前缀,目前只处理对象属性，除了日期意外其他属性默认为string			to be continued???
		if(proN.equals("日期"))
		{
			rdfNode=ontModel.createTypedLiteral(proNval, "http://www.w3.org/2001/XMLSchema#date");
		}
		else
		{
			rdfNode=ontModel.createTypedLiteral(proNval, "http://www.w3.org/2001/XMLSchema#string");
		}
		
		//RDFNode rdfNode = ontModel.createLiteral(proNval);//设置要添加的属性
		Individual indi = ontModel.getIndividual(insURI);//将要添加属性的实例名称转换为个体
		
		indi.setPropertyValue(pro, rdfNode);
	}
	
	/**
	 * //修改实例属性(暂时没有用到，利用删除与添加完成)
	 * @param ontModel
	 */
	public void modInsPro(OntModel ontModel)
	{
		Property pro = ontModel.getProperty("http://www.domain2.com#cause");
		OntResource insRes=ontModel.getOntResource("http://www.owl-ontologies.com/unnamed.owl#测试22");
		RDFNode rdfNode = ontModel.createLiteral("测试雪碧2");
		RDFNode rdfNode2=ontModel.createLiteral("消费者告可口可乐称雪碧内藏虫子法院开审");
		Individual indi = ontModel.getIndividual("http://www.owl-ontologies.com/unnamed.owl#测试22");
		
//		indi.removeProperty(pro,rdfNode2);
//		indi.removeAll(pro);
		indi.setPropertyValue(pro, rdfNode);
		//insRes.setPropertyValue(pro,rdfNode);
		
	}
	
	/**
	 * //增加子类
	 * @param ontclass
	 * @param ontModel
	 */
	public void addClass(OntClass ontclass,OntModel ontModel)
	{
		OntClass clsThing=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		Resource res=ontModel.createOntResource("http://www.domain2.com#添加类");
		OntClass temp=ontModel.createClass("http://www.domain2.com#添加类");
		
		clsThing.addSubClass(temp);
	}
	
	/**
	 * // 删除所选类(实际上是删除了该类下的所有实例)
	 * @param ontclass
	 * @param ontModel
	 */
	public void deleteClass(OntClass ontclass, OntModel ontModel) {
//		ontclass.remove();// 删除了该类的所有实例，但是必须要写回原来的文件才可以
//		Resource s = ontModel.getResource("http://www.domain2.com#致毒物质");
		
		OntClass clsThing=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		
		clsThing.removeSubClass(ontclass);
//		clsThing.removeSuperClass(ontclass);
//		ontclass.removeSuperClass(clsThing);
//		ontclass.removeSubClass(clsThing);
		
		// StmtIterator iter=

	}
	/**
	 * 展示目标本体类的子类
	 * @param ontclass
	 */
	public void ListSubClass(OntClass ontclass)
	{
		Iterator iter=ontclass.listSubClasses();
		while(iter.hasNext())
			System.out.println(iter.next().toString());
	}
	
	/**
	 * 展示目标本体类的父类
	 * @param ontclass
	 */
	public void ListSuperClass(OntClass ontclass)
	{
		Iterator iter=ontclass.listSuperClasses();
		while(iter.hasNext())
			System.out.println(iter.next().toString());
	}
	
	
	/**
	 * // 删除某一个实例
	 * @param ontModel
	 * @param ontclass
	 * @param individual
	 * @return
	 */
	public boolean deleteIns(OntModel ontModel, OntClass ontclass,
			Resource individual) {
		try {
			//先删除属性再删除实例
			individual.removeProperties();
			ontclass.dropIndividual(individual);// 删除ontclass下的某一个实例
//			ontclass.removeSameAs(individual);

		} catch (Exception e) {
			System.out.println("失败!");
			return false;
		}
		// //ontModel.remove(iter);//删除ontmodel下的某一个实例
		// Resource s=ontModel.getResource("http://www.domain2.com#致毒物质");
		// ontModel.remove(s, null, null);//利用更精确的方式删除某一个实例
		return true;
	}

	
	/**
	 * // 列出某一个实例
	 * @param ontModel
	 */
	public void anyNode(OntModel ontModel) {
		// LinkedList anynode=null;
		Resource thing = ontModel
				.getResource("http://www.w3.org/2002/07/owl#Thing");
		Property pro = ontModel
				.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		RDFNode rdfnode =ontModel.getResource("http://www.w3.org/2002/07/owl#Thing");
		thing=null;
		pro=null;
		StmtIterator stmtiter = ontModel.listStatements(thing, pro, rdfnode);
		while (stmtiter.hasNext()) {

			// Resource resany=(Resource)stmtiter.next();
			System.out.println(stmtiter.next().toString());
		}
		// return anynode;
	}
	
	/**
	 * //添加实例
	 * @param ontModel
	 * @param ontclass
	 * @param ins
	 * @return
	 */
	public boolean addIns(OntModel ontModel,OntClass ontclass,String ins) {
		String insUri="http://www.owl-ontologies.com/unnamed.owl#"+ins;
		insUri.trim();
		Resource res = ontModel
				.getResource(insUri);
		Property pro = ontModel
				.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
//		Property pro2=ontModel.getProperty("http://www.domain2.com#cause");
		
		//RDFNode rdfnode =ontModel.getResource("http://www.w3.org/2002/07/owl#Thing");
		RDFNode rdfnode =ontModel.getResource(ontclass.getURI());
		Statement statement=ontModel.createStatement(res, pro, rdfnode);
		ontModel.add(statement);	
		return true;
	}
	/**
	 * 将本体库写入到指定文件filename = "E:/workspace/foodsafety/本体库备份/foodsafety.owl"中
	 * @param ontModel
	 */
	public void writeOwl(OntModel ontModel)
	{
		FileOutputStream file;
		try {
			file = new FileOutputStream(filename);
			ontModel.write(file, "RDF/XML-ABBREV");
	     file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int j = 0;
		String uri1 = "http://www.domain2.com#问题食品";
		String uri2 = "http://www.domain2.com#致毒物质";
		String uri3 = "http://www.domain2.com#食品安全事件";
		String uri4="http://www.domain2.com#添加类";
		String uriAll="http://www.w3.org/2002/07/owl#Thing";
		String insUri="http://www.owl-ontologies.com/unnamed.owl#肯德基";
		QueryOwl a = new QueryOwl();
		OntModel ontModel = null;
		ontModel = a.readOwl();
		OntClass ocls = null;
		OntClass ocls2=null;
		OntClass oclsAll=null;
		oclsAll=a.iniClass(ontModel, uriAll);
		ocls = a.iniClass(ontModel, uri2);
		
		a.addInsPro(ontModel, "测试", "属性的值", "晨园乳业涉嫌违法添加非食用物质");
		a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#晨园乳业涉嫌违法添加非食用物质");
		 //列出所有的类
//		 LinkedList allcls=a.listAllClass(ontModel);
//		 for(int li=0;li<allcls.size();li++)
//		 System.out.println(allcls.get(li));
	
//		 列出当前类下的所有实例
//		System.out.println("该类的实例有：");
//		int ii = 0;
//		LinkedList allIns = a.getIns(ocls);
//		for (ii = 0; ii < allIns.size(); ii++)
//			System.out.println(allIns.get(ii));

//		 System.out.println("该类的属性有：");
//		 LinkedList prols=a.getPro(ocls);
//		 for(int pi=0;pi<prols.size();pi++)
//		 System.out.println(prols.get(pi));
		// a.listAllClass(ontModel);	
//		添加实例
//		a.addIns(ontModel,ocls,"啊哈哦也");
//		System.out.println("添加后的实例有 ");
//		 ii=0;
//		 allIns=a.getIns(ocls);
//		 for(ii=0;ii<allIns.size();ii++)
//			System.out.println(allIns.get(ii));
		
			// 删除某一个实例
//			Resource resTest = ontModel
//					.getResource("http://www.owl-ontologies.com/unnamed.owl#测试新版");
//			boolean bool = a.deleteIns(ontModel, ocls, resTest);
//			 System.out.println("删除的结果是"+bool);
//			 System.out.println("删除后的实例有 ");
//			 ii=0;
//			 allIns=a.getIns(ocls);
//			 for(ii=0;ii<allIns.size();ii++)
//			 System.out.println(allIns.get(ii));

		//修改实例
//		Resource resTest = ontModel
//		.getResource("http://www.owl-ontologies.com/unnamed.owl#食用盐");
//		 a.deleteIns(ontModel, ocls, resTest);
//		 a.addIns(ontModel,ocls,"吃的盐");
////		将所有的操作写会到 owl文件
//		a.writeOwl(ontModel);
		//列出所有属性
//	a.getPro(ocls);
//		for (Iterator i1 = ontModel.listAllOntProperties(); i1.hasNext();) {
//			OntProperty prop = (OntProperty) i1.next();
//			String tempPro = prop.getModel().getGraph().getPrefixMapping()
//					.shortForm(prop.getURI()).toString();
//			System.out.println(tempPro);
//		}
//		OntResource insRes=ontModel.getOntResource(insUri);
//		for (Iterator i1 = insRes.listProperties(); i1.hasNext();) {
//		System.out.println(i1.next().toString());}
//		Property pro = ontModel.getProperty("http://www.domain2.com#cause");
//		for(NodeIterator iterators= insRes.listPropertyValues(pro);iterators.hasNext();)
//	      {
//	       RDFNode iterator=iterators.nextNode();
//	       System.out.println(iterator.toString().replace("http://www.owl-ontologies.com/unnamed.owl#", ""));
//	      }
		//列出指定实例的所有属性
//		int pi=0;
//		LinkedList InsPro=new LinkedList<String>();
//		InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#三氯氰胺");
//		 for(pi=0;pi<InsPro.size();pi++)
//		System.out.println(InsPro.get(pi));
//		
		 //列出指定实例的指定属性的值
//		 Individual id=ontModel.getIndividual("http://www.owl-ontologies.com/unnamed.owl#三氯氰胺");
//		 id.removeProperties();
//		 System.out.println("删除后");
//		  pi=0;
//			 InsPro=new LinkedList<String>();
//			InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#三氯氰胺");
//			 for(pi=0;pi<InsPro.size();pi++)
//			System.out.println(InsPro.get(pi));
		 
//		int pvi=0;
//		LinkedList insProVal=new LinkedList<String>();
//		insProVal=a.getProVal(ontModel, "http://www.owl-ontologies.com/unnamed.owl#测试22", "http://www.domain2.com#cause");
//		 for(pvi=0;pvi<insProVal.size();pvi++)
//		 System.out.println(insProVal.get(pvi));
//		 //修改实例的属性
//		 a.modInsPro(ontModel);
//		 System.out.println("修改后");
//列出指定实例的所有属性以及其值
//		 
//		int pi=0;
//			LinkedList InsPro=new LinkedList<String>();
//			InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#测试22");
//			 for(pi=0;pi<InsPro.size();pi++)
//			 {
//				 String temp=InsPro.get(pi).toString();
//				 temp=temp.replace("http://www.domain2.com#", "");
//			System.out.println("第"+(pi+1)+"个属性的值为"+temp);
//			 pvi=0;
//			  insProVal=new LinkedList<String>();
//			insProVal=a.getProVal(ontModel, "http://www.owl-ontologies.com/unnamed.owl#测试22",InsPro.get(pi).toString().trim());
//			 for(pvi=0;pvi<insProVal.size();pvi++)
//			 System.out.println(insProVal.get(pvi)); 
//			 }
//		 Resource s=ontModel.getResource("http://www.domain2.com#有毒食品");
//		 Property p=ontModel.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
////		 Property p=null;
//		 RDFNode r =ontModel.getResource("http://www.w3.org/2002/07/owl#Thing");
////		 ontModel.removeAll(s, null, null);
//		 ontModel.removeAll();
////		 Statement state=ontModel.createStatement(s, p, r);
////		 ontModel.remove(state);
//		 LinkedList allcls=a.listAllClass(ontModel);
//		 for(int li=0;li<allcls.size();li++)
//		 System.out.println(allcls.get(li));
//			a.writeOwl(ontModel);
		
	
	}
}