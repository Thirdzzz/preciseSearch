package resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodForText {			//~~~learned
	public MethodForText()
	{
		
	}
	/**
	 * 
	 * �������ܣ�����ָ����ǩ
	 * <p>
	 * 
	 * @param str
	 * @param tag
	 *            ָ����ǩ
	 * ~~~���ǰ��ַ���str�еı�ǩtag��ȥ����
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
