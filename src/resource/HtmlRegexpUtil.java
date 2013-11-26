package resource;
import java.util.regex.Matcher;			//~~~learned
import java.util.regex.Pattern;		

	/**
	 * <p>
	 * Title: HTML相关的正则表达式工具类
	 * </p>
	 * <p>
	 * Description: 包括过滤HTML标记，转换HTML标记，替换特定HTML标记
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
		//final String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

	    //final String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签

//	    final String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

	}

		/**
		 * 
		 * 基本功能：替换标记以正常显示
		 * 替换输入input中包含的< > " & 符号
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
		 * 基本功能：判断标记是否存在
		 * 查找输入input中是否催在 > < " & 如果存在则返回true
		 * <p>
		 * 
		 * @param input
		 * @return boolean
		 */
	public boolean hasSpecialChars(String input) {			//~~~查找输入input中是否催在 > < " & 如果存在则返回true
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
		 * 基本功能：过滤str中所有以"<"开头以">"结尾的标签
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
				matcher.appendReplacement(sb, "");		//~~~正则表达式处理，将匹配到的<>中内容用“”消除
				result1 = matcher.find();
			}
			matcher.appendTail(sb);						//~~~过滤后的结果存在sb中
			return sb;
		}

		/**
		 * 
		 * 基本功能：过滤str中指定标签 <tag>类型
		 * <p>
		 * 
		 * @param str
		 * @param tag
		 *            指定标签
		 * @return String
		 */
		public StringBuffer fiterHtmlTag(String str, String tag) {
			String regxp = "<" + tag + "\\s+([^>]*)\\s*>";		//~~~<tag后可以跟一些东西>
			Pattern pattern = Pattern.compile(regxp);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result1 = matcher.find();
			while (result1) {
				matcher.appendReplacement(sb, "");				//~~~同理把找到的标签tag清除掉
				result1 = matcher.find();
			}
			matcher.appendTail(sb);
			return sb;
		}
		/**
		 * 
		 * 基本功能：过滤str中指定标签[tag]类型
		 * <p>
		 * 
		 * @param str
		 * @param tag
		 *            指定标签
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
		 * 基本功能：替换指定的标签
		 * <p>
		 * 
		 * @param str
		 * @param beforeTag
		 *            要替换的标签
		 * @param startTag
		 *            新标签开始标记
		 * @param endTag
		 *            新标签结束标记
		 * @return String
		 * ~~~这个功能好像只是在原标签的前面和后面又加上了一些标记，组成的新标签？？？
		 * @如：替换img标签的src属性值为[img]属性值[/img]
		 */
		public StringBuffer replaceHtmlTag(String str, String beforeTag,
				 String startTag, String endTag) {
			String regxpForTag = "<\\s*" + beforeTag + "([^>]*)\\s*>";		//~~~这是原先的tag标签
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
					matcherForTag.appendReplacement(sb, startTag			//~~~原标签用startTag + matcherForTag.group(1) + endTag来替换
							+ matcherForTag.group(1) + endTag);				//~~~m.group(g) and s.substring(m.start(g), m.end(g)) are equivalent. m。group(n)就是matcher m中匹配的第n个组
				//}				//~~~匹配上的表达式仍然在新的标签的中间，这样可以么？？？
				//matcherForTag.appendReplacement(sb, sbreplace.toString());
				result = matcherForTag.find();
			}
			matcherForTag.appendTail(sb);
			return sb;
		}
		/**
		 * 
		 * 基本功能：替换指定的标签
		 * <p>
		 * ~~~替换标签中的属性值  tagAttrib=？？？的修改
		 * @param str
		 * @param beforeTag
		 *            要替换的标签
		 * @param tagAttrib
		 *            要替换的标签属性值
		 * @param startTag
		 *            新标签开始标记		附加在属性值前后 的标记
		 * @param endTag
		 *            新标签结束标记
		 * @return String
		 * @如：替换img标签的src属性值为[img]属性值[/img]
		 */
		public StringBuffer replaceHtmlTag1(String str, String beforeTag,
				String tagAttrib, String startTag, String endTag) {
			String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";		//~~~原先的标签tag
			String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";				//~~~要被替换的标签属性值 tagAttrib=？？？？？的形式
			Pattern patternForTag = Pattern.compile(regxpForTag);				//~~~标签模式
			Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);		//~~~属性值模式
			Matcher matcherForTag = patternForTag.matcher(str);
			StringBuffer sb = new StringBuffer();
			boolean result = matcherForTag.find();
			while (result) {
				StringBuffer sbreplace = new StringBuffer();
				Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag	
						.group(1));												//~~~在第一个匹配到的子串中进行属性值的查找匹配
				if (matcherForAttrib.find()) {									//~~~只判断一次，因为在一个标签中一个属性名只出现一次
					matcherForAttrib.appendReplacement(sbreplace, startTag		
							+ matcherForAttrib.group(1) + endTag);				//~~~添加属性值的前后端
				}
				matcherForTag.appendReplacement(sb, sbreplace.toString());
				result = matcherForTag.find();
			}
			matcherForTag.appendTail(sb);
			return sb;
		}
}

