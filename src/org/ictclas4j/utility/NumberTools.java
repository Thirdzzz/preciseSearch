package org.ictclas4j.utility;

/**
 * 数字相关的工具类
 */

public class NumberTools {
	/**
	 * 字符串的所有单个字符都是数字但本身并不是一个有意义的数字
	 */
	public static boolean isNumStrNotNum(String word) {
		if (word != null) {
			if (word.length() == 2 && Utility.isInAggregate("第上成±—＋∶·．／", word))
				return true;
			if (word.length() == 1 && Utility.isInAggregate("+-./", word))
				return true;
		}
		return false;
	}

	/**
	 * 是否是数字、连字符的情况，如：３-4月
	 */
	public static boolean isNumDelimiter(int pos, String str) {
		if (str != null && str.length() > 1) {
			String first = str.substring(0, 1);
			// 27904='m'*256 29696='t'*256
			if ((Math.abs(pos) == PosTag.NUM || Math.abs(pos) == PosTag.TIME)
					&& ("—".equals(first) || "-".equals(first)))
				return true;
		}
		return false;
	}

}
