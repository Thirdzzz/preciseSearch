package resource;
import java.util.regex.Matcher;			//~~~learned
import java.util.regex.Pattern;		

	/**
	 * <p>
	 * Title: HTML��ص�������ʽ������
	 * </p>
	 * <p>
	 * Description: ��������HTML��ǣ�ת��HTML��ǣ��滻�ض�HTML���
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2006
	 * </p>
	 * 
	 * @author hejian
	 * @version 1.0
	 * @createtime 2006-10-16
	 */
public class HtmlRegexpUtil{
	public HtmlRegexpUtil() {
		//final String regxpForHtml = "<([^>]*)>"; // ����������<��ͷ��>��β�ı�ǩ

	    //final String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // �ҳ�IMG��ǩ

//	    final String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // �ҳ�IMG��ǩ��SRC����

	}

		/**
		 * 
		 * �������ܣ��滻�����������ʾ
		 * �滻����input�а�����< > " & ����
		 * <p>
		 * 
		 * @param input
		 * @return String
		 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}

		/**
		 * 
		 * �������ܣ��жϱ���Ƿ����
		 * ��������input���Ƿ���� > < " & ��������򷵻�true
		 * <p>
		 * 
		 * @param input
		 * @return boolean
		 */
	public boolean hasSpecialChars(String input) {			//~~~��������input���Ƿ���� > < " & ��������򷵻�true
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
					case '>':
						flag = true;
						break;
					case '<':
						flag = true;
						break;
					case '"':
						flag = true;
						break;
					case '&':
						flag = true;
						break;
					}
				}
		}
		return flag;
	}

		/**
		 * 
		 * �������ܣ�����str��������"<"��ͷ��">"��β�ı�ǩ
		 * <p>
		 * 
		 * @param str
		 * @return String
		 */
		public StringBuffer filterHtml(String str) {
			Pattern pattern = Pattern.compile("<([^>]*)>");
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");		//~~~������ʽ������ƥ�䵽��<>�������á�������
				result1 = matcher.find();
			}
			matcher.appendTail(sb);						//~~~���˺�Ľ������sb��
			return sb;
		}

		/**
		 * 
		 * �������ܣ�����str��ָ����ǩ <tag>����
		 * <p>
		 * 
		 * @param str
		 * @param tag
		 *            ָ����ǩ
		 * @return String
		 */
		public StringBuffer fiterHtmlTag(String str, String tag) {
			String regxp = "<" + tag + "\\s+([^>]*)\\s*>";		//~~~<tag����Ը�һЩ����>
			Pattern pattern = Pattern.compile(regxp);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");				//~~~ͬ����ҵ��ı�ǩtag�����
				result1 = matcher.find();
			}
			matcher.appendTail(sb);
			return sb;
		}
		/**
		 * 
		 * �������ܣ�����str��ָ����ǩ[tag]����
		 * <p>
		 * 
		 * @param str
		 * @param tag
		 *            ָ����ǩ
		 * @return String
		 */
		public StringBuffer fiterHtmlTag1(String str, String tag) {
			String regxp = "[" + tag + "\\s+([^\\]]*)\\s*]";
			Pattern pattern = Pattern.compile(regxp);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");
				result1 = matcher.find();
			}
			matcher.appendTail(sb);
			return sb;
		}
		/**
		 * 
		 * �������ܣ��滻ָ���ı�ǩ
		 * <p>
		 * 
		 * @param str
		 * @param beforeTag
		 *            Ҫ�滻�ı�ǩ
		 * @param startTag
		 *            �±�ǩ��ʼ���
		 * @param endTag
		 *            �±�ǩ�������
		 * @return String
		 * ~~~������ܺ���ֻ����ԭ��ǩ��ǰ��ͺ����ּ�����һЩ��ǣ���ɵ��±�ǩ������
		 * @�磺�滻img��ǩ��src����ֵΪ[img]����ֵ[/img]
		 */
		public StringBuffer replaceHtmlTag(String str, String beforeTag,
				 String startTag, String endTag) {
			String regxpForTag = "<\\s*" + beforeTag + "([^>]*)\\s*>";		//~~~����ԭ�ȵ�tag��ǩ
			//String regxpForTagAttrib = tagAttrib + "([^\"]+)\"";
			Pattern patternForTag = Pattern.compile(regxpForTag);
			//Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
			Matcher matcherForTag = patternForTag.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result = matcherForTag.find();
			while (result) {
				//StringBuffer sbreplace = new StringBuffer();
				//Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					//	.group(1));
				//if (matcherForAttrib.find()) {
					matcherForTag.appendReplacement(sb, startTag			//~~~ԭ��ǩ��startTag + matcherForTag.group(1) + endTag���滻
							+ matcherForTag.group(1) + endTag);				//~~~m.group(g) and s.substring(m.start(g), m.end(g)) are equivalent. m��group(n)����matcher m��ƥ��ĵ�n����
				//}				//~~~ƥ���ϵı��ʽ��Ȼ���µı�ǩ���м䣬��������ô������
				//matcherForTag.appendReplacement(sb, sbreplace.toString());
				result = matcherForTag.find();
			}
			matcherForTag.appendTail(sb);
			return sb;
		}
		/**
		 * 
		 * �������ܣ��滻ָ���ı�ǩ
		 * <p>
		 * ~~~�滻��ǩ�е�����ֵ  tagAttrib=���������޸�
		 * @param str
		 * @param beforeTag
		 *            Ҫ�滻�ı�ǩ
		 * @param tagAttrib
		 *            Ҫ�滻�ı�ǩ����ֵ
		 * @param startTag
		 *            �±�ǩ��ʼ���		����������ֵǰ�� �ı��
		 * @param endTag
		 *            �±�ǩ�������
		 * @return String
		 * @�磺�滻img��ǩ��src����ֵΪ[img]����ֵ[/img]
		 */
		public StringBuffer replaceHtmlTag1(String str, String beforeTag,
				String tagAttrib, String startTag, String endTag) {
			String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";		//~~~ԭ�ȵı�ǩtag
			String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";				//~~~Ҫ���滻�ı�ǩ����ֵ tagAttrib=��������������ʽ
			Pattern patternForTag = Pattern.compile(regxpForTag);				//~~~��ǩģʽ
			Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);		//~~~����ֵģʽ
			Matcher matcherForTag = patternForTag.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result = matcherForTag.find();
			while (result) {
				StringBuffer sbreplace = new StringBuffer();
				Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag	
						.group(1));												//~~~�ڵ�һ��ƥ�䵽���Ӵ��н�������ֵ�Ĳ���ƥ��
				if (matcherForAttrib.find()) {									//~~~ֻ�ж�һ�Σ���Ϊ��һ����ǩ��һ��������ֻ����һ��
					matcherForAttrib.appendReplacement(sbreplace, startTag		
							+ matcherForAttrib.group(1) + endTag);				//~~~�������ֵ��ǰ���
				}
				matcherForTag.appendReplacement(sb, sbreplace.toString());
				result = matcherForTag.find();
			}
			matcherForTag.appendTail(sb);
			return sb;
		}
}

