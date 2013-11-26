package spider;

import java.sql.*;
import java.util.*;


public class Pages
{

    private ResultSet rs=null;
    private ResultSetMetaData rsmd=null;

    private int rowCount;

    private int pageCount;


    private int columnCount;
    private int irows;
    public void initialize(String sqlStr,int pageSize)
    {

      try
      {
         DBoperate myoperate = new DBoperate();
         rs = myoperate.executeQuery(sqlStr);        
         rsmd = myoperate.getResultSetMetaData();
       
       if(rs!=null)
       {
          rs.last();
       rowCount=rs.getRow();
      // System.out.println("rowCount:"+rowCount);
       
       rs.first();
       columnCount=rsmd.getColumnCount();
    
       
       pageCount=(rowCount-1)/pageSize+1;
       
   //    System.out.println("pageCount:"+pageCount);
       
       rs.close();
       myoperate.close();
       }
      
       }catch(Exception ex)
    {
              ex.printStackTrace();
        }
    }
    public Vector getPage(String sqlStr,int pageSize,int showPage) throws Exception
    {
    	
        irows=pageSize*(showPage-1);
       	
    	 sqlStr=sqlStr+" limit "+irows+","+pageSize;
      
         DBoperate myoperate = new DBoperate();
         rs= myoperate.executeQuery(sqlStr);
    	
           Vector vData=new Vector();
     try
     {
         if(rs!=null)
      {
            
         while(rs.next())
      {     
             String[] sData=new String[columnCount];
            
             
          for(int j=0;j<columnCount;j++)
       {
               sData[j]=rs.getString(j+1);
          }
          vData.addElement(sData);
        }
         
         myoperate.close();
       }
      }catch(Exception ex)
      {
          ex.printStackTrace();
      }
            return vData;
  }
       

     public int getPageCount()
     {
             return pageCount;
     }
 
     public int getRowCount()
     {
             return rowCount;
     }
     
}
