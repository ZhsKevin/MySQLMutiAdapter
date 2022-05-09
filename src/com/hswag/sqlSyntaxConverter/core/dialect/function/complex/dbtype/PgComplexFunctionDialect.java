package com.hswag.sqlSyntaxConverter.core.dialect.function.complex.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.function.complex.BaseComplexFunctionDialect;

public class PgComplexFunctionDialect extends BaseComplexFunctionDialect {

    @Override
    public String handle_CURRENT_TIMESTAMP(String[] strs, String sql, int startFunPoint, int closeFunPoint) {
        sql = sql.substring(0, startFunPoint) + CURRENT_TIMESTAMP_REP+"::timestamp(0)without time zone "
                +((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        return sql;
    }

    @Override
    public String handle_INSTR(String[] strs, String sql, int startFunPoint, int closeFunPoint) { //不需替换，pg要先在public下创建instr()函数
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
    public String handle_CONCAT_WS(String[] strs, String sql, int startFunPoint, int closeFunPoint) {//不需替换，与mysql完全相同
        return sql;
    }
}
