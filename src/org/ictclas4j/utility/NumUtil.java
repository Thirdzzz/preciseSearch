package org.ictclas4j.utility;

/**
 * ������صĹ�����
 * 
 * @author sinboy
 * @since 2007.5.23
 */
public class NumUtil {
	/**
	 * �ַ��������е����ַ��������ֵ�����������һ�������������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumStrNotNum(String word) {
		if (word != null) {
			if (word.length() == 2 && Utility.isInAggregate("���ϳɡ������á�����", word))
				return true;
			if (word.length() == 1 && Utility.isInAggregate("+-./", word))
				return true;
		}
		return false;
	}

	/**
	 * �Ƿ������֡����ַ���������磺��-4��
	 * 
	 * @param pos
	 * @param str
	 * @return
	 */
	public static boolean isNumDelimiter(int pos, String str) {
		if (str != null && str.length() > 1) {
			String first = str.substring(0, 1);
			// //27904='m'*256 29696='t'*256
			if ((Math.abs(pos) == PosTag.NUM || Math.abs(pos) == PosTag.TIME)
					&& ("��".equals(first) || "-".equals(first)))
				return true;
		}
		return false;
	}

}