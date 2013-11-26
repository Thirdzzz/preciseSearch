package com.test.bean;

import java.util.List;

public class bean {
	public int getTotalPage(List ga,int pageSize) {

       
           int  pages = ((ga.size() - 1) / pageSize + 1);
           
        
        return pages;
    }
	
public int getPagedRs(int pageSize, int pageNumber) {
    	
        
            
        int absoluteLocation = pageSize * (pageNumber - 1);

       

            //让结果集rs定位到本页之前的最后一个结果处
      
        
        // 返回该页要显示的结果
        return absoluteLocation+1;

    }

}
