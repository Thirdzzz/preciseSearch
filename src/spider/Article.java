package spider;
import java.util.Date;
import java.io.File;
import java.sql.*;
import java.util.*;
import java.sql.ResultSet;

public class Article {
        
        public Article()
        {

        }
	private String url = "";
	private String path = "";
	private String website = "";
	private String title = "";
	private String keyword = "";
	private String content = "";
	private Date searchdate = new Date();
	private Date publicdate = new Date();
	
	public ResultSet Getqueryset(String querycondition)
	{
		ResultSet myset = null;
		
		
		return myset;
	}
	
	public Article getarticle(String title)
	{
		Article myarticle = new Article();
		

		
		return myarticle;
	}
    public static void DeleteArticle(int date )
    {
      try {
          DBoperate db = new DBoperate();
          Timestamp datetoday=new Timestamp(System.currentTimeMillis());  //�����ʱ��
          String sqlStr = "delete from article where datediff('"+datetoday.toString()+"',SearchTime) <= "+String.valueOf(date)+"";
          db.executeUpdate(sqlStr);
          db.close();
     } catch (Exception e) {
  		  e.printStackTrace();
	   }
  
   }
    public static void DeleteFile(int day)
    {
    	try{
    		 DBoperate my = new DBoperate();
             Timestamp datetoday=new Timestamp(System.currentTimeMillis());  //�����ʱ��
             String sqlStr = "select * from article where datediff('"+datetoday.toString()+"',SearchTime) <= "+String.valueOf(day)+"";
             ResultSet rs = my.executeQuery(sqlStr);
             while (rs.next()){
            	 String[] List = rs.getString("MirrorPath").split("mirror");
            	 String path = (List[0].replace("\\","\\\\"));
            	 int length = path.length();
            	 String folderPath = path.substring(0,length-2);      
            	 delFolder(folderPath);             
             }
             my.close();
                          
    	} catch (Exception e) {
    		  e.printStackTrace();
 	   }
    }
    
    public static void delFolder(String folderPath) {
    	   try {
    		// folderPath = folderPath.replace("\\","\\\\");
    	    delAllFile(folderPath); // ɾ����������������
    	    String filePath = folderPath;
    	    filePath = filePath.toString();
    	    java.io.File myFilePath = new java.io.File(filePath);
    	    myFilePath.delete(); // ɾ�����ļ���
    	   } catch (Exception e) {
    	    e.printStackTrace();
    	   }
    	}
    	// ɾ��ָ���ļ����������ļ�
    	// param path �ļ�����������·��
    	public static boolean delAllFile(String path) {
    	   boolean flag = false;
    	   File file = new File(path);
    	   if (!file.exists()) {
    	    return flag;
    	   }
    	   if (!file.isDirectory()) {
    	    return flag;
    	   }
    	   String[] tempList = file.list();
    	   File temp = null;
    	   for (int i = 0; i < tempList.length; i++) {
    	    if (path.endsWith(File.separator)) {
    	     temp = new File(path + tempList[i]);
    	    } else {
    	     temp = new File(path + File.separator + tempList[i]);
    	    }
    	    if (temp.isFile()) {
    	     temp.delete();
    	    }
    	    if (temp.isDirectory()) {
    	     delAllFile(path + "\\" + tempList[i]);// ��ɾ���ļ���������ļ�
    	     delFolder(path + "\\" + tempList[i]);// ��ɾ�����ļ���
    	     flag = true;
    	    }
    	   }
    	   return flag;
    	}
    public static void main(String args[]) {
    	   int i = 2;
    	   int j = 5;
    	   DeleteFile(i);
    	   String h = "F:\\jobs\\2";
    	   String k = "F:\\jobs\\3";
    	   delFolder(h);
    	   delFolder(k);
    	   DeleteArticle(j);
    	   
    	   System.out.println("deleted");
    	}
     		
}

