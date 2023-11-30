package com.dev.ck.ackwd.utils.aes;

public class AES256 {
	static final String decKey = "yournk.aa.aabb01";	//16자리
	static String certKey = "junseong.js.chun"; //16자리
	static AES256Util aes256 = null;
	
	static {
		try {
			certKey = new AES256Util(decKey).aesEncode(certKey);
			//암호화 객체 생성.
			aes256 = new AES256Util(certKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encode(String text) {
		String result = "";
		try {
			result = aes256.aesEncode(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String decode(String text) {
		String result = "";
		try {
			result = aes256.aesDecode(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(encode("202208290001"));
	}
}