package resource;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BACE extends HttpServlet {			//~~~learned

	/**
	 * Constructor of the object.
	 */
	public BACE() {
		super();		//~~~继承HttpServlet
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
	 * 实现一个service()方法
		Servlet的主要的功能是从浏览器接收HTTP请求，并返回HTTP响应。这个工
		作由你的servlet的service()方法完成。Service()方法包含了用来创建输出的response对象和用来接收客户端数据的request对象。
		你可以看到其它实现了doPost()和（或）doGet()方法的servlet例子。这些方法仅仅回复了POST或GET请求。如果你在一个方法里处理所有类型的请求，你的servlet可以就实现这个service()方法。（可是，如果你选择了实现service()方法，那你就不能实现doPost()或doGet()方法了，除非你在service()方法的开始调用super.service()。）HTTP Servlet标准描述了其它用来处理其它请求类型的方法，但是所有的这些方法都被收集起来，作为service()方法。
		所有的service()方法接收相同的参数。HttpServletRequest提供了关于请求的信息。你的servlet使用HttpServletResponse回复HTTP客户端。这个service方法如下：
		public void service(HttpServletRequest req, HttpServletResponse res)
  		throws IOException {
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		//~~~servlet是对网页请求和处理回应的操作
														//~~~HttpServletRequest请求信息
		response.setContentType("text/html");			//~~~HttpServletResponse响应信息
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
