package com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.common.BaseCommonFunctionDialect;


public class OracleCommonFunctionDialect extends BaseCommonFunctionDialect {

    @Override
    public String handle_LEFT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
       if (strs.length != 2){
           System.out.println("==error in handle_LEFT===" + sql);
       } else {
           sql = sql.substring(0, startFunPoint) + "SUBSTR(" + strs[0] + ",1 , " + strs[1] + ") " +
                   ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
       }
        return sql;
    }

    @Override
    public String handle_RIGHT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        if(strs.length!=2){
            System.out.println("==error in handle_RIGHT==="+sql);
        }else {
            sql = sql.substring(0, startFunPoint) + "SUBSTR(" + strs[0] + ",-" + strs[1] + ") " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_YEARWEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyyWW') " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_WEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"to_char(" + strs[0] + ",'WW') "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"to_char(" + strs[0] + ",'mm') "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_YEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +"to_char(" + strs[0] + ",'yyyy') "   +
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
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd HH24:mi:ss') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd HH24:mi') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd HH') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'HH24') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'mm') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'w') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyyWW') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "to_char(" + strs[0] + ",'yyyy-mm-dd HH24:mi:ss') " +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
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
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd HH24:mi:ss') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd HH24:mi') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd HH') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'HH24') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'mm') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'w') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyyWW') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "to_date(" + strs[0] + ",'yyyy-mm-dd HH24:mi:ss') " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATEDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +"round(to_date(" + strs[0] + ",'yyyy-mm-dd') - to_date(" + strs[1] + ",'yyyy-mm-dd')) "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TIMESTAMPDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if ("second".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "(extract (day from (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*24*60*60 +" +
                    "           extract (hour   from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*60*60+" +
                    "           extract (minute from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*60+" +
                    "           extract (second from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp))))  "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "(extract (day from (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*24*60 +" +
                    "           extract (hour   from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*60+" +
                    "           extract (minute from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) +"(extract (day from (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*24 +" +
                    "           extract (hour  from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))) "    +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("day".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint)+  "(extract (day from (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint)+ "trunc(extract (day  from (" + strs[2] + " -cast(" + strs[1] + " as timestamp)))/7) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "trunc(extract (day  from (" + strs[2] + " -cast(" + strs[1] + " as timestamp)))/30) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) + "trunc(extract (day  from (" + strs[2] + " -cast(" + strs[1] + " as timestamp)))/90) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(strs[0].trim())) {
            sql = sql.substring(0, startFunPoint) +  "trunc(extract (day  from (" + strs[2] + " -cast(" + strs[1] + " as timestamp)))/365) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) +  "(extract (day from (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*24*60*60 +" +
                    "           extract (hour   from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*60*60+" +
                    "           extract (minute from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp)))*60+" +
                    "           extract (second from  (" + strs[2] + "-cast(" + strs[1] + " as timestamp))))  " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATE_ADD(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        String tmp = strs[strs.length - 1].trim();
        int posss = tmp.lastIndexOf(" ");
        String time_key = tmp.substring(posss, tmp.length()).trim().replace("\"","").replace("'","");
        String time_value = tmp.substring(0, posss);
        if ("day".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "+" + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "+(" + time_value + ")/24) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) +    "(" + strs[0] + "+(" + time_value + ")/1440) "    +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("second".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "+(" + time_value + ")/86400) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "add_months(" + strs[0] + "," + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_day(" + strs[0] + ",(" + time_value + ")*7) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_months(" + strs[0] + ",(" + time_value + ")*3) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_months(" + strs[0] + ",(" + time_value + ")*12) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "+" + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATE_SUB(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        String tmp = strs[strs.length - 1].trim();
        int posss = tmp.lastIndexOf(" ");
        String time_key = tmp.substring(posss, tmp.length()).trim().replace("\"","").replace("'","");
        String time_value = tmp.substring(0, posss);
        if ("day".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "-" + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "-" + time_value + "/24) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) +    "(" + strs[0] + "-" + time_value + "/1440) "    +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("second".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "(" + strs[0] + "-" + time_value + "/86400) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint)+ "add_months(" + strs[0] + ",-" + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_day(" + strs[0] + ",-" + time_value + "*7) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_months(" + strs[0] + ",-" + time_value + "*3) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "add_months(" + strs[0] + ",-" + time_value + "*12) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "(" + strs[0] + "-" + time_value + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_UNIX_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==0){
            sql = sql.substring(0, startFunPoint) + "((sysdate-to_date('19700101','yyyymmdd'))*86400  - to_number(substr(tz_offset(sessiontimezone),1,3))*3600) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "((add_months(trunc("+strs[0]+"),-12)-to_date('19700101','yyyymmdd'))*86400  - to_number(substr(tz_offset(sessiontimezone),1,3))*3600) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_CONVERT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String oracle_convertRep = "`~##ORA_CONV##~`";
        strs = clearPrams(strs,"INTERVAL ", "");
        strs = clearPrams(strs,"DISTINCT ", "");
        if(strs.length==1){
            String tmp = strs[0].trim();
            String[] tmp_strs = tmp.split(" ");
            if(tmp_strs.length==3) {
                if (tmp_strs[2].indexOf("GBK") > -1 ||tmp_strs[2].indexOf("gbk") > -1){
                    sql = sql.substring(0, startFunPoint) +oracle_convertRep+"("+tmp_strs[0]+",'ZHS16GBK') " +
                            ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
                }else if(tmp_strs[2].indexOf("UTF8") > -1 ||tmp_strs[2].indexOf("utf8") > -1||tmp_strs[2].indexOf("UTF-8") > -1 ||tmp_strs[2].indexOf("utf-8") > -1) {
                    sql = sql.substring(0, startFunPoint) +oracle_convertRep+"("+tmp_strs[0]+",'AL32UTF8') " +
                            ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
                }else{
                    sql = sql.substring(0, startFunPoint)  +oracle_convertRep+"("+tmp_strs[0]+",'AL32UTF8') " +
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
        sql = sql.substring(0, startFunPoint) +   "case when "+strs[0]+" is null then 1 else 0 end  "   +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +   " to_char("+strs[0]+",'dd') "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_GROUP_CONCAT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//复杂 ?
        String orderby_REP = "`#ORD_REP#`";
        strs = clearPrams(strs,"INTERVAL ", "");
        strs = clearPrams(strs,"DISTINCT ", "");
        if(strs.length==1){
            String tmp = strs[0];
            if(tmp.indexOf("ORDER BY")>-1||tmp.indexOf("order by")>-1){
                int pos123 = tmp.indexOf("ORDER BY");
                if(pos123<0){
                    pos123 = tmp.indexOf("order by");
                }
                String pre = tmp.substring(0,pos123).trim();
                String suf = tmp.substring(pos123+"ORDER BY".length(),tmp.length()).trim();
                sql = sql.substring(0, startFunPoint) + "LISTAGG("+pre+",',') WITHIN GROUP ("+orderby_REP+pre+","+suf+") "
                        + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }else{
                sql = sql.substring(0, startFunPoint) + "LISTAGG("+tmp+",',') WITHIN GROUP ("+orderby_REP+tmp+") "
                        + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }
        }
        return sql;
    }

    @Override
    public String handle_DATE(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==1) {
            sql = sql.substring(0, startFunPoint) +  " trunc("+strs[0]+") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_TO_DAYS(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) +  " TRUNC("+strs[0]+" - TO_DATE('0001-01-01','YYYY-MM-DD')) " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TRIM(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //oracle  原样返回
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
        sql = sql.substring(0, startFunPoint) + "(case when to_char(" + param + ",'D')=1 then 6 else to_char(" + param + ",'D')-2 end) "
                + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MOD(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //不用处理，原样
        return sql;
    }

    @Override
    public String handle_DAYOFMONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "to_char(" + param + ",'DD') "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_QUARTER(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "to_char(" + param + ",'Q') "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAYOFYEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "to_char(" + param + ",'DDD') "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_LAST_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String oracle_lastday_REP = "~`##ORA_LDY##`~";
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "trunc("+oracle_lastday_REP+"(" + param + ")) "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

}
