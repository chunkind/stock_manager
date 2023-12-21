package com.dev.ck.ackwd.utils.connector;

import java.util.HashMap;

public class Main {
	public static void main(String args[]) {
		Api_Client api = new Api_Client("d05283d80dbcbc4ce799eb58ce36f722", "03c43223f6b59ceca21d5d1114605ad1");
		
		//계좌정보
//		String url = "/info/balance";
//		HashMap<String, String> rgParams = new HashMap<String, String>();
//		rgParams.put("order_currency", "BTC");
//		rgParams.put("payment_currency", "KRW");
		
		//거래내역
//		String url = "/info/orders";
//		HashMap<String, String> rgParams = new HashMap<String, String>();
//		rgParams.put("order_currency", "USDT"); //주문 통화 (가상자산) 달라, USDT, BTC, KRW
//		rgParams.put("payment_currency", "KRW"); //결제 통화 (마켓) 입력값: KRW / BTC
//		rgParams.put("after", "2312110000000"); //23-12-20 00:00:000
//		rgParams.put("order_id", "");
//		rgParams.put("type", ""); //bid:매수, ask:매도
//		rgParams.put("count", ""); //1~1000 default : 100
		
		//매수
//		String url = "/trade/place";
//		HashMap<String, String> rgParams = new HashMap<String, String>();
//		rgParams.put("order_currency", "USDT"); //주문 통화 (가상자산) 달라, USDT, BTC, KRW
//		rgParams.put("payment_currency", "KRW"); //결제 통화 (마켓) 입력값: KRW / BTC
//		rgParams.put("units", "1"); //주문수량
//		rgParams.put("price", "900"); //Currency 거래가
//		rgParams.put("type", "bid"); //bid:매수, ask:매도
		
		//주문취소
		String url = "/trade/cancel";
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("type", "bid"); //bid:매수, ask:매도
		rgParams.put("order_id", "C0866000000000591497");
		rgParams.put("order_currency", "USDT"); //주문 통화 (가상자산) 달라, USDT, BTC, KRW
		rgParams.put("payment_currency", "KRW"); //결제 통화 (마켓) 입력값: KRW / BTC
	
		try {
			String result = api.callApi(url, rgParams);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}