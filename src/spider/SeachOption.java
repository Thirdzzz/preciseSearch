package spider;
import java.sql.ResultSet;
import java.util.List; 
import java.util.ArrayList; 


public class SeachOption {
	static List KeywordList = new ArrayList();
	static List WebsiteList = new ArrayList();
	static int Depth = 0;
	static int ThreadCount = 0;
	static int StartTime = 0;
	static int EndTime = 0;
	
	public SeachOption() throws Exception
	{
		String sqlStr = "Select Depth,ThreadCount,StartTime,EndTime from searchconfig";
		DBoperate myoperate = new DBoperate();
		ResultSet rs = myoperate.executeQuery(sqlStr);
		 while (rs.next()){ 
			 Depth = rs.getInt("Depth");
			 ThreadCount = rs.getInt("ThreadCount");
			 StartTime = rs.getInt("StartTime");
			 EndTime = rs.getInt("EndTime");
	        } 
	}
	
	 
	public static List GetKeywordList() throws Exception
	{
		String sqlStr = "Select Ename from keyword where IsAvailble = 1";
		KeywordList = DBoperate.resultSetToSingleList(sqlStr);
		return KeywordList;
	}
	
	
	public static List WebsiteList() throws Exception
	{
		String sqlStr = "Select URL from website where Avaible = 1";
		WebsiteList = DBoperate.resultSetToSingleList(sqlStr);
		return WebsiteList;
	}
	
	public static int getDepth()
	{
		return Depth;
	}
	
	public static int getThreadCount()
	{
		return ThreadCount;
	}
	
	public static int getStartTime()
	{
		return StartTime;
	}
	
	public static int getEndTime()
	{
		return EndTime;
	}
	
	
	//example
	/*
	   		SeachOption myoption = new SeachOption();
		List mylist = SeachOption.GetKeywordList();
		for(int i=0;i<mylist.size();i++)
		{
			out.print(mylist.get(i)+"<br/>");
		}
		
          out.print(SeachOption.getDepth()+"<br/>");
	 */
}
