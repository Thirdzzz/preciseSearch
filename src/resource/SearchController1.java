package resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchController1 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SearchController1() {
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

		System.out.println("开始！");
		response.setContentType("text/html");
		String queryStr1=request.getParameter("queryStr");
		String Str=new String(queryStr1.getBytes("iso8859_1"));
		System.out.println(Str);
		GetFile g=new GetFile();
		HttpSession session=request.getSession(true);
		List l=new LinkedList();
		System.out.println(session.isNew());
		if((List)session.getAttribute("memory")!=null)
			l=(List)session.getAttribute("memory");
		String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		System.out.println("预处理保存路径");
		int index3=curPath.indexOf("file:");
		curPath=curPath.substring(6+index3);
		System.out.println(curPath);
		g.ToGetFile(Str,curPath+ "../../结果","",l);
		String pageNumberStr = request.getParameter("pageNumber");   
	    int pageNumber = 1;   
	    if(pageNumberStr!=null && !pageNumberStr.isEmpty())   
	    {   
	        pageNumber = Integer.parseInt(pageNumberStr);   
	    }   
 
	    int pageSize = 10; //分页大小   
	    int totalPosts = l.size(); //总文章数   
	    int totalPages = totalPosts/pageSize + ((totalPosts%pageSize)>0?1:0); //计算得出的总页数   
	       
	    session.setAttribute("pageSize", pageSize);   
	    session.setAttribute("totalPosts", totalPosts);   
	    session.setAttribute("pageNumber", pageNumber);   
	    session.setAttribute("totalPages", totalPages);
		session.setAttribute("searchResult", l);
		session.setAttribute("memory", l);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/implement2.jsp");
		dispatcher.forward(request, response);

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
