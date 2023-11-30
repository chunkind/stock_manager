package com.dev.ck.ackwd.config.core.environment;

import lombok.Data;

@Data
public class RequestTransactionContextHolder {
	static ThreadLocal<String> value = new ThreadLocal<String>();

	public static String getRequestTxId() {
		return value.get();
	}

	public static void setRequestTxId(String txId) {
		value.set(txId);
	}
}
