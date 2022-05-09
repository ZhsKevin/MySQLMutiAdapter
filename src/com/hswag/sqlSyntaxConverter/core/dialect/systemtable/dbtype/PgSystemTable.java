package com.hswag.sqlSyntaxConverter.core.dialect.systemtable.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.SystemTableHandler;

public class PgSystemTable extends SystemTableHandler {
    @Override
    public String getSystemTableSqlInner(String sql, String sql_orignal) {
        return sql;
    }
}
