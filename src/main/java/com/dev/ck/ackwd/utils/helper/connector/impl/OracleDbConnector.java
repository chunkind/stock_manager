package com.dev.ck.ackwd.utils.helper.connector.impl;

import com.dev.ck.ackwd.utils.helper.connector.Connector;


public class OracleDbConnector extends Connector{

	public OracleDbConnector(String driver, String url, String user, String pass) {
		super(driver, url, user, pass);
	}

	@Override
	public String createTableDesc(){
		String sql = "";
		sql += " SELECT TAB.OWNER AS tableOwner";
		sql +=	  ", TAB.TABLE_NAME AS tableName";
		sql +=	  ", TAB.DATA_TYPE AS columnType";
		sql +=	  ", TAB.COLUMN_NAME AS columnName";
		sql +=	  ", COL.COMMENTS AS columnComments";
		sql += " FROM ALL_TAB_COLUMNS TAB";
		sql += " INNER JOIN ALL_COL_COMMENTS COL ON TAB.OWNER = COL.OWNER AND TAB.TABLE_NAME = COL.TABLE_NAME AND TAB.COLUMN_NAME = COL.COLUMN_NAME";
		sql += " WHERE TAB.TABLE_NAME = ?";
		sql += " ORDER BY COLUMN_ID ASC";
		return sql;
	}

	@Override
	public String[] createSqlSelect(String tableName) {
		return null;
	}
	
}
