package com.hswag.sqlSyntaxConverter.core.dialect.systemtable;

import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.dbtype.OracleSystemTable;
import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.dbtype.PgSystemTable;
import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.dbtype.SqlServerSystemTable;

public abstract class SystemTableHandler {
    public static String getSystemTableSql(String sql,String sql_orignal,String databaseId){
        SystemTableHandler systemTableHandler = null;
        if("oracle".equals(databaseId)){
            systemTableHandler = new OracleSystemTable();
        }else if("sqlserver".equals(databaseId)){
            systemTableHandler = new SqlServerSystemTable();
        }else if("postgresql".equals(databaseId)){
            systemTableHandler = new PgSystemTable();
        }
        return systemTableHandler.getSystemTableSqlInner(sql,sql_orignal);
    }

    public abstract String getSystemTableSqlInner(String sql,String sql_orignal);
}
