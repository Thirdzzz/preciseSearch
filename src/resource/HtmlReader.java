package resource;
import java.io.*;
public class HtmlReader {			//learned
	public HtmlReader() {
	}
	/*
	* @param filePath 文件路径
	* @return 获得html的全部内容
	*/
	public String readHtml(String filePath) {		//~~~得到的是HTML中的全部内容，包括tag啥的
		BufferedReader br=null;
		StringBuffer sb = new  StringBuffer();
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),  "UTF-8")); 	//~~~这应该是读取的filePath路径下文件的内容           
			String temp=null;        
			while((temp=br.readLine())!=null){
				sb.append(temp);				//~~~将br内容一行行转入到StringBuffer sb中
			}            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return sb.toString();			//~~~返回所有内容，以String的形式
	}
	/*
	* @param filePath 文件路径
	* @return 获得的html文本内容
	*/
	public String getTextFromHtml(String filePath) {		//~~~最后返回的只是不带tag的内容
	//得到body标签中的内容
		String str= readHtml(filePath);			//~~~瞬间获得了html的全部内容
		StringBuffer buff = new StringBuffer();
		int maxindex = str.length() - 1;
		int begin = 0;
		int end;            
	//截取>和<之间的内容
		while((begin = str.indexOf('>',begin)) < maxindex){ 		//~~~indexOf('>',begin)从begin之后开始搜索           
			end = str.indexOf('<',begin);
			if(end - begin > 1){
				buff.append(str.substring(++begin, end));    		//~~~找到不是tag的内容加入到buff中            
			}            
			begin = end+1;
		};        
		return buff.toString();
	}
	
}