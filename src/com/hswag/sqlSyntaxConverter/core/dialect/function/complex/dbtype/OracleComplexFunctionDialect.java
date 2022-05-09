package com.hswag.sqlSyntaxConverter.core.dialect.function.complex.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.BaseComplexFunctionDialect;


public class OracleComplexFunctionDialect extends BaseComplexFunctionDialect {


    @Override
    public String handle_CURRENT_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) + CURRENT_TIMESTAMP_REP
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_INSTR(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //原样返回
        return sql;
    }

    @Override
    public String handle_CONCAT(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String newparam = "";
        for(int i=0;i<strs.length;i++){
            newparam+=strs[i]+"||";
        }
        if(!"".equals(newparam)){
            newparam = newparam.substring(0,newparam.length()-2);
        }
        sql = sql.substring(0, startFunPoint) + newparam+" "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_CONCAT_WS(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        String split = strs[0].replace("'", "");
        String param = "";
        if (strs.length > 2) {
            for (int i = 1; i < strs.length; i++) {
                param += strs[i] + "||" + split;
            }
            param = param.substring(0, param.length() - 1);
        }
        sql = sql.substring(0, startFunPoint) + param+" "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

}
