package com.github.lixhbs.zeyucommon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils extends org.apache.commons.lang3.StringUtils{

	/**
	 *
	 */
	private  static Log log = LogFactory.getLog(StringUtils.class);

	/**
	 * 前导标识
	 */
	public static final int BEFORE = 1;

	/**
	 * 后继标识
	 */
	public static final int AFTER = 2;

	public static final String DEFAULT_PATH_SEPARATOR = ",";


	/**
	 * 将一个中间带逗号分隔符的字符串转换为ArrayList对象
	 * @param str 待转换的符串对象
	 * @return ArrayList对象
	 */
	public static ArrayList strToArrayList(String str) {
		return strToArrayListManager(str, DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为ArrayList对象
	 * @param str 待转换的字符串对象
	 * @param separator 字符型分隔符
	 * @return ArrayList对象
	 */
	public static ArrayList<String> strToArrayList(String str, String separator) {
		return strToArrayListManager(str, separator);
	}

	private static ArrayList<String> strToArrayListManager(String str,String separator) {

		StringTokenizer strTokens = new StringTokenizer(str, separator);
		ArrayList<String> list = new ArrayList<String>();

		while (strTokens.hasMoreTokens()) {
			list.add(strTokens.nextToken().trim());
		}

		return list;
	}

	/**
	 * 将一个中间带逗号分隔符的字符串转换为字符型数组对象
	 * @param str 待转换的符串对象
	 * @return 字符型数组
	 */
	public static String[] strToStrArray(String str) {
		return strToStrArrayManager(str, DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为字符型数组对象
	 * @param str 待转换的符串对象
	 * @param separator 字符型分隔符
	 * @return 字符型数组
	 */
	public static String[] strToStrArray(String str, String separator) {
		return strToStrArrayManager(str, separator);
	}

	private static String[] strToStrArrayManager(
			String str,
			String separator) {

		StringTokenizer strTokens = new StringTokenizer(str, separator);
		String[] strArray = new String[strTokens.countTokens()];
		int i = 0;

		while (strTokens.hasMoreTokens()) {
			strArray[i] = strTokens.nextToken().trim();
			i++;
		}

		return strArray;
	}

	public static Set<String> strToSet(String str){
		return strToSet(str, DEFAULT_PATH_SEPARATOR);
	}

	public static Set<String> strToSet(String str, String separator){
		String[] values = strToStrArray(str, separator);
		Set<String> result = new LinkedHashSet<String>();
		for (int i = 0; i < values.length; i++) {
			result.add(values[i]);
		}
		return result;
	}

	/**
	 * 将一个数组，用逗号分隔变为一个字符串
	 * @param array
	 * @return
	 */
	public static String ArrayToStr(Object[] array){
		if(array == null || array.length <0) {
			return null;
		}
		String str = "";
		int _step = 0;
		for(int i = 0; i<array.length; i++){
			if(_step>0) {
				str += ",";
			}
			str += (String)array[i];
			_step++;
		}
		return str;
	}

	public static String CollectionToStr(Collection<String> coll){
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (String string : coll) {
			if(i > 0) {
				sb.append(",");
			}
			i++;
			sb.append(string);
		}
		return sb.toString();
	}

	/**
	 * 转换给定字符串的编码格式
	 * @param inputString 待转换字符串对象
	 * @param inencoding 待转换字符串的编码格式
	 * @param outencoding 准备转换为的编码格式
	 * @return 指定编码与字符串的字符串对象
	 */
	public static String encodingTransfer(String inputString,String inencoding,String outencoding)
	{
		if (inputString==null || inputString.length() ==0) {
			return inputString;
		}
		try
		{
			return new String( inputString.getBytes( outencoding), inencoding);
		}
		catch (Exception e)
		{
			return inputString;
		}
	}


	/**
	 * 删除字符串中指定的字符
	 * 只要在delStrs参数中出现的字符，并且在inputString中也出现都会将其它删除
	 * @param inputString 待做删除处理的字符串
	 * @param delStrs 作为删除字符的参照数据，用逗号分隔。如果要删除逗号可以在这个字符串再加一个逗号
	 * @return 返回一个以inputString为基础不在delStrs出现新字符串
	 */
	public static String delString(String inputString,String delStrs)
	{
		if (inputString==null || inputString.length() ==0) {
			return inputString;
		}

		String[] strs = strToStrArray(delStrs);
		for(int i = 0 ;i <strs.length ; i++){
			if(strs[i].equals("")) {
				inputString = inputString.replaceAll(" ","");
			} else
			{
				if(strs[i].compareTo("a")>=0) {
					inputString = replace(inputString,strs[i],"");
				} else {
					inputString = inputString.replaceAll("\\\\"+strs[i],"");
				}
			}
		}
		if(subCount(delStrs,",")>strs.length) {
			inputString = inputString.replaceAll("\\,","");
		}

		return inputString;
	}

	/**
	 * 去除左边多余的空格。
	 * @param value 待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 */
	public static String trimLeft(String value) {
		String result = value;
		if(result == null) {
			return result;
		}
		char ch[] = result.toCharArray();
		int index = -1;
		for(int i=0; i < ch.length ; i++) {
			if(Character.isWhitespace(ch[i])) {
				index = i;
			}
			else {
				break;
			}
		}
		if(index != -1) {
			result = result.substring(index+1);
		}
		return result;
	}

	/**
	 * 去除右边多余的空格。
	 * @param value 待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if(result == null) {
			return result;
		}
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for(int i=ch.length-1; i > -1; i--) {
			if(Character.isWhitespace(ch[i])) {
				endIndex = i;
			}
			else {
				break;
			}
		}
		if(endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}


	/**
	 * 判断一个字符串中是否包含另一个字符串
	 * @param parentStr	父串
	 * @param subStr	子串
	 * @return 如果父串包含子串的内容返回真，否则返回假
	 */
	public static boolean isInclude(String parentStr, String subStr) {
		return parentStr.indexOf(subStr) >= 0;
	}


	/**
	 * 判断一个字符串中是否包含另一个字符串数组的任何一个
	 * @param parentStr	父串
	 * @param subStrs	子串集合(以逗号分隔的字符串)
	 * @return 如果父串包含子串的内容返回真，否则返回假
	 */
	public static boolean isIncludes(String parentStr, String subStrs) {
		String[] subStrArray = strToStrArray(subStrs);
		for(int j = 0 ; j<subStrArray.length ; j++){
			String subStr = subStrArray[j];
			if(isInclude(parentStr,subStr)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 判断一个子字符串在父串中重复出现的次数
	 * @param parentStr	父串
	 * @param subStr	子串
	 * @return 子字符串在父字符串中重得出现的次数
	 */
	public static int subCount(String parentStr, String subStr) {
		int count = 0;

		for (int i = 0; i < (parentStr.length() - subStr.length());i++) {
			String tempString = parentStr.substring(i, i + subStr.length());
			if (subStr.equals(tempString)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 得到在字符串中从开始字符串到结止字符串中间的子串
	 * @param parentStr 父串
	 * @param startStr 开始串
	 * @param endStr 结止串
	 * @return 返回开始串与结止串之间的子串
	 */
	public static String subString(String parentStr, String startStr, String endStr){
		return parentStr.substring(parentStr.indexOf(startStr)+startStr.length(),parentStr.indexOf(endStr));
	}

	/** 得到在字符串中从开始字符串到结止字符串中间的子串的集合
	 * @param parentStr 父串
	 * @param startStr 开始串
	 * @param endStr 结止串
	 * @return 返回开始串与结止串之间的子串的集合
	 */
	public static List<String> subStringList(String parentStr, String startStr, String endStr){
		List<String> result = new ArrayList<String>();
		List<String> elements = dividToList(parentStr, startStr, endStr);
		for (String element : elements) {
			if (!isIncludes(element, startStr) || !isIncludes(element, endStr)) {
				continue;
			}
			result.add(subString(element,startStr,endStr));
		}
		return result;

	}

	/**
	 * 将指定的String转换为Unicode的等价值
	 *
	 * 基础知识：
	 *   所有的转义字符都是由 '\' 打头的第二个字符
	 *   0-9  :八进制
	 *   u    :是Unicode转意，长度固定为6位
	 *   Other:则为以下字母中的一个 b,t,n,f,r,",\  都不满足，则产生一个编译错误
	 *  提供八进制也是为了和C语言兼容.
	 * b,t,n,f,r 则是为控制字符.书上的意思为:描述数据流的发送者希望那些信息如何被格式化或者被表示.
	 * 它可以写在代码的任意位置，只要转义后是合法的.
	 * 例如:
	 * int c=0\u003b
	 * 上面的代码可以编译通过，等同于int c=0; \u003b也就是';'的Unicode代码
	 *
	 * @param inStr 待转换为Unicode的等价字符串
	 * @return Unicode的字符串
	 */
	public static String getUnicodeStr(String inStr){
		char[] myBuffer = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < inStr.length(); i++) {
			//byte b = (byte) myBuffer[i];
			short s = (short) myBuffer[i];
			//String hexB = Integer.toHexString(b);
			String hexS = Integer.toHexString(s);
		   /*
	    	//输出为大写
	    	String hexB = Integer.toHexString(b).toUpperCase();
	    	String hexS = Integer.toHexString(s).toUpperCase();
	    	//print char
		    sb.append("char[");
		    sb.append(i);
		    sb.append("]='");
		    sb.append(myBuffer[i]);
		    sb.append("'\t");

		    //byte value
		    sb.append("byte=");
		    sb.append(b);
		    sb.append(" \\u");
		    sb.append(hexB);
		    sb.append('\t');
		    */

			//short value
			sb.append(" \\u");
			sb.append(fillStr(hexS,"0",4,AFTER));
			//sb.append('\t');
			//Unicode Block
			//sb.append(Character.UnicodeBlock.of(myBuffer[i]));
		}

		return sb.toString();

	}


	/**
	 *判断一个字符串是否是合法的Java标识符
	 * @param s	待判断的字符串
	 * @return	如果参数s是一个合法的Java标识符返回真，否则返回假
	 */
	public static boolean isJavaIdentifier(String s){
		if(s.length()==0 || Character.isJavaIdentifierStart(s.charAt(0))) {
			return false;
		}
		for(int i = 0;i<s.length();i++) {
			if(!Character.isJavaIdentifierPart(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}



	/**
	 *替换字符串中满足条件的字符
	 * 例如:  replaceAll("\com\hi\platform\base\\util",'\\','/');
	 * 返回的结果为: /com/hi/platform/base/util
	 * @param str	待替换的字符串
	 * @param oldchar 待替换的字符
	 * @param newchar 替换为的字符
	 * @return 将str中的所有oldchar字符全部替换为newchar,并返回这个替换后的字符串
	 */
	public static String replaceAll(String str,char oldchar,char newchar){
		char[] chars = str.toCharArray();
		for(int i = 0 ; i<chars.length;i++){
			if(chars[i]==oldchar) {
				chars[i] = newchar;
			}
		}
		return new String(chars);
	}

	/**
	 * 如果inStr字符串与没有给定length的长度，则用fill填充，在指定direction的方向
	 * 如果inStr字符串长度大于length就直截返回inStr，不做处理
	 * @param inStr 待处理的字符串
	 * @param fill 以该字符串作为填充字符串
	 * @param length 填充后要求的长度
	 * @param direction 填充方向，如果是AFTER填充在后面，否则填充在前面
	 * @return 返回一个指定长度填充后的字符串
	 */
	public static String fillStr(String inStr,String fill,int length,int direction){
		if(inStr==null || inStr.length() > length || inStr.length()==0) {
			return inStr;
		}

		StringBuffer tempStr = new StringBuffer("");
		for (int i = 0 ; i<length-inStr.length() ; i++ ){
			tempStr.append(fill);
		}

		if(direction == AFTER) {
			return new String(tempStr.toString()+inStr);
		} else {
			return new String(inStr+tempStr.toString());
		}
	}

	/**
	 * 字符串替换
	 * @param str 源字符串
	 * @param pattern 待替换的字符串
	 * @param replace 替换为的字符串
	 * @return
	 */
	public static String replace(String str, String pattern, String replace){
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while((e = str.indexOf(pattern,s)) >= 0){
			result.append(str.substring(s,e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));

		return result.toString();
	}

	/**
	 * 分隔字符串到一个集合
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> dividToList(String str, String start, String end) {
		if (str == null || str.length() == 0) {
			return null;
		}

		int s = 0;
		int e = 0;
		List<String> result = new ArrayList<String>();
		if (isInclude(str, start) && isInclude(str, end)) {
			while ((e = str.indexOf(start, s)) >= 0) {
				result.add(str.substring(s, e));
				s = str.indexOf(end, e) + end.length();
				result.add(str.substring(e, s));
			}
			if (s < str.length()) {
				result.add(str.substring(s));
			}

			if (s == 0) {
				result.add(str);
			}
		} else {
			result.add(str);
		}

		return result;
	}


	/**
	 * 首字母大写
	 * @param string
	 * @return
	 */
	public static String upperFrist(String string){
		if(string == null) {
			return null;
		}
		String upper = string.toUpperCase();
		return upper.substring(0,1) + string.substring(1);
	}

	/**
	 * 首字母小写
	 * @param string
	 * @return
	 */
	public static String lowerFrist(String string){
		if(string == null) {
			return null;
		}
		String lower = string.toLowerCase();
		return lower.substring(0,1) + string.substring(1);
	}

	public static String URLEncode(String string, String encode){
		try {
			return URLEncoder.encode(string, encode);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
			return null;
		}
	}


	/**
	 * 将一个日期类型的对象，转换为指定格式的字符串
	 * @param date 待转换的日期
	 * @param format 转换为字符串的相应格式
	 * 例如：DateToStr(new Date() ,"yyyy.MM.dd G 'at' hh:mm:ss a zzz");
	 * @return 一个字符串<p>
	 *
	 * <table border="0" cellspacing="3" cellpadding="0">
	 *     <tr bgcolor="#ccccff">
	 *         <th align="left">Letter
	 *         <th align="left">Date or Time Component
	 *         <th align="left">Presentation
	 *         <th align="left">Examples
	 *     <tr>
	 *         <td><code>G</code>
	 *         <td>Era designator
	 *         <td><a href="#text">Text</a>
	 *         <td><code>AD</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td>Year
	 *         <td><a href="#year">Year</a>
	 *         <td><code>1996</code>; <code>96</code>
	 *     <tr>
	 *         <td><code>M</code>
	 *         <td>Month in year
	 *         <td><a href="#month">Month</a>
	 *         <td><code>July</code>; <code>Jul</code>; <code>07</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>w</code>
	 *         <td>Week in year
	 *         <td><a href="#number">Number</a>
	 *         <td><code>27</code>
	 *     <tr>
	 *         <td><code>W</code>
	 *         <td>Week in month
	 *         <td><a href="#number">Number</a>
	 *         <td><code>2</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>D</code>
	 *         <td>Day in year
	 *         <td><a href="#number">Number</a>
	 *         <td><code>189</code>
	 *     <tr>
	 *         <td><code>d</code>
	 *         <td>Day in month
	 *         <td><a href="#number">Number</a>
	 *         <td><code>10</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>F</code>
	 *         <td>Day of week in month
	 *         <td><a href="#number">Number</a>
	 *         <td><code>2</code>
	 *     <tr>
	 *         <td><code>E</code>
	 *         <td>Day in week
	 *         <td><a href="#text">Text</a>
	 *         <td><code>Tuesday</code>; <code>Tue</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>a</code>
	 *         <td>Am/pm marker
	 *         <td><a href="#text">Text</a>
	 *         <td><code>PM</code>
	 *     <tr>
	 *         <td><code>H</code>
	 *         <td>Hour in day (0-23)
	 *         <td><a href="#number">Number</a>
	 *         <td><code>0</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>k</code>
	 *         <td>Hour in day (1-24)
	 *         <td><a href="#number">Number</a>
	 *         <td><code>24</code>
	 *     <tr>
	 *         <td><code>K</code>
	 *         <td>Hour in am/pm (0-11)
	 *         <td><a href="#number">Number</a>
	 *         <td><code>0</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>h</code>
	 *         <td>Hour in am/pm (1-12)
	 *         <td><a href="#number">Number</a>
	 *         <td><code>12</code>
	 *     <tr>
	 *         <td><code>m</code>
	 *         <td>Minute in hour
	 *         <td><a href="#number">Number</a>
	 *         <td><code>30</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>s</code>
	 *         <td>Second in minute
	 *         <td><a href="#number">Number</a>
	 *         <td><code>55</code>
	 *     <tr>
	 *         <td><code>S</code>
	 *         <td>Millisecond
	 *         <td><a href="#number">Number</a>
	 *         <td><code>978</code>
	 *     <tr bgcolor="#eeeeff">
	 *         <td><code>z</code>
	 *         <td>Time zone
	 *         <td><a href="#timezone">General time zone</a>
	 *         <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code>
	 *     <tr>
	 *         <td><code>Z</code>
	 *         <td>Time zone
	 *         <td><a href="#rfc822timezone">RFC 822 time zone</a>
	 *         <td><code>-0800</code>
	 * </table>
	 */
	public static String DateToStr( Date date , String format){
		SimpleDateFormat formatter = new SimpleDateFormat (format);
		return formatter.format(date);
	}

	/**
	 * 对给定的字符串做html转义
	 * @param string
	 * @return
	 */
	public static String escapeHtml(String string) {
		if (string == null || string.length() == 0) {
			return "";
		}

		char         b;
		char         c = 0;
		int          i;
		int          len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);
		String       t;

		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
				case '\\':
				case '"':
					sb.append('\\');
					sb.append(c);
					break;
				case '/':
					if (b == '<') {
						sb.append('\\');
					}
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				default:
					if (c < ' ' || (c >= '\u0080' && c < '\u00a0') ||
							(c >= '\u2000' && c < '\u2100')) {
						t = "000" + Integer.toHexString(c);
						sb.append("\\u" + t.substring(t.length() - 4));
					} else {
						sb.append(c);
					}
			}
		}

		return sb.toString();
	}

	/**
	 * 生成一个指定长度的随机字符串
	 * @param length 返回的字符串长度
	 * @return 返回一个随机
	 */
	public static String randomString(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters  = ("abcdefghijklmnopqrstuvwxyz" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char [] randBuffer = new char[length];
		for (int i=0; i<randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(51)];
		}
		return new String(randBuffer);
	}

	/**
	 * 将字符串的首字符变成大写字符
	 *
	 * @param s
	 * @return
	 */
	public static String firstCharUpper(String s) {
		String result = s.substring(0, 1).toUpperCase() + s.substring(1);
		return result;
	}

	/**
	 * 将字符串的首字符变成小写字符
	 *
	 * @param s
	 * @return
	 */
	public static String firstCharLower(String s) {
		String result = s.substring(0, 1).toLowerCase() + s.substring(1);
		return result;
	}

	/**
	 * 生成指定长度的随机字符串（0--9,A--Z,a--Z）
	 *
	 *
	 *
	 *
	 * @param length
	 * @return
	 */
	public static String genRandom(int length) {
		StringBuffer buffer = new StringBuffer();
		Random r = new Random();
		int i = 0;
		int c;
		while (i < length) {
			c = r.nextInt(122);
			if ((48 <= c && c <= 57) || (65 <= c && c <= 90)
					|| (97 <= c && c <= 122)) {
				buffer.append((char) c);
				i++;
			}
		}
		return buffer.toString();
	}



	/**
	 * 字符串左边补零
	 *
	 *
	 *
	 *
	 * 例：将字符串"ABC"用"0"补足8位，结果为"00000ABC"
	 *
	 * @param orgStr
	 *            原始字符串
	 *
	 *
	 *
	 *
	 * @param fillWith
	 *            用来填充的字符
	 *
	 *
	 *
	 *
	 * @param fixLen
	 *            固定长度
	 * @return
	 */
	public static String fillLeft(String orgStr, char fillWith, int fixLen) {

		return fillStr(orgStr, fillWith, fixLen, true);

	}

	/**
	 * 字符串右边补零
	 *
	 *
	 *
	 *
	 *
	 * @param orgStr
	 * @param fillWith
	 * @param fixLen
	 * @return
	 */
	public static String fillRight(String orgStr, char fillWith, int fixLen) {

		return fillStr(orgStr, fillWith, fixLen, false);

	}

	private static String fillStr(String orgStr, char fillWith, int fixLen,
								  boolean isLeft) {

		int toFill = fixLen - orgStr.length();

		if (toFill <= 0) {
			return orgStr;
		}

		StringBuilder sb = new StringBuilder(orgStr);
		for (; toFill > 0; toFill--) {
			if (isLeft) {
				sb.insert(0, fillWith);
			} else {
				sb.append(fillWith);
			}
		}

		return sb.toString();

	}

	/**
	 * toTrim
	 *
	 * @param str
	 * @return
	 */
	public static String toTrim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static String convertToString(int length, int value) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length - ("" + value).length(); i++) {
			buffer.append("0");
		}
		buffer.append(value);
		return buffer.toString();
	}

	public static String arrayToString(Object[] array, String split) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buffer.append(array[i].toString()).append(split);
		}
		if (buffer.length() != 0) {
			return buffer.substring(0, buffer.length() - split.length());
		} else {
			return "";
		}
	}

	public static String arrayToString(Set set, String split) {
		StringBuffer buffer = new StringBuffer();
		for (Iterator i = set.iterator(); i.hasNext();) {
			buffer.append(i.next().toString()).append(split);
		}
		if (buffer.length() != 0) {
			return buffer.substring(0, buffer.length() - split.length());
		} else {
			return "";
		}
	}

	public static String toHexString(String src){
		byte[] b = src.getBytes();
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < b.length;i++){
			sb.append(Integer.toHexString(0xFF&b[i]));
		}
		return sb.toString();
	}
	public static String hexStringToString(String hex)
	{
		return new String(hexStringToByte(hex));
	}

	/**
	 * 把16进制字符串转换成字节数组
	 *
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		if (hex.length() == 1)
			hex = "0" + hex;
		hex = hex.toUpperCase();

		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 将字符数组转化成HexString字符串
	 * @param arr
	 * @return
	 */
	public static String byteArray2HexString(byte[] arr){
		StringBuilder sbd = new StringBuilder();
		for (byte b : arr) {
			String tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() < 2) {
				tmp = "0" + tmp;
			}
			sbd.append(tmp);
		}
		return sbd.toString();
	}


	// public static String trimLeft(String s,char c){
	// if (s == null) {
	// return "";
	// } else {
	// StringBuffer b = new StringBuffer();
	// char[] cc = s.toCharArray();
	// for (int i=0;i<cc.length;i++){
	// if (cc[i]!=c){
	// break;
	// }
	// }
	// }
	// }

	public static String repNull(Object o) {
		if (o == null) {
			return "";
		} else {
			return o.toString().trim();
		}
	}

	public static String generateInitPwd() {
		return generateRandomString(6);
	}

	public static String generateRandomString(int len) {
		final char[] mm = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };

		StringBuffer sb = new StringBuffer();
		Random random = new Random();

		for (int i = 0; i < len; i++) {
			sb.append(mm[random.nextInt(mm.length)]);
		}
		return sb.toString();

	}
	public static String dateFormat(String source){
		String[] lst = source.split("-");
		StringBuffer sb = new StringBuffer();
		if(lst.length > 1){
			for(String item:lst){
				sb.append(item);
			}
			return sb.toString();
		}else {
			return source;
		}
	}
	/**
	 * @param orderdate
	 * @return
	 */
	public static String formatDate(String orderdate) {
		if(orderdate == null) {
			return "";
		}
		if(orderdate.length() == 8)	{
			return orderdate.substring(0,4)+"-"+orderdate.substring(4,6)+"-"+orderdate.substring(6);
		}else if(orderdate.length() == 6){
			return orderdate.substring(0,4)+"-"+orderdate.substring(4,6);
		}
		return orderdate;
	}
	/**
	 * 产生紧凑型32位UUID
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}

	public static boolean isEmpty(String... str){
		for (int i = 0; i < str.length; i++) {
			if (isEmpty(str[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 类似oracle的nvl
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static String nvl(String str, String defValue){
		if(isEmpty(str)){
			return defValue;
		}else{
			return str;
		}
	}

	/**
	 * 从str中截取指定开始、结束位置的子字符
	 * @param str
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static String getSubstring(String str,int startPos,int endPos){
		String ret = null;
		if (str!=null&&str.length()>=endPos){
			ret = str.substring(startPos-1,endPos-startPos+1);
		}
		return ret;
	}

	/**
	 * 根据正则提取字符串
	 * example:.*?(?=\\()
	 */
	public static String regMatch(String str, String reg, String def){
		Pattern regex = Pattern.compile(reg);
		Matcher regexMatcher = regex.matcher(str);
		if (regexMatcher.find()) {
			String resultString = regexMatcher.group();
			return resultString;
		}
		return def;
	}


	/**
	 * 获取异常的堆栈信息
	 *
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

	/**
	 *
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String replaceToXml(String source) throws Exception{
		if(isBlank(source)) {
			return source;
		}
		return source.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
	}

	/**
	 * 转换null为空字符串
	 * @param source  源字符串
	 * @return
	 */
	public static final String convertNullToEmpty(String source){
		try {
			return source==null?"":source.trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public static final String concat(String source1,String source2){
		return (isBlank(source1)?"":source1) + (isBlank(source2)?"":source2);
	}

	public static long getValidity(int expires){

		long SystemTime = System.currentTimeMillis();
		Date d = new Date(SystemTime);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MINUTE, expires / 60);

		return c.getTimeInMillis();
	}

	public static boolean isNotExpired(Object ticket, Object ValidityTime) {

		if (ticket != null && ValidityTime != null) {
			long currentTime = System.currentTimeMillis();
			long isValidity = Long.valueOf(String.valueOf(ValidityTime)).longValue() - currentTime;
			if (isValidity > 100) {
				return true;
			}
		}
		return false;
	}

	public static String makeToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] =  md.digest(token.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getDataPath ()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowtime = sdf.format(new Date());
		String[] dateStr = nowtime.split("-");
		String dateuri = dateStr[0]+"/"+Integer.parseInt(dateStr[1])+"/"+ Integer.parseInt(dateStr[2]);

		return "/" + dateuri+"/";
	}

	public static void  main(String[] args){
		System.out.println(concat(null, null));
	}


}