<%@ page contentType="text/html;charset=gbk" language="java"%>
<html>
	<head>

	</head>
	<body>
	<script>
function setImagePreview() {
        var docObj=document.getElementById("file1");
 
        var imgObjPreview=document.getElementById("preview");
                if(docObj.files &&    docObj.files[0]){
                        //火狐下，直接设img属性
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '300px';
                        imgObjPreview.style.height = '220px';                    
                        //imgObjPreview.src = docObj.files[0].getAsDataURL();

      //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
      imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

                }else{
                        //IE下，使用滤镜
                        docObj.select();
                        var imgSrc = document.selection.createRange().text;
                        var localImagId = document.getElementById("localImag");
                        //必须设置初始大小
                        localImagId.style.width = "300px";
                        localImagId.style.height = "220px";
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
         <%
            String name=request.getParameter("file1");
            if(name!=null)
            {
            System.out.println(name);
            
            }
          %>
        <center>
		<form action="index.jsp"  method="post" >
			<p>
			<h2>
				请上传图片
			</h2>

			<input type="text" value="D:\image\" id="uppath" name="uppath"   style="display:none;"/>
			<p>
				<input class="default" type="file" id="file1" name="file1" onchange="javascript:setImagePreview();"/>
	
				<input type="submit" value="上传" id="uploadbutton" />

				<br/>
		
			<div id="localImag"><img id="preview" width=-1 height=-1 style="diplay:none" /></div>
			
			<br>

		</form>
	</body>
</html>
