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
                        //����£�ֱ����img����
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '300px';
                        imgObjPreview.style.height = '220px';                    
                        //imgObjPreview.src = docObj.files[0].getAsDataURL();

      //���7���ϰ汾�����������getAsDataURL()��ʽ��ȡ����Ҫһ�·�ʽ  
      imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

                }else{
                        //IE�£�ʹ���˾�
                        docObj.select();
                        var imgSrc = document.selection.createRange().text;
                        var localImagId = document.getElementById("localImag");
                        //�������ó�ʼ��С
                        localImagId.style.width = "300px";
                        localImagId.style.height = "220px";
                        //ͼƬ�쳣�Ĳ�׽����ֹ�û��޸ĺ�׺��α��ͼƬ
try{
                                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                        }catch(e){
                                alert("���ϴ���ͼƬ��ʽ����ȷ��������ѡ��!");
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
				���ϴ�ͼƬ
			</h2>

			<input type="text" value="D:\image\" id="uppath" name="uppath"   style="display:none;"/>
			<p>
				<input class="default" type="file" id="file1" name="file1" onchange="javascript:setImagePreview();"/>
	
				<input type="submit" value="�ϴ�" id="uploadbutton" />

				<br/>
		
			<div id="localImag"><img id="preview" width=-1 height=-1 style="diplay:none" /></div>
			
			<br>

		</form>
	</body>
</html>
