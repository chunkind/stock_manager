package com.dev.ck.ackwd.utils.connector;

import java.util.HashMap;

public class Main {
	public static void main(String args[]) {
		Api_Client api = new Api_Client("d05283d80dbcbc4ce799eb58ce36f722", "03c43223f6b59ceca21d5d1114605ad1");
	
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("order_currency", "BTC");
		rgParams.put("payment_currency", "KRW");
	
		try {
			String result = api.callApi("/info/balance", rgParams);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}