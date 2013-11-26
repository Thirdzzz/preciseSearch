<%@ page language="java" import="java.util.*,baidutupian.*" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,edu.stanford.smi.protegex.owl.ProtegeOWL,edu.stanford.smi.protegex.owl.jena.JenaOWLModel,com.test.bean.*,java.util.*,resource.search.ResultBean,resource.search.SearchManager,resource.MethodForInput,edu.stanford.smi.protegex.owl.jena.JenaOWLModel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Admin Template</title>
<meta name="description" content="Administry - Admin Template by www.865171.cn" />
<meta name="keywords" content="Admin,Template" />
<!-- Favicons --> 
<link rel="shortcut icon" type="image/png" HREF="img/favicons/favicon.png"/>
<link rel="icon" type="image/png" HREF="img/favicons/favicon.png"/>
<link rel="apple-touch-icon" HREF="img/favicons/apple.png" />
<!-- Main Stylesheet --> 
<link rel="stylesheet" href="css/style.css" type="text/css" />
<!-- Colour Schemes
Default colour scheme is blue. Uncomment prefered stylesheet to use it.
<link rel="stylesheet" href="css/brown.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/gray.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/green.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/pink.css" type="text/css" media="screen" />  
<link rel="stylesheet" href="css/red.css" type="text/css" media="screen" />
-->
<!-- Your Custom Stylesheet --> 
<!-- jQuery data tables -->
<script type="text/javascript" SRC="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="css/custom.css" type="text/css" />
<!--swfobject - needed only if you require <video> tag support for older browsers -->
<script type="text/javascript" SRC="js/swfobject.js"></script>
<!-- jQuery with plugins -->
<script type="text/javascript" SRC="js/jquery-1.4.2.min.js"></script>
<!-- Could be loaded remotely from Google CDN : <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> -->
<script type="text/javascript" SRC="js/jquery.ui.core.min.js"></script>
<script type="text/javascript" SRC="js/jquery.ui.widget.min.js"></script>
<script type="text/javascript" SRC="js/jquery.ui.tabs.min.js"></script>
<!-- jQuery tooltips -->
<script type="text/javascript" SRC="js/jquery.tipTip.min.js"></script>
<!-- Superfish navigation -->
<script type="text/javascript" SRC="js/jquery.superfish.min.js"></script>
<script type="text/javascript" SRC="js/jquery.supersubs.min.js"></script>
<!-- jQuery popup box -->
<script type="text/javascript" SRC="js/jquery.nyroModal.pack.js"></script>
<!-- jQuery form validation -->
<script type="text/javascript" SRC="js/jquery.validate_pack.js"></script>
<!-- Internet Explorer Fixes --> 
<!--[if IE]>
<link rel="stylesheet" type="text/css" media="all" href="css/ie.css"/>
<script src="js/html5.js"></script>
<![endif]-->
<!--Upgrade MSIE5.5-7 to be compatible with MSIE8: http://ie7-js.googlecode.com/svn/version/2.1(beta3)/IE8.js -->
<!--[if lt IE 8]>
<script src="js/IE8.js"></script>
<![endif]-->
<script type="text/javascript">

$(document).ready(function(){
	
	/* setup navigation, content boxes, etc... */
	Administry.setup();

	/* progress bar animations - setting initial values */
	Administry.progress("#capacity", 72, 100);
	
	/* tabs */
	$("#tabs").tabs();
	
});
</script>
</head>
<body>
	<!-- Header -->
	<header id="top">
				<div class="wrapper">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title">精准搜索系统<!--<span>Administry</span> demo--></div>
			<!-- Top navigation -->
			<div id="topnav">
				<a href="#"><img class="avatar" SRC="img/user_32.png" alt="" /></a>
				
			</div>
			<!-- End of Top navigation -->
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
				    <li><a HREF="PictureMatching.jsp">图片匹配</a></li>				
					<li class="current"><a HREF="test1.jsp">图片搜索</a></li>
					<li><a HREF="test2.jsp">文本搜索</a></li>
				</ul>
			</nav>
			<!-- End of Main navigation -->
			<!-- Aside links -->
			<aside></aside>
			<!-- End of Aside links -->
		</div>
	</header>
	<!-- End of Header -->
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper">
			<h1>图片搜索结果 &rarr; <span>右侧为语义扩展结果</span></h1>
			<!-- Quick search box -->
			<form action="" method="get"><input class="" type="text" id="q" name="search" /></form>
		</div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper">
				<!-- Left column/section -->
				<section class="column width6 first">					
                 
					
					
					<div id="tabs">
						<ul>
							<li><a class="corner-tl" href="#tabs-date">搜索结果</a></li>
						</ul>
					<div id="tabs-date">
							<div class="colgroup">
							</div>
							
						
					<%
					    int pageSize=10;
					    int pageNumber=1;
					    
					    String pages=request.getParameter("pages");
					    if(pages!=null)
					    {
					      pageNumber= Integer.valueOf(pages).intValue(); 
					    }
					    
					    String search=request.getParameter("search");
					    
					    if(search==null)
					    {
					        System.out.println("~~~~!");
					        List<High.itmode> list=(List<High.itmode>)session.getAttribute("list");
					       // System.out.println("list.size():"+list.size());
					        if(list==null)
					          {
					            %>
					            <h3>请输入搜索词汇</h3>
					            <% 
					          }
					        else
					          {
					            bean it =new bean();
					            int start=it.getPagedRs(pageSize,pageNumber);
					            int allpage=it.getTotalPage(list,pageSize);
					            System.out.println("start:"+start);//输出它的其他页
					            int ii=pageNumber-3>1?(pageNumber-3):1;
                                int jj=pageNumber+3>allpage?allpage:(pageNumber+3);
                                int kk=ii+5>allpage?allpage:(ii+5);
                                jj=kk>jj?kk:jj;
                                 %>
                                 <div class="width3 column first">
									<p>Showing <b><%=list.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								          <%if(pageNumber!=1){%><a href=test1.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test1.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 for(int i=start;i<=((start+pageSize-1)<list.size()?(start+pageSize-1):list.size());i++)
                                 {
                                 
                                        High.itmode ui=list.get(i-1);//获取集合
                                     %>
                                       <h5>Img <%=i%></h5>
							
							           <div class="colgroup leading">
								           <div class="width1 column first ta-center">
									           <img SRC="image_store//<%=ui.name%>" height="80" width="100" alt="" />
								           </div>
								           <div class="width5 column">
									           <a href="#" title="<%=ui.website%>"><b class="big"><%=ui.content%></b></a><br/>
								           	<small><b>75kB</b> |  Author: <b>UITemplates.com</b> |  Tags: <a href="#">cv</a> <a href="#">resume</a></small><br/>
								           	<a href="#">remove</a> &middot; <a href="#">show</a> &middot; <a href="#">edit</a>
								           </div>
							           </div>
							           <div class="clearfix"></div>
							           <hr />
                                     <%
                                 }
                                 %>
                                 <div class="width3 column first">
									<p>Showing <b><%=list.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
									       <%if(pageNumber!=1){%><a href=test1.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test1.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
                                 <%
					            
					            
					          }
					    
					    
					    
					    }
					    else
					    {
					        
					       
					          search=new String(request.getParameter("search").toString().getBytes("ISO8859-1"),"GB2312");
				              System.out.println("search:"+search);
				              
			                  
					          //JenaOWLModel owlModel=(JenaOWLModel)session.getAttribute("Protege");
                              MethodForInput m=null;
                              JenaOWLModel p=null;
                              List searchResult=null;
                              p=(JenaOWLModel)session.getAttribute("Protege");
                             
                              try{
                               m = new MethodForInput(p);
                               searchResult=m.Process(search,"word");
                               }catch(Exception e){
                              
                              String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
			                  System.out.println(curPath);
			                  JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI(curPath + "ontology/foodsafety.owl");
			                  session.setAttribute("Protege",owlModel); 
			                  m = new MethodForInput(owlModel);
			                  searchResult=m.Process(search,"word");
			                 
                              }
                              
                              SortedSet<String>inset=new TreeSet<String>();
                              inset.add(search);
                              session.setAttribute("inset",inset);		 
                              for(int ii=0;ii<searchResult.size();ii++)
                              {
                                  ResultBean l=(ResultBean)searchResult.get(ii);
	                              List<String>food=l.getHtmlFood();
	                              List<String>material=l.getHtmlMaterial();			
	                              for(String ff:food)
		                              {
			                              inset.add(ff);
		                              }
	                              for(String ff1:material)
		                             {
			                              inset.add(ff1);
	                                  }
                              }
                              High uu=new High();
                              

                              List<High.itmode>ga=uu.num(inset);
                              System.out.println("ga.size():"+ga.size());
                              if(ga.size()>0)
                              {
                                 
                                 session.setAttribute("list",ga);//把搜索结果存入到session中;
                                 
                                 bean it =new bean();
                                 int allpage=it.getTotalPage(ga,pageSize);
                                 int start=it.getPagedRs(pageSize,pageNumber);
                                 System.out.println("allpage:"+allpage);
                                 System.out.println("start:"+start);
                                 
                                 int ii=pageNumber-3>1?(pageNumber-3):1;
                                 int jj=pageNumber+3>allpage?allpage:(pageNumber+3);
                                 int kk=ii+5>allpage?allpage:(ii+5);
                                jj=kk>jj?kk:jj;
                                 %>
                                 <div class="width3 column first">
									<p>Showing <b><%=ga.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
									       <%if(pageNumber!=1){%><a href=test1.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %> 
										      <%if(i!=pageNumber){%><a href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test1.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 for(int i=start;i<=((start+pageSize-1)<ga.size()?(start+pageSize-1):ga.size());i++)
                                 {
                                      High.itmode ui=ga.get(i-1);//获取集合
                                     %>
                                       <h5>Img <%=i%></h5>
							
							           <div class="colgroup leading">
								           <div class="width1 column first ta-center">
									           <img SRC="image_store//<%=ui.name%>" height="80" width="100" alt="" />
								           </div>
								           <div class="width5 column">
									           <a href="#" title="<%=ui.website%>"><b class="big"><%=ui.content%></b></a><br/>
								           	<small><b>75kB</b> |  Author: <b>UITemplates.com</b> |  Tags: <a href="#">cv</a> <a href="#">resume</a></small><br/>
								           	<a href="#">remove</a> &middot; <a href="#">show</a> &middot; <a href="#">edit</a>
								           </div>
							           </div>
							           <div class="clearfix"></div>
							           <hr />
                                     <%
                                 }
                                  %>
                                 <div class="width3 column first">
									<p>Showing <b><%=ga.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								          <%if(pageNumber!=1){%><a href=test1.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %> 
										      <%if(i!=pageNumber){%><a href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test1.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test1.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
                                 <%
                                 
                              }
                            
					     
					    }
					
					
					%>
					
					
					
					
					
						
						
						
						
						
								
							
							
							
							
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
							
							<div class="clearfix"></div>
							
							<hr/>
							
						
						</div>
					</div> 

				</section>
				<!-- End of Left column/section -->
				
				<!-- Right column/section -->
				<%
				   SortedSet<String>inset=(SortedSet<String>)session.getAttribute("inset");
				   
			    %>
				<aside class="column width2">
					
					<div class="content-box">
						<header>
							<h3>扩展集合</h3>
						</header>
						<section>
							<div class="tagcloud">
							
							     <%if(inset!=null){ int []a={1,3,5};  Iterator i=inset.iterator(); for(;i.hasNext();) {   
							     
							                Random random = new Random();
							                int rand=Math.abs(random.nextInt())%3;
							                String name=i.next().toString();
							            %>
							        <a href="test1.jsp?search=<%=name%>" class="tag<%=a[rand]%>"><%=name%></a>					          
							       <%}%>
							     <%} %>
								
							</div>
						</section>
					</div>
				</aside>
				<!-- End of Right column/section -->
				
		</div>
		<!-- End of Wrapper -->
	</div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper">
			<nav>
				<a href="#">Dashboard</a> &middot;
				<a href="#">Content</a> &middot;
				<a href="#">Reports</a> &middot;
				<a href="#">Users</a> &middot;
				<a href="#">Media</a> &middot;
				<a href="#">Events</a> &middot;
				<a href="#">Newsletter</a> &middot;
				<a href="#">Settings</a>
			</nav>
			<p>Copyright &copy; 2010</p>
		</div>
	</footer>
	<!-- End of Page footer -->
	
	<!-- Animated footer -->
	<footer id="animated">
		<ul>
			<li><a href="#">Dashboard</a></li>
			<li><a href="#">Content</a></li>
			<li><a href="#">Reports</a></li>
			<li><a href="#">Users</a></li>
			<li><a href="#">Media</a></li>
			<li><a href="#">Events</a></li>
			<li><a href="#">Newsletter</a></li>
			<li><a href="#">Settings</a></li>
		</ul>
	</footer>
	<!-- End of Animated footer -->
	
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ scroll to top</a>

<!-- User interface javascript load -->
<script type="text/javascript" SRC="js/administry.js"></script>
</body>
</html>