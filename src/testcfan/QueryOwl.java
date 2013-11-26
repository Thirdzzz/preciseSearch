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
	String filename = "E:/workspace/foodsafety/����ⱸ��/foodsafety.owl";

	// String fileurl="file:///";
	
	/**
	 * // ��ȡ����owl�ļ�
	 * ��filename = "E:/workspace/foodsafety/����ⱸ��/foodsafety.owl";�л��
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
	 * //�г���ǰ������е����е���
	 * ��������а�����http://www.domain2.com#�������ռ�����
	 * ����һ��������http://www.domain2.com#��""���棬��ɾ��
	 * @param owlModel
	 * @return
	 * ���ص��ַ�������  LinkedList<String>()
	 */
	public LinkedList listAllClass(OntModel ontModel) {
		LinkedList rescls = new LinkedList<String>();
		Iterator iter = ontModel.listNamedClasses();
		while (iter.hasNext()) {
			String tempstr = iter.next().toString();
			// �����õĽڵ���ȫ��ȥ�� �����
			if (tempstr.contains("http://www.domain2.com")) {
				tempstr = tempstr.replace("http://www.domain2.com#", "");
				rescls.add(tempstr);
			}
		}
		return rescls;
		// //�г�thing�ڵ��µ����е����࣬����ѯ��������Ҳ�ڴ˽ڵ���
		// OntClass
		// ontclass=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		// Iterator iter=ontclass.listSubClasses();
		// while(iter.hasNext())
		// {
		// System.out.println(iter.next().toString());
		// }
	}

	
	/**
	 * // ��ʼ��Ҫ��ѯ����
	 * @param ontModel
	 * @param uri	uri��Ҫ��ѯ���uri
	 * @return	Ҳ���Ǵ�ontModel���ҵ�uri��Ӧ���࣬���ҷ���
	 */
	public OntClass iniClass(OntModel ontModel, String uri) {
		// uriΪҪ��ѯ�����uri"http://www.domain2.com#ʳƷ��ȫ�¼�"
		OntClass cls = ontModel.getOntClass(uri);
		return cls;
	}

	
	/**
	 * // ��ȡ��ǰ���µ�����ʵ��
	 * ���ص��ǵ�ǰ����ʵ�����˼򻯵�uri
	 * ɾ�����С�����
	 * @param ontclass
	 * @return	�Լ�ʵ��uri���ַ�������LinkedList<String>()��Ϊ��������
	 */
	public LinkedList getIns(OntClass ontclass) {
		LinkedList resIns = new LinkedList<String>();
		String tempstr;
		try {
			Iterator iter = ontclass.listInstances();

			while (iter.hasNext()) {
				Resource resTemp = (Resource) iter.next();
				tempstr = resTemp.getModel().getGraph().getPrefixMapping()
						.shortForm(resTemp.getURI()).toString();	//~~~��ȡ���URI������������ʱ��URI���˼򻯣��������ռ�ǰ׺ʡ�ԣ�
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
	 * // ��ȡ��ǰ�����������
	 * @param ontclass
	 * ��uri�����еġ�foodsafety���������
	 * ��������а�������ء�������ƥ����"[a-zA-Z]*"�����ܼ��뵽�������
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
				if(!(tempPro.contains("���")||tempPro.matches("[a-zA-Z]*")))
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
	 * //��ȡָ��ʵ������������
	 * @param ontModel
	 * @param insUri	insUriΪ��Ҫ��ȡ��ʵ����Uri
	 * @return
	 * //����������ʽȥ��Ӣ�����ԣ����������ԣ�ֻ������������,ȥ��type����
	 */
	public LinkedList getInsPro(OntModel ontModel,String insUri)
	{
		/**
		 * insUriΪ��Ҫ��ȡ��ʵ����Uri
		 * */
		String temp=null;
		OntResource insRes=ontModel.getOntResource(insUri);
		LinkedList listInsPro = new LinkedList<String>();
		for (Iterator i1 = insRes.listProperties(); i1.hasNext();) 
		{
//		����1��������Ԫ���ִʵõ������Լ����Ե�ֵ	
			String tempPro=i1.next().toString();
			//System.out.println("----");
			//System.out.println(tempPro);
			String[] aa=tempPro.split(",");		//��RDF��Ԫ��ֿ���ȡ���м�������з���listInsPro��
			//System.out.println(aa[1]);
			temp=aa[1].replace("http://www.domain2.com#","");	//~~~��RDF��Ԫ���е�ν���������"http://www.domain2.com#"����ɾȥ"http://www.domain2.com#"
			temp=temp.trim();
			//����������ʽȥ��Ӣ�����ԣ����������ԣ�ֻ������������,ȥ��type����
			if(!(temp.matches("[a-zA-Z]*")||temp.contains("���")||temp.contains("type")))
			{
				listInsPro.add(aa[1]);		//~~~how����Ϊʲôaa[1]�������������ˣ�����˵�õ���Ϊν��ô����������
			}	
//			if(!(aa[1].contains("���")||aa[1].contains("[a-zA-Z]*")))//��type���Ժ��Ե�,���������Ժ��Ե�(Ӣ�����ԣ��������),ֻ������������
//			{
//			listInsPro.add(aa[1]);
//		
//			}
//			//����2��ֱ������getPredicate������ȡ�м������
//			Statement st1=(Statement) i1.next();//���ýڵ㸳ֵ��һ��statement
//			Resource sub=st1.getSubject();//��ȡ��ʵ������
//			Property pre=st1.getPredicate();//��ȡ��ʵ���ĵ�i������ 
//			RDFNode obj=st1.getObject();//��ȡ��Ӧ�����Ե�ֵ
//			 System.out.print(sub+"    ");
//	         System.out.println(pre);
//	         System.out.println(obj.toString());
			
		}
		return listInsPro;
	}
	
	/**
	 * //��ȡָ��ʵ����ָ�����Ե�ֵ
	 * @param ontModel
	 * @param insUri	insUri��ʾʵ����uri
	 * @param proUri	proUri��ʾָ�����Ե�uri
	 * @return
	 */
	public LinkedList getProVal(OntModel ontModel,String insUri,String proUri)
	{
		/**
		 * insUri��ʾʵ����uri
		 * proUri��ʾָ�����Ե�uri
		 * */
		//Property pro = ontModel.getProperty("http://www.domain2.com#cause");
	
		Property pro=ontModel.getProperty(proUri);
		OntResource insRes=ontModel.getOntResource(insUri);
		LinkedList proVal=new LinkedList<String>();
		String tempVal=null;
		for(NodeIterator iterators= insRes.listPropertyValues(pro);iterators.hasNext();)
	      {
	       RDFNode iterator=iterators.nextNode();//��Ӧ�ĵ�һֵ
	       tempVal=iterator.toString();
	      // System.out.println(tempVal);
	       if(tempVal.contains("string"))//��������ֵΪ�ַ���������������ԵĶ�������ȥ��
	       {
	    	  tempVal=tempVal.replace("^^http://www.w3.org/2001/XMLSchema#string","");
	    	  tempVal=tempVal.trim();
	    	 //  System.out.println(tempVal);
	       }
	       if(tempVal.contains("date"))//��������ֵΪdate��������Ѷ������������ȥ��
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
	 * //���ʵ��������
	 * @param ontModel
	 * @param proN		proNΪ��N�����Ե�����
	 * @param proNval	proNvalΪ��N�����Ե�ֵ
	 * @param insName	insNameΪʵ��������
	 */
	public void addInsPro(OntModel ontModel,String proN,String proNval,String insName)
	{
		//proNΪ��N�����Ե�����
		//proNvalΪ��N�����Ե�ֵ
		//insNameΪʵ��������
		String proURI="http://www.domain2.com#"+proN;//��ȡ��Ҫ��ӵ����Ե�URI
		proURI=proURI.trim();
		Property pro = ontModel.getProperty(proURI);//��ȡҪ��ӵ�����
		String insURI="http://www.owl-ontologies.com/unnamed.owl#"+insName;//��ȡҪ������Ե�ʵ������
		insURI=insURI.trim();
	
		OntResource insRes=ontModel.getOntResource(insURI);//��ȡҪ������Ե�ʵ��
		RDFNode rdfNode = null;
		//��ͬ���͵��������ò�ͬ������ֵǰ׺,Ŀǰֻ����������ԣ���������������������Ĭ��Ϊstring			to be continued???
		if(proN.equals("����"))
		{
			rdfNode=ontModel.createTypedLiteral(proNval, "http://www.w3.org/2001/XMLSchema#date");
		}
		else
		{
			rdfNode=ontModel.createTypedLiteral(proNval, "http://www.w3.org/2001/XMLSchema#string");
		}
		
		//RDFNode rdfNode = ontModel.createLiteral(proNval);//����Ҫ��ӵ�����
		Individual indi = ontModel.getIndividual(insURI);//��Ҫ������Ե�ʵ������ת��Ϊ����
		
		indi.setPropertyValue(pro, rdfNode);
	}
	
	/**
	 * //�޸�ʵ������(��ʱû���õ�������ɾ����������)
	 * @param ontModel
	 */
	public void modInsPro(OntModel ontModel)
	{
		Property pro = ontModel.getProperty("http://www.domain2.com#cause");
		OntResource insRes=ontModel.getOntResource("http://www.owl-ontologies.com/unnamed.owl#����22");
		RDFNode rdfNode = ontModel.createLiteral("����ѩ��2");
		RDFNode rdfNode2=ontModel.createLiteral("�����߸�ɿڿ��ֳ�ѩ���ڲس��ӷ�Ժ����");
		Individual indi = ontModel.getIndividual("http://www.owl-ontologies.com/unnamed.owl#����22");
		
//		indi.removeProperty(pro,rdfNode2);
//		indi.removeAll(pro);
		indi.setPropertyValue(pro, rdfNode);
		//insRes.setPropertyValue(pro,rdfNode);
		
	}
	
	/**
	 * //��������
	 * @param ontclass
	 * @param ontModel
	 */
	public void addClass(OntClass ontclass,OntModel ontModel)
	{
		OntClass clsThing=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		Resource res=ontModel.createOntResource("http://www.domain2.com#�����");
		OntClass temp=ontModel.createClass("http://www.domain2.com#�����");
		
		clsThing.addSubClass(temp);
	}
	
	/**
	 * // ɾ����ѡ��(ʵ������ɾ���˸����µ�����ʵ��)
	 * @param ontclass
	 * @param ontModel
	 */
	public void deleteClass(OntClass ontclass, OntModel ontModel) {
//		ontclass.remove();// ɾ���˸��������ʵ�������Ǳ���Ҫд��ԭ�����ļ��ſ���
//		Resource s = ontModel.getResource("http://www.domain2.com#�¶�����");
		
		OntClass clsThing=ontModel.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		
		clsThing.removeSubClass(ontclass);
//		clsThing.removeSuperClass(ontclass);
//		ontclass.removeSuperClass(clsThing);
//		ontclass.removeSubClass(clsThing);
		
		// StmtIterator iter=

	}
	/**
	 * չʾĿ�걾���������
	 * @param ontclass
	 */
	public void ListSubClass(OntClass ontclass)
	{
		Iterator iter=ontclass.listSubClasses();
		while(iter.hasNext())
			System.out.println(iter.next().toString());
	}
	
	/**
	 * չʾĿ�걾����ĸ���
	 * @param ontclass
	 */
	public void ListSuperClass(OntClass ontclass)
	{
		Iterator iter=ontclass.listSuperClasses();
		while(iter.hasNext())
			System.out.println(iter.next().toString());
	}
	
	
	/**
	 * // ɾ��ĳһ��ʵ��
	 * @param ontModel
	 * @param ontclass
	 * @param individual
	 * @return
	 */
	public boolean deleteIns(OntModel ontModel, OntClass ontclass,
			Resource individual) {
		try {
			//��ɾ��������ɾ��ʵ��
			individual.removeProperties();
			ontclass.dropIndividual(individual);// ɾ��ontclass�µ�ĳһ��ʵ��
//			ontclass.removeSameAs(individual);

		} catch (Exception e) {
			System.out.println("ʧ��!");
			return false;
		}
		// //ontModel.remove(iter);//ɾ��ontmodel�µ�ĳһ��ʵ��
		// Resource s=ontModel.getResource("http://www.domain2.com#�¶�����");
		// ontModel.remove(s, null, null);//���ø���ȷ�ķ�ʽɾ��ĳһ��ʵ��
		return true;
	}

	
	/**
	 * // �г�ĳһ��ʵ��
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
	 * //���ʵ��
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
	 * �������д�뵽ָ���ļ�filename = "E:/workspace/foodsafety/����ⱸ��/foodsafety.owl"��
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
		String uri1 = "http://www.domain2.com#����ʳƷ";
		String uri2 = "http://www.domain2.com#�¶�����";
		String uri3 = "http://www.domain2.com#ʳƷ��ȫ�¼�";
		String uri4="http://www.domain2.com#�����";
		String uriAll="http://www.w3.org/2002/07/owl#Thing";
		String insUri="http://www.owl-ontologies.com/unnamed.owl#�ϵ»�";
		QueryOwl a = new QueryOwl();
		OntModel ontModel = null;
		ontModel = a.readOwl();
		OntClass ocls = null;
		OntClass ocls2=null;
		OntClass oclsAll=null;
		oclsAll=a.iniClass(ontModel, uriAll);
		ocls = a.iniClass(ontModel, uri2);
		
		a.addInsPro(ontModel, "����", "���Ե�ֵ", "��԰��ҵ����Υ����ӷ�ʳ������");
		a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#��԰��ҵ����Υ����ӷ�ʳ������");
		 //�г����е���
//		 LinkedList allcls=a.listAllClass(ontModel);
//		 for(int li=0;li<allcls.size();li++)
//		 System.out.println(allcls.get(li));
	
//		 �г���ǰ���µ�����ʵ��
//		System.out.println("�����ʵ���У�");
//		int ii = 0;
//		LinkedList allIns = a.getIns(ocls);
//		for (ii = 0; ii < allIns.size(); ii++)
//			System.out.println(allIns.get(ii));

//		 System.out.println("����������У�");
//		 LinkedList prols=a.getPro(ocls);
//		 for(int pi=0;pi<prols.size();pi++)
//		 System.out.println(prols.get(pi));
		// a.listAllClass(ontModel);	
//		���ʵ��
//		a.addIns(ontModel,ocls,"����ŶҲ");
//		System.out.println("��Ӻ��ʵ���� ");
//		 ii=0;
//		 allIns=a.getIns(ocls);
//		 for(ii=0;ii<allIns.size();ii++)
//			System.out.println(allIns.get(ii));
		
			// ɾ��ĳһ��ʵ��
//			Resource resTest = ontModel
//					.getResource("http://www.owl-ontologies.com/unnamed.owl#�����°�");
//			boolean bool = a.deleteIns(ontModel, ocls, resTest);
//			 System.out.println("ɾ���Ľ����"+bool);
//			 System.out.println("ɾ�����ʵ���� ");
//			 ii=0;
//			 allIns=a.getIns(ocls);
//			 for(ii=0;ii<allIns.size();ii++)
//			 System.out.println(allIns.get(ii));

		//�޸�ʵ��
//		Resource resTest = ontModel
//		.getResource("http://www.owl-ontologies.com/unnamed.owl#ʳ����");
//		 a.deleteIns(ontModel, ocls, resTest);
//		 a.addIns(ontModel,ocls,"�Ե���");
////		�����еĲ���д�ᵽ owl�ļ�
//		a.writeOwl(ontModel);
		//�г���������
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
		//�г�ָ��ʵ������������
//		int pi=0;
//		LinkedList InsPro=new LinkedList<String>();
//		InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#�����谷");
//		 for(pi=0;pi<InsPro.size();pi++)
//		System.out.println(InsPro.get(pi));
//		
		 //�г�ָ��ʵ����ָ�����Ե�ֵ
//		 Individual id=ontModel.getIndividual("http://www.owl-ontologies.com/unnamed.owl#�����谷");
//		 id.removeProperties();
//		 System.out.println("ɾ����");
//		  pi=0;
//			 InsPro=new LinkedList<String>();
//			InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#�����谷");
//			 for(pi=0;pi<InsPro.size();pi++)
//			System.out.println(InsPro.get(pi));
		 
//		int pvi=0;
//		LinkedList insProVal=new LinkedList<String>();
//		insProVal=a.getProVal(ontModel, "http://www.owl-ontologies.com/unnamed.owl#����22", "http://www.domain2.com#cause");
//		 for(pvi=0;pvi<insProVal.size();pvi++)
//		 System.out.println(insProVal.get(pvi));
//		 //�޸�ʵ��������
//		 a.modInsPro(ontModel);
//		 System.out.println("�޸ĺ�");
//�г�ָ��ʵ�������������Լ���ֵ
//		 
//		int pi=0;
//			LinkedList InsPro=new LinkedList<String>();
//			InsPro=a.getInsPro(ontModel, "http://www.owl-ontologies.com/unnamed.owl#����22");
//			 for(pi=0;pi<InsPro.size();pi++)
//			 {
//				 String temp=InsPro.get(pi).toString();
//				 temp=temp.replace("http://www.domain2.com#", "");
//			System.out.println("��"+(pi+1)+"�����Ե�ֵΪ"+temp);
//			 pvi=0;
//			  insProVal=new LinkedList<String>();
//			insProVal=a.getProVal(ontModel, "http://www.owl-ontologies.com/unnamed.owl#����22",InsPro.get(pi).toString().trim());
//			 for(pvi=0;pvi<insProVal.size();pvi++)
//			 System.out.println(insProVal.get(pvi)); 
//			 }
//		 Resource s=ontModel.getResource("http://www.domain2.com#�ж�ʳƷ");
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