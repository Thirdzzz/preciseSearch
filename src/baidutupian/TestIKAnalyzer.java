package baidutupian;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestIKAnalyzer {
	
	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new IKAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("", new StringReader("永和服装饰品有限公司"));
		//2.x写法 3.0之后不支持了
		/*Token token =new Token();
		while(tokenStream.next(token)!=null){
			System.out.println(token.term());
		}*/
		//3.x的写法
		TermAttribute termAtt = (TermAttribute) tokenStream.getAttribute(TermAttribute.class); 
		TypeAttribute typeAtt = (TypeAttribute) tokenStream.getAttribute(TypeAttribute.class); 

		while (tokenStream.incrementToken()) { 
			System.out.print(termAtt.term()); 
			System.out.print(' '); 
		} 
	}

}
