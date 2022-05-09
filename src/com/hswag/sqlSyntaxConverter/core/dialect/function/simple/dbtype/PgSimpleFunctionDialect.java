package com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.BaseSimpleFunctionDialect;

public class PgSimpleFunctionDialect extends BaseSimpleFunctionDialect {
    @Override
    public String handle_NOW(String sql) {
        sql = keyReplace(sql,"NOW()","clock_timestamp()");
        return sql;
    }

    @Override
    public String handle_SUBSTRING(String sql) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_SUBSTR(String sql) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_LENGTH(String sql) {
        sql = keyReplace(sql,"LENGTH(","OCTET_LENGTH(");
        return sql;
    }

    @Override
    public String handle_CHAR_LENGTH(String sql) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_UUID(String sql) { //pg要先在public下创建uuid()函数
        return sql;
    }

    @Override
    public String handle_IFNULL(String sql) {
        sql = keyReplace(sql,"IFNULL(","coalesce(");
        return sql;
    }

    @Override
    public String handle_DATABASE(String sql) {
        sql = keyReplace(sql,"DATABASE()","current_database()");
        return sql;
    }

    @Override
    public String handle_CURDATE(String sql) {
        sql = keyReplace(sql,"CURDATE()","current_date");
        return sql;
    }

    @Override
    public String handle_CURTIME(String sql) {
        sql = keyReplace(sql,"CURTIME()","to_char(current_timestamp,'HH24:MI:SS')");
        return sql;
    }
}
