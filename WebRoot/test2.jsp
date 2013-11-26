<%@ page language="java" import="java.util.*,baidutupian.*" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,edu.stanford.smi.protegex.owl.ProtegeOWL,edu.stanford.smi.protegex.owl.jena.JenaOWLModel,com.test.bean.*,java.util.*,resource.search.ResultBean,resource.search.SearchManager,resource.MethodForInput,edu.stanford.smi.protegex.owl.jena.JenaOWLModel"%>
<%@ page import="newbean.UserInfo" %>
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

<style type="text/css">
body{background-color:#3A4954;}
#div1 {position:absolute; width:200px; height:450px; margin: 20px auto 0; }
#div1 a {position:absolute; top:0px; left:0px; font-family: Microsoft YaHei; color:#fff; 
font-weight:bold; text-decoration:none; padding: 3px 6px; }
#div1 a:hover {border: 1px solid #eee; background: #000; }
#div1 .blue {color:blue;}
#div1 .red {color:red;}
#div1 .yellow {color:yellow;}

p { font: 16px Microsoft YaHei; text-align: center; color: #ba0c0c; }
p a { font-size: 14px; color: #ba0c0c; }
p a:hover { color: red; }
</style>


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



var radius = 150;
var dtr = Math.PI/180;
var d=200;

var mcList = [];
var active = false;
var lasta = 1;
var lastb = 1;
var distr = true;
var tspeed=10;
var size=200;

var mouseX=0;
var mouseY=0;

var howElliptical=1;

var aA=null;
var oDiv=null;

window.onload=function ()
{
    var i=0;
    var oTag=null;
    
    oDiv=document.getElementById('div1');
    
    aA=oDiv.getElementsByTagName('a');
    
    for(i=0;i<aA.length;i++)
    {
        oTag={};
        
        oTag.offsetWidth=aA[i].offsetWidth;
        oTag.offsetHeight=aA[i].offsetHeight;
        
        mcList.push(oTag);
    }
    
    sineCosine( 0,0,0 );
    
    positionAll();
    
    oDiv.onmouseover=function ()
    {
        active=true;
    };
    
    oDiv.onmouseout=function ()
    {
        active=false;
    };
    
    oDiv.onmousemove=function (ev)
    {
        var oEvent=window.event || ev;
        
        mouseX=oEvent.clientX-(oDiv.offsetLeft+oDiv.offsetWidth/2);
        mouseY=oEvent.clientY-(oDiv.offsetTop+oDiv.offsetHeight/2);
        
        mouseX/=5;
        mouseY/=5;
    };
    
    setInterval(update, 30);
};

function update()
{
    var a;
    var b;
    
    if(active)
    {
        a = (-Math.min( Math.max( -mouseY, -size ), size ) / radius ) * tspeed;
        b = (Math.min( Math.max( -mouseX, -size ), size ) / radius ) * tspeed;
    }
    else
    {
        a = lasta * 0.98;
        b = lastb * 0.98;
    }
    
    lasta=a;
    lastb=b;
    
    if(Math.abs(a)<=0.01 && Math.abs(b)<=0.01)
    {
        return;
    }
    
    var c=0;
    sineCosine(a,b,c);
    for(var j=0;j<mcList.length;j++)
    {
        var rx1=mcList[j].cx;
        var ry1=mcList[j].cy*ca+mcList[j].cz*(-sa);
        var rz1=mcList[j].cy*sa+mcList[j].cz*ca;
        
        var rx2=rx1*cb+rz1*sb;
        var ry2=ry1;
        var rz2=rx1*(-sb)+rz1*cb;
        
        var rx3=rx2*cc+ry2*(-sc);
        var ry3=rx2*sc+ry2*cc;
        var rz3=rz2;
        
        mcList[j].cx=rx3;
        mcList[j].cy=ry3;
        mcList[j].cz=rz3;
        
        per=d/(d+rz3);
        
        mcList[j].x=(howElliptical*rx3*per)-(howElliptical*2);
        mcList[j].y=ry3*per;
        mcList[j].scale=per;
        mcList[j].alpha=per;
        
        mcList[j].alpha=(mcList[j].alpha-0.6)*(10/6);
    }
    
    doPosition();
    depthSort();
}

function depthSort()
{
    var i=0;
    var aTmp=[];
    
    for(i=0;i<aA.length;i++)
    {
        aTmp.push(aA[i]);
    }
    
    aTmp.sort
    (
        function (vItem1, vItem2)
        {
            if(vItem1.cz>vItem2.cz)
            {
                return -1;
            }
            else if(vItem1.cz<vItem2.cz)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    );
    
    for(i=0;i<aTmp.length;i++)
    {
        aTmp[i].style.zIndex=i;
    }
}

function positionAll()
{
    var phi=0;
    var theta=0;
    var max=mcList.length;
    var i=0;
    
    var aTmp=[];
    var oFragment=document.createDocumentFragment();
    
    //随机排序
    for(i=0;i<aA.length;i++)
    {
        aTmp.push(aA[i]);
    }
    
    aTmp.sort
    (
        function ()
        {
            return Math.random()<0.5?1:-1;
        }
    );
    
    for(i=0;i<aTmp.length;i++)
    {
        oFragment.appendChild(aTmp[i]);
    }
    
    oDiv.appendChild(oFragment);
    
    for( var i=1; i<max+1; i++){
        if( distr )
        {
            phi = Math.acos(-1+(2*i-1)/max);
            theta = Math.sqrt(max*Math.PI)*phi;
        }
        else
        {
            phi = Math.random()*(Math.PI);
            theta = Math.random()*(2*Math.PI);
        }
        //坐标变换
        mcList[i-1].cx = radius * Math.cos(theta)*Math.sin(phi);
        mcList[i-1].cy = radius * Math.sin(theta)*Math.sin(phi);
        mcList[i-1].cz = radius * Math.cos(phi);
        
        aA[i-1].style.left=mcList[i-1].cx+oDiv.offsetWidth/2-mcList[i-1].offsetWidth/2+'px';
        aA[i-1].style.top=mcList[i-1].cy+oDiv.offsetHeight/2-mcList[i-1].offsetHeight/2+'px';
    }
}

function doPosition()
{
    var l=oDiv.offsetWidth/2;
    var t=oDiv.offsetHeight/2;
    for(var i=0;i<mcList.length;i++)
    {
        aA[i].style.left=mcList[i].cx+l-mcList[i].offsetWidth/2+'px';
        aA[i].style.top=mcList[i].cy+t-mcList[i].offsetHeight/2+'px';
        
        aA[i].style.fontSize=Math.ceil(12*mcList[i].scale/2)+8+'px';
        
        aA[i].style.filter="alpha(opacity="+100*mcList[i].alpha+")";
        aA[i].style.opacity=mcList[i].alpha;
    }
}

function sineCosine( a, b, c)
{
    sa = Math.sin(a * dtr);
    ca = Math.cos(a * dtr);
    sb = Math.sin(b * dtr);
    cb = Math.cos(b * dtr);
    sc = Math.sin(c * dtr);
    cc = Math.cos(c * dtr);
}
 

</script>
</head>
<body> 
	 
	ne&quot;&quot;<!-- Header -->
	<header id="top">
				 <form action = "login.jsp">
				 	<input type = "submit" value = ".">
				 </form>
		<div class="wrapper">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title">精准搜索系统<!--<span>Administry</span> demo--></div>
			<!-- Top navigation -->
			<div id="topnav">
					
			<%
				System.out.println("11111");
				//String userName = session.getAttribute("username").toString();
				newbean.UserInfo user = (newbean.UserInfo)session.getAttribute("userbean");
				System.out.println("request.getAttri--------"+user.getUsername());
				System.out.println("22222");
				//user.setName((request.getAttribute("username")).toString());
				//user.setName((session.getAttribute("username")).toString());
				//String userName = user.getUsername();
				String userName = "Mike";
				System.out.println("333");
				//session.setAttribute("username",userName);
				int type = 2;
				if(userName == "Anna")
				{
				type = 4;
			 %>
				欢迎您，Anna！<a href="#"><img class="avatar" SRC="img/girl.jpg" alt="" /></a>
				<%
				}
				else{
				type = 2;
				 %>
				 欢迎您，Mike！<a href="#"><img class="avatar" SRC="img/boy.jpg" alt="" /></a>
				 <%
				}
				 %>
				
			</div>
			<!-- End of Top navigation -->
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
				    <li><a HREF="PictureMatching.jsp">图片匹配</a></li>				
					<li><a HREF="test1.jsp">图片搜索</a></li>
					<li class="current"><a HREF="test2.jsp">文本搜索</a></li>
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
			<h1>文本搜索结果 &rarr; <span>右侧为语义扩展结果</span></h1>
			<!-- Quick search box -->
			<form action="" method="get"><input class="" type="text" id="q" name="search" />
			<%
				
			 %>
			</form>
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
					    	System.out.println("search==null------"+search);
					        System.out.println("~~~~!");
					        List<High1.itmode> list=(List<High1.itmode>)session.getAttribute("list1");
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
									<p>共 <b><%=allpage%></b>&nbsp页     <b><%=list.size()%></b> 个结果</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
									       <%if(pageNumber!=1){%><a href=test2.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 
				                              <%if(i!=pageNumber){%><a href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test2.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 for(int i=start;i<=((start+pageSize-1)<list.size()?(start+pageSize-1):list.size());i++)
                                 {
                                 
                                     High1.itmode ui=list.get(i-1);//获取集合
                                     String website=ui.website;
                                     String date=ui.date;

						             String title=ui.title;
						             String contents=ui.contents;
						             System.out.println("title:"+title);
						            
						             {
						             
                                     %>
                                   <!--    <h5>Result <%=i%></h5>  -->
							
							           <div class="colgroup leading">
								           
								           <div class="width5 column">
									           <a href="<%=website%>"><b class="big"><%=title%></b></a><br/>
								           	<small><b><%=contents%></b></small><br/>
								           	<b><%=website%> &middot; <%=date%></b>
								           </div>
							           </div>
							           <div class="clearfix"></div>
							           <hr />
                                     <%
                                     }
                                 }
                                 %>
                                 <div class="width3 column first">
									<p>共 <b><%=allpage%></b>&nbsp页     <b><%=list.size()%></b> 个结果</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								           <%if(pageNumber!=1){%><a href=test2.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 
				                              <%if(i!=pageNumber){%><a href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test2.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
                                 <%
					            
					            
					          }
					    
					    
					    
					    }
					    else
					    {
					      
					          search=new String(request.getParameter("search").toString().getBytes("ISO8859-1"),"GB2312");
				              System.out.println("search != null -----:"+search);
				              
                             
                              MethodForInput m=null;
                              JenaOWLModel p=null;
                              List searchResult=null;
                              
                              p=(JenaOWLModel)session.getAttribute("Protege");
                              try
                              {
                              m = new MethodForInput(p);
                              searchResult=m.Process(search,"word");
                              }catch(Exception e)
                              {
                              String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
			                  System.out.println(curPath);
			                  JenaOWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI(curPath + "ontology/foodsafety.owl");
			                  session.setAttribute("Protege",owlModel); 
			                  m = new MethodForInput(owlModel);
			                  searchResult=m.Process(search,"word");
                              }
                              
                             // int type = 4;
					//int type = Integer.parseInt(session.getAttribute("userType").toString());
					//System.out.println("type--------"+type);
					String[] type1 = {"自然灾害","地震","强震","大火","火灾","洪水","洪灾","泥石流","大雪","暴风雪","火山","海啸","龙卷风"};
					String[] type2 = {"公共卫生","食品安全","病毒","病菌","上涨","中毒","有毒","添加剂","造假","超标","感染","变质","致癌","三聚氰胺","麦乐鸡","泥胶","添加剂","膨大西瓜","膨大剂"};
					String[] type3 = {"事故灾害","医保","坠毁","出轨","相撞","坍塌","辐射","泄漏","踩踏","核燃料","事故"};
					String[] type4 = {"社会安全","枪击","爆炸","炸弹","政局","动荡","恶化","炮击","罢工","交火","军事","政变","袭击","骚乱","恐怖","反恐","坠毁","绑架","转基因"};
                              
                              Set<String>inset=new LinkedHashSet<String>();
                              inset.add(search);
                              session.setAttribute("inset1",inset);	
                              if(search.equals("突发事件") == false){	 
	                              for(int ii=0;ii<searchResult.size();ii++)
	                              {
	                                  ResultBean l=(ResultBean)searchResult.get(ii);
		                              List<String>food=l.getHtmlFood();
		                              List<String>material=l.getHtmlMaterial();			
		                              for(String ff:food)
			                              {
			                                 // if(!((ff.charAt(0)<'z'&&ff.charAt(0)>'a')||(ff.charAt(0)<'Z'&&ff.charAt(0)>'A')))
			                                  {
			                                  if(ff.length()<=4)
				                              	inset.add(ff);
				                              }
			                              }
		                              for(String ff1:material)
			                             {
				                             // if(!((ff1.charAt(0)<'z'&&ff1.charAt(0)>'a')||(ff1.charAt(0)<'Z'&&ff1.charAt(0)>'A')))
			                                  {
				                              if(ff1.length()<=4)
				                              	inset.add(ff1);
				                              }
		                                  }
	                              }
                              }
                              
                              switch(type){
								case 1:for(String w:type1)
								if(w.length()<=4)
				                	inset.add(w);
								break;
								case 2:for(String w:type2)
								if(w.length()<=4)
				                	inset.add(w);
								break;
								case 3:for(String w:type3)
								if(w.length()<=4)
				                	inset.add(w);
								break;
								case 4:for(String w:type4)
								if(w.length()<=4)
				                	inset.add(w);
								break;
								default:break;
								}
                              
                              High1 uu=new High1();
                             
                              List<High1.itmode>ga=uu.num(inset,type);
                              System.out.println("ga.size():"+ga.size());
                              
                              
                              
                               if(ga.size()>0)
                              {
                                 
                                 
                                 
                                 session.setAttribute("list1",ga);//把搜索结果存入到session中;
                                 
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
									<p>共 <b><%=allpage%></b>&nbsp页     <b><%=ga.size()%></b> 个结果</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
									       <%if(pageNumber!=1){%><a href=test2.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 
										      <%if(i!=pageNumber){%><a href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test2.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 
                                 
                                 for(int i=start;i<=((start+pageSize-1)<ga.size()?(start+pageSize-1):ga.size());i++)
                                 {
                                      High1.itmode ui=ga.get(i-1);//获取集合
                                      String website=ui.website;
                                      String date=ui.date;

						              String title=ui.title;
						              String contents=ui.contents;
                                    
                                     {
                                     %>
                                     
                                  <!--      <h5>Img <%=i%></h5> -->
							
							           	           <div class="colgroup leading">
								           
								           <div class="width5 column">
									           <a href="<%=website%>""><b class="big"><%=title%></b></a><br/>
								           	<small><b><%=contents%></b></small><br/>
								           <b><%=website%> &middot; <%=date%></b>
								           </div>
							           </div>
							           <div class="clearfix"></div>
							           <hr />
                                     <%
                                     }
                                     
                                 }
                                  %>
                                 <div class="width3 column first">
									<p>共 <b><%=allpage%></b>&nbsp页     <b><%=ga.size()%></b> 个结果</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								           <%if(pageNumber!=1){%><a href=test2.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 
										      <%if(i!=pageNumber){%><a href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=test2.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=test2.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
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
				   Set<String>inset=(Set<String>)session.getAttribute("inset1");
				   
			    %>
				<aside class="column width2">
					
					<div class="content-box">
						<header>
							<h3>扩展关键词</h3>
						</header>
						<section>
							<div class="tagcloud">
							
							     
								
								
<div id="div1"> 
    
    <%
    if(inset!=null){ int []a={1,3,5}; String color[] = {"red","blue","yellow"}; 
    Iterator i=inset.iterator(); for(;i.hasNext();) {   
							     
							                Random random = new Random();
							                int rand=Math.abs(random.nextInt())%3;
							                String name=i.next().toString();
							            %>
							        <!-- <a href="test2.jsp?search=<%=name%>" class="tag<%=a[rand]%>"><%=name%></a>	 -->
							        <a href="test2.jsp?search=<%=name%>" class="<%=color[rand]%>"><%=name%></a>			          
							       <%}%>
							     <%} %>
</div> 
								
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