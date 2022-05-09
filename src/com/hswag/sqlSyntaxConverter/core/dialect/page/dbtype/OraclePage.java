package com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.page.PageHandler;
import com.hswag.sqlSyntaxConverter.util.ConvertUtil;

import java.util.Map;

public class OraclePage extends PageHandler {

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
            int count = 0;
            int lastqut = -1;
            String sqltmp = "";
            int pos2222 = -1;
            for (int i = pos111; i >= 15; i--) {
                String tmp = String.valueOf(sql.charAt(i));
                if (")".equals(tmp)) {
                    count++;
                } else if ("(".equals(tmp)) {
                    count--;
                    if (count == 0) {
                        lastqut = i;
                    }
                }
                if (count == 0) {
                    String tmp_sql = "";
                    if(lastqut<0) {
                        tmp_sql = sql.substring(i, pos111);
                    }else{
                        tmp_sql =  sql.substring(i, lastqut);
                    }
                    int pos222 = tmp_sql.indexOf("select ");
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("SELECT ");
                    }
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("select\n");
                    }
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("SELECT\n");
                    }
                    if (pos222 > -1 && lastqut == -1) {
                        pos2222 = pos222 + i;
                        sqltmp = sql.substring(pos2222, pos111);
                        break;
                    } else if (pos222 > -1 && pos222 + i < lastqut) {
                        pos2222 = pos222 + i;
                        sqltmp = sql.substring(pos2222, pos111);
                        break;
                    }
                }
            }
            String sql_ori = "";
            if (pos2222 > -1) {
                sql_ori = sql.substring(pos2222, lastpos);
                String sql_replace = "";
                if (limit2 == null||"".equals(limit2.trim())) {
                    sql_replace = " SELECT * FROM (SELECT TMP.*,ROWNUM ROW_ID FROM (" + sqltmp + ") TMP WHERE ROWNUM &lt;=" + limit1 + ")\n";
                } else {
                    sql_replace = " SELECT * FROM (SELECT TMP.*,ROWNUM ROW_ID FROM (" + sqltmp + ") TMP WHERE ROWNUM &lt;=(" + limit2+"+"+limit1 + ") ) WHERE ROW_ID &gt;" + limit1+"\n";

                }
                if("true".equals(rst.get("check"))){
                    sql_ori+=rst.get("suff_if");
                    String pre_if = rst.get("pre_if");
                    int offset_1 = sql_replace.lastIndexOf(pre_if);
                    if(offset_1>-1){
                        sql_replace = sql_replace.substring(0,offset_1)+sql_replace.substring(offset_1,sql_replace.length()).replace(pre_if,"");
                    }
                    int offset_2 = sqltmp.lastIndexOf(pre_if);
                    if(offset_2>-1){
                        sqltmp = sqltmp.substring(0,offset_2)+sqltmp.substring(offset_2,sqltmp.length()).replace(pre_if,"");
                    }

                    sql_replace = " <choose>\n"+rst.get("pre_if").replace("<if","<when")+"\n"+sql_replace+rst.get("suff_if").replace("</if","</when")
                            +"\n<otherwise>\n "+sqltmp+"\n</otherwise>\n</choose> ";
                }
                sql = sql.replace(sql_ori, sql_replace);
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
            int count = 0;
            int lastqut = -1;
            String sqltmp = "";
            int pos2222 = -1;
            for (int i = pos111; i >= 15; i--) {
                String tmp = String.valueOf(sql.charAt(i));
                if (")".equals(tmp)) {
                    count++;
                } else if ("(".equals(tmp)) {
                    count--;
                    if (count == 0) {
                        lastqut = i;
                    }
                }
                if (count == 0) {
                    String tmp_sql = "";
                    if(lastqut<0) {
                        tmp_sql = sql.substring(i, pos111);
                    }else{
                        tmp_sql =  sql.substring(i, lastqut);
                    }
                    int pos222 = tmp_sql.indexOf("select ");
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("SELECT ");
                    }
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("select\n");
                    }
                    if (pos222 < 0) {
                        pos222 = tmp_sql.indexOf("SELECT\n");
                    }
                    if (pos222 > -1 && lastqut == -1) {
                        pos2222 = pos222 + i;
                        sqltmp = sql.substring(pos2222, pos111);
                        break;
                    } else if (pos222 > -1 && pos222 + i < lastqut) {
                        pos2222 = pos222 + i;
                        sqltmp = sql.substring(pos2222, pos111);
                        break;
                    }
                }
            }
            String sql_ori = "";
            if (pos2222 > -1) {
                sql_ori = sql.substring(pos2222, lastpos);
                String sql_replace = "";
                if (limit2 == null||"".equals(limit2.trim())) {
                    sql_replace = " SELECT * FROM (SELECT TMP.*,ROWNUM ROW_ID FROM (" + sqltmp + ") TMP WHERE ROWNUM &lt;=" + limit1 + ")\n";
                } else {
                    sql_replace = " SELECT * FROM (SELECT TMP.*,ROWNUM ROW_ID FROM (" + sqltmp + ") TMP WHERE ROWNUM &lt;=(" + limit2+"+"+limit1 + ")) WHERE ROW_ID &gt;" + limit1+"\n";
                }
                if("true".equals(rst.get("check"))){
                    sql_ori+=rst.get("suff_if");
                    String pre_if = rst.get("pre_if");
                    int offset_1 = sql_replace.lastIndexOf(pre_if);
                    if(offset_1>-1){
                        sql_replace = sql_replace.substring(0,offset_1)+sql_replace.substring(offset_1,sql_replace.length()).replace(pre_if,"");
                    }
                    int offset_2 = sqltmp.lastIndexOf(pre_if);
                    if(offset_2>-1){
                        sqltmp = sqltmp.substring(0,offset_2)+sqltmp.substring(offset_2,sqltmp.length()).replace(pre_if,"");
                    }
                    sql_replace = " <choose>\n"+rst.get("pre_if").replace("<if","<when")+"\n"+sql_replace+rst.get("suff_if").replace("</if","</when")
                            +"\n<otherwise>\n "+sqltmp+"\n</otherwise>\n</choose> ";
                }
                sql = sql.replace(sql_ori, sql_replace);
            }
        }
        return sql;
    }
}
