package com.dev.ck.ackwd.config.noo.pagination.dialect.db;

import com.dev.ck.ackwd.config.noo.pagination.dialect.Dialect;

public class DB2Dialect implements Dialect {
	@Override
	public boolean supportsLimit() {
		return true;
	}

	private static String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50)
				.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().contains("select distinct");
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100)
				.append(sql.substring(0, startOfSelect)) //add the comment
				.append("select * from ( select ") //nest the main query in an outer select
				.append(getRowNumber(sql)); //add the rownnumber bit into the outer query select list

		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ") //add another (inner) nested select
					.append(sql.substring(startOfSelect)) //add the main query
					.append(" ) as row_"); //close off the inner nested select
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6)); //add the main query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		//add the restriction to the outer select
		if (offset > 0) {
//			int end = offset + limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append("between ").append(offsetPlaceholder)
					.append("+1 and ").append(endString);
		} else {
			pagingSelect.append("<= ").append(limitPlaceholder);
		}

		return pagingSelect.toString();
	}
}
