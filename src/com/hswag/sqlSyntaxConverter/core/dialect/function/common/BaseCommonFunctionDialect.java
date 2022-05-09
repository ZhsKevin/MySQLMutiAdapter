package com.hswag.sqlSyntaxConverter.core.dialect.function.common;

import com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype.OracleCommonFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype.PgCommonFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.common.dbtype.SqlServerCommonFunctionDialect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCommonFunctionDialect {
    public static String orderby_REP = "`#ORD_REP#`";
    public static String oracle_lastday_REP = "~`##ORA_LDY##`~";
    public static String WEEK_REP = "`#WK_REP#`";
    public static String MONTH_REP = "`#MON_REP#`";
    public static String YEAR_REP = "`#YE_REP#`";
    public static String DATEDIFF_REP = "`#DAT_DIF#`";
    public static String DATE_REP = "`#DY_REP#`";
    public static String RTRIM_REP = "`#RTM_REP#`";
    public static String LTRIM_REP = "`#LTM_REP#`";


    public static String functionConvert(String key,String[] innerPrams,String sql,int startFunPoint,int closeFunPoint,BaseCommonFunctionDialect dialect){
       if("LEFT(".equals(key)){
           return dialect.handle_LEFT(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("RIGHT(".equals(key)){
           return dialect.handle_RIGHT(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("YEARWEEK(".equals(key)){
           return dialect.handle_YEARWEEK(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("WEEK(".equals(key)){
           return dialect.handle_WEEK(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("MONTH(".equals(key)){
           return dialect.handle_MONTH(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("YEAR(".equals(key)){
           return dialect.handle_YEAR(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DATE_FORMAT(".equals(key)){
           return dialect.handle_DATE_FORMAT(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("STR_TO_DATE(".equals(key)){
           return dialect.handle_STR_TO_DATE(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DATEDIFF(".equals(key)){
           return dialect.handle_DATEDIFF(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("TIMESTAMPDIFF(".equals(key)){
           return dialect.handle_TIMESTAMPDIFF(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DATE_ADD(".equals(key)){
           return dialect.handle_DATE_ADD(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DATE_SUB(".equals(key)){
           return dialect.handle_DATE_SUB(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("UNIX_TIMESTAMP(".equals(key)){
           return dialect.handle_UNIX_TIMESTAMP(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("CONVERT(".equals(key)){
           return dialect.handle_CONVERT(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("ISNULL(".equals(key)){
           return dialect.handle_ISNULL(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DAY(".equals(key)){
           return dialect.handle_DAY(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("GROUP_CONCAT(".equals(key)){
           return dialect.handle_GROUP_CONCAT(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if(" DATE(".equals(key)){
           return dialect.handle_DATE(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("TO_DAYS(".equals(key)){
           return dialect.handle_TO_DAYS(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("TRIM(".equals(key)){
           return dialect.handle_TRIM(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("IF(".equals(key)){
           return dialect.handle_IF(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("WEEKDAY(".equals(key)){
           return dialect.handle_WEEKDAY(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("MOD(".equals(key)){
           return dialect.handle_MOD(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DAYOFMONTH(".equals(key)){
           return dialect.handle_DAYOFMONTH(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("QUARTER(".equals(key)){
           return dialect.handle_QUARTER(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("DAYOFYEAR(".equals(key)){
           return dialect.handle_DAYOFYEAR(innerPrams,sql,startFunPoint,closeFunPoint);
       }else if("LAST_DAY(".equals(key)){
           return dialect.handle_LAST_DAY(innerPrams,sql,startFunPoint,closeFunPoint);
       }
       return sql;
    }
    public abstract String handle_LEFT(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_RIGHT(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_YEARWEEK(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_WEEK(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_MONTH(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_YEAR(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DATE_FORMAT(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_STR_TO_DATE(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DATEDIFF(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_TIMESTAMPDIFF(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DATE_ADD(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DATE_SUB(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_UNIX_TIMESTAMP(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_CONVERT(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_ISNULL(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DAY(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_GROUP_CONCAT(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DATE(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_TO_DAYS(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_TRIM(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_IF(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_WEEKDAY(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_MOD(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DAYOFMONTH(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_QUARTER(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_DAYOFYEAR(String[] strs,String sql,int startFunPoint,int closeFunPoint);
    public abstract String handle_LAST_DAY(String[] strs,String sql,int startFunPoint,int closeFunPoint);

    public static String[] clearPrams(String[] strs,String keyword,String rep){
        if(strs==null) {
            return strs;
        }
        for(int i=0;i<strs.length;i++){
            strs[i] = strs[i].replace(keyword, rep).replace(keyword.toLowerCase(), rep);
        }
        return strs;
    }


    public static Map<String,String> functionCommonHandle(String key, String sql, String databaseId) {
        Map<String,String> rst = new HashMap<>();
        String result ="success";
        String special = specialFun(key,sql,databaseId);
        if(special!=null){
            rst.put("sql",special);
            rst.put("databaseId",databaseId);
            rst.put("result",result);
            rst.put("haschange","0");
            return rst;
        }
        String sql_ori = sql;
        try {
            int k = 0;
            while (sql.contains(key)) {
                k++;
                if (k > 500) { //限定一定的循环解析次数
                    System.out.println("==ERROR==key:" + key + " " + sql);
                    result ="error";
                    sql = sql_ori;
                    break;
                }
                Map<String,Integer> funPoints = getCloseFunPoint(key,sql);
                int startFunPoint = funPoints.get("startFunPoint");
                int closeFunPoint = funPoints.get("closeFunPoint");
                int cnt = funPoints.get("cnt");
                if (closeFunPoint > 0 && cnt==0) { //正常闭合的函数 closeFunPoint为函数闭合点
                    String[] strs = getFunParams(startFunPoint, key, sql, closeFunPoint);
                    sql = functionCommonHandleInner(databaseId,key,strs,sql,startFunPoint,closeFunPoint);
                }else if(cnt!=0){//sql异常，没有闭合，说明是不完整的sql 则不做替换
                    sql = sql_ori;
                    break;
                }
            }
            int j = 0;
            while (sql.contains(key.toLowerCase())) {
                j++;
                if (j > 500) {
                    System.out.println("==ERROR==key:" + key.toLowerCase() + " " + sql);
                    result ="error";
                    sql = sql_ori;
                    break;
                }
                Map<String,Integer> funPoints = getCloseFunPoint(key.toLowerCase(),sql);
                int startFunPoint = funPoints.get("startFunPoint");
                int closeFunPoint = funPoints.get("closeFunPoint");
                int cnt = funPoints.get("cnt");
                if (closeFunPoint > 0 && cnt==0) { //正常闭合的函数 closeFunPoint为函数闭合点
                    String[] strs = getFunParams(startFunPoint, key.toLowerCase(), sql, closeFunPoint);
                    sql = functionCommonHandleInner(databaseId,key,strs,sql,startFunPoint,closeFunPoint);
                }else if(cnt!=0){//sql异常，没有闭合，说明是不完整的sql 则不做替换
                    sql = sql_ori;
                    result ="error";
                    break;
                }
            }
        }catch (Exception e){
            result ="error";
            sql = sql_ori;
            e.printStackTrace();
        }
        sql = specialFunSqlRstRep(key,sql,databaseId);
        rst.put("sql",sql);
        rst.put("databaseId",databaseId);
        rst.put("result",result);
        if("error".equals(result)) {
            rst.put("haschange", "0");
        }else{
            rst.put("haschange", "1");
        }
        return rst;
    }

    private static String functionCommonHandleInner(String databaseId,String key,String[] innerParams,String sql,int startFunPoint,int closeFunPoint){ //获取函数体内的参数，返回数组
        BaseCommonFunctionDialect dialect = null;
        if("oracle".equals(databaseId)){
            dialect = new OracleCommonFunctionDialect();
        }else  if("sqlserver".equals(databaseId)){
            dialect = new SqlServerCommonFunctionDialect();
        }else  if("postgresql".equals(databaseId)){
            dialect = new PgCommonFunctionDialect();
        }
        return functionConvert(key,innerParams,sql,startFunPoint,closeFunPoint,dialect);
    }

    private static Map<String,Integer> getCloseFunPoint(String key,String sql ){ //获取函数的在sql中的起止位置,以及函数是否正常闭合cnt==0正常闭合
        Map<String,Integer> funPoints = new HashMap<>();
        int startFunPoint = sql.indexOf(key);
        int closeFunPoint = -1;
        int cnt = 1;
        for (int i = startFunPoint + key.length(); i < sql.length(); i++) { //获取函数的闭合位置
            String str = String.valueOf(sql.charAt(i));
            if ("(".equals(str)) {
                cnt++;
            } else if (")".equals(str)) {
                cnt--;
            }
            if (cnt == 0) {
                closeFunPoint = i;
                break;
            }
        }
        funPoints.put("startFunPoint",startFunPoint);
        funPoints.put("closeFunPoint",closeFunPoint);
        funPoints.put("cnt",cnt);
        return funPoints;
    }


    private static String[] getFunParams(int startFunPoint,String key,String sql,int closeFunPoint ){ //获取函数体内的参数，返回数组
        String param = sql.substring(startFunPoint + key.length(), closeFunPoint); //获取参数内容
        List<String> strss = new ArrayList<>();
        int count = 0;
        int lastpos = 0;
        for (int j = 0; j < param.length(); j++) { //将参数拆分到集合中
            String tmp = String.valueOf(param.charAt(j));
            if ("(".equals(tmp)) {
                count++;
            } else if (")".equals(tmp)) {
                count--;
            } else if (",".equals(tmp)) {
                if (count == 0) {
                    strss.add(param.substring(lastpos, j));
                    if (j < param.length()) {
                        j++;
                    }
                    lastpos = j;
                }
            }
            if (j == param.length() - 1) {
                strss.add(param.substring(lastpos, param.length()));
            }
        }
        String[] strs = new String[strss.size()]; //将参数集合转换到数组
        for (int st = 0; st < strs.length; st++) {
            strs[st] = strss.get(st);
        }
        return strs;
    }


    private static String specialFun(String key,String sql,String databaseId){
        String rst = null;
        if("LEFT(".equals(key)){
            if("sqlserver".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        }else if("RIGHT(".equals(key)){
            if("sqlserver".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        } else if("TIMESTAMPDIFF(".equals(key)){
            if("sqlserver".equals(databaseId)){
                rst = sql.replace(key, "datediff(").replace(key.toLowerCase(), "datediff(");
            }
        }else if("MOD(".equals(key)){
            if("oracle".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        }else if("TRIM(".equals(key)){
            if("oracle".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        }
        return rst;
    }

    /**
     * 替换占位符<br>
     * @param key
     * @param sql
     * @param databaseId
     * @return
     */
    private static String specialFunSqlRstRep(String key,String sql,String databaseId){
        if ("oracle".equals(databaseId)) {
            sql = sql.replace(orderby_REP," ORDER BY ").replace(oracle_lastday_REP,"last_day");
        }else if("sqlserver".equals(databaseId)||"postgresql".equals(databaseId)){
            sql = sql.replace(WEEK_REP,"Week").replace(MONTH_REP,"month").replace(YEAR_REP,"year")
                    .replace(DATEDIFF_REP,"datediff").replace(DATE_REP,"date").replace(RTRIM_REP,"rtrim")
                    .replace(LTRIM_REP,"ltrim");
        }
        return sql;
    }




}
