package resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Posts2 extends HttpServlet {		//~~~ͬposts1.java	ֻ����pagesize��Ϊ5��	  learned

	/**
	 * Constructor of the object.
	 */
	public Posts2() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		System.out.println("��ҳ��ʼ��");
		String pageNumberStr = request.getParameter("pageNumber2");   
	    int pageNumber2 = 1;   
	    if(pageNumberStr!=null && !pageNumberStr.isEmpty())   
	    {   
	        pageNumber2 = Integer.parseInt(pageNumberStr);   
	    }   
	    System.out.println(pageNumber2);
	    int pageSize2 = 5; //��ҳ��С   
	    HttpSession session=request.getSession(true);
	    List<String>l=(List)session.getAttribute("memory2");
	    int totalPosts2 = l.size(); //��������   
	    int totalPages2 = totalPosts2/pageSize2 + ((totalPosts2%pageSize2)>0?1:0); //����ó�����ҳ��   
	       
	    session.setAttribute("pageSize2", pageSize2);   
	    session.setAttribute("totalPosts2", totalPosts2);   
	    session.setAttribute("pageNumber2", pageNumber2);   
	    session.setAttribute("totalPages2", totalPages2);       
	    request.getRequestDispatcher("intelliresult1.jsp").forward(request, response);          
	
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
