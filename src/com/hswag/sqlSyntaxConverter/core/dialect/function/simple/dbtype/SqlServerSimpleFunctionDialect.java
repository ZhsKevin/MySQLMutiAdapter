package com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.BaseSimpleFunctionDialect;

public class SqlServerSimpleFunctionDialect extends BaseSimpleFunctionDialect {
    @Override
    public String handle_NOW(String sql) {
        sql = keyReplace(sql,"NOW(","getdate(");
        return sql;
    }

    @Override
    public String handle_SUBSTRING(String sql) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_SUBSTR(String sql) {
        sql = keyReplace(sql,"SUBSTR(","SUBSTRING(");
        return sql;
    }

    @Override
    public String handle_LENGTH(String sql) {
        sql = keyReplace(sql,"LENGTH(","DataLength(");
        return sql;
    }

    @Override
    public String handle_CHAR_LENGTH(String sql) {
        sql = keyReplace(sql,"CHAR_LENGTH(","Len(");
        return sql;
    }

    @Override
    public String handle_UUID(String sql) {
        sql = keyReplace(sql,"UUID()","newid()");
        return sql;
    }

    @Override
    public String handle_IFNULL(String sql) {
        sql = keyReplace(sql,"IFNULL(","isnull(");
        return sql;
    }

    @Override
    public String handle_DATABASE(String sql) {
        sql = keyReplace(sql,"database()","DB_NAME()");
        return sql;
    }

    @Override
    public String handle_CURDATE(String sql) {
        sql = keyReplace(sql,"CURDATE()","CONVERT(date, GETDATE(), 120)");
        return sql;
    }

    @Override
    public String handle_CURTIME(String sql) {
        sql = keyReplace(sql,"CURTIME()","CONVERT(varchar, GETDATE(), 108)");
        return sql;
    }
}
