package resource;
import java.io.*;
public class HtmlReader {			//learned
	public HtmlReader() {
	}
	/*
	* @param filePath �ļ�·��
	* @return ���html��ȫ������
	*/
	public String readHtml(String filePath) {		//~~~�õ�����HTML�е�ȫ�����ݣ�����tagɶ��
		BufferedReader br=null;
		StringBuffer sb = new  StringBuffer();
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),  "UTF-8")); 	//~~~��Ӧ���Ƕ�ȡ��filePath·�����ļ�������           
			String temp=null;        
			while((temp=br.readLine())!=null){
				sb.append(temp);				//~~~��br����һ����ת�뵽StringBuffer sb��
			}            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return sb.toString();			//~~~�����������ݣ���String����ʽ
	}
	/*
	* @param filePath �ļ�·��
	* @return ��õ�html�ı�����
	*/
	public String getTextFromHtml(String filePath) {		//~~~��󷵻ص�ֻ�ǲ���tag������
	//�õ�body��ǩ�е�����
		String str= readHtml(filePath);			//~~~˲������html��ȫ������
		StringBuffer buff = new StringBuffer();
		int maxindex = str.length() - 1;
		int begin = 0;
		int end;            
	//��ȡ>��<֮�������
		while((begin = str.indexOf('>',begin)) < maxindex){ 		//~~~indexOf('>',begin)��begin֮��ʼ����           
			end = str.indexOf('<',begin);
			if(end - begin > 1){
				buff.append(str.substring(++begin, end));    		//~~~�ҵ�����tag�����ݼ��뵽buff��            
			}            
			begin = end+1;
		};        
		return buff.toString();
	}
	
}