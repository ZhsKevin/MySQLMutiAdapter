package com.hswag.sqlSyntaxConverter.core.dialect.function.complex;



import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.dbtype.OracleComplexFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.dbtype.PgComplexFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.dbtype.SqlServerComplexFunctionDialect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseComplexFunctionDialect {

    public static String CURRENT_TIMESTAMP_REP =  "`#CUR_TSPREP#`";
    private static String specail_split = "','";
    private static String specail_split_REP ="`#sp_split_rep#`";

    public abstract String handle_CURRENT_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint);
    public abstract String handle_INSTR(String[] strs, String sql, int startFunPoint, int closeFunPoint);
    public abstract String handle_CONCAT(String[] strs, String sql, int startFunPoint, int closeFunPoint);
    public abstract String handle_CONCAT_WS(String[] strs, String sql, int startFunPoint, int closeFunPoint);


    public static String functionConvert(String key,String[] strs,String sql,int startFunPoint,int closeFunPoint,BaseComplexFunctionDialect dialect) {
        if ("CURRENT_TIMESTAMP(".equals(key)) {
            return dialect.handle_CURRENT_TIMESTAMP(strs,sql,startFunPoint,closeFunPoint);
        }else if("INSTR(".equals(key)){
            return dialect.handle_INSTR(strs,sql,startFunPoint,closeFunPoint);
        }else if("CONCAT(".equals(key)){
            return dialect.handle_CONCAT(strs,sql,startFunPoint,closeFunPoint);
        }else if("CONCAT_WS(".equals(key)){
            return dialect.handle_CONCAT_WS(strs,sql,startFunPoint,closeFunPoint);
        }
        return sql;
    }

    public static Map<String,String> functionComplexHandle(String key, String sql, String databaseId) {
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
        sql = specialFunSqlRep(key,sql,databaseId);
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

    public static Map<String,Integer> getCloseFunPoint(String key,String sql ){ //获取函数的在sql中的起止位置,以及函数是否正常闭合cnt==0正常闭合
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


    public static String[] getFunParams(int startFunPoint,String key,String sql,int closeFunPoint ){ //获取函数体内的参数，返回数组
        String param = sql.substring(startFunPoint + key.length(), closeFunPoint); //获取参数内容
        if("INSTR(".equals(key)||"CONCAT(".equals(key)||"CONCAT_WS(".equals(key)){
            param = param.replace(specail_split,specail_split_REP);
        }
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
            strs[st] = strss.get(st).replace(specail_split_REP,specail_split);
        }
        return strs;
    }

    public String[] clearPrams(String[] strs,String keyword,String rep){
        if(strs==null) {
            return strs;
        }
        for(int i=0;i<strs.length;i++){
            strs[i] = strs[i].replace(keyword, rep).replace(keyword.toLowerCase(), rep);
        }
        return strs;
    }

    private static String functionCommonHandleInner(String databaseId,String key,String[] innerParams,String sql,int startFunPoint,int closeFunPoint){ //获取函数体内的参数，返回数组
        BaseComplexFunctionDialect dialect = null;
        if("oracle".equals(databaseId)){
            dialect = new OracleComplexFunctionDialect();
        }else  if("sqlserver".equals(databaseId)){
            dialect = new SqlServerComplexFunctionDialect();
        }else  if("postgresql".equals(databaseId)){
            dialect = new PgComplexFunctionDialect();
        }
        return functionConvert(key,innerParams,sql,startFunPoint,closeFunPoint,dialect);
    }

    private static String specialFun(String key,String sql,String databaseId){
        String rst = null;
        if("INSTR(".equals(key)){
            if("oracle".equals(databaseId)||"postgresql".equals(databaseId)){ //oracle无需对instr转换,pg要先在public下创建instr()函数
                rst = sql;
            }
        }else if("CONCAT_WS(".equals(key)){
            if("postgresql".equals(databaseId)){ //oracle无需对instr转换,pg要先在public下创建instr()函数
                rst = sql;
            }
        }else if("CONCAT(".equals(key)){  //避免sqlserver group_concat没处理,又被截断，导致sql异常情况
            if(sql.toLowerCase().indexOf("group_concat(")>-1){
                rst = sql;
            }
        }
        return rst;
    }

    private static String specialFunSqlRep(String key,String sql,String databaseId){
        if ("CURRENT_TIMESTAMP(".equals(key)) {
            if("oracle".equals(databaseId)){
                sql = sql.replace("CURRENT_TIMESTAMP()", "sysdate").replace("CURRENT_TIMESTAMP()".toLowerCase(), "sysdate");
            }else if("sqlserver".equals(databaseId)){
                sql = sql.replace("CURRENT_TIMESTAMP()", "getdate()").replace("CURRENT_TIMESTAMP()".toLowerCase(), "getdate()");
            }else if("postgresql".equals(databaseId)){
                sql = sql.replace("CURRENT_TIMESTAMP()", "current_timestamp::timestamp(0)without time zone").replace("CURRENT_TIMESTAMP()".toLowerCase(), "current_timestamp::timestamp(0)without time zone");
            }
        }
        return sql;
    }

    private static String specialFunSqlRstRep(String key,String sql,String databaseId){
        if ("CURRENT_TIMESTAMP(".equals(key)) {
            sql = sql.replace(CURRENT_TIMESTAMP_REP, "current_timestamp");
        }
        return sql;
    }
}
