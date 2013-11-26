package testcfan;

import java.util.Collection;
import java.util.Iterator;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;

public class owlManager {
	
	/**
	 * ����Ŀ��uri������ʱ����Ĭ��Ϊ"http://www.domain2.com/foodsafety.owl"
	 * ��ñ����owl
	 * ���������owl����͸���ֱ����չʾ
	 */
	public void owlQuery()
	{
		 String uri = "http://www.domain2.com/foodsafety.owl";
		    //alternatively, you can specify a local path on your computer
		    //for the travel.owl ontology. Example:
		    //String uri = "file:///c:/Work/Projects/travel.owl"
		    OWLModel owlModel=null;
			try {
				owlModel = ProtegeOWL.createJenaOWLModelFromURI(uri);		//~~~Ӧ���Ǹ���Ŀ��·���ȴ���owlģ�ͣ��������ʱʹ�õ�uri���ǡ�owl��·��
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    Collection classes = owlModel.getUserDefinedOWLNamedClasses();	//~~~����Ī����rdfs�ļ��ϣ�����
		    for (Iterator it = classes.iterator(); it.hasNext();) {			//~~~��������
		        OWLNamedClass cls = (OWLNamedClass) it.next();
		        Collection instances = cls.getInstances(false);				//~~~��ȡcls ��ʵ�����󼯺�
		        System.out.println("Class " + cls.getBrowserText() + " (" + instances.size() + ")");
		        for (Iterator jt = instances.iterator(); jt.hasNext();) {
		            OWLIndividual individual = (OWLIndividual) jt.next();
		            System.out.println(" - " + individual.getBrowserText());
		        }
		    }

	}

}
