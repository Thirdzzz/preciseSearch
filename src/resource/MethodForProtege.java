package resource;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import resource.search.ResultBean;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;

public class MethodForProtege {			//~~~使用到了resource.search包中的   ResultBean.java		没看太懂？？？？
	JenaOWLModel owlModel;
	private List<String> KeyWord;
	public MethodForProtege(JenaOWLModel p) throws Exception
	{
		owlModel = p;	
	}
	@SuppressWarnings("null")
	/**
	 * string s 作为单个的单词
	 * 进行protege的处理，处理的过程和结果上不明白？？？？
	 * */
	public List SearchProtege(String s,List<ResultBean> temp) throws Exception	//~~~使用到了resource.search包中的   ResultBean.java
	{		//~~~List后面应该加上<ResultBean>？？？
		OWLObjectProperty hasContactProperty = owlModel.getOWLObjectProperty("foodsafety:cause");		//~~~提示不存在？？？
		OWLDatatypeProperty hasContactProperty3 = owlModel.getOWLDatatypeProperty("foodsafety:关键词");
		OWLObjectProperty hasContactProperty4 = owlModel.getOWLObjectProperty("foodsafety:relate");
		OWLIndividual individual1;
		System.out.println(s);
		if(owlModel.getOWLIndividual(s)==null)			//~~~没有发现目标单词s
		{
			System.out.println("发现");
			return temp;
		}
		else
		{	
			
			individual1=owlModel.getOWLIndividual(s);
			List<String> tempKeyWord=new LinkedList();
			OWLDatatypeProperty hasContactProperty5=owlModel.getOWLDatatypeProperty("foodsafety:详情");
			OWLObjectProperty hasContactProperty6 = owlModel.getOWLObjectProperty("foodsafety:relatedto");
			OWLObjectProperty hasContactProperty7 = owlModel.getOWLObjectProperty("foodsafety:causedby");
			if(individual1.hasPropertyValue(hasContactProperty))
			{
				Collection classes1=individual1.getPropertyValues(hasContactProperty);
				for (Iterator jt = classes1.iterator(); jt.hasNext();) {
					OWLIndividual individual = (OWLIndividual) jt.next();
					ResultBean resultBean = new ResultBean();
					//取得搜索结果，存放在ResultBean结果Bean中
					resultBean.setHtmlTitle(individual.getName());
					resultBean.setHtmlPath((String)individual.getPropertyValue(hasContactProperty5));
					System.out.println(individual.getName());
					Collection classes2=individual.getPropertyValues(hasContactProperty6);
					List<String> temp1 = new LinkedList() ;
					for (Iterator jt1 = classes2.iterator(); jt1.hasNext();) {
						OWLIndividual individual11 = (OWLIndividual) jt1.next();
						temp1.add(individual11.getName());
					}
					resultBean.setHtmlMaterial(temp1);
					classes2=individual.getPropertyValues(hasContactProperty7);
					List<String> temp2=new LinkedList();
					for (Iterator jt1 = classes2.iterator(); jt1.hasNext();) {
						OWLIndividual individual11 = (OWLIndividual) jt1.next();
						temp2.add(individual11.getName());
					}
			
					resultBean.setHtmlFood(temp2);//添加进List集合对象
					temp.add(resultBean);
					Collection classes=individual.getPropertyValues(hasContactProperty3);
					String keyWord="";
					for (Iterator it = classes.iterator(); it.hasNext();) {
						String s1=(String)it.next();
		
						keyWord+=s1+"/";
					}
					tempKeyWord.add(keyWord);
				}
				KeyWord=tempKeyWord;
				return temp;
			}
			else if(individual1.hasPropertyValue(hasContactProperty4))
			{
				Collection classes1=individual1.getPropertyValues(hasContactProperty4);
				for (Iterator jt = classes1.iterator(); jt.hasNext();) {
					OWLIndividual individual = (OWLIndividual) jt.next();
					ResultBean resultBean = new ResultBean();
					//取得搜索结果，存放在ResultBean结果Bean中
					resultBean.setHtmlTitle(individual.getName());
					resultBean.setHtmlPath((String)individual.getPropertyValue(hasContactProperty5));
					Collection classes2=individual.getPropertyValues(hasContactProperty6);
					List<String> temp1 = new LinkedList();
					for (Iterator jt1 = classes2.iterator(); jt1.hasNext();) {
						OWLIndividual individual11 = (OWLIndividual) jt1.next();
						temp1.add(individual11.getName());
					}
					resultBean.setHtmlMaterial(temp1);
					classes2=individual.getPropertyValues(hasContactProperty7);
					List<String> temp2 = new LinkedList();
					for (Iterator jt1 = classes2.iterator(); jt1.hasNext();) {
						OWLIndividual individual11 = (OWLIndividual) jt1.next();
						temp2.add(individual11.getName());
					}
					System.out.println("查找！");
					resultBean.setHtmlFood(temp2);
						
					//添加进List集合对象
					temp.add(resultBean);
					Collection classes=individual.getPropertyValues(hasContactProperty3);
					String keyWord="";
					for (Iterator it = classes.iterator(); it.hasNext();) {
						String s1=(String)it.next();
						System.out.println(s1);
						keyWord+=s1+"/";
					}
					tempKeyWord.add(keyWord);
				}
				KeyWord=tempKeyWord;
				return temp;
			}
			else return temp;
		}
	}
	/**
	 * 就是返回一下KeyWord，
	 * KeyWord在SearchProtege()函数中生成的
	 * */
	public List<String> GetKeyWord()
	{
		return KeyWord;
	}
}
