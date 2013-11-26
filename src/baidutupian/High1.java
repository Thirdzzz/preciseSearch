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

		// lpath��¼���·��

		BooleanQuery q1 = new BooleanQuery(); // ~~~�߼�����
		// ~~~Lucene��һ������ȫ�ļ�������Ѱ�Ŀ�Դ��ʽ�⣬��Apache��������֧�ֺ��ṩ��Lucene�ṩ��һ����ȴǿ���Ӧ�ó�ʽ�ӿڣ��ܹ���ȫ����������Ѱ��
		// ����һ������������ֻ����ʽ��һ������

		Directory directory = FSDirectory.open(new File("E:\\test\\index"));

		DirectoryReader directoryReader = DirectoryReader.open(directory);

		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		Analyzer analyzer = new IKAnalyzer();// �趨�ִ���
		

		String types = null;
		List<String> typeWord = new ArrayList<String>();
		Boolean flag;
		System.out.println("type-----"+type);
		switch (type) {
		case 1:
			types = "&&&1";
			typeWord.add("��Ȼ�ֺ�");
			typeWord.add("����");
			typeWord.add("ǿ��");
			typeWord.add("���");
			typeWord.add("����");
			typeWord.add("��ˮ");
			typeWord.add("����");
			typeWord.add("��ʯ��");
			typeWord.add("��ѩ");
			typeWord.add("����ѩ");
			typeWord.add("��ɽ");
			typeWord.add("��Х");
			typeWord.add("�����");
			break;
		case 2:
			types = "&&&2";
			break;
		case 3:
			types = "&&&3";
			System.out.println("switch3---------");
			typeWord.add("�¹��ֺ�");
			typeWord.add("ҽ��");
			typeWord.add("׹��");
			typeWord.add("����");
			typeWord.add("��ײ");
			typeWord.add("̮��");
			typeWord.add("����");
			typeWord.add("й©");
			typeWord.add("��̤");
			typeWord.add("��ȼ��");
			typeWord.add("�¹�");
			break;
		case 4:
			types = "&&&4";
			typeWord.add("��ᰲȫ");
			typeWord.add("ǹ��");
			typeWord.add("��ը");
			typeWord.add("ը��");
			typeWord.add("����");
			typeWord.add("����");
			typeWord.add("��");
			typeWord.add("�ڻ�");
			typeWord.add("�չ�");
			typeWord.add("����");
			typeWord.add("����");
			typeWord.add("����");
			typeWord.add("Ϯ��");
			typeWord.add("ɧ��");
			typeWord.add("�ֲ�");
			typeWord.add("����");
			typeWord.add("׹��");
			typeWord.add("���");
			typeWord.add("ת����");
			break;
		default:
			break;
		}
		
		Iterator j = name.iterator();// �ȵ�������
		System.out.println("��չ�μ���------"+name.size());
		while (j.hasNext()) {// ����
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

		// q1Ϊ��ѯ

		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(
				"<font color=\"red\">", "</font>");// �趨������ʾ�ĸ�ʽ��Ҳ���ǶԸ�����ʾ�Ĵ������ǰ׺��׺
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter,
				new QueryScorer(q1));
		System.out.println("q1.toString-------"+q1.toString());
		highlighter.setTextFragmenter(new SimpleFragmenter(200));// ����ÿ�η��ص��ַ���.��ش����ʹ�����������ʱ��Ҳû��һ����ȫ������չʾ�����ɣ���Ȼ����Ҳ���趨ֻչʾ��������
		ScoreDoc[] hits = indexSearcher.search(q1, null, 1000).scoreDocs;
		System.out.println("changdu:" + hits.length);
		System.out.println("���ȸɣ�");
		for (int i = 0; i < hits.length; i++) {

			ScoreDoc hit = hits[i];
			itmode oo = new itmode();// ��������

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
		name.add("ţ��");
		name.add("����");
		High1 it = new High1();
		//it.num(name);

	}
}
