package resource;

import java.io.*;
import java.util.List;



public class GetFile {			//~~~~使用了ProcessForWeb    learned
	public GetFile(){
		
	}
	
	/**
	 * 最主要的意思应该就是从目标路径path中读取文件，可能是文件夹目录，稍微处理下，递归
	 * 然后对路径文件处理后，去除<>tag、符号、空格符等
	 * 把处理后的文本写入到文件名.txt中
	 * 原文件并不改变
	 * */
	public void ToGetFile(String path,String a,String add,List<String> fram)
	{
		//String path = ; //路径
		File f = new File(path);
		if (!f.exists())
		{
			System.out.println(path+" not exists");
		    return;
		}
		if(f.isDirectory())						//~~~第一层是文件夹的处理
		{
			File fa[] = f.listFiles();			//~~~File类型也能有数组，有木有！
			for(int i=0;i<fa.length;i++)
			{
				File fs = fa[i];
			    if (fs.isDirectory())			//~~~如果第二层还是文件夹
			    {	    	
			    	File   theFile=new   File(a+add+"\\"+fs.getName()); 
		            if   (theFile.exists()   ==   true)   { 
		                theFile.delete(); 		//~~~如果第二层
		            } 
		            theFile.mkdirs();  			//~~~创建文件夹   	没看懂为啥要删了在建？？？？  
			    	ToGetFile(path+"\\"+fs.getName(),a,add+"\\"+fs.getName(),fram);
			    }
			    else
			    {
			    	if(fs.getName().toLowerCase().endsWith(".html")||fs.getName().toLowerCase().endsWith(".htm")||fs.getName().toLowerCase().endsWith(".txt")||fs.getName().toLowerCase().endsWith(".asp"))
			    	{			//~~~对 第二层文件.html   .htm  .txt   .asp的处理
			    		if(add=="")
			    		{
			    			ProcessForWeb p=new ProcessForWeb();
			    			StringBuffer t=p.SubContentBufferAll(p.ReadFile(fs.getAbsolutePath()));
			    			t=p.DelOther(t);				//~~~这里调用了ProcessForWeb的函数
			    			t=p.DelSpace(t);
			    			p.WriteFile(t.toString(),a+"\\"+fs.getName()+".txt");		//~~~经过对文本的处理，去除tag，符号，空格符等，再重新写回到。txt文件中
			    			fram.add(fs.getAbsolutePath().toString());					//~~~fram字符串集合中添加了被修改文件的路径
			    		}
			    		else
			    		{
			    			ProcessForWeb p=new ProcessForWeb();
			    			StringBuffer t=p.SubContentBufferAll(p.ReadFile(fs.getAbsolutePath()));
			    			t=p.DelOther(t);
			    			t=p.DelSpace(t);
			    			p.WriteFile(t.toString(),a+add+"\\"+fs.getName()+".txt");	//~~~我觉得就算add=""的情况也可以并入到else里面啊，毕竟加上空还是不变路径啊？？？
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
