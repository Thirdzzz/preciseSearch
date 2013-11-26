package resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.search.Hits;

public class Posts3 extends HttpServlet {	//~~~同posts2.java  只不过传到了intelliresult2.jsp	learned

	/**
	 * Constructor of the object.
	 */
	public Posts3() {
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
		response.setContentType("text/html");
		System.out.println("分页开始！");
		String pageNumberStr = request.getParameter("pageNumber3");   
	    int pageNumber3 = 1;   
	    if(pageNumberStr!=null && !pageNumberStr.isEmpty())   
	    {   
	        pageNumber3 = Integer.parseInt(pageNumberStr);   
	    }   
	    System.out.println(pageNumber3);
	    int pageSize3 = 5; //分页大小   
	    HttpSession session=request.getSession(true);
	    Hits l=(Hits)session.getAttribute("memory3");
	    int totalPosts3 = l.length(); //总文章数   
	    int totalPages3 = totalPosts3/pageSize3 + ((totalPosts3%pageSize3)>0?1:0); //计算得出的总页数   
	       
	    session.setAttribute("pageSize3", pageSize3);   
	    session.setAttribute("totalPosts3", totalPosts3);   
	    session.setAttribute("pageNumber3", pageNumber3);   
	    session.setAttribute("totalPages3", totalPages3);       
	    request.getRequestDispatcher("intelliresult2.jsp").forward(request, response);          
	
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
