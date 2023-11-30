package com.dev.ck.ackwd.config.persistent.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class YesNoBooleanTypeHandler implements TypeHandler<Boolean> {
	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter == null ? null : parameter.booleanValue() ? "Y" : "N");
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		if (value == null) {
			return null;
		}
		return "Y".equals(value);
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		if (value == null) {
			return null;
		}
		return "Y".equals(value);
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		if (value == null) {
			return null;
		}
		return "Y".equals(value);
	}
}
