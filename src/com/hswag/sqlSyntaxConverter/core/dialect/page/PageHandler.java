package com.hswag.sqlSyntaxConverter.core.dialect.page;

import com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype.OraclePage;
import com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype.PgPage;
import com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype.SqlServerPage;

public abstract class PageHandler {
    public static String getPageSql(String sql,String sql_ori,String databaseId){
        PageHandler pageHandler = null;
        if("oracle".equals(databaseId)){
            pageHandler = new OraclePage();
        }else if("sqlserver".equals(databaseId)){
            pageHandler = new SqlServerPage();
        }else if("postgresql".equals(databaseId)){
            pageHandler = new PgPage();
        }
        return pageHandler.getPageSqlInner(sql,sql_ori);
    }

    public abstract String getPageSqlInner(String sql,String sql_ori);
}