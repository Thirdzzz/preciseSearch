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

	private static String INDEX_PATH = "E:\\test\\imageindex";// �����ļ����·��
	
	//Ҫ������ͼƬ�ļ�Ŀ¼
	
	private static String INDEX_FILE_PATH = "E:\\test\\imgtest\\��Ϻ";
	
	@Test
 
	public static void createIndex() throws Exception {
		
		//����һ�����ʵ��ļ���������Lire���ͼ��Ķ��������в�ͬ��������
		DocumentBuilder db = DocumentBuilderFactory.getCEDDDocumentBuilder();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
		IndexWriter iw = new IndexWriter(FSDirectory.open(new File(INDEX_PATH)), iwc);
		File parent = new File(INDEX_FILE_PATH);
		int count=0;
		
		for (File f : parent.listFiles()) {
			// ����Lucene����
			System.out.println("�ļ�:"+(count++));
			//////
			String sqlstr = "Select * from imagekey where imagename='"+f.getName()+"'";
			
	        List mylist = DBoperate.resultSetToList(sqlstr);
	     
	        
			Map colMap = new HashMap();
			colMap = (Map) mylist.get(0);
			
			String website=colMap.get("keyword").toString();//�ؼ���
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
			
			// ���ļ���������
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
					//System.out.println("�ļ���:" + file2.getAbsolutePath());
					list.add(file2);
					fileNum++;
				} else {
					System.out.println(file2.getName());
					Document doc = db.createDocument(new FileInputStream(file2), file2.getName());
					// ���ļ���������
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
						
						//System.out.println("�ļ���:" + file2.getAbsolutePath());
						
						list.add(file2);
						fileNum++;
					} else {
						System.out.println(file2.getName());
						Document doc = db.createDocument(new FileInputStream(file2), file2.getName());
						// ���ļ���������
						iw.addDocument(doc);
						
						folderNum++;
					}
					
				}
			}
		} else {
			System.out.println("�ļ�������!");
		}
		System.out.println("�ļ��й���:" + folderNum + ",�ļ�����:" + fileNum);
		iw.close();

	}

	


	@Test//����ǰ�Ƚ������ظ�ͼƬ���ļ���������
	public static List<imgbean> search(String searchname) throws Exception {
		IndexReader ir = IndexReader.open(FSDirectory.open(new File("E:/test/imageindex")));
		ImageSearcher is = ImageSearcherFactory.createDefaultSearcher();//����һ��ͼƬ������
        FileInputStream fis = new FileInputStream("E:/test/imageupload/"+searchname);//����ͼƬԴ
        BufferedImage bi = ImageIO.read(fis);
        ImageSearchHits ish = is.search(bi, ir);//���������ṩ��ͼƬ�������Ƶ�ͼƬ
        
        
    	Analyzer analyzer = new IKAnalyzer();//�趨�ִ���
        Directory directory = FSDirectory.open(new File("E:/test/imageindex"));
		
	    DirectoryReader directoryReader = DirectoryReader.open(directory);
	
	    IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");//�趨������ʾ�ĸ�ʽ��Ҳ���ǶԸ�����ʾ�Ĵ������ǰ׺��׺
        QueryParser parser =new MultiFieldQueryParser(Version.LUCENE_41, new String[]{"title","contens"}, analyzer);
    	//MultiFieldQueryParser.Parse("Java",new string[]{"Title","Author"},BooleanClause.Occur[],analyzer);
        Query query = parser.parse("��Ϻ");
        
        Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(100));//����ÿ�η��ص��ַ���.��ش����ʹ�����������ʱ��Ҳû��һ����ȫ������չʾ�����ɣ���Ȼ����Ҳ���趨ֻչʾ��������
        //Document d = ish.doc(0);//ƥ�����ߵļ�¼
        //ish = is.search(d, ir);// �ӽ������������
		
	
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
        	if(score<10)it.score="<font color=\"green\">����</font>";
        	else if(score<20)it.score="<font color=\"blue\">��</font>";
        	else if(score>=20&&score<50)it.score="<font color=\"purple\">��</font>";
        	else if(score>=50&&score<70){it.score="<font color=\"red\">��</font>";}
        	else {it.score="<font color=\"black\">����</font>";}
        	
        	
        		
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