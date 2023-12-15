package com.dev.ck.ackwd.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * @Auth : K. J. S.
 * @Date : 2019. 3. 5.
 */
public class StringUtil {
	
	private static String emptyStr = "";
	
	/**
	 * 문자열이 비어있는지 검사하는 로직
	 */
	public static boolean isEmpty(String text) {
		boolean result = false;
		
		if(null == text) {
			result = true;
		}
		if("".equals(text)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	* @Param String pData : 원본 데이터
	* @Param int length : 패딩할 길이
	* @Param char fillChar : 패딩시 적용할 문자
	* 
	* @return 원본문자열에서 길이만큼 왼쪽기준하여 패딩 문자 먼저 채우고 리턴한다. 
	 * @throws Exception 
	*/	
	public static String lPad(final String pData, final int length) throws RuntimeException {
		return lPad(pData,length,' ');
	}
	public static String lPad(final String pData, final int length, final char fillChar) throws RuntimeException {
		return padString(pData,1,length, fillChar);
	}
	
	
	/**
	* @Param String pData : 원본 데이터
	* @Param int length : 패딩할 길이
	* @Param char fillChar : 패딩시 적용할 문자
	* 
	* @return 원본문자열에서 길이만큼 오른쪽기준하여 패딩 문자 먼저 채우고 리턴한다. 
	 * @throws Exception 
	*/	
	public static String rPad(final String pData, final int length) throws RuntimeException {
		return rPad(pData, length,' ');
	}
	public static String rPad(final String pData, final int length,final char fillChar) throws RuntimeException {
		return padString(pData, 0, length, fillChar);
	}
	
	private static String padString(final String data, final int align, final int fillSize, final char fillChar) throws RuntimeException {
		StringBuffer rtnBuf;
		byte[] bytes;
		String result;
		
		try {
			result = (data == null ? emptyStr : data);
			rtnBuf = new StringBuffer();
			
			bytes = result.trim().getBytes("EUC-KR");
			final int len = bytes.length;
	
			// 모자라는 길이만큼 채울 문자열을 만든다.
			if (len < fillSize)  {	
				if (align == 0) {
					rtnBuf.append(result);
					for (int i = len; i < fillSize; i++) { rtnBuf.append(fillChar); }
					
				} else {
					for (int i = len; i < fillSize; i++) { rtnBuf.append(fillChar); }
					rtnBuf.append(result);
				}
				
			// 원하는 길이보다 크면 잘라 보낸다.
			} else {	
				if (align == 0) { rtnBuf.append(new String(bytes, 0, fillSize)); }
				else { rtnBuf.append(new String(bytes, len - fillSize, fillSize)); }
			}
			result = rtnBuf.toString();
			return result;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
	}

	/**
	* @Param String fomat : 날짜포멧
	* 
	* @return 파라미터로 받은 날짜포멧을 적용하여 현재 날짜를 구하고 리턴한다. 
	 * @throws Exception 
	*/		
	public static String getCurDate(final String fomat) throws RuntimeException {
		SimpleDateFormat dateFormatter;
		
		try {
			dateFormatter = new SimpleDateFormat(fomat);
			
			return  dateFormatter.format(new Date());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	* @Param String strAny : 원본 데이터
	* 
	* @return 파라미터로 받은 String 널체크 후 리턴한다.
	 * @throws Exception 
	*/		 
	public static String stringCheck(final String strAny) throws RuntimeException {
		
		return strAny == null ? emptyStr : strAny;
	}
	
	/**
	* @Param String strAny : 원본 데이터
	* @Param String arg : Null 일경우 리턴할 문자열
	* 
	* @return 파라미터로 받은 String 널체크 후 리턴한다.
	 * @throws Exception 
	*/			
	public static String stringCheck(final String strAny, final String arg) throws RuntimeException {
		return strAny == null ? arg : strAny;
	}

	/**
	* @Param String src : 원본 문자열
	* @Param String oldStr : 바꾸고 싶은 문자열
	* @Param String newStr : 새로운 문자열로 바꿀 문자열
	* 
	* @return 원본 문자열에서 특정 문자열을 새로운 문자열로  치환 후 리턴한다.
	 * @throws Exception 
	*/		
	public static String replaceStr(final String src, final String oldStr, final String newStr) throws RuntimeException {
		try {
			StringBuffer strBuf;
			String result;
			int idx = 0;
			int curIdx;
			
			strBuf = new StringBuffer();
			curIdx = src.indexOf(oldStr, idx);
			
			while (curIdx >= 0) {
				strBuf.append(src.substring(idx, curIdx));
				strBuf.append(newStr);
				
				idx = curIdx + oldStr.length();
				curIdx = src.indexOf(oldStr, idx);
			}
	
			if (idx <= src.length()) {
				strBuf.append(src.substring(idx, src.length()));
			}
			result = strBuf.toString();
			
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		} 
	}

	/**
	* @Param String date : yyyymmdd 형식의 날짜 데이터
	* @Param String fomat : delim 문자
	* 
	* @return yyyymmdd 형식의 날짜 데이터 길이가 8보다 작을 날짜 yy-mm-dd  형식으로 8자인 경우는 yyyy-mm-dd 형식으로 변환 후 리턴한다.
	 * @throws Exception 
	*/
	public static String getConvertDate(final String date, final String fomat) throws RuntimeException {
		try {
			StringBuffer strBuf;
			String result;
			result = (date == null ? emptyStr : date.trim());
			
			if (!emptyStr.equals(result)) {
				strBuf = new StringBuffer();
				
				if (result.length() < 8) {
					result = strBuf.append(result.substring(0,2)).append(fomat).append(result.substring(2,4)).append(fomat).append(result.substring(4,6)).toString();
					
				} else {
					result = strBuf.append(result.substring(0,4)).append(fomat).append(result.substring(4,6)).append(fomat).append(result.substring(6,8)).toString();
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
	}

	/**
	* @Param String date : yyyymmddhhmmss 형식의 날짜
	* @Param String fomat1 : delim 문자1
	* @Param String fomat2 : delim 문자2
	* 
	* @return yyyymmddhhmmss 형식의 날짜를 yyyy-mm-dd hh:mm:ss 형식으로 변환. format1, 2는 사용자에 맡게 변경할 수 있음.
	 * @throws Exception 
	*/	
	public static String getConvertFullDateTime(final String date, final String fomat1, final String fomat2) throws RuntimeException {
		
		try {
			StringBuffer strBuf; 
			StringBuffer resultBuf; 
			String result;
			result = (date == null ? emptyStr : date.trim());
			
			if (!"".equals(result)) {
				resultBuf = new StringBuffer();
				resultBuf.append(result);
				if (resultBuf.length() == 14) {
					strBuf = new StringBuffer();
					strBuf.append(result.substring(0,4)).append(fomat1).append(result.substring(4,6)).append(fomat1).append(result.substring(6,8));
					strBuf.append(" *");
					strBuf.toString().replace("*", "");
					strBuf.append(result.substring(8,10)).append(fomat2).append(result.substring(10,12)).append(fomat2).append(result.substring(12,14));
					
					result = strBuf.toString();
				}
			}
			return	result;
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
	}	
	
	/**
	* @Param str 문자열
	* @Param token token문자열
	* 
	* @return 문자열을 token으로 잘라서 배열에 담아 리턴한다.
	 * @throws Exception 
	*/
	public static String[] getStringToken(final String str, final String token) throws RuntimeException {
		
		try {
			StringTokenizer stToken;
			List<String> result;
			String[] resultArrValue;
			
			if (str.length() > 0) {
				stToken = new StringTokenizer(str, token);
				result = new ArrayList<String>();
				
				while(stToken.hasMoreTokens()) {
					result.add( stToken.nextToken() );
				}
			} else {
				result = new ArrayList<String>();
			}
			
			resultArrValue = result.toArray(new String[result.size()]);
			
			return resultArrValue;
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		} 
	}

	/**
	* @Param String date : yyyymmddhhmmss 형식의 날짜
	* 
	* @return Unix timestamp 를 구한다.
	*/		
	public static long getUnixTimeStamp(final String date) throws ParseException {
		final String result = (date == null ? "0" : date);
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",Locale.KOREA);

		return	(long) format.parse(result).getTime() / 1000;
	}
	
	
	/**
	* 바이트 체크  
	*   @param code: 입력 코드
	*   @return
	 */
	public static boolean codeCheck(final String code) {
		final char[] codes = code.toCharArray();
		
		int sum = 0;
		final int length = code.length();
		int mod;
		
		//끝 자리를 뺀 모든 수 더하기
		for(int i=0; i < length -1; i++) {
			sum += (codes[i] -48);
		}
		
		//바코드 길이가 11보다 크면 11로 나누고 11보다 작으면 바코드 길이로 나누다
		if(code.length() > 11) {
			mod = sum % 11 == 10 ? 0 : (sum % 11);
		} else {
			mod = sum % length;
		}
		
		//마지막 바코드값이 mod 값과 같을 경우 유효한 바코드
		return mod == codes[length -1] -48 ? true : false;
	}
	
	/**
	 * 코드 확인 값 생성 
	*   @param code: 원본 코드
	*   @return
	 */
	public static int calcuateMod(final String code) throws RuntimeException {
		try {
			
			int sum = 0;
			final int length = code.length();
			int mod;
			
			//끝 자리를 뺀 모든 수 더하기
			for(int i=0; i < length; i++) {
				sum += (code.toCharArray()[i] -48);
			}
			
			//바코드 길이가 11보다 크면 11로 나누고 11보다 작으면 바코드 길이로 나누다
			if(code.length() > 11) {
				mod = sum % 11;
			} else {
				mod = sum % length;
			}
			
			return mod == 10 ? 0 : mod;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 유효 바코드 생성
	*   @param barcode 원본 바코드
	*   @return 유효 바코드
	 */
	public static String addMod(final String barcode) throws RuntimeException {
		return new StringBuffer()
						.append(barcode)
						.append(calcuateMod(barcode))
						.toString();
	}
	
	
	public static long getNextByRandom() {
		final SecureRandom random = new SecureRandom();
		final long temp = random.nextLong();
		return temp < 0 ? temp * -1 : temp;
	}
	
	/**
	 * 날짜바탕의 숫자 랜덤 코드 생성
	*   @param size: 코드 길이
	*   @return 랜덤 코드
	 */
	public static String makeRandomCodeByDate(final int size) {
		final StringBuffer strBuf = new StringBuffer();
		
		strBuf.append(new SimpleDateFormat("yyyyMMdd",Locale.KOREA)
						.format(new Date())
					);
		
		while(strBuf.length() < size) {
			strBuf.append(getNextByRandom());
		}
		return strBuf.toString().substring(0,size);
	}
	
	/**
	 * @ stringFormater : 입력된 문자열에 특정 사이즈마다 원하는 문자열을 추가한다.
	 * @param - str : 입력문자열
	 * @param cutSize : cutSize 값마다 appendChar를 추가한다.
	 * @param appendChar : 추가할 문자열 
	 * @throws RuntimeException
	 * */
	public static String stringFormater(final String str,final int cutSize, final String appendChar) throws RuntimeException{
		int fullLength;
		IntVO plusByte;
		IntVO dashLen;
		IntVO cut_size;
		int forLoof;
		StringBuffer stf;

		try {
			
			fullLength = str.length();
			plusByte = new IntVO(0);
			plusByte.set(0);
			dashLen = new IntVO(cutSize);
			dashLen.set(cutSize);
			cut_size = new IntVO(cutSize);
			cut_size.set(cutSize);
			forLoof = fullLength/cut_size.get();
			stf = new StringBuffer();
			
			for(int i=0; i<forLoof; i++){

				if(i > 0){
					plusByte.add(dashLen.get());
					cut_size.add(dashLen.get());
//					plusByte += dashLen;
//					cut_size += dashLen;
				}
				
				stf.append(str.substring(plusByte.get(), cut_size.get())).append(appendChar);

				if(cut_size.get() > fullLength){
					break;
				}
			}
			
			stf.append(str.substring(cut_size.get(), fullLength));
			if(fullLength%cut_size.get() == 0){
				stf.deleteCharAt(stf.length()-1);
			}
			
			return stf.toString();
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		} 
	}
	
	public static void arrayCopy(final byte[] target, final int position, final byte[] bytes, final int offset, final int length) {
		System.arraycopy(target, position, bytes, offset, length);
	} 
	
	/**
	 * @ 오늘의 요일 이름을 구한다.

	 * @throws RuntimeException
	 * 
	 */
	public static String getCurDayName() throws RuntimeException {
									 // 1     2     3     4     5     6     7
		final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		return week[Calendar.getInstance( ).get(Calendar.DAY_OF_WEEK) - 1]; 
	}
	
	
	/**
	 * 하나의 입력창에서 전화번호를 받아 3자리 배열로 변환
	 */
	public static String[] splitTelNo(String telNo) {

		String[] telNoArr = new String[] {"000", "0000", "0000"};

		if(telNo != null && !"".equals(telNo) && telNo.matches("[\\d-]*")) {

			// 1. - 로 구분되어 있으면 지역번호(핸드폰 구분번호) 포함된 경우
			if(telNo.matches("\\d{2,3}-\\d{3,4}-\\d{4}")) {
				String[] tmpTelNoArr = telNo.split("-");
				System.arraycopy(tmpTelNoArr, 0, telNoArr, 0, tmpTelNoArr.length);
			}
			// 2. - 로 구분되어 있으며 지역번호(핸드폰 구분번호) 미포함
			else if(telNo.matches("\\d{3,4}-\\d{4}")) {
				String[] tmpTelNoArr = telNo.split("-");
				telNoArr[1] = tmpTelNoArr[0];
				telNoArr[2] = tmpTelNoArr[1];
			}
			// 3. 구분자 미포함, 지역번호(핸드폰 구분번호) 포함
			else if(telNo.length() == 11 ||
					telNo.length() == 10 ||
					telNo.length() == 9) {

				// 11자리 일 경우
				if(telNo.startsWith("02")) { // 서울 지역번호 일 경우
					telNoArr[0] = telNo.substring(0, 2);
					telNoArr[1] = telNo.substring(2, telNo.length() - 4);
				}
				else {
					telNoArr[0] = telNo.substring(0, 3);
					telNoArr[1] = telNo.substring(3, telNo.length() - 4);
				}
				telNoArr[2] = telNo.substring(telNo.length() - 4);
			}
			// 4. 구분자 미포함, 지역번호(핸드폰 구분번호) 미포함
			else if(telNo.length() == 8 ||
					telNo.length() == 7) {
				telNoArr[1] = telNo.substring(0, telNo.length() - 4);
				telNoArr[2] = telNo.substring(telNo.length() - 4);
			}
		}
		return telNoArr;
	}

	/**
	 * 전화번호를 입력 받아 구분자를 제외한 배열로 돌려준다. 
	 * @param noStr
	 * @return
	 */
	public static String[] getSplitTellNo(String noStr) {
		Pattern tellPattern = Pattern
				.compile("^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");

		if (noStr == null)
			return new String[] {"000", "0000", "0000"};

		Matcher matcher = tellPattern.matcher(noStr);

		if (matcher.matches()) {
			return new String[] { matcher.group(1), matcher.group(2),
					matcher.group(3) };
		} else {
			return new String[] {"000", "0000", "0000"};
		}
	}
	
	/**
	 * MAP을 JSON으로 변환할때 NULL VALUE 공백 스트링으로 변환
	 *
	 * usage : TextUtil.fixMapForJson(map);
	 */
	public static void fixMapForJson(Map<String, Object> map) {

		Set<String> mapSet = map.keySet();

		for(String key : mapSet) {
			if(map.get(key) == null) {
				map.put(key, "");
			}
		}
	}

	/**
	 * 숫자를 제외한 나머지 제거
	 *
	 * usage : TextUtil.removeNoDigit(string);
	 */
	public static String removeNoDigit(String text) {
		return text == null ? null : text.replaceAll("[^\\d]", "");
	}

	/**
	 * 사업자등록번호
	 *
	 * usage : 1234567890 -> 123-45-67890
	 */
	public static String toBizNo(String str)
	{
		String temp;

		if( str == null || str.length() !=10 ) {
			return str;
		}else {
			temp = str.substring(0,3)+"-"+str.substring(3,5)+"-"+str.substring(5,10);
			return temp;
		}
	}

	/**
	 * 일자 칼럼(8자리) 을 yyyy-MM-dd 포맷으로 변경
	 * @return
	 */
	public static String toDateFormat(String str, String format) {
		String temp;

		if ( str == null || str.length() != 8 ) {
			return str;
		} else {
			if ( format == null || "".equals(format) ) {
				format = "-";
			}

			temp = str.substring(0,4) + format + str.substring(4,6) + format + str.substring(6,8);
			return temp;
		}
	}

	/**
	 * null 을 공백으로 리턴
	 */
	public static String toEmpty(String str) {
		if ( str == null ) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 의미없는 0 을 공백으로 리턴
	 */
	public static String toEmpty(long str) {
		if ( str == 0 ) {
			return "";
		} else {
			return Long.toString(str);
		}
	}

	/**
	 * ArrayList<Map>을 Json으로 변환
	 */
	public static JSONObject toJson(List<Map> list){

		JSONObject rstObj = new JSONObject();
		JSONArray  tmplArr = new JSONArray();
		
		try{
		    for(int i = 0 ; i < list.size(); i++){
		    	JSONObject tmplObj = new JSONObject();
		
				  Iterator iter = ((HashMap)list.get(i)).entrySet().iterator();
		            while (iter.hasNext()) {
		                Map.Entry entry = (Map.Entry)iter.next();
		                tmplObj.put(entry.getKey().toString().toLowerCase(), entry.getValue() );
		            }
		
				tmplArr.add(tmplObj);
		    }
		}catch(Exception e){
			// [2012.0829 정영훈] PMD RULEST 위반사항 정리 2차
			//e.getStackTrace();
		}
		
		rstObj.put("rst", tmplArr);
		
		return rstObj;
	}

	/**
	 * 주문번호 포멧 : ####-##-##-#######
	 */
	public static String toOrdNoFormat(String ord_no) {
		String strOrdNo = toEmpty(String.valueOf(ord_no));

		if( "".equals(strOrdNo) || strOrdNo.length() < 15){
			return strOrdNo;
		}else{
			return strOrdNo.substring(0,4) + "-" +
			   strOrdNo.substring(4,6) + "-" +
			   strOrdNo.substring(6,8) + "-" +
			   strOrdNo.substring(8);
		}
	}
	
	/**
	 * null 을 "0" 으로 리턴
	 */
	public static String nulltoZero(String str) {
		if ( str == null ) {
			return "0";
		} else {
			return str;
		}
	}
	
	/**
	 * long 을 String의 화폐단위로 리턴
	 */
	public static String longtoCurrency (long lng) {
		String str = "";
		String cur = "";
		
		if (lng < 0) {
			cur = "-";
			lng = lng * -1;
		}
		
		str = lng + "";
		int j = str.length();
		
		for (int i = 0; i < str.length(); i++) {
			cur += str.charAt(i);
			if (j > 3 && j%3 == 1) {
				cur += ",";
			}
			j--;
		}
		
		return cur; 
	}
	
	
	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 " ..."을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다.
	 * @param msg 원본 String
	 * @param cut_size String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @return String 변경된 내용
	 */	
	public static final String getByteLimit(String msg, int cut_size) {
		if ( cut_size < 0 ) return msg;
		if ( cut_size == 0 ) cut_size = 20;
		
		if ( msg == null || msg.length() == 0 ) return "";
			
		int strLength = 0;
		int byteLength = 0;
		char c;
		StringBuffer str = new StringBuffer();
		while ( strLength < msg.length() ) {
			if ( byteLength < cut_size ) 
				str.append(msg.substring(strLength,strLength+1));
			
			// pick a character
			c = msg.charAt(strLength);	
			strLength++;
			byteLength++;
			
			// check double byte character
			if ( c > 127 ) byteLength++; 	
		}
		if ( byteLength > cut_size ) msg= str.toString()+"  ...";
		return msg;
	}
	
	/**
	 * 파일 확장자를 반환. 
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null) return null;
		
		String[] fileSplits = fileName.split("\\.");
		
		if (fileSplits.length > 0)
			return fileSplits[fileSplits.length-1].toLowerCase();
		else 
			return "";
	}
	
	
	/*
	public void main(String[] args) throws Exception {
		//String str = "test";
		//System.out.println(StringUtil.rPad(str, 6, '0'));
		
		System.out.println(org.springframework.core.SpringVersion.getVersion());
	}
	*/
	
	/**
	 * 문자열에서 ASCII 이외 문자를 3byte 계산해서 공밸을 붙여 돌려준다.
	 * 
	 * @param str
	 * @param nSize
	 * @return
	 * @throws Exception
	 */
	public static String rightPadSpace(String str, int nLen) throws Exception {

		if ( str == null ) return StringUtils.rightPad("", nLen, ' ');

		char[] cString = str.toCharArray();
		int nDef = 0;
		int nAdd = 0;
		for ( char c : cString ) {
			nDef++;
			if ( c > 0x7f ) {
				nAdd += 2;
			}
			if ( nDef + nAdd == nLen ) {
				cString = Arrays.copyOf(cString, nDef);
				break;
			} else if ( nDef + nAdd > nLen ) {
				if ( cString[nDef - 1] > 0x7f ) {
					cString = Arrays.copyOf(cString, nDef - 1);
					nAdd -= 2;
				} else {
					cString = Arrays.copyOf(cString, nDef);
				}
				break;
			}
		}

		return StringUtils.rightPad(String.valueOf(cString), nLen - nAdd, ' ');
	}
	
	/**
	 * 문자열에서 ASCII 이외 문자를 2byte 계산해서 공밸을 붙여 돌려준다.
	 * 
	 * @param str
	 * @param nSize
	 * @return
	 * @throws Exception
	 */
	public static String rightPadSpace2(String str, int nLen) throws Exception {

		if ( str == null ) return StringUtils.rightPad("", nLen, ' ');

		char[] cString = str.toCharArray();
		int nDef = 0;
		int nAdd = 0;
		for ( char c : cString ) {
			nDef++;
			if ( c > 0x7f ) {
				nAdd += 1;
			}
			if ( nDef + nAdd == nLen ) {
				cString = Arrays.copyOf(cString, nDef);
				break;
			} else if ( nDef + nAdd > nLen ) {
				if ( cString[nDef - 1] > 0x7f ) {
					cString = Arrays.copyOf(cString, nDef - 1);
					nAdd -= 1;
				} else {
					cString = Arrays.copyOf(cString, nDef);
				}
				break;
			}
		}

		return StringUtils.rightPad(String.valueOf(cString), nLen - nAdd, ' ');
	}
	
}

class IntVO {
	private int value;

	public IntVO(int value) {
		this.value = value;
	}
	
	public void add(int value) {
		this.value += value;
	}
	
	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}
	
}