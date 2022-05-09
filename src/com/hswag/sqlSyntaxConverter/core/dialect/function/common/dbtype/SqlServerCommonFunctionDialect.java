package com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.common.BaseCommonFunctionDialect;


public class SqlServerCommonFunctionDialect extends BaseCommonFunctionDialect {

    @Override
    public String handle_LEFT(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //sqlserver不用转换此函数
        return sql;
    }

    @Override
    public String handle_RIGHT(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //sqlserver不用转换此函数
        return sql;
    }

    @Override
    public String handle_YEARWEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) +  "(cast(DatePart(year," + strs[0] + ") as varchar)+ case when (DatePart(Week," + strs[0] + ")-1 )<10  " +
                " then '0'+cast(DatePart(Week," + strs[0] + ")-1 as varchar) else cast(DatePart(Week," + strs[0] + ")-1 as varchar)  end ) " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_WEEK(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String WEEK_REP = "`#WK_REP#`";
        sql = sql.substring(0, startFunPoint) +  "(DatePart("+WEEK_REP+"," + strs[0] + ")-1) "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String MONTH_REP = "`#MON_REP#`";
        sql = sql.substring(0, startFunPoint) +  "DatePart("+MONTH_REP+"," + strs[0] + ") "  +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_YEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String YEAR_REP = "`#YE_REP#`";
        sql = sql.substring(0, startFunPoint) +  "DatePart("+YEAR_REP+"," + strs[0] + ") "  +
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
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(19)," + strs[0] + ",120)  " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(16)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(13)," + strs[0] + ",120)  " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(13)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(10)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(7)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(4)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(2)," + strs[0] + ",108) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(2)," + strs[0] + ",108) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "DatePart(month," + strs[0] + ") " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "DatePart(Weekday ," + strs[0] + ")-1 " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(4)," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "(cast(DatePart(year," + strs[0] + ") as varchar)+ cast(DatePart(Week," + strs[0] + ")-1 as varchar)) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "convert(nvarchar(19)," + strs[0] + ",120)  " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
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
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120)  "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H:%i".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime,convert(nvarchar(16)," + strs[0] + ",120),120)  "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120)  "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d %h".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m-%d".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(date," + strs[0] + ",120) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y-%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%H".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",108) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%K".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",108) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%m".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%w".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120 "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120) "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("%Y%u".equals(strs[1])) {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120)  " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "convert(datetime," + strs[0] + ",120)  "  +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DATEDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String DATEDIFF_REP = "`#DAT_DIF#`";
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) + DATEDIFF_REP+"(day," + strs[1] + "," + strs[0] + ") " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TIMESTAMPDIFF(String[] strs, String sql, int startFunPoint, int closeFunPoint) { // 已特殊处理
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
                sql = sql.substring(0, startFunPoint) + "dateadd(day," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("hour".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(hour," + time_value + "," + strs[0] + " )  " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("minute".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(minute," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("second".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(second," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("month".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(month," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("week".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(week," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("quarter".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(quarter," + time_value + "," + strs[0] + ") " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            } else if ("year".equalsIgnoreCase(time_key)) {
                sql = sql.substring(0, startFunPoint) + "dateadd(year," + time_value + "," + strs[0] + " ) " +
                        ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }else {
                sql = sql.substring(0, startFunPoint) + "dateadd(day," + time_value + "," + strs[0] + " )  " +
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
            sql = sql.substring(0, startFunPoint) + "dateadd(day,-" + time_value + "," + strs[0] + " ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("hour".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(hour,-" + time_value + "," + strs[0] + " ) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("minute".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(minute,-" + time_value + "," + strs[0] + " )  "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("second".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(second,-" + time_value + "," + strs[0] + " ) "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("month".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(month,-" + time_value + "," + strs[0] + " )  "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("week".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(week,-" + time_value + "," + strs[0] + " )  "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("quarter".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(quarter,-" + time_value + "," + strs[0] + " )  "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        } else if ("year".equalsIgnoreCase(time_key)) {
            sql = sql.substring(0, startFunPoint) + "dateadd(year,-" + time_value + "," + strs[0] + " ) "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else {
            sql = sql.substring(0, startFunPoint) + "dateadd(day,-" + time_value + "," + strs[0] + " ) "
                    +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_UNIX_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==0){
            sql = sql.substring(0, startFunPoint) + "DATEDIFF(second, '19700101', getdate()) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }else{
            sql = sql.substring(0, startFunPoint) + "DATEDIFF(second, '19700101', dateadd(year,-1,CONVERT(date, "+strs[0]+", 120))) " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_CONVERT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==1){
            String tmp = strs[0].trim();
            String[] tmp_strs = tmp.split(" ");
            if(tmp_strs.length==3) {
                sql = sql.substring(0, startFunPoint) + tmp_strs[0]+" " + ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
            }
        }else if(strs.length==2){
            if("SIGNED".equalsIgnoreCase(strs[1].trim())){
                sql = sql.substring(0, startFunPoint) + "cast("+strs[0]+" as bigint) " +
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
        if(strs.length==1) {
            sql = sql.substring(0, startFunPoint) + "case when "+strs[0]+" is null then 1 else 0 end " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    @Override
    public String handle_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//sqlserver不用转换此函数
        return sql;
    }

    @Override
    public String handle_GROUP_CONCAT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//sqlserver不用转换此函数,转换不出
        return sql;
    }

    @Override
    public String handle_DATE(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String DATE_REP = "`#DY_REP#`";
        strs = clearPrams(strs,"INTERVAL ", "");
        if(strs.length==1) {
            sql = sql.substring(0, startFunPoint) + "case when "+strs[0]+" is null then 1 else 0 end " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }


    @Override
    public String handle_TO_DAYS(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) + " datediff(day, convert(datetime,'1753-01-01',120),"+strs[0]+") " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_TRIM(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String RTRIM_REP = "`#RTM_REP#`";
        String LTRIM_REP = "`#LTM_REP#`";
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) + " "+RTRIM_REP+"("+LTRIM_REP+"("+strs[0]+")) "+
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_IF(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        strs = clearPrams(strs,"INTERVAL ", "");
        sql = sql.substring(0, startFunPoint) + " case when "+strs[0]+" then " + strs[1]+" else "+ strs[2]+" end " +
                ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_WEEKDAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "(case when DatePart(weekday," + param + ")=1 then 6 else DatePart(weekday," + param + ")-2 end) "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_MOD(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) + "(" + strs[0] + ")" + "%" + "(" + strs[1] + ")" + " "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAYOFMONTH(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "DatePart(day," + param + ") "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_QUARTER(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "DatePart(QUARTER," + param + ") "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_DAYOFYEAR(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "DatePart(dayofyear," + param + ") "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_LAST_DAY(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String param = "";
        for(int i = 0;i<strs.length;i++){
            param+=strs[i];
        }
        sql = sql.substring(0, startFunPoint) + "convert(date,dateadd(month, datediff(month, -1, " + param + "), -1),23) "
                +  ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }


}
