package resource;

import java.io.*;
import java.util.List;



public class GetFile {			//~~~~ʹ����ProcessForWeb    learned
	public GetFile(){
		
	}
	
	/**
	 * ����Ҫ����˼Ӧ�þ��Ǵ�Ŀ��·��path�ж�ȡ�ļ����������ļ���Ŀ¼����΢�����£��ݹ�
	 * Ȼ���·���ļ������ȥ��<>tag�����š��ո����
	 * �Ѵ������ı�д�뵽�ļ���.txt��
	 * ԭ�ļ������ı�
	 * */
	public void ToGetFile(String path,String a,String add,List<String> fram)
	{
		//String path = ; //·��
		File f = new File(path);
		if (!f.exists())
		{
			System.out.println(path+" not exists");
		    return;
		}
		if(f.isDirectory())						//~~~��һ�����ļ��еĴ���
		{
			File fa[] = f.listFiles();			//~~~File����Ҳ�������飬��ľ�У�
			for(int i=0;i<fa.length;i++)
			{
				File fs = fa[i];
			    if (fs.isDirectory())			//~~~����ڶ��㻹���ļ���
			    {	    	
			    	File   theFile=new   File(a+add+"\\"+fs.getName()); 
		            if   (theFile.exists()   ==   true)   { 
		                theFile.delete(); 		//~~~����ڶ���
		            } 
		            theFile.mkdirs();  			//~~~�����ļ���   	û����ΪɶҪɾ���ڽ���������  
			    	ToGetFile(path+"\\"+fs.getName(),a,add+"\\"+fs.getName(),fram);
			    }
			    else
			    {
			    	if(fs.getName().toLowerCase().endsWith(".html")||fs.getName().toLowerCase().endsWith(".htm")||fs.getName().toLowerCase().endsWith(".txt")||fs.getName().toLowerCase().endsWith(".asp"))
			    	{			//~~~�� �ڶ����ļ�.html   .htm  .txt   .asp�Ĵ���
			    		if(add=="")
			    		{
			    			ProcessForWeb p=new ProcessForWeb();
			    			StringBuffer t=p.SubContentBufferAll(p.ReadFile(fs.getAbsolutePath()));
			    			t=p.DelOther(t);				//~~~���������ProcessForWeb�ĺ���
			    			t=p.DelSpace(t);
			    			p.WriteFile(t.toString(),a+"\\"+fs.getName()+".txt");		//~~~�������ı��Ĵ���ȥ��tag�����ţ��ո���ȣ�������д�ص���txt�ļ���
			    			fram.add(fs.getAbsolutePath().toString());					//~~~fram�ַ�������������˱��޸��ļ���·��
			    		}
			    		else
			    		{
			    			ProcessForWeb p=new ProcessForWeb();
			    			StringBuffer t=p.SubContentBufferAll(p.ReadFile(fs.getAbsolutePath()));
			    			t=p.DelOther(t);
			    			t=p.DelSpace(t);
			    			p.WriteFile(t.toString(),a+add+"\\"+fs.getName()+".txt");	//~~~�Ҿ��þ���add=""�����Ҳ���Բ��뵽else���氡���Ͼ����Ͽջ��ǲ���·����������
			    			fram.add(fs.getAbsolutePath().toString());
			    			//System.out.println(a+add+"\\"+fs.getName()+".txt");
			    		}
			    	}//System.out.println(fs.getName()+"<br />");
			    }
			}
		}
		else
		{
			if(f.getName().toLowerCase().endsWith(".html")||f.getName().toLowerCase().endsWith(".htm")||f.getName().toLowerCase().endsWith(".txt")||f.getName().toLowerCase().endsWith(".asp"))
	    	{
	    			ProcessForWeb p=new ProcessForWeb();
	    			StringBuffer t=p.SubContentBufferAll(p.ReadFile(f.getAbsolutePath()));
	    			t=p.DelOther(t);
	    			t=p.DelSpace(t);
	    			p.WriteFile(t.toString(),a+"\\"+f.getName()+".txt");
	    			fram.add(f.getAbsolutePath().toString());
	    	}
		}
		}
}
