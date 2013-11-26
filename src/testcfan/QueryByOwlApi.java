package testcfan;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.RDFIndividual;
import edu.stanford.smi.protegex.owl.model.RDFResource;

public class QueryByOwlApi {
	
	public QueryByOwlApi()
	{}

	
	/**
	 * // 读取owl文件并放入到owlModel中
	 * "file:///C:/foodsafety.owl";
	 * @return
	 */
	public OWLModel readOwl() {
		String uri = "file:///C:/foodsafety.owl";
		OWLModel owlModel=null;
		try {
			owlModel = ProtegeOWL.createJenaOWLModelFromURI(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return owlModel;
	}
	/**
	 * 获得thing节点，即节点中包含thing属性的
	 */
	public OWLNamedClass thingCls(OWLModel owlModel)
	{		
		OWLNamedClass cls=null;
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// 将无用的节点类全部去掉 不输出
			RDFResource res=(RDFResource) iter.next();
			String tempStr=res.getURI().toString();
			if(tempStr.contains("Thing"))
			{
			cls=(OWLNamedClass) res;			//~~~这样的话，返回的clse还只是owlmodel中最后一个包含thing的节点	
			}
		}
		return cls;
	}
	
	/**
	 * //初始化要查询的类
	 * @param owlModel
	 * @param clsName
	 * @return
	 * 包括clsName的本体类才被加入到返回类集合cls中
	 */
	public OWLNamedClass iniClass(OWLModel owlModel, String clsName) {
		// uri为要查询的类的uri"http://www.domain2.com#食品安全事件"
		//	OWLNamedClass cls = owlModel.getOWLNamedClass("致毒物质");
		//System.out.println(cls.getNamespacePrefix());
		OWLNamedClass cls=null;
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// 将无用的节点类全部去掉 不输出
			RDFResource res=(RDFResource) iter.next();
			String tempStr=res.getURI().toString();
			if(tempStr.contains(clsName))
			{
			cls=(OWLNamedClass) res;	
			}
		}
//		System.out.println(cls.getURI());
		return cls;

		  
	}
	
	/**
	 * //列出当前本体库中的所有的类
	 * 将本题库中包含“http://www.domain2.com#”的类收集起来
	 * 并做一个处理：将http://www.domain2.com#用""代替，即删除
	 * @param owlModel
	 * @return
	 * 返回的字符串集合  LinkedList<String>()
	 */
	public LinkedList listAllClass(OWLModel owlModel) {
		String tempStr=null;
		LinkedList rescls = new LinkedList<String>();
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// 将无用的节点类全部去掉 不输出
			RDFResource res=(RDFResource) iter.next();
			tempStr=res.getURI().toString();
			if(tempStr.contains("www.domain2.com"))
			{
				tempStr=tempStr.replace("http://www.domain2.com#","");
				System.out.println(tempStr);
				rescls.add(tempStr);
			}
		}
		return rescls;
	}
	
	/**
	 * //添加新的类及实例属性
	 * @param owlModel
	 * @return
	 * 这添加的是个JB，连自主定义创建的参数都没有！
	 */
	public OWLNamedClass addNewCls(OWLModel owlModel)
	{
		 owlModel.getNamespaceManager().setDefaultNamespace("http://www.domain2.com#");	//~~~修改默认命名空间
	      OWLNamedClass newCls = owlModel.createOWLNamedClass("World");
	      OWLDatatypeProperty newPro1 = owlModel.createOWLDatatypeProperty("age");
	      newPro1.setRange(owlModel.getXSDint());
	      newPro1.setDomain(newCls);
	      owlModel.getNamespaceManager().setDefaultNamespace("http://www.owl-ontologies.com/unnamed.owl#");	//~~~修改默认命名空间
	      RDFIndividual darwin = newCls.createRDFIndividual("Darwin");//创建新的实例
	      darwin.setPropertyValue(newPro1, "1");
	      RDFIndividual darwin2 = newCls.createRDFIndividual("张云");//创建新的实例2
	      darwin2.setPropertyValue(newPro1, "1");
	      return newCls;


//	 OWLNamedClass sisterClass = owlModel.createOWLNamedSubclass("Sister",thing);
	}
	
	
	/**
	 * //列出指定类的所有的实例
	 * @param owlCls
	 * @return
	 * 获得参数owlCls类的所有实例，并且将实例中的http://www.owl-ontologies.com/unnamed.owl#属性删去
	 * 最后返回LinkedList<String>()实例字符串集合
	 * @SuppressWarnings("deprecation")
	 */
	public LinkedList getIns(OWLNamedClass owlCls) {
		LinkedList resIns = new LinkedList<String>();
		String tempstr;
		Iterator  iter=null;  
		Collection collection=null;
		try {
			collection=owlCls.getInstances();
			iter=collection.iterator();
			while(iter.hasNext())
			{
				RDFResource res=(RDFResource) iter.next();
				tempstr=res.getURI().toString().replace("http://www.owl-ontologies.com/unnamed.owl#","");
				resIns.add(tempstr);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return resIns;
	}
	
	/**
	 * //列出指定类的所有的属性
	 * @param owlCls
	 * @return
	 */
	public LinkedList getPro(OWLNamedClass owlCls) {
		Iterator iter=null;
		LinkedList prols = new LinkedList<String>();
		Collection collection=null;
		try {
			collection=owlCls.getRDFProperties();
			iter=collection.iterator();
			while(iter.hasNext())
			{
				System.out.println(iter.next().toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return prols;
	}
	public void test(OWLModel owlModel)
	{
		String tempStr=null;
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext())
		{
//			tempStr=iter.next().toString();
//			if(tempStr.contains("foodsafety"))
//			{	//这种输出的格式为Cls(foodsafety:问题食品, FrameID(1:29921))
//				System.out.println(tempStr);
//			}
			RDFResource res=(RDFResource) iter.next();
			tempStr=res.getURI().toString();
			if(tempStr.contains("www.domain2.com"))
			{
				tempStr=tempStr.substring(25);
				System.out.println(tempStr);
			}
			
		}
		
	}
	
	/**
	 * //打印某个list的内容
	 * @param list
	 */
	public void printList(LinkedList list)
	{
		int i=0;
		for(i=0;i<list.size();i++)
		{
			System.out.println(list.get(i));
		}
	}
	public static void main(String agrs[]) {
		QueryByOwlApi a = new QueryByOwlApi();
		String clsName ="问题食品";
		String clsName2="致毒物质";
		String clsName3="食品安全事件";
		LinkedList clsIns=new LinkedList<String>();//存储某个类的所有实例list
		OWLNamedClass owlCls=null;
		OWLNamedClass thing=null;
		OWLNamedClass newCls=null;
		OWLModel owlModel=a.readOwl();		//~~~owlModel是file:///C:/foodsafety.owl这个大玩意
//		thing=a.thingCls(owlModel);//获取thing节点作为父类,暂时未用到
		newCls=a.addNewCls(owlModel);
//		a.listAllClass(owlModel);
		owlCls=a.iniClass(owlModel, clsName3);
		//clsIns=a.getIns(owlCls);
		clsIns=a.getIns(newCls);
	//	a.getPro(owlCls);
		//显示某个list的内容
		a.printList(clsIns);
	}

}
