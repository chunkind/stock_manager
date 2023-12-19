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
	private String apiClientType;
	private String apiKey;
	private String apiNonce;
	private String apiSign;
	
	private String currency;
}
