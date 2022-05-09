package com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.page.PageHandler;
import com.hswag.sqlSyntaxConverter.util.ConvertUtil;

import java.util.Map;

public class PgPage extends PageHandler {

    private static String LIMIT_REP = "~`#LT_REP#`~";

    @Override
    public String getPageSqlInner(String sql,String sql_orignal) {
        int k =0;
        while (sql.contains("limit ")) {
            String key = "limit ";
            k++;
            if (k > 500) {
                System.out.println("==ERROR==key:" + key + " " + sql);
                sql = sql_orignal;
                break;
            }
            int pos111 = sql.indexOf(key);
            String limit1 = null;
            String limit2 = null;
            Map<String, String> rst = ConvertUtil.setLimit(pos111, sql, key, limit1, limit2);
            limit1 = rst.get("limit1");
            limit2 = rst.get("limit2");
            int lastpos = Integer.parseInt(rst.get("possss"));
            String sqlsub = sql.substring(pos111,lastpos);
            if (limit2 == null||"".equals(limit2.trim())) {
                sql = sql.replace(sqlsub,LIMIT_REP+" "+limit1 +" offset 0\n");
            } else {
                sql = sql.replace(sqlsub,LIMIT_REP+" "+limit2 +" offset "+limit1+"\n");
            }
        }
        while (sql.contains("LIMIT ")) {
            String key = "LIMIT ";
            k++;
            if (k > 500) {
                System.out.println("==ERROR==key:" + key + " " + sql);
                sql = sql_orignal;
                break;
            }
            int pos111 = sql.indexOf(key);
            String limit1 = null;
            String limit2 = null;
            Map<String, String> rst = ConvertUtil.setLimit(pos111, sql, key, limit1, limit2);
            limit1 = rst.get("limit1");
            limit2 = rst.get("limit2");
            int lastpos = Integer.parseInt(rst.get("possss"));
            String sqlsub = sql.substring(pos111,lastpos);
            if (limit2 == null||"".equals(limit2.trim())) {
                sql = sql.replace(sqlsub,LIMIT_REP+" "+limit1 +" offset 0\n");
            } else {
                sql = sql.replace(sqlsub,LIMIT_REP+" "+limit2 +" offset "+limit1+"\n");
            }
        }
        return sql.replace(LIMIT_REP,"limit");
    }

}
