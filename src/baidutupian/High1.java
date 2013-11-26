package baidutupian;

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class High1 {

	public class itmode {
		public String contents;

		public String website;

		public String date;

		public String title;

	}

	@SuppressWarnings("deprecation")
	public List<itmode> num(Set<String> name,int type) throws IOException,
			ParseException, InvalidTokenOffsetsException {

		List<itmode> ga = new ArrayList<itmode>();
		List<itmode> ga1 = new ArrayList<itmode>();

		// lpath记录相对路径

		BooleanQuery q1 = new BooleanQuery(); // ~~~逻辑搜索
		// ~~~Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。
		// 创建一个搜索器，以只读方式打开一个索引

		Directory directory = FSDirectory.open(new File("E:\\test\\index"));

		DirectoryReader directoryReader = DirectoryReader.open(directory);

		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		Analyzer analyzer = new IKAnalyzer();// 设定分词器
		

		String types = null;
		List<String> typeWord = new ArrayList<String>();
		Boolean flag;
		System.out.println("type-----"+type);
		switch (type) {
		case 1:
			types = "&&&1";
			typeWord.add("自然灾害");
			typeWord.add("地震");
			typeWord.add("强震");
			typeWord.add("大火");
			typeWord.add("火灾");
			typeWord.add("洪水");
			typeWord.add("洪灾");
			typeWord.add("泥石流");
			typeWord.add("大雪");
			typeWord.add("暴风雪");
			typeWord.add("火山");
			typeWord.add("海啸");
			typeWord.add("龙卷风");
			break;
		case 2:
			types = "&&&2";
			break;
		case 3:
			types = "&&&3";
			System.out.println("switch3---------");
			typeWord.add("事故灾害");
			typeWord.add("医保");
			typeWord.add("坠毁");
			typeWord.add("出轨");
			typeWord.add("相撞");
			typeWord.add("坍塌");
			typeWord.add("辐射");
			typeWord.add("泄漏");
			typeWord.add("踩踏");
			typeWord.add("核燃料");
			typeWord.add("事故");
			break;
		case 4:
			types = "&&&4";
			typeWord.add("社会安全");
			typeWord.add("枪击");
			typeWord.add("爆炸");
			typeWord.add("炸弹");
			typeWord.add("政局");
			typeWord.add("动荡");
			typeWord.add("恶化");
			typeWord.add("炮击");
			typeWord.add("罢工");
			typeWord.add("交火");
			typeWord.add("军事");
			typeWord.add("政变");
			typeWord.add("袭击");
			typeWord.add("骚乱");
			typeWord.add("恐怖");
			typeWord.add("反恐");
			typeWord.add("坠毁");
			typeWord.add("绑架");
			typeWord.add("转基因");
			break;
		default:
			break;
		}
		
		Iterator j = name.iterator();// 先迭代出来
		System.out.println("拓展次集合------"+name.size());
		while (j.hasNext()) {// 遍历
			String s = j.next().toString();
			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_41,
					new String[] { "title", "contens" }, analyzer);
			// MultiFieldQueryParser.Parse("Java",new
			// string[]{"Title","Author"},BooleanClause.Occur[],analyzer);
			Query query = parser.parse(s);
			System.out.println("~~~:" + s);
			q1.add(query, Occur.SHOULD);
			//break;
		}

		// q1为查询

		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(
				"<font color=\"red\">", "</font>");// 设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter,
				new QueryScorer(q1));
		System.out.println("q1.toString-------"+q1.toString());
		highlighter.setTextFragmenter(new SimpleFragmenter(200));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
		ScoreDoc[] hits = indexSearcher.search(q1, null, 1000).scoreDocs;
		System.out.println("changdu:" + hits.length);
		System.out.println("长度干！");
		for (int i = 0; i < hits.length; i++) {

			ScoreDoc hit = hits[i];
			itmode oo = new itmode();// 插入类中

			Document coDocument = indexSearcher.doc(hit.doc);

			TokenStream tokenStream = analyzer.tokenStream("",
					new StringReader(coDocument.get("contents").toString()));
			String str = highlighter.getBestFragment(tokenStream, coDocument
					.get("contents").toString());
			//System.out.println(coDocument.toString());
			int pos = coDocument.toString().indexOf(types);
			if(pos != -1){
				TokenStream tokenStream1 = analyzer.tokenStream("",
						new StringReader(coDocument.get("title").toString()));
				String str1 = highlighter.getBestFragment(tokenStream, coDocument
						.get("title").toString());
				if (str != null)
					str = str.concat("......");
				// str=str.concat("....");
				oo.contents = str;
				oo.date = coDocument.get("date");
				oo.title = str1;
				oo.website = coDocument.get("website");
				flag = false;
				for (String tw : typeWord) {
					System.out.println("tw---"+tw+"oo.title"+oo.title);
					if(oo.title == null){
						System.out.println("oo.title == null");
						continue;
					}
					if (tw!=null && oo.title.indexOf(tw) != -1) {
						ga.add(oo);
						flag = true;
						System.out.println(tw);
						break;
					}
				}
				if(flag == false)
					ga1.add(oo);
	//			
//				ga.add(oo);
			}

		}
		for(itmode oo:ga1)
			ga.add(oo);
		System.out.println("~~~~~");
		return ga;
	}

	public static void main(String[] args) throws IOException, ParseException,
			InvalidTokenOffsetsException {
		SortedSet<String> name = new TreeSet<String>();
		name.add("牛奶");
		name.add("猪肉");
		High1 it = new High1();
		//it.num(name);

	}
}
