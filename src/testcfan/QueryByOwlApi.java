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
	 * // ��ȡowl�ļ������뵽owlModel��
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
	 * ���thing�ڵ㣬���ڵ��а���thing���Ե�
	 */
	public OWLNamedClass thingCls(OWLModel owlModel)
	{		
		OWLNamedClass cls=null;
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// �����õĽڵ���ȫ��ȥ�� �����
			RDFResource res=(RDFResource) iter.next();
			String tempStr=res.getURI().toString();
			if(tempStr.contains("Thing"))
			{
			cls=(OWLNamedClass) res;			//~~~�����Ļ������ص�clse��ֻ��owlmodel�����һ������thing�Ľڵ�	
			}
		}
		return cls;
	}
	
	/**
	 * //��ʼ��Ҫ��ѯ����
	 * @param owlModel
	 * @param clsName
	 * @return
	 * ����clsName�ı�����ű����뵽�����༯��cls��
	 */
	public OWLNamedClass iniClass(OWLModel owlModel, String clsName) {
		// uriΪҪ��ѯ�����uri"http://www.domain2.com#ʳƷ��ȫ�¼�"
		//	OWLNamedClass cls = owlModel.getOWLNamedClass("�¶�����");
		//System.out.println(cls.getNamespacePrefix());
		OWLNamedClass cls=null;
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// �����õĽڵ���ȫ��ȥ�� �����
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
	 * //�г���ǰ������е����е���
	 * ��������а�����http://www.domain2.com#�������ռ�����
	 * ����һ��������http://www.domain2.com#��""���棬��ɾ��
	 * @param owlModel
	 * @return
	 * ���ص��ַ�������  LinkedList<String>()
	 */
	public LinkedList listAllClass(OWLModel owlModel) {
		String tempStr=null;
		LinkedList rescls = new LinkedList<String>();
		Iterator iter = owlModel.listOWLNamedClasses();
		while (iter.hasNext()) {
			// �����õĽڵ���ȫ��ȥ�� �����
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
	 * //����µ��༰ʵ������
	 * @param owlModel
	 * @return
	 * ����ӵ��Ǹ�JB�����������崴���Ĳ�����û�У�
	 */
	public OWLNamedClass addNewCls(OWLModel owlModel)
	{
		 owlModel.getNamespaceManager().setDefaultNamespace("http://www.domain2.com#");	//~~~�޸�Ĭ�������ռ�
	      OWLNamedClass newCls = owlModel.createOWLNamedClass("World");
	      OWLDatatypeProperty newPro1 = owlModel.createOWLDatatypeProperty("age");
	      newPro1.setRange(owlModel.getXSDint());
	      newPro1.setDomain(newCls);
	      owlModel.getNamespaceManager().setDefaultNamespace("http://www.owl-ontologies.com/unnamed.owl#");	//~~~�޸�Ĭ�������ռ�
	      RDFIndividual darwin = newCls.createRDFIndividual("Darwin");//�����µ�ʵ��
	      darwin.setPropertyValue(newPro1, "1");
	      RDFIndividual darwin2 = newCls.createRDFIndividual("����");//�����µ�ʵ��2
	      darwin2.setPropertyValue(newPro1, "1");
	      return newCls;


//	 OWLNamedClass sisterClass = owlModel.createOWLNamedSubclass("Sister",thing);
	}
	
	
	/**
	 * //�г�ָ��������е�ʵ��
	 * @param owlCls
	 * @return
	 * ��ò���owlCls�������ʵ�������ҽ�ʵ���е�http://www.owl-ontologies.com/unnamed.owl#����ɾȥ
	 * ��󷵻�LinkedList<String>()ʵ���ַ�������
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
	 * //�г�ָ��������е�����
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
//			{	//��������ĸ�ʽΪCls(foodsafety:����ʳƷ, FrameID(1:29921))
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
	 * //��ӡĳ��list������
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
		String clsName ="����ʳƷ";
		String clsName2="�¶�����";
		String clsName3="ʳƷ��ȫ�¼�";
		LinkedList clsIns=new LinkedList<String>();//�洢ĳ���������ʵ��list
		OWLNamedClass owlCls=null;
		OWLNamedClass thing=null;
		OWLNamedClass newCls=null;
		OWLModel owlModel=a.readOwl();		//~~~owlModel��file:///C:/foodsafety.owl���������
//		thing=a.thingCls(owlModel);//��ȡthing�ڵ���Ϊ����,��ʱδ�õ�
		newCls=a.addNewCls(owlModel);
//		a.listAllClass(owlModel);
		owlCls=a.iniClass(owlModel, clsName3);
		//clsIns=a.getIns(owlCls);
		clsIns=a.getIns(newCls);
	//	a.getPro(owlCls);
		//��ʾĳ��list������
		a.printList(clsIns);
	}

}
