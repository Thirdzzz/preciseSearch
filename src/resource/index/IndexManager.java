package resource.index;

import java.io.*;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.demo.html.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;

public class IndexManager {				//~~~learned  �������⡣����������������
	//��������ļ���Ŀ¼
	//private final String indexDir = "E:/MyEclipseProject/���";
	//��Ž�Ҫ��������HTML�ļ���Ŀ¼
	//private final String dataDir = "E:/MyEclipseProject/health";		~~~�����������·�����ֵÿ��Լ������ˡ�������������
	private String indexDir;
	private String dataDir;
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
	public IndexManager(String strPath)
	{
		String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		System.out.println(curPath);
		int Idx = curPath.indexOf("file:/")+"file:/".length();
		curPath = curPath.substring(Idx);		//~~~��ȡfile:/֮�����Ϊ·��
		
		indexDir ="D:/index";//����Ŀ¼
		System.out.println(indexDir);		//~~~indexDir	��������ļ���Ŀ¼
		dataDir = strPath;		//~~~dataDir	��Ž�Ҫ��������HTML�ļ���Ŀ¼
	}
	
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
				if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
					System.out.println("���������ļ���" + dataFiles[i].getCanonicalPath());
					Document document = new Document();
					//Reader txtReader = new FileReader(dataFiles[i]);
					//���path·���ֶ�
					document.add(new Field("path",dataFiles[i].getCanonicalPath(),
							Field.Store.YES,Field.Index.NO));
					//String temp=txtReader.toString();
					document.add(new Field("contents",loadFileToString(dataFiles[i]),Field.Store.YES, Field.Index.TOKENIZED));//���contents�����ֶ�
					document.add(new Field("title",dataFiles[i].getName(),Field.Store.YES, Field.Index.TOKENIZED));
					indexWriter.addDocument(document);//д������
					System.out.println(dir.getPath());//�ڿ���̨��ӡ��������·��
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
