package yyh;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
public class searchResult {
    public getInfModel myGet = new getInfModel ();
    public InfModel model=myGet.getInfModel() ;		//~~~推理模式
public  ResIterator search(String item,String value){

    Property searchProperty;
    ResIterator searchResult=null;
    getInfModel myGet = new getInfModel ();

    searchProperty=model.getProperty(myGet.itemToProperty(item));
    if(item.equals("poisfood")||item.equals("poison") ){
    Resource SearchValue;
    SearchValue=model.createResource(myGet.addNameSpace(value));
    searchResult=model.listSubjectsWithProperty(searchProperty,SearchValue);
    }else if (item.equals("events")){
    String SearchValue=value;
    searchResult=model.listSubjectsWithProperty(searchProperty,SearchValue);
    }

     return searchResult;
}
 public String getValue(Resource res,String item){
       String result=null;
       Property searchProperty;
       searchProperty=model.getProperty(myGet.itemToProperty(item));
       StmtIterator SearchResult=model.listStatements(res,searchProperty,(RDFNode)null);

       while(SearchResult.hasNext() ){
       result=SearchResult.nextStatement().getObject().toString() ;
       }
       return myGet.removeType(result);//SearchResult.toString();
      }
 
}
