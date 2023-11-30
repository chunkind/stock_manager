package com.dev.ck.ackwd.utils.helper.connector.impl;

import java.util.List;

import com.dev.ck.ackwd.utils.helper.connector.Connector;
import com.dev.ck.ackwd.utils.helper.model.TargetTableInfo;
import com.dev.ck.ackwd.utils.helper.util.DevHelperUtil;

public class MariaDbConnector extends Connector{

	public MariaDbConnector(String driver, String url, String user, String pass) {
		super(driver, url, user, pass);
	}

	@Override
	public String createTableDesc() {
		String sql = "";
		sql += " SELECT A.TABLE_SCHEMA AS tableOwner";
		sql +=	  ", A.TABLE_NAME AS tableName";
		sql +=	  ", A.COLUMN_TYPE AS columnType";
		sql +=	  ", A.COLUMN_NAME AS columnName";
		sql +=	  ", A.COLUMN_COMMENT AS columnComments";
		sql += " FROM information_schema.columns A";
		sql += " WHERE 1=1";
		sql += " AND A.TABLE_NAME = ?";
		sql += " ORDER BY ORDINAL_POSITION ASC";
		return sql;
	}

	@Override
	public String[] createSqlSelect(String t) {
		String tableName = t;
		String selectQuery = "SELECT ";
		List<TargetTableInfo> list = super.getTableInfo(tableName);
		int colSize = list.size();
		for(int i=0; i<list.size(); i++) {
			String colNm = list.get(i).getColumnName();
			String camelColNm = DevHelperUtil.toCamelCase(colNm);
			if(i == 0) {
				selectQuery += " " + colNm + " AS " + camelColNm +"\n";
			}else {
				selectQuery += "    , " + colNm + " AS " + camelColNm +"\n";
			}
		}
		selectQuery += "  FROM " + tableName;
		return new String[]{selectQuery, String.valueOf(colSize)};
	}

}
