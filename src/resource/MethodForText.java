package resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodForText {			//~~~learned
	public MethodForText()
	{
		
	}
	/**
	 * 
	 * 基本功能：过滤指定标签
	 * <p>
	 * 
	 * @param str
	 * @param tag
	 *            指定标签
	 * ~~~就是把字符串str中的标签tag给去除了
	 * @return String
	 */
	public StringBuffer fiterString(String str, String tag) {
		String regxp = tag;
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
}
