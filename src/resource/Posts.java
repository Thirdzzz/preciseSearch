package resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Posts extends HttpServlet {		//~~~本体拓展需要原始网页文章，按一页显示N个文章标题的传给implement.jsp界面中		learned

	/**
	 * Constructor of the object.
	 */
	public Posts() {
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

		System.out.println("分页开始！");
		String pageNumberStr = request.getParameter("pageNumber");   
	    int pageNumber = 1;   
	    if(pageNumberStr!=null && !pageNumberStr.isEmpty())   
	    {   
	        pageNumber = Integer.parseInt(pageNumberStr);   
	    }   
	    System.out.println(pageNumber);
	    int pageSize = 10; //分页大小   		分页时一个页面可以放不多于10个文章的内容
	    HttpSession session=request.getSession(true);
	    List<String>l=(List)session.getAttribute("memory");
	    int totalPosts = l.size(); //总文章数   		~~~“memory”中的List<String>l真的是原材料的文章集合
	    int totalPages = totalPosts/pageSize + ((totalPosts%pageSize)>0?1:0); //计算得出的总页数   
	       
	    session.setAttribute("pageSize", pageSize);   
	    session.setAttribute("totalPosts", totalPosts);   
	    session.setAttribute("pageNumber", pageNumber);   
	    session.setAttribute("totalPages", totalPages);       
	    request.getRequestDispatcher("implement.jsp").forward(request, response);    	//~~~把分页后的数据参数发送给implement.jsp页面中	本体拓展      
	}  													//~~~把原始的文章都传到本体拓展的界面上时，每个页面上先显示一定数量的文章


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
