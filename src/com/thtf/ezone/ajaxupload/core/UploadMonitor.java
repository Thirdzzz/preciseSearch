package com.thtf.ezone.ajaxupload.core;

import org.directwebremoting.WebContextFactory;

import javax.servlet.http.HttpServletRequest;

public class  UploadMonitor {
	//返回值是自定义Java Bean
	public static UploadInfo getUploadInfo() {
		HttpServletRequest req = WebContextFactory.get()
				.getHttpServletRequest();

		if (req.getSession().getAttribute("uploadInfo") != null)
			return (UploadInfo) req.getSession().getAttribute("uploadInfo");
		else
			return new UploadInfo();
	}
}
