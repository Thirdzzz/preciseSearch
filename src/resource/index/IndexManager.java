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

public class IndexManager {				//~~~learned  不求甚解。。。。？？？？？
	//存放索引文件的目录
	//private final String indexDir = "E:/MyEclipseProject/结果";
	//存放将要被索引的HTML文件的目录
	//private final String dataDir = "E:/MyEclipseProject/health";		~~~如果是这样的路径，又得靠自己创建了。。？？？？？
	private String indexDir;
	private String dataDir;
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
	public IndexManager(String strPath)
	{
		String curPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		System.out.println(curPath);
		int Idx = curPath.indexOf("file:/")+"file:/".length();
		curPath = curPath.substring(Idx);		//~~~截取file:/之后的作为路径
		
		indexDir ="D:/index";//索引目录
		System.out.println(indexDir);		//~~~indexDir	存放索引文件的目录
		dataDir = strPath;		//~~~dataDir	存放将要被索引的HTML文件的目录
	}
	
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
				if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
					System.out.println("正在索引文件：" + dataFiles[i].getCanonicalPath());
					Document document = new Document();
					//Reader txtReader = new FileReader(dataFiles[i]);
					//添加path路径字段
					document.add(new Field("path",dataFiles[i].getCanonicalPath(),
							Field.Store.YES,Field.Index.NO));
					//String temp=txtReader.toString();
					document.add(new Field("contents",loadFileToString(dataFiles[i]),Field.Store.YES, Field.Index.TOKENIZED));//添加contents内容字段
					document.add(new Field("title",dataFiles[i].getName(),Field.Store.YES, Field.Index.TOKENIZED));
					indexWriter.addDocument(document);//写入索引
					System.out.println(dir.getPath());//在控制台打印被索引的路径
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
