package com.thtf.ezone.ajaxupload.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import spider.*;
import javax.imageio.ImageIO;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.ImageDuplicates;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jzy.test.imgbean;
import com.jzy.test.test1;

public class TestImageSearch {

	private static String INDEX_PATH = "E:\\test\\imageindex";// 索引文件存放路径
	
	//要索引的图片文件目录
	
	private static String INDEX_FILE_PATH = "E:\\test\\imgtest\\龙虾";
	
	@Test
 
	public static void createIndex() throws Exception {
		
		//创建一个合适的文件生成器，Lire针对图像的多种属性有不同的生成器
		DocumentBuilder db = DocumentBuilderFactory.getCEDDDocumentBuilder();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
		IndexWriter iw = new IndexWriter(FSDirectory.open(new File(INDEX_PATH)), iwc);
		File parent = new File(INDEX_FILE_PATH);
		int count=0;
		
		for (File f : parent.listFiles()) {
			// 创建Lucene索引
			System.out.println("文件:"+(count++));
			//////
			String sqlstr = "Select * from imagekey where imagename='"+f.getName()+"'";
			
	        List mylist = DBoperate.resultSetToList(sqlstr);
	     
	        
			Map colMap = new HashMap();
			colMap = (Map) mylist.get(0);
			
			String website=colMap.get("keyword").toString();//关键字
			org.jsoup.nodes.Document doc1=Jsoup.connect(website).timeout(60000).get();
			String title=doc1.title();
			
			
			test1 jk=new test1();
			 
		
			String contents=(jk.FlagGuolv(website));
			
			
		
			BufferedImage sourceImg =ImageIO.read(new FileInputStream(f)); 
			
			
			

			
			
			
			Document doc = db.createDocument(new FileInputStream(f), f.getName());
			doc.add(new Field("width",String.valueOf(sourceImg.getWidth()),Field.Store.YES,Field.Index.ANALYZED));
			
			doc.add(new Field("height",String.valueOf(sourceImg.getHeight()),Field.Store.YES,Field.Index.ANALYZED));
			
			doc.add(new Field("contents",contents,Field.Store.YES,Field.Index.ANALYZED));
			
			doc.add(new Field("title",title,Field.Store.YES,Field.Index.ANALYZED));
			doc.add(new Field("website",website,Field.Store.YES,Field.Index.ANALYZED));
			System.out.println(title);
			
			// 将文件加入索引
			iw.addDocument(doc);
		}
		//iw.optimize();
		iw.close();
	}
	public static void traverseFolder1() throws IOException {
		DocumentBuilder db = DocumentBuilderFactory.getCEDDDocumentBuilder();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
		IndexWriter iw = new IndexWriter(FSDirectory.open(new File(INDEX_PATH)), iwc);
		
		int fileNum = 0, folderNum = 0;
		File file = new File(INDEX_FILE_PATH);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					//System.out.println("文件夹:" + file2.getAbsolutePath());
					list.add(file2);
					fileNum++;
				} else {
					System.out.println(file2.getName());
					Document doc = db.createDocument(new FileInputStream(file2), file2.getName());
					// 将文件加入索引
					iw.addDocument(doc);
					folderNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					
					
					if (file2.isDirectory()) {
						
						//System.out.println("文件夹:" + file2.getAbsolutePath());
						
						list.add(file2);
						fileNum++;
					} else {
						System.out.println(file2.getName());
						Document doc = db.createDocument(new FileInputStream(file2), file2.getName());
						// 将文件加入索引
						iw.addDocument(doc);
						
						folderNum++;
					}
					
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
		iw.close();

	}

	


	@Test//测试前先将包含重复图片的文件进行索引
	public static List<imgbean> search(String searchname) throws Exception {
		IndexReader ir = IndexReader.open(FSDirectory.open(new File("E:/test/imageindex")));
		ImageSearcher is = ImageSearcherFactory.createDefaultSearcher();//创建一个图片搜索器
        FileInputStream fis = new FileInputStream("E:/test/imageupload/"+searchname);//搜索图片源
        BufferedImage bi = ImageIO.read(fis);
        ImageSearchHits ish = is.search(bi, ir);//根据上面提供的图片搜索相似的图片
        
        
    	Analyzer analyzer = new IKAnalyzer();//设定分词器
        Directory directory = FSDirectory.open(new File("E:/test/imageindex"));
		
	    DirectoryReader directoryReader = DirectoryReader.open(directory);
	
	    IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
        QueryParser parser =new MultiFieldQueryParser(Version.LUCENE_41, new String[]{"title","contens"}, analyzer);
    	//MultiFieldQueryParser.Parse("Java",new string[]{"Title","Author"},BooleanClause.Occur[],analyzer);
        Query query = parser.parse("龙虾");
        
        Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(100));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
        //Document d = ish.doc(0);//匹配度最高的记录
        //ish = is.search(d, ir);// 从结果集中再搜索
		
	
        List <imgbean>imagescool=new ArrayList<imgbean>();
        for (int i = 0; i <ish.length(); i++) {
        	TokenStream tokenStream = analyzer.tokenStream("",new StringReader(ish.doc(i).get("contents").toString()));
			String contents = highlighter.getBestFragment(tokenStream, ish.doc(i).get("contents").toString());
			tokenStream = analyzer.tokenStream("",new StringReader(ish.doc(i).get("title").toString()));
			String title = highlighter.getBestFragment(tokenStream, ish.doc(i).get("title").toString());
        	
			
			
        	imgbean it=new imgbean();
        	it.website=ish.doc(i).get("website");
        
        	it.allcontents=ish.doc(i).get("contents");
        	it.contents=(contents==null?"":contents);
        	it.name=ish.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
        	float score=ish.score(i);
        	if(score<10)it.score="<font color=\"green\">极高</font>";
        	else if(score<20)it.score="<font color=\"blue\">高</font>";
        	else if(score>=20&&score<50)it.score="<font color=\"purple\">中</font>";
        	else if(score>=50&&score<70){it.score="<font color=\"red\">低</font>";}
        	else {it.score="<font color=\"black\">极低</font>";}
        	
        	
        		
        	it.title=(title==null?ish.doc(i).get("title"):title);
        	it.height=ish.doc(i).get("height");
        	it.width=ish.doc(i).get("width");

        	
        	imagescool.add(it);
        }
        return imagescool;
	}
	   public static void main(String []args) throws Exception
	    {
		  // search("Img376636069.jpg");
			//searchSimilar();
		    // traverseFolder1();
			createIndex();
			//traverseFolder1();
			//searchSimilar();
			//searchSimilar("E:/123.jpg");
			 
	    }
	
	
}