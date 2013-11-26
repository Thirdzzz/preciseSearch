package resource.index;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.demo.html.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.jsoup.Jsoup;

import spider.DBoperate;

public class IndexManager1 {				//~~~learned  �������⡣����������������
	//��������ļ���Ŀ¼
	//private final String indexDir = "E:/MyEclipseProject/���";
	//��Ž�Ҫ��������HTML�ļ���Ŀ¼
	//private final String dataDir = "E:/MyEclipseProject/health";		~~~�����������·�����ֵÿ��Լ������ˡ�������������
	private String indexDir="D://test//index1";
	private String dataDir="WebRoot\\image_store";
	private Analyzer analyzer = null;
	private IndexWriter indexWriter = null;
	private HTMLParser htmlParser = null;
	private Document document = null;
	private Directory fsDirectory = null;
	
	/**
	 * dataDir(������strPath��·��)	��Ž�Ҫ��������HTML�ļ���Ŀ¼������Ҫ��������HTML�ļ��Ľ�Ҫ��ŵĵط�Ŀ¼
	 * indexDir	��������ļ���Ŀ¼	������·��"D:/Documents and Settings/Administra������
	 * @param strPath
	 */
	
	/**
	 * ���ļ�file�����ݱ��浽string��
	 * @param file
	 * @return
	 */
	public String loadFileToString(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * �ж������Ƿ����
	 * @return
	 */
	public boolean isIndexExisted(){
		File directory = new File(indexDir);
		if(!directory.exists())
			return false;
		if(directory.listFiles().length > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	//��������,���Ҫ�����������Ѿ����ڣ��Ͳ��ù���
	 * @return
	 */

	public boolean createIndex() {
		//�ж������Ƿ����
		if(this.isIndexExisted() == true){
			return true;
		}
		File dir = new File(dataDir);		//~~~dir=dataDir����¼���Ǵ���Ŀ��������Ŀ¼
		//����ѱ�������Ŀ¼�Ƿ����
		if(!dir.exists()){
			return false;		//~~~��ҪҪ����������Ŀ¼�ļ���û���ˣ��ǻ�����ʲô����
		}
		//ʹ�ñ�׼������
		Analyzer analyzer = new MMAnalyzer();
		try{
			IndexWriter indexWriter = new IndexWriter(indexDir,analyzer,true);
			//��ʼ��������
			File[] dataFiles = dir.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
				{
					
					///
					
					String sqlstr = "Select * from imagekey where imagename='"+dataFiles[i].getName()+"'";
				      List mylist = DBoperate.resultSetToList(sqlstr);
				        
				      Map colMap = new HashMap();
					  colMap = (Map) mylist.get(0);
						
					  String website=colMap.get("keyword").toString();//�ؼ���
				      
					  org.jsoup.nodes.Document doc1=Jsoup.connect(website).timeout(60000).get();
					  
					  String title=doc1.title(); 
					///
					
					
					
					
					
					
					Document document = new Document();
					//Reader txtReader = new FileReader(dataFiles[i]);
					//���path·���ֶ�
					document.add(new Field("name",dataFiles[i].getName(),
							Field.Store.YES,Field.Index.NO));
					//String temp=txtReader.toString();
					document.add(new Field("content",title,Field.Store.YES, Field.Index.TOKENIZED));//���contents�����ֶ�
					document.add(new Field("path",dataFiles[i].getParent(),Field.Store.YES, Field.Index.TOKENIZED));
					document.add(new Field("website",website,Field.Store.YES, Field.Index.TOKENIZED));
					indexWriter.addDocument(document);//д������
					
				}
			}
			indexWriter.optimize();
			indexWriter.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 *
	//ȡ������Ŀ¼ 
	 * @return
	 */
	public String getIndexDir(){
		return this.indexDir;
	}
}
