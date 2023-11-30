package com.dev.ck.ackwd.utils.helper.model;

import lombok.Data;

@Data
public class TargetTableInfo{
	private String tableOwner;
	private String tableName;
	private String columnType;
	private String columnName;
	private String columnComments;
}