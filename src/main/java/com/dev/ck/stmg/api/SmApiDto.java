package com.dev.ck.stmg.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmApiDto {
	//headers
	private String apiClientType;
	private String apiKey;
	private String apiNonce;
	private String apiSign;
	
	//request params
	private String currency;
	
	//response
	private String status;
}
