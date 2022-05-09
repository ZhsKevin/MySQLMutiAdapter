package com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.common.BaseCommonFunctionDialect;

public class PgCommonFunctionDialect extends BaseCommonFunctionDialect {


    @Override
    public String handle_LEFT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_RIGHT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_YEARWEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'YYYYWW') " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_WEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"extract(week from "+strs[0]+"::timestamp) "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"extract(month from "+strs[0]+"::timestamp) "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_YEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"extract(year from "+strs[0]+"::timestamp) "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DATE_FORMAT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        for (int st = 0; st < strs.length; st++) {
            if(st==1){
                strs[st] =  strs[st].trim().replace("\"","").replace("'","");
            }
        }
        if ("%Y-%m-%d %H:%i:%S".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD HH24:MI:SS') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD HH24:MI') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD HH12') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'MM') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'D')-1 " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYYWW') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + "::timestamp,'YYYY-MM-DD HH24:MI:SS') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_STR_TO_DATE(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        for (int st = 0; st < strs.length; st++) {
            if(st==1){
                strs[st] =  strs[st].trim().replace("\"","").replace("'","");
            }
        }
        if ("%Y-%m-%d %H:%i:%S".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD HH24:MI:SS') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD HH24:MI') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD HH12') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'MM') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'D') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYYWW') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "to_timestamp(" + strs[0] + "::varchar,'YYYY-MM-DD HH24:MI:SS') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATEDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //pg:先在public下创建datediff函数
        String DATEDIFF_REP = "`#DAT_DIF#`";
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +"datediff(" + strs[0] + "::timestamp," + strs[1] + "::timestamp) "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TIMESTAMPDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if ("second".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp))) as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp)))/60.0 as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp)))/3600.0 as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("day".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint)+ "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp)))/3600.0/24.0 as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint)+ "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp)))/3600.0/24.0/7.0 as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(year from age(" + strs[2] + "::timestamp, " + strs[1] + "::timestamp)) as numeric),0)*12" +
                    "+round(cast(extract(month from age(" + strs[2] + "::timestamp," + strs[1] + "::timestamp)) as numeric),0) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "round((round(cast(extract(year from age(" + strs[2] + "::timestamp, " + strs[1] + "::timestamp)) as numeric),0)*12" +
                    "+round(cast(extract(month from age(" + strs[2] + "::timestamp, " + strs[1] + "::timestamp)) as numeric),0))/3.0,0) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) +  "round(cast(extract(year from age(" + strs[2] + "::timestamp, " + strs[1] + "::timestamp)) as numeric),0) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(epoch from((" + strs[2] + "::timestamp - " + strs[1] + "::timestamp))) as numeric),0)   "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATE_ADD(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        //strs = clearPrams(strs,"INTERVAL ", "");
        String tmp = strs[strs.length - 1].trim();
        int posss = tmp.lastIndexOf(" ");
        String time_key = tmp.substring(posss, tmp.length()).trim().replace("\"","").replace("'","");
        String time_value = tmp.substring(0, posss);
        if ("day".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (" + time_value + "||'day')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (" + time_value + "||'hour')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (" + time_value + "||'min')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("second".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (" + time_value + "||'sec')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (" + time_value + "||'month')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (" + time_value + "||'week')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "((" + strs[0] + ")*3::timestamp + (" + time_value + "||'month')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (" + time_value + "||'year')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (" + time_value + "||'day')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATE_SUB(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        //strs = clearPrams(strs,"INTERVAL ", "");
        String tmp = strs[strs.length - 1].trim();
        int posss = tmp.lastIndexOf(" ");
        String time_key = tmp.substring(posss, tmp.length()).trim().replace("\"","").replace("'","");
        String time_value = tmp.substring(0, posss);
        if ("day".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'day')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'hour')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'min')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("second".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'sec')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'month')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'week')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "((" + strs[0] + ")*3::timestamp + (-(" + time_value + ")||'month')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'year')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "::timestamp + (-(" + time_value + ")||'day')::interval ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_UNIX_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==0){
            sql = sql.substring(0, startFunPoint) + " round(cast(extract(epoch from now()::timestamptz) as numeric),0) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "round(cast(extract(epoch from "+strs[0]+"::timestamptz) as numeric),0) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_CONVERT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        strs = clearPrams(strs,"DISTINCT ", "");
        if(strs.length==1){
            String tmp = strs[0].trim();
            String[] tmp_strs = tmp.split(" ");
            if(tmp_strs.length==3) {
                if (tmp_strs[2].indexOf("GBK") > -1 ||tmp_strs[2].indexOf("gbk") > -1){
                    sql = sql.substring(0, startFunPoint) +"convert_to("+tmp_strs[0]+",'GBK') " +
                            ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
                }else if(tmp_strs[2].indexOf("UTF8") > -1 ||tmp_strs[2].indexOf("utf8") > -1||tmp_strs[2].indexOf("UTF-8") > -1 ||tmp_strs[2].indexOf("utf-8") > -1) {
                    sql = sql.substring(0, startFunPoint) +"convert_to("+tmp_strs[0]+",'UTF8') " +
                            ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
                }else{
                    sql = sql.substring(0, startFunPoint) +"convert_to("+tmp_strs[0]+",'UTF8') " +
                            ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
                }
            }
        }else if(strs.length==2){
            if("SIGNED".equalsIgnoreCase(strs[1].trim())){
                sql = sql.substring(0, startFunPoint) + "cast("+strs[0]+" as int) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }else{
                sql = sql.substring(0, startFunPoint) + "cast("+strs[0]+" as "+strs[1]+") " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }
        }
        return sql;
    }


    @Override
    public String handle_ISNULL(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +   "coalesce( "+strs[0]+" ,1)  "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +   " cast(to_char("+strs[0]+"::timestamp,'DD') as int) "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_GROUP_CONCAT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        strs = clearPrams(strs,"DISTINCT ", "");
        if(strs.length==1){
            String tmp = strs[0];
            sql = sql.substring(0, startFunPoint) + "string_agg("+strs[0]+",',') "
                        + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATE(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==1) {
            sql = sql.substring(0, startFunPoint) +  " to_char("+strs[0]+",'YYYY-MM-DD') " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }


    @Override
    public String handle_TO_DAYS(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +  "  round(cast(extract(epoch from(("+strs[0]+"::timestamp - '0001-01-01'::timestamp)))/3600.0/24.0 as numeric),0)+366 " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TRIM(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_IF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +  " case when "+strs[0]+" then " + strs[1]+" else "+ strs[2]+" end " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_WEEKDAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {

        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "(case when extract(dow from "+param+"::timestamp)=0 then 6 else extract(dow from "+param+"::timestamp)-1 end) "
                + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MOD(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //不需替换，与mysql完全相同
        return sql;
    }

    @Override
    public String handle_DAYOFMONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + " extract(day from " + param + "::timestamp) "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_QUARTER(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "extract(quarter from " + param + "::timestamp) "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAYOFYEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "doy from " + param + "::timestamp) "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_LAST_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "to_char((to_char("+param+"::timestamp,'YYYY-MM-01')::timestamp + '1 month -1 day')::timestamp,'YYYY-MM-DD') "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

}
