package resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import resource.search.ResultBean;
import resource.search.SearchManager;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

@SuppressWarnings("serial")
public class ReStart extends HttpServlet {		//~~~将会话中的一些参数全部改为初值，实现了restart的效果，并将初值的参数传给intelliresult1.jsp界面		语义拓展	learned

	/**
	 * Constructor of the object.
	 */
	public ReStart() {
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
		HttpSession session=request.getSession(true);
		session.setAttribute("memory1",null);
		session.setAttribute("pageNumber1",null);
		session.setAttribute("totalPosts1", null);      
		session.setAttribute("totalPages1", null);
		String queryStr1=request.getParameter("queryStr");
		String queryStr=new String(queryStr1.getBytes("iso8859_1"));
		String radioStr=request.getParameter("radio");
		session.setAttribute("key1", queryStr);
		session.setAttribute("key2", radioStr);
		System.out.println("实现！");
		response.sendRedirect("intelliresult1.jsp");
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
