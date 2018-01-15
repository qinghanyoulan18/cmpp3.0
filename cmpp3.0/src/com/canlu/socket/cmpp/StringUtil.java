package com.canlu.socket.cmpp;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;



public class StringUtil {

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param str
	 *            null���� ������null��������true
	 * @return
	 */
	public static boolean isNullString(String str) {
		return (null == str || StringUtils.isBlank(str.trim()) || "null"
				.equals(str.trim().toLowerCase())) ? true : false;
	}

	/**
	 * ��ʽ���ַ� ���Ϊ�գ����ء���
	 * 
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if (isNullString(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * ��ȡ�ַ���ĸ�����ֶ����ԣ����ֲ����ȡ��
	 * 
	 * @param str
	 *            �ַ�
	 * @param n
	 *            ��ȡ�ĳ��ȣ���ĸ�����Ϊ���֣�һ�����ֵ���������ĸ��
	 * @return
	 */
	public static String subStringByByte(String str, int n) {
		int num = 0;
		try {
			byte[] buf = str.getBytes("GBK");
			if (n >= buf.length) {
				return str;
			}
			boolean bChineseFirstHalf = false;
			for (int i = 0; i < n; i++) {
				if (buf[i] < 0 && !bChineseFirstHalf) {
					bChineseFirstHalf = true;
				} else {
					num++;
					bChineseFirstHalf = false;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.substring(0, num);
	}
	
	public static String getNewString(String str) throws UnsupportedEncodingException

    {

       //return new String(str.getBytes("ISO-8859-1"),"UTF-8");
		
		return new String(str.getBytes("ISO-8859-1"),"utf-8");

    }
}