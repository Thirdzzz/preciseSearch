package tfidf;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import java.util.Map.Entry;
public class Main {
 public static void main(String[] args) throws IOException {
 Map<String, HashMap<String, Integer>> normal = ReadFiles.NormalTFOfAll("e:/testfiles");
 for (String filename : normal.keySet()) {
 System.out.println("fileName " + filename);
 System.out.println("TF " + normal.get(filename).toString());
 }
 
 System.out.println("3-----------------------------------------");
 Map<String, HashMap<String, Float>> tfidf = ReadFiles.tfidf("e:/testfiles");
 for (String filename : tfidf.keySet()) {
 System.out.println("fileName 123" + filename);
 System.out.println(tfidf.get(filename));
 Map <String, Float> it = tfidf.get(filename);
 
 
 ArrayList<Entry<String, Float>> arrayList = new ArrayList<Map.Entry<String,Float>>(it.entrySet());  
//ÅÅÐò  
Collections.sort(arrayList, new Comparator<Map.Entry<String, Float>>(){  
  public int compare(Map.Entry<String, Float> map1,  
          Map.Entry<String,Float> map2) {  
      return ((map2.getValue() - map1.getValue() == 0) ? 0  
              : (map2.getValue() - map1.getValue() > 0) ? 1  
                      : -1);  
  }  
});  
//Êä³ö  
int i=0;

for (Entry<String, Float> entry : arrayList) {  
  if(i<20)
  {
  System.out.println(entry.getKey() + "\t" + entry.getValue());  
  
  i++;
  }
  else
  {
  break;
  }
}  
 
 
 }
 }
}
