package spider;

import java.net.URI;
import java.sql.*;
import java.io.*;
import java.util.List; 
import java.util.ArrayList; 
import java.util.Map; 
import java.util.HashMap; 
import java.util.Collections; 
import javax.xml.parsers.*;
import org.xml.sax.helpers.*;
import org.xml.sax.*;
import java.io.*;
import org.w3c.dom.*;

public class DBoperate {
	

	String driverName="com.mysql.jdbc.Driver";
	
	static String userName1="root";

	static String userPasswd1="123";

	static String dbName1="spider";

	

	static String url1="jdbc:mysql://127.0.0.1:3306/"+dbName1+"?user="+userName1+"&password="+userPasswd1+"&useUnicode=true&characterEncoding=gbk";
	
	//String url="jdbc:mysql://127.0.0.1:3306/"+dbName+"?user="+userName+"&password="+userPasswd+"&useUnicode=true&characterEncoding=gbk";
	
	
	String userName="";

	String userPasswd="123";

	String dbName="";

	String tableName="users";
   
	String ul = "";
	
	String url="";
	
	//Connection connection=DriverManager.getConnection(url);
	
//	private String username = "root";
//	private String password = "lab417";
//	
//	private String url = "jdbc:mysql://spiderproject.3322.org:3306/spider?user="+username+"&passWord="+password+"&useUnicode=true&characterEncoding=GB2312";

	
	
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepstmt = null;
    private ResultSet myset = null;


   // private String Connstr = url+","+username+","+password;

    public DBoperate() throws Exception {
    Class.forName("com.mysql.jdbc.Driver"); 
    url = MyXMLReader();
    System.out.println("url:"+url);
    conn = DriverManager.getConnection(url);
    stmt = conn.createStatement();
    }
    public DBoperate(String sql) throws Exception {
        this();
    this.prepareStatement(sql);
    }


    public Connection getConnection() {
    return conn;
    }

    public void prepareStatement(String sql) throws SQLException {
    prepstmt = conn.prepareStatement(sql);
    }

    public void setString(int index,String value) throws SQLException {
    prepstmt.setString(index,value);
    }
    public void setInt(int index,int value) throws SQLException {
    prepstmt.setInt(index,value);
    }
    public void setBoolean(int index,boolean value) throws SQLException {
    prepstmt.setBoolean(index,value);
    }
    public void setDate(int index,Date value) throws SQLException {
    prepstmt.setDate(index,value);
    }
    public void setLong(int index,long value) throws SQLException {
    prepstmt.setLong(index,value);
    }
    public void setFloat(int index,float value) throws SQLException {
    prepstmt.setFloat(index,value);
    }
    //File file = new File("test/data.txt");
    //int fileLength = file.length();
    //InputStream fin = new java.io.FileInputStream(file);
    //DBoperate.setBinaryStream(5,fin,fileLength);
    public void setBinaryStream(int index,InputStream in,int length) throws SQLException {
    prepstmt.setBinaryStream(index,in,length);
    }

    public void clearParameters()
    throws SQLException
    {
    prepstmt.clearParameters();
    }

    public PreparedStatement getPreparedStatement() {
    return prepstmt;
    }

    public Statement getStatement() {
    return stmt;
    }

    public ResultSetMetaData getResultSetMetaData() throws SQLException
    {
    	 return myset.getMetaData(); 	
    }
    
    public ResultSet executeQuery(String sql) throws SQLException {
    if (stmt != null) {
    	myset = stmt.executeQuery(sql);
    return myset;
    }
    else return null;
    }
    public ResultSet executeQuery() throws SQLException {
    if (prepstmt != null) {
    return prepstmt.executeQuery();
    }
    else return null;
    }

    public void executeUpdate(String sql) throws SQLException {
    if (stmt != null)
    stmt.executeUpdate(sql);
    }
    public void executeUpdate() throws SQLException {
    if (prepstmt != null)
    prepstmt.executeUpdate();
    }

    public void close() throws Exception {
    if (stmt != null) {
    stmt.close();
    stmt = null;
    }
    if (prepstmt != null) {
    prepstmt.close();
    prepstmt = null;
    }
    if(!conn.isClosed())
    {	
        conn.close();
        conn = null;    	
    }
    }
    
//    public static ResultSet getDataSet(String sqlStr) throws Exception
//    {
//		DBoperate myoperate = new DBoperate();
//    	ResultSet myset = myoperate.executeQuery(sqlStr);
//    	
//    	myoperate.close();
//    	myoperate = null;
//    
//    	return myset;
//    }
  
    /** 
     
     * @param rs ResultSet 
     * @return List 
     * @throws Exception 
     */ 
    public static List resultSetToList(String sqlStr) throws Exception{ 

        
		DBoperate myoperate = new DBoperate();
    	ResultSet rs = myoperate.executeQuery(sqlStr);
    	
        if (rs==null) return Collections.EMPTY_LIST;  
        
        ResultSetMetaData rsmd = rs.getMetaData(); 
        
        int columnCount = rsmd.getColumnCount(); 
  
        List list = new ArrayList(); 
        Map rowData; 
        while (rs.next()){ 
            rowData = new HashMap(columnCount); 
            for (int i=1; i<=columnCount; i++){ 
                rowData.put(rsmd.getColumnName(i),rs.getObject(i)); 
            } 
            list.add(rowData); 
        } 
        
    	myoperate.close();
    	myoperate = null;
        
        return list; 
    } 
    
    
    public static List resultSetToSingleList(String sqlStr) throws Exception{ 

    	List list = new ArrayList(); 
		DBoperate myoperate = new DBoperate();
    	ResultSet rs = myoperate.executeQuery(sqlStr);
    	
        if (rs==null) return Collections.EMPTY_LIST;           
       
        while (rs.next()){ 
            list.add(rs.getObject(1)); 
        } 
        
    	myoperate.close();
    	myoperate = null;
        
        return list; 
    } 
    public String MyXMLReader() {  
	     String u = "";
	     try{		  
/*		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	   
		  DocumentBuilder builder = factory.newDocumentBuilder();
		//  Document document = builder.parse(new File("E:\\spider\\.metadata\\.me_tcat\\webapps\\spider\\Heritrix\\webapps\\admin\\WEB-INF\\DBConnect.xml"));
	String childPath = DBoperate.class.getResource("/").toString();
		  File childFile = new File(new URI(childPath));
	String filePath = childFile.getParent().toString()+"\\DBConnect.xml";	*/
	
	
	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	   
	  DocumentBuilder builder = factory.newDocumentBuilder();
	  String filePath = DBoperate.class.getResource("/").toString();
	  File dir = new File(new URI(filePath));
	  dir = dir.getParentFile();//go to parent directory
	  File conf = new File(dir, "DBConnect.xml");
	 // System.out.println(dir);
	  Document document = builder.parse(conf);
	 
		   NodeList nodelist = document.getElementsByTagName("url");
	       int size = nodelist.getLength();
		   for(int k=0; k<size;k++)
		     {
			   Node node = nodelist.item(k);
			    // String name = node.getNodeName();
			     ul = node.getTextContent();
			     //System.out.print(name);
			    // System.out.println(ul);
		     }
		   NodeList nodelist1 = document.getElementsByTagName("password");
	       int size1 = nodelist1.getLength();
		   for(int k=0; k<size1;k++)
		     {
			   Node node = nodelist1.item(k);
			    // String name = node.getNodeName();
			      userPasswd = node.getTextContent();
			     //System.out.print(name);
			    // System.out.println(content);
		     }
		   NodeList nodelist2 = document.getElementsByTagName("username");
	       int size2 = nodelist2.getLength();
		   for(int k=0; k<size;k++)
		     {
			   Node node = nodelist2.item(k);
			    // String name = node.getNodeName();
			      userName = node.getTextContent();
			     //System.out.print(name);
			   //  System.out.println(content);
		     }
		   NodeList nodelist3 = document.getElementsByTagName("dbname");
	       int size3 = nodelist3.getLength();
		   for(int k=0; k<size;k++)
		     {
			   Node node = nodelist3.item(k);
			    // String name = node.getNodeName();
			      dbName = node.getTextContent();
			     //System.out.print(name);
			     //System.out.println(content);
		     }
		 
		  
	  }
	 
	  catch(Exception e)
	     {
		     System.out.println(e);
	     }
	 u = ul+dbName+"?user="+userName+"&password="+userPasswd+"&useUnicode=true&characterEncoding=gbk";		  
	 return u;
//   private String page_navlink_insert="insert into page_navlink values (?,?,?,?)";
//
//    public void insertnavlink() throws Exception
//    {
//    	ResultSet rs=null;
//    	try {
//	DBoperate dboperate = new DBoperate(page_navlink_insert);
//	dboperate.setInt(1,this.siteid);
//	dboperate.setInt(2,this.pageid);
//	dboperate.setString(3,this.navlinkname);
//	dboperate.setString(4,this.pagefile);
//	dboperate.executeUpdate();
//
//	dboperate.close();
//	dboperate = null;
//
//	} catch (Exception ex) {
//		throw new Exception("insertnavlink()"+ex.getMessage());
//    }

   // }
    }    
   /* 
    public static void main(String args[]) throws Exception {
  	
    	String Name=null;
    	String Category=null;
    	String Cid=null;
    	int id=40;
    	
    	 ResultSet rs = null;

    	 Class.forName("com.mysql.jdbc.Driver"); 
    	 //url = MyXMLReader();
    	    //System.out.println("url:"+url);
    	 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider?user=root&password=123&useUnicode=true&characterEncoding=gbk");
    	 Statement stmt = conn.createStatement();
    	 Statement stmt2 = conn.createStatement();
    	 rs=stmt.executeQuery("select * from keyword");
    	 while(rs.next())
    	 {
    		Name=rs.getString("CName");
    		Category=rs.getString("Category");
    		
    		if(Category.equals("自然灾害类"))
    			Cid="6";
    		else if(Category.equals("公共卫生事件类"))
    			Cid="7";
    		else if(Category.equals("事故灾难类"))
    			Cid="8";
    		else if(Category.equals("社会安全事件类"))
    			Cid="9";
    			
    		 stmt2.executeUpdate("insert into expand(expandid,expanname,weight,topicid) value("+String.valueOf(id)+",'"+Name+"','',"+Cid+");");
    		 id++;
    	 }
    	 
    	 stmt2.close();
    	 stmt.close();
    	 conn.close();
    	 
    	 System.out.println("op");
 	}*/
} 
