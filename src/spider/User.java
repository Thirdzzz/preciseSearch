package spider;

import java.sql.*;

public class User {

	private String userid = "";
	private String username = "";
	private int right = 0;
	private int isavailble = 0;
    
	public String getname()
	{
		return username;
	}
	
	public void setname(String name)
	{
		this.username = name;		
	}
	
	public int getright()
	{
		return right;
	}
	
	public void setright(int right)
	{
		this.right = right;
	}
	
	public int getavailble()
	{
		return isavailble;
	}
	
	public void setavaible(int avaible)
	{
		this.isavailble = avaible; 
	}
	
	public String getuserid()
	{
		return this.userid;
	}
	
	public void setuserid(String userid)
	{
		this.userid = userid; 
	}

    public User()
    {

    }
    
    public User getuser(String username,String password) throws Exception 
    {
    	User loginuser = new User();
    	String sqlstr = "Select * from users where username ='"+username+"' and password ='"+password+"'";
    	DBoperate myoperate = new DBoperate();    	
    	ResultSet rs = myoperate.executeQuery(sqlstr);	
    	if(rs.next())   
    	{
    			loginuser.userid = Integer.toString((rs.getInt("ID")));
    			loginuser.username = rs.getString("UserName");
           		loginuser.right = rs.getInt("UserRight");
        		loginuser.isavailble = rs.getInt("IsAvailble");
    	}
    	    		
        	myoperate.close();
        	myoperate = null;
  	  	
    	return loginuser;
    }
    
    
}
	