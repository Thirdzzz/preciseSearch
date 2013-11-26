package baidutupian;


import java.util.List;

/**
 * 就是一直在set什么内容，get什么内容
 * */
public class ResultBean {			//~~~learned
	private String htmlPath = null;//HTML文件的路径
	private String htmlTitle = null;//HTML文件的标题
	private String htmlContent = null;
	private List<String> htmlMaterial = null;
	private List<String> htmlFood=null;
	public void setHtmlPath(String htmlPath){
		this.htmlPath = htmlPath;
	}
	public void setHtmlContent(String htmlContent){
		this.htmlContent = htmlContent;
	}
	public String getHtmlPath(){
		return htmlPath;
	}
	public String getHtmlContent(){
		return htmlContent;
	}
	public void setHtmlTitle(String htmlTitle){
		this.htmlTitle = htmlTitle;
	}
	public String getHtmlTitle(){
		return htmlTitle;
	}
	public void setHtmlMaterial(List<String> htmlMaterial)
	{
		this.htmlMaterial=htmlMaterial;
	}
	public void setHtmlFood(List<String> htmlFood)
	{
		this.htmlFood=htmlFood;
	}
	public List<String> getHtmlFood(){
		return htmlFood;
	}
	public List<String> getHtmlMaterial(){
		return htmlMaterial;
	}
	
}

