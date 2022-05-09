package com.hswag.sqlSyntaxConverter.core.dialect.page.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.page.PageHandler;
import com.hswag.sqlSyntaxConverter.util.ConvertUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlServerPage extends PageHandler {
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
                int cnt33 = 0;
                int lastqut2 = -1;
                String orderby = null;
                for (int st = 0; st < sqltmp.length(); st++) {
                    String tmp = String.valueOf(sqltmp.charAt(st));
                    if ("(".equals(tmp)) {
                        cnt33++;
                    } else if (")".equals(tmp)) {
                        cnt33--;
                        if (cnt33 == 0) {
                            lastqut2 = st;
                        }
                    }
                    if (cnt33 == 0) {
                        int offset1 = sqltmp.lastIndexOf("order by ");
                        if (offset1 < 0) {
                            offset1 = sqltmp.lastIndexOf("order by\n");
                        }
                        int offset2 = sqltmp.lastIndexOf("ORDER BY ");
                        if (offset2 < 0) {
                            offset2 = sqltmp.lastIndexOf("ORDER BY\n");
                        }
                        if (offset1 > lastqut2) {
                            if(sqltmp.indexOf("</if>",offset1)>-1){
                                int offset123 = sqltmp.indexOf("</if>",offset1);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<if");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, offset123+"</if>".length());
                                    orderby = " <choose>\n"+orderby.replace("<if","<when").replace("</if","</when")
                                            +"\n<otherwise>\n order by CURRENT_TIMESTAMP\n</otherwise>\n</choose> "+sqltmp.substring(offset123+"</if>".length(),sqltmp.length());
                                }
                            }else if(sqltmp.indexOf("</choose>",offset1)>-1){
                                int offset123 = sqltmp.indexOf("</choose>",offset1);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<choose");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, sqltmp.length());
                                }
                            }else {
                                orderby = sqltmp.substring(offset1, sqltmp.length());
                            }
                            break;
                        } else if (offset2 > lastqut2) {
                            if(sqltmp.indexOf("</if>",offset2)>-1){
                                int offset123 = sqltmp.indexOf("</if>",offset2);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<if");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, offset123+"</if>".length());
                                    orderby = " <choose>\n"+orderby.replace("<if","<when").replace("</if","</when")
                                            +"\n<otherwise>\n order by CURRENT_TIMESTAMP \n</otherwise>\n</choose> "+sqltmp.substring(offset123+"</if>".length(),sqltmp.length());
                                }
                            }else if(sqltmp.indexOf("</choose>",offset2)>-1){
                                int offset123 = sqltmp.indexOf("</choose>",offset2);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<choose");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, sqltmp.length());
                                }
                            }else {
                                orderby = sqltmp.substring(offset2, sqltmp.length());
                            }
                            break;
                        }
                    }
                }
                if (limit2 != null&&!"".equals(limit2.trim())) {
                    if (orderby != null && !"".equals(orderby)) {
                        if("true".equals(rst.get("check"))) {
                            String pre_if = rst.get("pre_if");
                            int offset_1 = orderby.lastIndexOf(pre_if);
                            if (offset_1 > -1) {
                                orderby = orderby.substring(0, offset_1) + orderby.substring(offset_1, orderby.length()).replace(pre_if, "");
                            }
                        }
                        sqltmp = sqltmp.replace(orderby,"");
                                /*增加如下规则:
                                  1. 如果分页子查询为 select 1 from 则修改为 select 1 as defaultcnt from
                                  2. 如果分页子查询包含orderby排序, 但排序的列字段在select中不存在,则动态添加(select 查询中包含了*,例外)
                                  3. 如果分页没有order by条件,则新增一个虚拟的order by CURRENT_TIMESTAMP 用于分页
                                 */
                        List<String> orderbyFileds = new ArrayList<>();
                        if(orderby!=null&&orderby.toLowerCase().indexOf("order by")>-1) {
                            int pos = orderby.toLowerCase().indexOf("order by");
                            String orderby_sub = orderby.substring(pos+"order by".length());
                            String orderby_lowver = orderby_sub.toLowerCase();
                            String[] tmpFileds = new String[0];
                            String[] tmpFileds2 = new String[0];
                            if (orderby_lowver.indexOf(" asc") == -1 && orderby_lowver.indexOf(" desc") == -1) {
                                tmpFileds = orderby_sub.split(",");
                            } else {
                                int ascIndex = orderby_lowver.indexOf(" asc");
                                int descIndex = orderby_lowver.indexOf(" desc");
                                if (ascIndex != -1 && descIndex == -1) {
                                    tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                } else if (ascIndex == -1 && descIndex != -1) {
                                    tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                } else if (ascIndex != -1 && descIndex != -1) {
                                    if (ascIndex > descIndex) {
                                        tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(descIndex + 5, ascIndex).split(",");
                                    } else {
                                        tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(ascIndex + 4, descIndex).split(",");
                                    }
                                }
                            }
                            for (String field : tmpFileds) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                            for (String field : tmpFileds2) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                        }
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                } else if (selectFileds.indexOf("*") == -1) {
                                    String selectFileds_lower = selectFileds.toLowerCase();
                                    for (String field : orderbyFileds) {
                                        String field_tmp = field.toLowerCase().substring(field.indexOf(".") + 1).trim();
                                        if (selectFileds_lower.indexOf(field_tmp) == -1) {
                                            if (selectFileds_new == null) {
                                                selectFileds_new = selectFileds;
                                            }
                                            selectFileds_new = field + "," + selectFileds_new;
                                        }
                                    }
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                        orderby = ConvertUtil.rep_orderby(orderby);
                        //sql_replace = " select * from (select top (" + limit2 + ") atmp.* from ( select atmp.*,row_number() over(" + orderby + ") as rownumber from (" + sqltmp + ")atmp )atmp where rownumber &gt;" + limit1 + ")atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(" + orderby +") as rownumber from (select top  (" + limit2 + ") atmp.* from (" + sqltmp + ")atmp " +orderby+") atmp"+
                                ")atmp where rownumber &gt;"+limit1+" "+orderby+"\n";
                    } else {
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                       // sql_replace = " select * from (select top (" + limit2 + ") atmp.* from ( select atmp.*,row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (" + sqltmp + ")atmp )atmp where rownumber &gt;" + limit1 + ")atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (select top  (" + limit2 + ") atmp.* from (" + sqltmp + ")atmp ) atmp"+
                                ")atmp where rownumber &gt;"+limit1 +"\n";
                    }
                } else {
                    if (orderby != null && !"".equals(orderby)) {
                        if("true".equals(rst.get("check"))) {
                            String pre_if = rst.get("pre_if");
                            int offset_1 = orderby.lastIndexOf(pre_if);
                            if (offset_1 > -1) {
                                orderby = orderby.substring(0, offset_1) + orderby.substring(offset_1, orderby.length()).replace(pre_if, "");
                            }
                        }
                        sqltmp = sqltmp.replace(orderby,"");
                                     /*增加如下规则:
                                  1. 如果分页子查询为 select 1 from 则修改为 select 1 as defaultcnt from
                                  2. 如果分页子查询包含orderby排序, 但排序的列字段在select中不存在,则动态添加(select 查询中包含了*,例外)
                                  3. 如果分页没有order by条件,则新增一个虚拟的order by CURRENT_TIMESTAMP 用于分页
                                 */
                        List<String> orderbyFileds = new ArrayList<>();
                        if(orderby!=null&&orderby.toLowerCase().indexOf("order by")>-1) {
                            int pos = orderby.toLowerCase().indexOf("order by");
                            String orderby_sub = orderby.substring(pos+"order by".length());
                            String orderby_lowver = orderby_sub.toLowerCase();
                            String[] tmpFileds = new String[0];
                            String[] tmpFileds2 = new String[0];
                            if (orderby_lowver.indexOf(" asc") == -1 && orderby_lowver.indexOf(" desc") == -1) {
                                tmpFileds = orderby_sub.split(",");
                            } else {
                                int ascIndex = orderby_lowver.indexOf(" asc");
                                int descIndex = orderby_lowver.indexOf(" desc");
                                if (ascIndex != -1 && descIndex == -1) {
                                    tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                } else if (ascIndex == -1 && descIndex != -1) {
                                    tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                } else if (ascIndex != -1 && descIndex != -1) {
                                    if (ascIndex > descIndex) {
                                        tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(descIndex + 5, ascIndex).split(",");
                                    } else {
                                        tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(ascIndex + 4, descIndex).split(",");
                                    }
                                }
                            }
                            for (String field : tmpFileds) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                            for (String field : tmpFileds2) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                        }
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                } else if (selectFileds.indexOf("*") == -1) {
                                    String selectFileds_lower = selectFileds.toLowerCase();
                                    for (String field : orderbyFileds) {
                                        String field_tmp = field.toLowerCase().substring(field.indexOf(".") + 1).trim();
                                        if (selectFileds_lower.indexOf(field_tmp) == -1) {
                                            if (selectFileds_new == null) {
                                                selectFileds_new = selectFileds;
                                            }
                                            selectFileds_new = field + "," + selectFileds_new;
                                        }
                                    }
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                        orderby = ConvertUtil.rep_orderby(orderby);
                       // sql_replace = " select * from (select top (" + limit1 + ") atmp.* from ( select  atmp.*,row_number() over(" + orderby + ") as rownumber from (" + sqltmp + ")atmp )atmp )atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(" + orderby +") as rownumber from (select top  (" + limit1 + ") atmp.* from (" + sqltmp + ")atmp " +orderby+") atmp"+
                                ")atmp where rownumber &gt;0 " +orderby+"\n";
                    } else {
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                        //sql_replace = " select * from (select top (" + limit1 + ") atmp.* from ( select atmp.*,row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (" + sqltmp + ")atmp )atmp )atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (select top  (" + limit1 + ") atmp.* from (" + sqltmp + ")atmp ) atmp"+
                                ")atmp where rownumber &gt;0\n";
                    }
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
            } else {
                break;
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
                int cnt33 = 0;
                int lastqut2 = -1;
                String orderby = null;
                for (int st = 0; st < sqltmp.length(); st++) {
                    String tmp = String.valueOf(sqltmp.charAt(st));
                    if ("(".equals(tmp)) {
                        cnt33++;
                    } else if (")".equals(tmp)) {
                        cnt33--;
                        if (cnt33 == 0) {
                            lastqut2 = st;
                        }
                    }
                    if (cnt33 == 0) {
                        int offset1 = sqltmp.lastIndexOf("order by ");
                        if (offset1 < 0) {
                            offset1 = sqltmp.lastIndexOf("order by\n");
                        }
                        int offset2 = sqltmp.lastIndexOf("ORDER BY ");
                        if (offset2 < 0) {
                            offset2 = sqltmp.lastIndexOf("ORDER BY\n");
                        }
                        if (offset1 > lastqut2) {
                            if(sqltmp.indexOf("</if>",offset1)>-1){
                                int offset123 = sqltmp.indexOf("</if>",offset1);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<if");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, offset123+"</if>".length());
                                    orderby = " <choose>\n"+orderby.replace("<if","<when").replace("</if","</when")
                                            +"\n<otherwise>\n order by CURRENT_TIMESTAMP \n</otherwise>\n</choose> "+sqltmp.substring(offset123+"</if>".length(),sqltmp.length());
                                }
                            }else if(sqltmp.indexOf("</choose>",offset1)>-1){
                                int offset123 = sqltmp.indexOf("</choose>",offset1);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<choose");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, sqltmp.length());
                                }
                            }else {
                                orderby = sqltmp.substring(offset1, sqltmp.length());
                            }
                            break;
                        } else if (offset2 > lastqut2) {
                            if(sqltmp.indexOf("</if>",offset2)>-1){
                                int offset123 = sqltmp.indexOf("</if>",offset2);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<if");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, offset123+"</if>".length());
                                    orderby = " <choose>\n"+orderby.replace("<if","<when").replace("</if","</when")
                                            +"\n<otherwise>\n order by CURRENT_TIMESTAMP \n</otherwise>\n</choose> "+sqltmp.substring(offset123+"</if>".length(),sqltmp.length());
                                }
                            }else if(sqltmp.indexOf("</choose>",offset2)>-1){
                                int offset123 = sqltmp.indexOf("</choose>",offset2);
                                String sqltmp_ = sqltmp.substring(0,offset123);
                                int offset456 = sqltmp_.lastIndexOf("<choose");
                                if(offset456>-1) {
                                    orderby = sqltmp.substring(offset456, sqltmp.length());
                                }
                            }else {
                                orderby = sqltmp.substring(offset2, sqltmp.length());
                            }
                            break;
                        }
                    }
                }
                if (limit2 != null&&!"".equals(limit2.trim())) {
                    if (orderby != null && !"".equals(orderby)) {
                        if("true".equals(rst.get("check"))) {
                            String pre_if = rst.get("pre_if");
                            int offset_1 = orderby.lastIndexOf(pre_if);
                            if (offset_1 > -1) {
                                orderby = orderby.substring(0, offset_1) + orderby.substring(offset_1, orderby.length()).replace(pre_if, "");
                            }
                        }
                        sqltmp = sqltmp.replace(orderby,"");
                                /*增加如下规则:
                                  1. 如果分页子查询为 select 1 from 则修改为 select 1 as defaultcnt from
                                  2. 如果分页子查询包含orderby排序, 但排序的列字段在select中不存在,则动态添加(select 查询中包含了*,例外)
                                  3. 如果分页没有order by条件,则新增一个虚拟的order by CURRENT_TIMESTAMP 用于分页
                                 */
                        List<String> orderbyFileds = new ArrayList<>();
                        if(orderby!=null&&orderby.toLowerCase().indexOf("order by")>-1) {
                            int pos = orderby.toLowerCase().indexOf("order by");
                            String orderby_sub = orderby.substring(pos+"order by".length());
                            String orderby_lowver = orderby_sub.toLowerCase();
                            String[] tmpFileds = new String[0];
                            String[] tmpFileds2 = new String[0];
                            if (orderby_lowver.indexOf(" asc") == -1 && orderby_lowver.indexOf(" desc") == -1) {
                                tmpFileds = orderby_sub.split(",");
                            } else {
                                int ascIndex = orderby_lowver.indexOf(" asc");
                                int descIndex = orderby_lowver.indexOf(" desc");
                                if (ascIndex != -1 && descIndex == -1) {
                                    tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                } else if (ascIndex == -1 && descIndex != -1) {
                                    tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                } else if (ascIndex != -1 && descIndex != -1) {
                                    if (ascIndex > descIndex) {
                                        tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(descIndex + 5, ascIndex).split(",");
                                    } else {
                                        tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(ascIndex + 4, descIndex).split(",");
                                    }
                                }
                            }
                            for (String field : tmpFileds) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                            for (String field : tmpFileds2) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                        }
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            String fromSql = sqltmp.substring(fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                } else if (selectFileds.indexOf("*") == -1) {
                                    String selectFileds_lower = selectFileds.toLowerCase();
                                    for (String field : orderbyFileds) {
                                        String field_tmp = field.toLowerCase().substring(field.indexOf(".") + 1).trim();
                                        if (selectFileds_lower.indexOf(field_tmp) == -1) {
                                            if (selectFileds_new == null) {
                                                selectFileds_new = selectFileds;
                                            }
                                            selectFileds_new = field + "," + selectFileds_new;
                                        }
                                    }
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                        orderby = ConvertUtil.rep_orderby(orderby);
                       // sql_replace = " select * from (select top (" + limit2 +  ") atmp.* from ( select atmp.*,row_number() over(" + orderby + ") as rownumber from (" + sqltmp + ") atmp )atmp where rownumber  &gt;" + limit1 + ")atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(" + orderby +") as rownumber from (select top  (" + limit2 + ") atmp.* from (" + sqltmp + ")atmp " +orderby+") atmp"+
                                ")atmp where rownumber &gt;"+limit1+" "+orderby+"\n";
                    } else {
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                       // sql_replace = " select * from (select top (" + limit2 +  ") atmp.* from ( select atmp.*,row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (" + sqltmp + ") atmp )atmp where rownumber &gt;" + limit1 + ")atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (select top  (" + limit2 + ") atmp.* from (" + sqltmp + ")atmp ) atmp"+
                                ")atmp where rownumber &gt;"+limit1 +"\n";
                    }
                } else {
                    if (orderby != null && !"".equals(orderby)) {
                        if("true".equals(rst.get("check"))) {
                            String pre_if = rst.get("pre_if");
                            int offset_1 = orderby.lastIndexOf(pre_if);
                            if (offset_1 > -1) {
                                orderby = orderby.substring(0, offset_1) + orderby.substring(offset_1, orderby.length()).replace(pre_if, "");
                            }
                        }
                        sqltmp = sqltmp.replace(orderby,"");
                        /*增加如下规则:
                                  1. 如果分页子查询为 select 1 from 则修改为 select 1 as defaultcnt from
                                  2. 如果分页子查询包含orderby排序, 但排序的列字段在select中不存在,则动态添加(select 查询中包含了*,例外)
                                  3. 如果分页没有order by条件,则新增一个虚拟的order by CURRENT_TIMESTAMP 用于分页
                         */
                        List<String> orderbyFileds = new ArrayList<>();
                        if(orderby!=null&&orderby.toLowerCase().indexOf("order by")>-1) {
                            int pos = orderby.toLowerCase().indexOf("order by");
                            String orderby_sub = orderby.substring(pos+"order by".length());
                            String orderby_lowver = orderby_sub.toLowerCase();
                            String[] tmpFileds = new String[0];
                            String[] tmpFileds2 = new String[0];
                            if (orderby_lowver.indexOf(" asc") == -1 && orderby_lowver.indexOf(" desc") == -1) {
                                tmpFileds = orderby_sub.split(",");
                            } else {
                                int ascIndex = orderby_lowver.indexOf(" asc");
                                int descIndex = orderby_lowver.indexOf(" desc");
                                if (ascIndex != -1 && descIndex == -1) {
                                    tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                } else if (ascIndex == -1 && descIndex != -1) {
                                    tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                } else if (ascIndex != -1 && descIndex != -1) {
                                    if (ascIndex > descIndex) {
                                        tmpFileds = orderby_sub.substring(0, descIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(descIndex + 5, ascIndex).split(",");
                                    } else {
                                        tmpFileds = orderby_sub.substring(0, ascIndex).split(",");
                                        tmpFileds2 = orderby_sub.substring(ascIndex + 4, descIndex).split(",");
                                    }
                                }
                            }
                            for (String field : tmpFileds) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                            for (String field : tmpFileds2) {
                                field = field.trim();
                                if (!"".equals(field)) {
                                    orderbyFileds.add(field);
                                }
                            }
                        }
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                } else if (selectFileds.indexOf("*") == -1) {
                                    String selectFileds_lower = selectFileds.toLowerCase();
                                    for (String field : orderbyFileds) {
                                        String field_tmp = field.toLowerCase().substring(field.indexOf(".") + 1).trim();
                                        if (selectFileds_lower.indexOf(field_tmp) == -1) {
                                            if (selectFileds_new == null) {
                                                selectFileds_new = selectFileds;
                                            }
                                            selectFileds_new = field + "," + selectFileds_new;
                                        }
                                    }
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                        orderby = ConvertUtil.rep_orderby(orderby);
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(" + orderby +") as rownumber from (select top  (" + limit1 + ") atmp.* from (" + sqltmp + ")atmp " +orderby+") atmp"+
                                ")atmp where rownumber &gt;0 "+orderby+"\n";
                    } else {
                        String sql_lower = sqltmp.toLowerCase();
                        int selectIndex = sql_lower.indexOf("select");
                        int fromIndex = -1;
                        int fromIndex1 = sql_lower.indexOf(" from");
                        int fromIndex2 = sql_lower.indexOf("\nfrom");
                        int fromIndex3 = sql_lower.indexOf("\tfrom");
                        if(fromIndex1>-1){
                            fromIndex = fromIndex1;
                        }else if(fromIndex2>-1){
                            fromIndex = fromIndex2;
                        }else if(fromIndex3>-1){
                            fromIndex = fromIndex3;
                        }
                        String selectFileds = null;
                        if(fromIndex>selectIndex&&selectIndex>-1){
                            selectFileds = sqltmp.substring(selectIndex+6,fromIndex);
                            if(selectFileds!=null&&selectFileds.indexOf("(")<0) {
                                String fromSql = sqltmp.substring(fromIndex);
                                String selectFileds_new = null;
                                if ("1".equals(selectFileds.trim())) {
                                    selectFileds_new = " 1 as defaultcnt ";
                                }
                                if (selectFileds_new != null) {
                                    sqltmp = " SELECT " + selectFileds_new + " " + fromSql;
                                }
                            }
                        }
                      //sql_replace = " select * from (select top (" + limit1 + ") atmp.* from ( select  atmp.*, row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (" + sqltmp + ")atmp )atmp )atmp\n";
                        sql_replace = " select atmp.* from ( select atmp.*, row_number() over(order by CURRENT_TIMESTAMP) as rownumber from (select top  (" + limit1 + ") atmp.* from (" + sqltmp + ")atmp ) atmp"+
                                ")atmp where rownumber &gt;0 \n";
                    }
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
            } else {
                break;
            }
        }
        return sql;
    }
}
