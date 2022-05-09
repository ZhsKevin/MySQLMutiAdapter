package com.hswag.sqlSyntaxConverter.core.dialect.function;

import com.hswag.sqlSyntaxConverter.core.dialect.function.common.BaseCommonFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.BaseComplexFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.BaseSimpleFunctionDialect;

import java.util.Map;

public class FunctionHandler {

    public static Map<String,String> functionHandle(String key, String sql, String databaseId) {
        if("NOW(".equals(key)||"SUBSTRING(".equals(key)||"SUBSTR(".equals(key)||"LENGTH(".equals(key)||"CHAR_LENGTH(".equals(key)
        ||"UUID(".equals(key)||"IFNULL(".equals(key)||"DATABASE(".equals(key)||"CURDATE(".equals(key)||"CURTIME(".equals(key)) {
            return  BaseSimpleFunctionDialect.functionSimpleHandle(key,sql,databaseId);
        }else if("CURRENT_TIMESTAMP(".equals(key)||"INSTR(".equals(key)||"CONCAT(".equals(key)||"CONCAT_WS(".equals(key)){
            return  BaseComplexFunctionDialect.functionComplexHandle(key,sql,databaseId);
        }else{
            return  BaseCommonFunctionDialect.functionCommonHandle(key,sql,databaseId);
        }
    }


}
