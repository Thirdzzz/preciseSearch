package com.thtf.ezone.ajaxupload.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
//download by http://www.codefans.net
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.thtf.ezone.ajaxupload.core.MonitoredDiskFileItemFactory;
import com.thtf.ezone.ajaxupload.core.UploadListener;


public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3371936869824399857L;


	public FileUploadServlet() {

		super();
	}

	public void destroy() {

		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();      
		ServletContext  application  = session.getServletContext();    
		String serverRealPath = application.getRealPath("/") ;
		// upload file
		String path_fullname=upload(request, response);//返回存储路径
		PrintWriter out = response.getWriter();
		TestImageSearch image_Lire=new  TestImageSearch();
		List <String>pipeijihe=null;
		try {
		     pipeijihe=image_Lire.searchSimilar(path_fullname,serverRealPath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		String path_name=FilenameUtils.getName(path_fullname);//获取文件名
		response.sendRedirect("image_Lire.jsp?path_name="+path_name+"&pipeijihe0="+pipeijihe.get(0)+"&pipeijihe1="+pipeijihe.get(1)+"&pipeijihe2="+pipeijihe.get(2));
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {

		// Put your code here
	}

	private static String upload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
         String Path = null;
		// 用于存放上传文件的目录
		String uploadPath = "d:\\temp\\"; 
		// 创建了一个监听器，用来监听文件上传的状态
		// 进度条是通过这个监听器来动态改变的
		UploadListener listener = new UploadListener(request, 30);

		// (1)Create a factory for disk-based file items
		FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);

		// (2)Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try { // 允许上传的文件大小,4G
			// upload.setSizeMax(4 * 1024 * 1024 * 1024);

			// (3) process uploads ..
			List<FileItem > uploadlist = upload.parseRequest(request);
			// 开始读取上传信息
			Iterator<FileItem> iter = uploadlist.iterator();

			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();
				if (item.getFieldName().equals("uppath")) {
 
					uploadPath = item.getString(); 
					

				}
				if (!item.isFormField()) {
					// 忽略其他不是文件域的所有表单信息

					// 取到客户端完整 路径+文件名 c:/abc.jpg
					String fullpath = item.getName();
					

					// 取到 文件名 abc.jpg
					String filename = FilenameUtils.getName(fullpath);

					// 取到文件类型 jpg
					String filetype = getFiletype(filename);

					// 判断是否有非法的文件类型
					boolean flag = false;

					// 过滤掉的文件类型
					String[] errorType = { "exe", "com", "cgi", "jsp","asp","php" };

					for (int i = 0; i <= errorType.length - 1; i++) {
						if (filetype.equals(errorType[i])) {
							flag = true;
						}
					}
					if (flag)
						// 如果有非法文件,退出
						break;

					if (!filename.equals("")) {
						// 文件重命名
						File path = new File(uploadPath);
		
	
						
						if (!path.exists()) {
							// 目录不存在则创建
						
							path.mkdirs();
						}

						String savepath = uploadPath + filename;
						System.out.println("save!!"+savepath);
						Path=savepath;
						// System.out.println("savepath is " + savepath);
						File saveFilepath = new File(savepath);

						item.write(saveFilepath);
					} else {
						// System.out.print("没东西!");
					}
				}
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {
			// System.out.println("文件太大了吧!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Path;
	}

	// 取文件名后缀
	private static String getFiletype(String fileName) {

		String type = "";
		if (fileName == null || fileName.equals(""))
			return type;
		int position = fileName.lastIndexOf(".");
		if (position != -1) {
			type = fileName.substring(position + 1, fileName.length());
		}
		return type;
	}

}
