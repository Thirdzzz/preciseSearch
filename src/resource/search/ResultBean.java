package resource.search;

import java.util.List;

/**
 * ����һֱ��setʲô���ݣ�getʲô����
 * */
public class ResultBean {			//~~~learned
	private String htmlPath = null;//HTML�ļ���·��
	private String htmlDate = null;
	private String htmlTitle = null;//HTML�ļ��ı���
	private String htmlContent = null;
	private String htmlWebsite = null;
	private List<String> htmlMaterial = null;
	private List<String> htmlFood=null;
	
	public void sethtmlDate(String htmlDate){
		this.htmlDate =htmlDate;
	}
	public void sethtmlWebsite(String htmlWebsite){
		this.htmlWebsite =htmlWebsite;
	}
	
	
	public void setHtmlPath(String htmlPath){
		this.htmlPath = htmlPath;
	}
	public void setHtmlContent(String htmlContent){
		this.htmlContent = htmlContent;
	}
	public String gethtmlDate(){
		return htmlDate;
	}
	public String gethtmlWebsite(){
		return htmlWebsite;
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
