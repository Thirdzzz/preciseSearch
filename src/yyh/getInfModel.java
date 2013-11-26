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
	      infModel=ModelFactory.createInfModel(reasoner, data);		//~~~这里应该是推理器之类的吧???
	   return infModel;
	   }
    	 
    /**
     * item转property
     * eg：	name ->	http://www.domain2.com#名称
     * @param item
     * @return
     */
     public String itemToProperty(String item){
   String property=null;
     if (item.equals("events")){
           property="http://www.domain2.com#关键词";
       }
     if (item.equals("poison")){
         property="http://www.domain2.com#belongto";
     }
       else if(item.equals("name")){
            property="http://www.domain2.com#名称";
       }
       else if(item.equals("date")){
           property="http://www.domain2.com#日期";
       }
       else if(item.equals("place")){
          property="http://www.domain2.com#地点";
       }
       else if(item.equals("organization")){
           property="http://www.domain2.com#相关单位";
        }
       else if(item.equals("poisfood")){
            property="http://www.domain2.com#causedby";
       }    
       else if(item.equals("resemble") )   {
           property="http://www.domain2.com#resemble";
       }
       else if(item.equals("details") )   {
        property="http://www.domain2.com#详情";
       }
       else if(item.equals("belongto") )   {
           property="http://www.domain2.com#belongto";
       }
       else if(item.equals("poisonname")){
           property="http://www.domain2.com#物质名称";
      }
       else if(item.equals("introduction") )   {
           property="http://www.domain2.com#简介";
       }
     
       else if(item.equals("harm") )   {
           property="http://www.domain2.com#危害";
       }
       else if(item.equals("category") )   {
           property="http://www.domain2.com#类别";
       }
        else if(item.equals("clinical") )   {
           property="http://www.domain2.com#临床表现";
       } else if(item.equals("basic") )   {
           property="http://www.domain2.com#基本信息";
       } else if(item.equals("treat") )   {
           property="http://www.domain2.com#急救治疗";
       } else if(item.equals("detect") )   {
           property="http://www.domain2.com#检测方法";
       }
       else if(item.equals("purpose") )   {
           property="http://www.domain2.com#用途";
       }else if(item.equals("prevent") )   {
           property="http://www.domain2.com#预防";
       }
       else if(item.equals("exist") )   {
           property="http://www.domain2.com#exist";
       }
       else if(item.equals("relate") )   {
           property="http://www.domain2.com#相关";
       }
   return property;
   }
     /**
      * 在value前加上http://www.owl-ontologies.com/unnamed.owl#形成新的result
      * @param value
      * @return
      */
   public String addNameSpace(String value){
         String result=value;
         result= "http://www.owl-ontologies.com/unnamed.owl#"+value;
         return result;
   }
   /**
    * 把string sou中"^^"之后的内容删掉形成新的string result
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
      * 在string sou之前加上http://www.owl-ontologies.com/unnamed.owl#形成了新的string result 返回
      * @param sou
      * @return
      */
   public String addPrefix(String sou){
   String result=null;
   result="http://www.owl-ontologies.com/unnamed.owl#"+sou;
   return result;
   }
}
