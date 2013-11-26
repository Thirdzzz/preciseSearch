<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page language="java" import="java.util.*,
java.awt.image.BufferedImage,
java.io.File,
java.io.FileInputStream,
java.io.IOException,
java.util.*,
spider.*,
com.test.bean.*,
com.jzy.test.*,
javax.imageio.ImageIO,
com.thtf.ezone.ajaxupload.servlet.*,
net.semanticmetadata.lire.DocumentBuilder,
net.semanticmetadata.lire.DocumentBuilderFactory,
net.semanticmetadata.lire.ImageDuplicates,
net.semanticmetadata.lire.ImageSearchHits,
net.semanticmetadata.lire.ImageSearcher,
net.semanticmetadata.lire.ImageSearcherFactory,

org.apache.lucene.analysis.*,
org.apache.lucene.analysis.standard.StandardAnalyzer,
org.apache.lucene.document.Document,
org.apache.lucene.document.Field,
org.apache.lucene.index.IndexReader,
 org.apache.lucene.index.IndexWriter,
org.apache.lucene.index.IndexWriterConfig,
 org.apache.lucene.store.FSDirectory,
 org.apache.lucene.util.Version,
 org.junit.Test,

baidutupian.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Admin</title>
<meta name="description" content="Administry - Admin Template by www.865171.cn" />
<meta name="keywords" content="Admin,Template" />
<!-- We need to emulate IE7 only when we are to use excanvas -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<![endif]-->
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
<!-- jQuery form validation -->
<script type="text/javascript" SRC="js/jquery.validate_pack.js"></script>
<!-- jQuery popup box -->
<script type="text/javascript" SRC="js/jquery.nyroModal.pack.js"></script>
<!-- jQuery graph plugins -->
<!--[if IE]><script type="text/javascript" src="js/flot/excanvas.min.js"></script><![endif]-->
<script type="text/javascript" SRC="js/flot/jquery.flot.min.js"></script>
<!-- Internet Explorer Fixes --> 
<!--[if IE]>
<link rel="stylesheet" type="text/css" media="all" href="css/ie.css"/>
<script src="js/html5.js"></script>
<![endif]-->
<!--Upgrade MSIE5.5-7 to be compatible with MSIE8: http://ie7-js.googlecode.com/svn/version/2.1(beta3)/IE8.js -->
<!--[if lt IE 8]>
<script src="js/IE8.js"></script>
<script>
<![endif]-->
<style type="text/css">


.wrap {
    width:300px;
    height:500px;
    background:#FFF;
    margin:20px auto;
    padding: 10px;
}

/*==================================================
 * Effect 7
 * ===============================================*/
.effect7
{
      position:relative;       
    -webkit-box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
       -moz-box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
            box-shadow:0 1px 4px rgba(0, 0, 0, 0.3), 0 0 40px rgba(0, 0, 0, 0.1) inset;
}

.effect7:before, .effect7:after
{
    content:"";
    position:absolute; 
    z-index:-1;
    -webkit-box-shadow:0 0 20px rgba(0,0,0,0.8);
    -moz-box-shadow:0 0 20px rgba(0,0,0,0.8);
    box-shadow:0 0 20px rgba(0,0,0,0.8);
    top:0;
    bottom:0;
    left:10px;
    right:10px;
    -moz-border-radius:100px / 10px;
    border-radius:100px / 10px;
} 

.effect7:after
{
    right:10px; 
    left:auto;
    -webkit-transform:skew(8deg) rotate(3deg); 
       -moz-transform:skew(8deg) rotate(3deg);     
        -ms-transform:skew(8deg) rotate(3deg);     
         -o-transform:skew(8deg) rotate(3deg); 
            transform:skew(8deg) rotate(3deg);
}

</style>
<script>
function setImagePreview() {
        var docObj=document.getElementById("file1");
 
        var imgObjPreview=document.getElementById("preview");
                if(docObj.files &&    docObj.files[0]){
                        //火狐下，直接设img属性
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '220px';
                        imgObjPreview.style.height = '150px';                    
                        //imgObjPreview.src = docObj.files[0].getAsDataURL();

      //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
      imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

                }else{
                        //IE下，使用滤镜
                        docObj.select();
                        var imgSrc = document.selection.createRange().text;
                        var localImagId = document.getElementById("localImag");
                        //必须设置初始大小
                        localImagId.style.width = "220px";
                        localImagId.style.height = "150px";
                        //图片异常的捕捉，防止用户修改后缀来伪造图片
try{
                                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                        }catch(e){
                                alert("您上传的图片格式不正确，请重新选择!");
                                return false;
                        }
                        imgObjPreview.style.display = 'none';
                        document.selection.empty();
                }
                return true;
        }
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
 function ScreenArea(){
     this.clientWidth = 0;//可见区域宽度
     this.clientHeight = 0;//可见区域高度
  
  //可见区域宽度
  if(document.documentElement && document.documentElement.clientWidth){
   this.clientWidth = document.documentElement.clientWidth;
  }else if(document.body && document.body.clientWidth){
   this.clientWidth = document.body.clientWidth;
  }else if(window.innerWidth){
   this.clientWidth=window.innerWidth-18;
  }

  if(document.documentElement && document.documentElement.clientHeight){
   this.clientHeight=document.documentElement.clientHeight;
  }else if(document.body && document.body.clientHeight){
   this.clientHeight=document.body.clientHeight;
  }else if(window.innerHeight){
   this.clientHeight=window.innerHeight-18;   
  }

  this.bodyWidth = document.body.clientWidth;//网页宽度
     this.bodyHeight = document.body.clientHeight;//网页高度

     this.scrollLeft = (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft); 
     this.scrollTop = (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop); 
  this.scrollWidth = (document.documentElement.scrollWidth ? document.documentElement.scrollWidth : document.body.scrollWidth); 
  this.scrollHeight = (document.documentElement.scrollHeight ? document.documentElement.scrollHeight : document.body.scrollHeight);
 }

 function fit(tw,th,sw,sh){
  var temw = tw;
  var temh = th;
  var flag = 1;
  
  if(sw<=tw && sh<=th){
   temw = sw;
   temh = sh;
   flag = 0;
  }else if(sw>tw && sh<=th){   
   temw = tw;
   temh = sh*(tw/sw);
  }else if(sw<=tw && sh>th){
   temw = tw*(th/sh);
   temh = th;
  }else if(sw>tw && sh>th){   
   var dw = tw/sw;
   var dh = th/sh;
   if((dw-dh)>=0){
    temw = tw;
    temh = sh*dw;
   }else{
    temw = sw*dh;
    temh = th;
   }
  } 

  return {'width':temw,'height':temh,'flag':flag};
 }

 function showImgView(imgSrc,imgWidth,imgHeight){
  var ca = new ScreenArea();
  var viewMask = document.createElement("DIV");
  viewMask.style.cssText="position:absolute;filter:alpha(opacity=50);opacity:0.5;visibility:visible;background:#000;";
  viewMask.style.zIndex=1;
  viewMask.style.top = 0;
  viewMask.style.left = 0;
  viewMask.style.width = (ca.bodyWidth)+'px';
  viewMask.style.height = (ca.bodyHeight)+'px';
  document.body.appendChild(viewMask);


  var imgDiv = document.createElement("DIV");
  imgDiv.style.position='absolute';
  imgDiv.style.border='3px solid #333333';
  imgDiv.style.zIndex=999;
  //imgView.content=imgDiv;
  imgDiv.style.left = Math.max((ca.scrollLeft+((ca.clientWidth-imgWidth)/2)),0) + 'px'; 
  //alert("ch="+ca.clientHeight);
  imgDiv.style.top = Math.max((ca.scrollTop+((ca.clientHeight-imgHeight)/2)),0) + 'px';
  //alert(ca.clientHeight);

  var imgObj=document.createElement("img");   
  imgObj.title="单击关闭";
  imgObj.onclick=function(){
   document.body.removeChild(imgDiv);
   document.body.removeChild(viewMask);
   //imgDiv.style.display='none';
   //viewMask.style.display='none';
  }
  imgObj.src=imgSrc;
  imgDiv.appendChild(imgObj);

  //imgView.imgObj=imgObj;
  document.body.appendChild(imgDiv);
 }

 function adapt(imgObj,tw,th,isView){
  var cw = parseInt(imgObj.width);
  var ch = parseInt(imgObj.height);
  var wh = fit(tw,th,cw,ch);
  //alert('w = '+wh.width);
  //alert('h = '+wh.height);
  imgObj.width = wh.width;
  imgObj.height = wh.height;
  //alert('flag = '+wh.flag);
  if(wh.flag !=0 && isView){
   imgObj.title="单击查看大图";
   imgObj.style.cursor = 'pointer';
   imgObj.onclick = function(){
    //alert('click');
    showImgView(imgObj.src,cw,ch);
   }
  }
 }
//
--></SCRIPT>
<script type="text/javascript">
$(document).ready(function(){
	
	/* setup navigation, content boxes, etc... */
	Administry.setup();
	
	/* progress bar animations - setting initial values */
	Administry.progress("#progress1", 1, 5);
	Administry.progress("#progress2", 2, 5);
	Administry.progress("#progress3", 2, 5);

	/* flot graphs */
	var sales = [{
		label: 'Total Paid',
		data: [[1, 0],[2,0],[3,0],[4,0],[5,0],[6,0],[7,900],[8,0],[9,0],[10,0],[11,0],[12,0]]
	},{
		label: 'Total Due',
		data: [[1, 0],[2,0],[3,0],[4,0],[5,0],[6,422.10],[7,0],[8,0],[9,0],[10,0],[11,0],[12,0]]
	}
	];

	var plot = $.plot($("#placeholder"), sales, {
		bars: { show: true, lineWidth: 1 },
		legend: { position: "nw" },
		xaxis: { ticks: [[1, "Jan"], [2, "Feb"], [3, "Mar"], [4, "Apr"], [5, "May"], [6, "Jun"], [7, "Jul"], [8, "Aug"], [9, "Sep"], [10, "Oct"], [11, "Nov"], [12, "Dec"]] },
		yaxis: { min: 0, max: 1000 },
		grid: { color: "#666" },
		colors: ["#0a0", "#f00"]			
    });


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
					<li class="current"><a HREF="PictureMatching.jsp">图片匹配</a></li>				
					<li><a HREF="test1.jsp">图片搜索</a></li>
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
			<h1>图片匹配：用户上传图片后，系统将返回匹配结果</h1>
			<!-- Quick search box -->
			
		</div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper">
				<!-- Left column/section -->
				<section class="column width6 first">
				<%
						int pageSize=4;
					    int pageNumber=1;
					    
					    String pages=request.getParameter("pages");
					    if(pages!=null)
					    {
					      pageNumber= Integer.valueOf(pages).intValue(); 
					    }
					    
					    String imgsearch=request.getParameter("imgsearch");
					    if(imgsearch==null)
					    {
					      List<imgbean> list=(List<imgbean>)session.getAttribute("imglist");
					      if(list==null)
					          {
					            %>
					              <div class="width3 column first">
									<p><b>请上传图片</b></p>
								 </div>
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
								          <%if(pageNumber!=1){%><a href=PictureMatching.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=PictureMatching.jsp??pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=PictureMatching.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 for(int i=start;i<=((start+pageSize-1)<list.size()?(start+pageSize-1):list.size());i++)
                                 {
                                 
                                        imgbean ui=list.get(i-1);//获取集合
                                     %>
                                      		<div class="colgroup leading">
									<div class="column width3 first">
										  <div class="content-box corners content-box-closed">
						  			        <header>
						  			   	     <h3><%=ui.title%></h3>
						  			        </header>
						  			        <section>
							    			   <dl>
			
											     <dt><%=ui.allcontents%></dt>
											     <dd><a href=<%=ui.website%>>Read more</a></dd>
		                     			     </dl>
						      			    </section>
					   			   </div>
								   <div class="wrap effect7">
										<center>
   											<img src="image_store\<%=ui.name%>" onLoad="adapt(this,200,160,1)" />
       
								   <div>
									<h4>尺寸:<%=ui.height%>x<%=ui.width%></h4>
									
									<h4>相似度:<%=ui.score%></h4>
									<small><%=ui.contents%></small>
								   </div>

								  </div>
                      
						         </div>
						          <%if((++i)<=list.size()) { imgbean ui1=list.get(i-1);%>
						         
						         <div class="column width3">
									<div class="content-box corners content-box-closed">
										<header>
											<h3><%=ui1.title%></h3>
										</header>
										<section>
											<dl>
												<dt><%=ui1.allcontents %></dt>
												<dd><a href=<%=ui1.website%>>Read more</a></dd>
												<dt>
 				    						</dl>
										</section>
									</div>
								<div class="wrap effect7">
								
								<center>

										<img src="image_store\<%=ui1.name%>" onLoad="adapt(this,200,160,1)" />
								<div>
								<h4>尺寸:<%=ui1.height%>x<%=ui.width%></h4>
									
									<h4>相似度:<%=ui1.score%></h4>
									<small><%=ui1.contents%></small>
								</div>
								</div>
                    

								</div>
								<hr />
								<%} %>
								
                                     <%
                                     
                                 }
                                 %>
                                 <div class="width3 column first">
									<p>Showing <b><%=list.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
									       <%if(pageNumber!=1){%><a href=PictureMatching.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=PictureMatching.jsp??pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=PictureMatching.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
                                 <%
					          
					          
					          
					          
					          }
					    
					      
					    }
					    else
					    {
					      
					      imgsearch=new String(request.getParameter("imgsearch").toString().getBytes("ISO8859-1"),"GB2312");
					      
	                      session.setAttribute("imgname","image_store/"+imgsearch);
	                      
	                      TestImageSearch its=new TestImageSearch();
		                 
		                  List <imgbean>imagescool=its.search(imgsearch);;
		                 
		                  session.setAttribute("imglist",imagescool);//把搜索结果存入到session中;
		                  
		                  bean it =new bean();
                          int allpage=it.getTotalPage(imagescool,pageSize);
                          int start=it.getPagedRs(pageSize,pageNumber);
                          System.out.println("allpage:"+allpage);
                          System.out.println("start:"+start);
                                 
                          int ii=pageNumber-3>1?(pageNumber-3):1;
                          int jj=pageNumber+3>allpage?allpage:(pageNumber+3);
                          int kk=ii+5>allpage?allpage:(ii+5);
                          jj=kk>jj?kk:jj;
                           %>
                                 <div class="width3 column first">
									<p>Showing <b><%=imagescool.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								          <%if(pageNumber!=1){%><a href=PictureMatching.jsp?pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=PictureMatching.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
                                 for(int i=start;i<=((start+pageSize-1)<imagescool.size()?(start+pageSize-1):imagescool.size());i++)
                                 {
                                      imgbean ui=imagescool.get(i-1);//获取集合
                                     %>
                                      		<div class="colgroup leading">
						<div class="column width3 first">
							  <div class="content-box corners content-box-closed">
						<header>
							<h3><%=ui.title%></h3>
						</header>
						<section>
							<dl>
								<dt><%=ui.allcontents %></dt>
								<dd><a href=<%=ui.website%>>Read more</a></dd>
								
								
								
							</dl>
						</section>
					</div>
					         
							<div class="wrap effect7">
							<center>

<img src="image_store\<%=ui.name%>" onLoad="adapt(this,200,160,1)" />
       
<div>
<h4>尺寸:<%=ui.height%>x<%=ui.width%></h4>
									
									<h4>相似度:<%=ui.score%></h4>
									<small><%=ui.contents%></small>
</div>

</div>
                      
						</div>
						<%if((++i)<=imagescool.size()) { imgbean ui1=imagescool.get(i-1);%>
						<div class="column width3">
							<div class="content-box corners content-box-closed">
						<header>
							<h3><%=ui1.title%></h3>
						</header>
						<section>
							<dl>
								<dt><%=ui1.allcontents%></dt>
								<dd><a href=<%=ui1.website%>>Read more</a></dd>
								<dt>
								
								
							</dl>
						</section>
					</div>
								<div class="wrap effect7">
								
								<center>

<img src="image_store\<%=ui1.name%>" onLoad="adapt(this,200,160,1)" />
<div>
<h4>尺寸:<%=ui1.height%>x<%=ui1.width%></h4>
									
									<h4>相似度:<%=ui1.score%></h4>
									<small><%=ui1.contents%></small>
</div>
</div>
                    

						</div>
						<%} %>
                                     <%
                                 }
                                  %>
                                
                                 <div class="width3 column first">
									<p>Showing <b><%=imagescool.size()%></b> results</p>
								 </div>
								 <div class="width3 column">
									<p class="pagination ta-right">
								          <%if(pageNumber!=1){%><a href=PictureMatching.jsp??pages=<%=pageNumber-1%>>上一页</a><%}%>	
								           <%
								           for (int i = ii; i <= jj; i++) 
								           { 
								           %>	 

										      <%if(i!=pageNumber){%><a href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
				                              <%if(i==pageNumber){%><a class="pagination-active" href=PictureMatching.jsp?pages=<%=i%>><%=i%></a><%}%>
										   <%
										   }
									       %>
									       <%if(pageNumber!=allpage){%><a href=PictureMatching.jsp?pages=<%=pageNumber+1%>>下一页</a><%}%>
									</p>
								 </div>
								 <div class="clearfix"></div>
							      <hr />
                                 <%
		                  
		                  
		                  
					    }
					    
				  
				
				
			    %>
				
				
				
				
				
				
				
				
				
				
				
			
					
				
					
				
				</section>
				<!-- End of Left column/section -->
				
				<!-- Right column/section -->
				<aside class="column width2">
					<div id="rightmenu">
						<form name="form1"  action="PictureMatching.jsp"  method="post" >
	
				<input class="default" type="file" id="file1" name="imgsearch" onchange="javascript:setImagePreview();" style="width:220px"/>
	           
		
			    <div id="localImag"><img id="preview" width=-1 height=-1 style="diplay:none" /></div>
		        <p class="leading"><a href="javascript:form1.submit();" class="btn btn-special btn-green">
<img SRC="img/add.png" class="icon" alt=""/>上传文件
  </a></p>
				

			   

		</form>
		         <%
		            String imgname=(String)session.getAttribute("imgname");
		            if(imgname!=null)
		            System.out.println(imgname);
		            {
		            %>
		              <h3>原图：</h3>
		              <center>
		              <img src=<%=imgname%> onLoad="adapt(this,200,160,1)">
		              </center>
		            <% 
		            }
		            
		          %>
		            
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

<!-- Admin template javascript load -->
<script type="text/javascript" SRC="js/administry.js"></script>
</body>
</html>
