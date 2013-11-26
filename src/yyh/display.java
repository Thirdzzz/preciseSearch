package yyh;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
public class display {		//~~~借用了getInfModel。java中的函数

      public getInfModel myGet = new getInfModel ();
      public InfModel model=myGet.getInfModel() ;
      public String getValue(String res,String item){
        String result=null;
        Resource resource=model.createResource(myGet.addPrefix(res));
        Property searchProperty;
        searchProperty=model.getProperty(myGet.itemToProperty(item));
        StmtIterator SearchResult=model.listStatements(resource,searchProperty,(RDFNode)null);

        while(SearchResult.hasNext() ){
        result=SearchResult.nextStatement().getObject().toString() ;
        }
        return myGet.removeType(result);//SearchResult.toString();
      }
   public  NodeIterator getIterator(String res,String item){
    NodeIterator result=null;
    Resource resource=model.createResource(myGet.addPrefix(res));
    Property property=model.getProperty(myGet.itemToProperty(item));
    result=model.listObjectsOfProperty(resource,property);
    return result;
    }

}
