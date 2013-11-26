<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>多模态图像语义挖掘</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script>
  function demo(){
    window.setTimeout(function(){
      gif.style.display="";
    },5000);
    window.setTimeout(function(){
      gif.style.display="none";
      var img=document.getElementById("img");
      img.style.display="";
    },9000);    
  }
  
  </script>
  </head>
  
  <body>
  <center>
  <form action="result3.jsp">
    <br>上传图片后，点击"分析"进行语义挖掘<br>
    <input type="file" onclick="demo()">
    <input type="submit" value="分析"><br><br>
    <div id="gif" style="display:none;">
              图片上传中<br>
      <img src="resources/wei.gif">
      </div>
    <div id="img" style="display:none;">
     <br>图片预览<br>
       <img src="resources/test.jpg" width="280">
     </div>
  </form>
  </center>  
  </body>
</html>
