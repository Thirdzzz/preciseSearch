package testcfan;

import java.util.Collection;
import java.util.Iterator;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;

public class owlManager {
	
	/**
	 * 根据目标uri，测试时这里默认为"http://www.domain2.com/foodsafety.owl"
	 * 获得本体库owl
	 * 并将本题库owl中类和个体分别输出展示
	 */
	public void owlQuery()
	{
		 String uri = "http://www.domain2.com/foodsafety.owl";
		    //alternatively, you can specify a local path on your computer
		    //for the travel.owl ontology. Example:
		    //String uri = "file:///c:/Work/Projects/travel.owl"
		    OWLModel owlModel=null;
			try {
				owlModel = ProtegeOWL.createJenaOWLModelFromURI(uri);		//~~~应该是根据目标路径先创建owl模型，这里测试时使用的uri就是。owl的路径
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    Collection classes = owlModel.getUserDefinedOWLNamedClasses();	//~~~这里莫非是rdfs的集合？？？
		    for (Iterator it = classes.iterator(); it.hasNext();) {			//~~~本体类型
		        OWLNamedClass cls = (OWLNamedClass) it.next();
		        Collection instances = cls.getInstances(false);				//~~~获取cls 的实例对象集合
		        System.out.println("Class " + cls.getBrowserText() + " (" + instances.size() + ")");
		        for (Iterator jt = instances.iterator(); jt.hasNext();) {
		            OWLIndividual individual = (OWLIndividual) jt.next();
		            System.out.println(" - " + individual.getBrowserText());
		        }
		    }

	}

}
