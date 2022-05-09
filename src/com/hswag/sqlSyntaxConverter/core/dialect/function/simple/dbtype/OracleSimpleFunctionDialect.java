package com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.BaseSimpleFunctionDialect;

public class OracleSimpleFunctionDialect extends BaseSimpleFunctionDialect {
    @Override
    public String handle_NOW(String sql) {
        sql = keyReplace(sql,"NOW()","sysdate");
        return sql;
    }

    @Override
    public String handle_SUBSTRING(String sql) {
        sql = keyReplace(sql,"SUBSTRING(","SUBSTR(");
        return sql;
    }

    @Override
    public String handle_SUBSTR(String sql) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_LENGTH(String sql) {
        sql = keyReplace(sql,"LENGTH(","lengthb(");
        return sql;
    }

    @Override
    public String handle_CHAR_LENGTH(String sql) {
        sql = keyReplace(sql,"CHAR_LENGTH(","length(");
        return sql;
    }

    @Override
    public String handle_UUID(String sql) {
        sql = keyReplace(sql,"UUID()","sys_guid()");
        return sql;
    }

    @Override
    public String handle_IFNULL(String sql) {
        sql = keyReplace(sql,"IFNULL(","nvl(");
        return sql;
    }

    @Override
    public String handle_DATABASE(String sql) {
        sql = keyReplace(sql,"DATABASE()","user");
        return sql;
    }

    @Override
    public String handle_CURDATE(String sql) {
        sql = keyReplace(sql,"CURDATE()","trunc(sysdate)");
        return sql;
    }

    @Override
    public String handle_CURTIME(String sql) {
        sql = keyReplace(sql,"CURTIME()","to_char(sysdate,'hh24:mi:ss')");
        return sql;
    }

}
