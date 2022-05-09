package com.hswag.sqlSyntaxConverter.core.dialect.function.simple;

import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype.OracleSimpleFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype.PgSimpleFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.dbtype.SqlServerSimpleFunctionDialect;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseSimpleFunctionDialect { //函数替换

    public static String functionConvert(String key,String sql ,BaseSimpleFunctionDialect dialect){
        if("NOW(".equals(key)){
             return dialect.handle_NOW(sql);
        }else if("SUBSTRING(".equals(key)){
             return dialect.handle_SUBSTRING(sql);
        }else if("SUBSTR(".equals(key)){
             return dialect.handle_SUBSTR(sql);
        }else if("LENGTH(".equals(key)){
             return dialect.handle_LENGTH(sql);
        }else if("CHAR_LENGTH(".equals(key)){
             return dialect.handle_CHAR_LENGTH(sql);
        }else if("UUID(".equals(key)){
             return dialect.handle_UUID(sql);
        }else if("IFNULL(".equals(key)){
             return dialect.handle_IFNULL(sql);
        }else if("DATABASE(".equals(key)){
             return dialect.handle_DATABASE(sql);
        }else if("CURDATE(".equals(key)){
             return dialect.handle_CURDATE(sql);
        }else if("CURTIME(".equals(key)){
             return dialect.handle_CURTIME(sql);
        }
        return sql;
    }

    public abstract String handle_NOW(String sql);
    public abstract String handle_SUBSTRING(String sql);
    public abstract String handle_SUBSTR(String sql);
    public abstract String handle_LENGTH(String sql);
    public abstract String handle_CHAR_LENGTH(String sql);
    public abstract String handle_UUID(String sql);
    public abstract String handle_IFNULL(String sql);
    public abstract String handle_DATABASE(String sql);
    public abstract String handle_CURDATE(String sql);
    public abstract String handle_CURTIME(String sql);
    
    
    public static String keyReplace(String sql,String key,String target){
        if("ETEAMS.".equals(key)){
            sql = sql .replace("`eteams`.","{$publicdb}.").replace("`ETEAMS`.","{$publicdb}.");
        }
        sql = sql.replace(" "+key, " "+target).replace(" "+key.toLowerCase(), " "+target.toLowerCase())
                .replace(","+key, ","+target).replace(","+key.toLowerCase(), ","+target.toLowerCase())
                .replace("("+key, "("+target).replace("("+key.toLowerCase(), "("+target.toLowerCase())
                .replace("\n"+key, "\n"+target).replace("\n"+key.toLowerCase(), "\n"+target.toLowerCase())
                .replace("\t"+key, "\t"+target).replace("\t"+key.toLowerCase(), "\t"+target.toLowerCase());
        return sql;
    }

    public static Map<String,String> functionSimpleHandle(String key, String sql, String databaseId) {
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
           sql = functionSimpleHandleInner(databaseId,key,sql);
        }catch (Exception e){
            result ="error";
            sql = sql_ori;
            e.printStackTrace();
        }
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

    private static String functionSimpleHandleInner(String databaseId,String key,String sql){ //获取函数体内的参数，返回数组
        BaseSimpleFunctionDialect dialect = null;
        if("oracle".equals(databaseId)){
            dialect = new OracleSimpleFunctionDialect();
        }else  if("sqlserver".equals(databaseId)){
            dialect = new SqlServerSimpleFunctionDialect();
        }else  if("postgresql".equals(databaseId)){
            dialect = new PgSimpleFunctionDialect();
        }
        return dialect.functionConvert(key,sql,dialect);
    }



    private static String specialFun(String key,String sql,String databaseId){
        String rst = null;
        if("SUBSTRING(".equals(key)){
            if("sqlserver".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        }else if("SUBSTR(".equals(key)){
            if("oracle".equals(databaseId)||"postgresql".equals(databaseId)){
                rst = sql;
            }
        } else if("CHAR_LENGTH(".equals(key)){
            if("postgresql".equals(databaseId)){
                rst = sql;
            }
        }else if("UUID(".equals(key)){
            if("postgresql".equals(databaseId)){
                rst = sql;
            }
        }
        return rst;
    }


}
