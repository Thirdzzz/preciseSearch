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

public class IndexManager1 {				//~~~learned  不求甚解。。。。？？？？？
	//存放索引文件的目录
	//private final String indexDir = "E:/MyEclipseProject/结果";
	//存放将要被索引的HTML文件的目录
	//private final String dataDir = "E:/MyEclipseProject/health";		~~~如果是这样的路径，又得靠自己创建了。。？？？？？
	private String indexDir="D://test//index1";
	private String dataDir="WebRoot\\image_store";
	private Analyzer analyzer = null;
	private IndexWriter indexWriter = null;
	private HTMLParser htmlParser = null;
	private Document document = null;
	private Directory fsDirectory = null;
	
	/**
	 * dataDir(即参数strPath的路径)	存放将要被索引的HTML文件的目录，即将要被索引的HTML文件的将要存放的地方目录
	 * indexDir	存放索引文件的目录	，给定路径"D:/Documents and Settings/Administra。。。
	 * @param strPath
	 */
	
	/**
	 * 将文件file的内容保存到string中
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
	 * 判断索引是否存在
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
	//创建索引,如果要创建的索引已经存在，就不用管了
	 * @return
	 */

	public boolean createIndex() {
		//判断索引是否存在
		if(this.isIndexExisted() == true){
			return true;
		}
		File dir = new File(dataDir);		//~~~dir=dataDir，记录的是储存目标索引的目录
		//如果已被索引的目录是否存在
		if(!dir.exists()){
			return false;		//~~~如要要储存索引的目录文件都没有了，那还能做什么？！
		}
		//使用标准解析器
		Analyzer analyzer = new MMAnalyzer();
		try{
			IndexWriter indexWriter = new IndexWriter(indexDir,analyzer,true);
			//开始建立索引
			File[] dataFiles = dir.listFiles();
			for(int i = 0; i < dataFiles.length; i++){
				{
					
					///
					
					String sqlstr = "Select * from imagekey where imagename='"+dataFiles[i].getName()+"'";
				      List mylist = DBoperate.resultSetToList(sqlstr);
				        
				      Map colMap = new HashMap();
					  colMap = (Map) mylist.get(0);
						
					  String website=colMap.get("keyword").toString();//关键字
				      
					  org.jsoup.nodes.Document doc1=Jsoup.connect(website).timeout(60000).get();
					  
					  String title=doc1.title(); 
					///
					
					
					
					
					
					
					Document document = new Document();
					//Reader txtReader = new FileReader(dataFiles[i]);
					//添加path路径字段
					document.add(new Field("name",dataFiles[i].getName(),
							Field.Store.YES,Field.Index.NO));
					//String temp=txtReader.toString();
					document.add(new Field("content",title,Field.Store.YES, Field.Index.TOKENIZED));//添加contents内容字段
					document.add(new Field("path",dataFiles[i].getParent(),Field.Store.YES, Field.Index.TOKENIZED));
					document.add(new Field("website",website,Field.Store.YES, Field.Index.TOKENIZED));
					indexWriter.addDocument(document);//写入索引
					
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
	//取得索引目录 
	 * @return
	 */
	public String getIndexDir(){
		return this.indexDir;
	}
}
