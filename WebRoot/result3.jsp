<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
Thread.sleep(1500);
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>语义挖掘结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <center>
    <table width="600" height="280" border="1" >
    <tr>
      <td colspan="2" height="20" align="center">
      <h2>语义挖掘结果</h2>
      </td>
    </tr>
    <tr>
      <td width="230"><img src="resources/test.jpg" width="230"></td>
      <td>                     
       <b>&nbsp;图像语义标注：</b><br><br>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;猪肉 &nbsp;瘦肉精 &nbsp;健美猪 &nbsp;致癌 &nbsp;中毒 <br><br>
       <b>&nbsp;图像语义分类：</b><br><br>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;瘦肉精事件
      </td>
    </tr>
    </table>    
    </center>
  </body>
</html>
