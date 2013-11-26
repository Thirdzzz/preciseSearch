package yyh;
import java.io.InputStream;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
public class getInfModel {
public InfModel getInfModel(){
	   InfModel infModel;
	      String file = "E:/Workspaces/foodsafety/yyhontology/foodsafety.owl";
	      Model data = ModelFactory.createDefaultModel();
	      Model model = ModelFactory.createDefaultModel();
	      InputStream in ;
	     try{
	         in = FileManager.get().open( file );
	     data.read(in, "");
	     }
	     catch (Exception e ){
	     }
	      Resource configuration=model.createResource() ;
	      configuration.addProperty(ReasonerVocabulary.PROPruleMode, "forward");
	      configuration.addProperty(ReasonerVocabulary.PROPruleSet, "E:/Workspaces/foodsafety/rules/foodsafety.rules");
	      Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
	      infModel=ModelFactory.createInfModel(reasoner, data);		//~~~����Ӧ����������֮��İ�???
	   return infModel;
	   }
    	 
    /**
     * itemתproperty
     * eg��	name ->	http://www.domain2.com#����
     * @param item
     * @return
     */
     public String itemToProperty(String item){
   String property=null;
     if (item.equals("events")){
           property="http://www.domain2.com#�ؼ���";
       }
     if (item.equals("poison")){
         property="http://www.domain2.com#belongto";
     }
       else if(item.equals("name")){
            property="http://www.domain2.com#����";
       }
       else if(item.equals("date")){
           property="http://www.domain2.com#����";
       }
       else if(item.equals("place")){
          property="http://www.domain2.com#�ص�";
       }
       else if(item.equals("organization")){
           property="http://www.domain2.com#��ص�λ";
        }
       else if(item.equals("poisfood")){
            property="http://www.domain2.com#causedby";
       }    
       else if(item.equals("resemble") )   {
           property="http://www.domain2.com#resemble";
       }
       else if(item.equals("details") )   {
        property="http://www.domain2.com#����";
       }
       else if(item.equals("belongto") )   {
           property="http://www.domain2.com#belongto";
       }
       else if(item.equals("poisonname")){
           property="http://www.domain2.com#��������";
      }
       else if(item.equals("introduction") )   {
           property="http://www.domain2.com#���";
       }
     
       else if(item.equals("harm") )   {
           property="http://www.domain2.com#Σ��";
       }
       else if(item.equals("category") )   {
           property="http://www.domain2.com#���";
       }
        else if(item.equals("clinical") )   {
           property="http://www.domain2.com#�ٴ�����";
       } else if(item.equals("basic") )   {
           property="http://www.domain2.com#������Ϣ";
       } else if(item.equals("treat") )   {
           property="http://www.domain2.com#��������";
       } else if(item.equals("detect") )   {
           property="http://www.domain2.com#��ⷽ��";
       }
       else if(item.equals("purpose") )   {
           property="http://www.domain2.com#��;";
       }else if(item.equals("prevent") )   {
           property="http://www.domain2.com#Ԥ��";
       }
       else if(item.equals("exist") )   {
           property="http://www.domain2.com#exist";
       }
       else if(item.equals("relate") )   {
           property="http://www.domain2.com#���";
       }
   return property;
   }
     /**
      * ��valueǰ����http://www.owl-ontologies.com/unnamed.owl#�γ��µ�result
      * @param value
      * @return
      */
   public String addNameSpace(String value){
         String result=value;
         result= "http://www.owl-ontologies.com/unnamed.owl#"+value;
         return result;
   }
   /**
    * ��string sou��"^^"֮�������ɾ���γ��µ�string result
    * @param sou
    * @return
    */
     public String removeType(String sou){
       String result=sou;
       if(sou.indexOf("^^")>0){
       result=sou.substring(0,sou.indexOf("^^"));
       }
       return result;
}
     /**
      * ��string sou֮ǰ����http://www.owl-ontologies.com/unnamed.owl#�γ����µ�string result ����
      * @param sou
      * @return
      */
   public String addPrefix(String sou){
   String result=null;
   result="http://www.owl-ontologies.com/unnamed.owl#"+sou;
   return result;
   }
}
