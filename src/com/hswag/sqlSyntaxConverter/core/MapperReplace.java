package com.hswag.sqlSyntaxConverter.core;


import com.hswag.sqlSyntaxConverter.core.config.MapperReplaceConfig;
import com.hswag.sqlSyntaxConverter.core.dialect.function.FunctionHandler;
import com.hswag.sqlSyntaxConverter.core.dialect.function.simple.BaseSimpleFunctionDialect;
import com.hswag.sqlSyntaxConverter.core.dialect.page.PageHandler;
import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.SystemTableHandler;
import com.hswag.sqlSyntaxConverter.entity.ResultBean;
import com.hswag.sqlSyntaxConverter.util.ConvertUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapperReplace {

    private static List<String> mysql_keys = new ArrayList<>();
    private static List<String> mysql_funcs = new ArrayList<>();
    private static List<String> mysql_autoFuncs = new ArrayList<>();
    private static List<String> keywords = new ArrayList<>();

    static{
        keywords.add("<select") ;
        keywords.add("<update") ;
        keywords.add("<delete") ;
        keywords.add("<insert") ;
        keywords.add("<sql") ;


        mysql_keys.add("`COMMENT`");
        mysql_keys.add("`USER`");
        mysql_keys.add("`ORDER`");
        mysql_keys.add("`CHECK`");
        mysql_keys.add("`DATE`");
        mysql_keys.add("`DESC`");
        mysql_keys.add("`FROM`");
        mysql_keys.add("`INCREMENT`");
        mysql_keys.add("`INDEX`");
        mysql_keys.add("`LEVEL`");
        mysql_keys.add("`MODE`");
        mysql_keys.add("`NUMBER`");
        mysql_keys.add("`ONLINE`");
        mysql_keys.add("`OPTION`");
        mysql_keys.add("`ORDER`");
        mysql_keys.add("`RESOURCE`");
        mysql_keys.add("`ROWID`");
        mysql_keys.add("`ROW`");
        mysql_keys.add("`SIZE`");
        mysql_keys.add("`START`");
        mysql_keys.add("`TABLE`");
        mysql_keys.add("`TOTAL`");
        mysql_keys.add("`TO`");
        mysql_keys.add("`UID`");
        mysql_keys.add("`USER`");
        mysql_keys.add("`VIEW`");
        mysql_keys.add("`CURRENT_TIMESTAMP`");
        mysql_keys.add("`EXTERNAL`");
        mysql_keys.add("`FUNCTION`");
        mysql_keys.add("`KEY`");
        mysql_keys.add("`LEFT`");
        mysql_keys.add("`MERGE`");
        mysql_keys.add("`OPEN`");
        mysql_keys.add("`OUTER`");
        mysql_keys.add("`PERCENT`");
        mysql_keys.add("`PRECISION`");
        mysql_keys.add("`READ`");
        mysql_keys.add("`RIGHT`");
        mysql_keys.add("`SCHEMA`");
        mysql_keys.add("`TOP`");
        mysql_keys.add("`PLAN`");
        mysql_keys.add("`RULE`");
        mysql_keys.add("`DESCRIBE`");

        mysql_autoFuncs.add("NOW(");
        mysql_autoFuncs.add("DATEDIFF(");
        mysql_autoFuncs.add("CONVERT(");
        mysql_autoFuncs.add("CURRENT_TIMESTAMP(");
        mysql_autoFuncs.add("WEEKDAY(");
        mysql_autoFuncs.add("MOD(");
        mysql_autoFuncs.add("DAYOFMONTH(");
        mysql_autoFuncs.add("QUARTER(");
        mysql_autoFuncs.add("DAYOFYEAR(");
        mysql_autoFuncs.add("LAST_DAY(");
        mysql_autoFuncs.add("DAY(");
        mysql_autoFuncs.add("SUBSTRING(");
        mysql_autoFuncs.add("SUBSTR(");
        mysql_autoFuncs.add("LENGTH(");
        mysql_autoFuncs.add("CHAR_LENGTH(");
        mysql_autoFuncs.add("INSTR(");
        mysql_autoFuncs.add("UUID(");
        mysql_autoFuncs.add("ISNULL(");
        mysql_autoFuncs.add("IFNULL(");
        mysql_autoFuncs.add("LEFT(");
        mysql_autoFuncs.add("RIGHT(");
        mysql_autoFuncs.add("YEARWEEK(");
        mysql_autoFuncs.add("WEEK(");
        mysql_autoFuncs.add("MONTH(");
        mysql_autoFuncs.add("YEAR(");
        mysql_autoFuncs.add("DATABASE(");
        mysql_autoFuncs.add("DATE_FORMAT(");
        mysql_autoFuncs.add("STR_TO_DATE(");
        mysql_autoFuncs.add("TIMESTAMPDIFF(");
        mysql_autoFuncs.add("DATE_ADD(");
        mysql_autoFuncs.add("DATE_SUB(");
        mysql_autoFuncs.add("UNIX_TIMESTAMP(");
        mysql_autoFuncs.add("CURDATE(");
        mysql_autoFuncs.add("GROUP_CONCAT(");// oracle???????????? ???sqlserver????????????
        mysql_autoFuncs.add("CONCAT(");
        mysql_autoFuncs.add("CONCAT_WS(");
        mysql_autoFuncs.add(" DATE(");
        mysql_autoFuncs.add("CURTIME(");
        mysql_autoFuncs.add("TO_DAYS(");
        mysql_autoFuncs.add("TRIM(");
        mysql_autoFuncs.add("IF(");

        mysql_funcs.add("INTERVAL(");
        mysql_funcs.add("ELT(");
        mysql_funcs.add("FORMAT(");
        mysql_funcs.add("MAKEDATE(");
        mysql_funcs.add("EXTRACT(");
        mysql_funcs.add("LOCATE(");
    }



    public static ResultBean handleMapperReplace(String projectRootPath,String filePath, String sql, String sql_withtrim, boolean iscover){  //??????sqlMapper??????
        boolean only_pg = MapperReplaceConfig.isOnlyPg();
        //??????sql??????????????????
        String sql_rst = sql;
        String sql_oracle = sql_withtrim;
        String sql_sqlserver = sql_withtrim;
        String sql_postgresql = sql_withtrim;
        boolean need_sqlAdd = false;
        //??????sqlId
        String sqlId = ConvertUtil.getSqlId(sql);
        //???????????????????????????
        String type = "";
        ResultBean rb = new ResultBean();
        rb.setSql(sql);
        rb.setSqlId(sqlId);
        rb.setFilePath(filePath);
        //??????????????????
        try {
            String[] project_args = projectRootPath.split("\\\\");
            if (sqlId == null || filePath == null) {
                System.out.println("$$$$filepath==" + filePath + " <<sqlId>>" + sqlId + " sql==" + sql);
            }
            rb.setProjectName(project_args[project_args.length - 1]);
        }catch (Exception e){
            e.printStackTrace();
        }
        //?????????????????????????????????????????????????????????
        int pos4 = sql_withtrim.indexOf(">");
        if(pos4>0){
            if(sql_withtrim.indexOf("databaseId=")<0||sql_withtrim.indexOf("databaseId=\"mysql\"")>0) { //?????????mysql??????
                sql_rst = sql_rst.replace(" databaseId=\"mysql\"","");
                sql_oracle = sql_withtrim.substring(0, pos4) + " databaseId=\"oracle\"" + sql_withtrim.substring(pos4, sql_withtrim.length());
                sql_oracle = sql_oracle.replace(" databaseId=\"mysql\"","");
                sql_sqlserver = sql_withtrim.substring(0, pos4) + " databaseId=\"sqlserver\"" + sql_withtrim.substring(pos4, sql_withtrim.length());
                sql_sqlserver = sql_sqlserver.replace(" databaseId=\"mysql\"","");
                sql_postgresql = sql_withtrim.substring(0, pos4) + " databaseId=\"postgresql\"" + sql_withtrim.substring(pos4, sql_withtrim.length());
                sql_postgresql = sql_postgresql.replace(" databaseId=\"mysql\"","");
            }else{
                if(iscover){ //?????????oracle??????????????????????????????oracle?????????????????? (??????mysql?????????????????????????????????)
                    if(only_pg){
                        if(sql_rst.indexOf("databaseId=\"oracle\"")>0||sql_rst.indexOf("databaseId=\"sqlserver\"")>0){
                            rb.setHasChange("???");
                            rb.setConvertedSql(sql_rst);
                            return rb ;
                        }else{
                            rb.setHasChange("???");
                            rb.setConvertedSql("");
                            return rb ;
                        }
                    }else{
                        rb.setHasChange("???");
                        rb.setConvertedSql("");
                        return rb ;
                    }
                }
            }
        }
        //???????????????sql??????ID
        String oralce_unqkey = sqlId+"_databaseId=oracle";
        String sqlserver_unqkey = sqlId+"_databaseId=sqlserver";
        String postgresql_unqkey = sqlId+"_databaseId=postgresql";
        List<String> keysss = ConvertUtil.getIds(filePath);
        //??????refSql??????
        if(sql_oracle.indexOf("<include")>-1) {
            Map<String,String> refs = ConvertUtil.refSqls(filePath);
            rb = ConvertUtil.replaceRefSql(rb,type,sql_oracle,sql_rst,refs);
            String sql_reptmp = rb.getReplacedRefSql();
            if(sql_reptmp!=null&&!"".equals(sql_reptmp)){
                sql_oracle = sql_reptmp;
                sql_reptmp = null;
            }else{
                return rb;
            }
            rb = ConvertUtil.replaceRefSql(rb,type,sql_sqlserver,sql_rst,refs);
            sql_reptmp = rb.getReplacedRefSql();
            if(sql_reptmp!=null&&!"".equals(sql_reptmp)){
                sql_sqlserver = sql_reptmp;
                sql_reptmp = null;
            }else{
                return rb;
            }
            rb = ConvertUtil.replaceRefSql(rb,type,sql_postgresql,sql_rst,refs);
            sql_reptmp = rb.getReplacedRefSql();
            if(sql_reptmp!=null&&!"".equals(sql_reptmp)){
                sql_postgresql = sql_reptmp;
                sql_reptmp = null;
            }else{
                return rb;
            }
        }
        //??????sql????????????
        rb = ConvertUtil.subComment(rb,type,sql_oracle,sql_rst);
        String sql_tmp = rb.getSubCommSql();
        if(sql_tmp!=null&&!"".equals(sql_tmp)){
            sql_oracle = sql_tmp;
            sql_tmp = null;
        }else{
            return rb;
        }
        rb = ConvertUtil.subComment(rb,type,sql_sqlserver,sql_rst);
        sql_tmp = rb.getSubCommSql();
        if(sql_tmp!=null&&!"".equals(sql_tmp)){
            sql_sqlserver = sql_tmp;
            sql_tmp = null;
        }else{
            return rb;
        }
        rb = ConvertUtil.subComment(rb,type,sql_postgresql,sql_rst);
        sql_tmp = rb.getSubCommSql();
        if(sql_tmp!=null&&!"".equals(sql_tmp)){
            sql_postgresql = sql_tmp;
            sql_tmp = null;
        }else{
            return rb;
        }

        //????????????????????????????????????
        if(sql.contains("eteams")||sql.contains("ETEAMS")){
            sql_rst = BaseSimpleFunctionDialect.keyReplace(sql,"ETEAMS.","{$publicdb}.");
            sql_oracle = BaseSimpleFunctionDialect.keyReplace(sql_oracle,"ETEAMS.","{$publicdb}.");
            sql_sqlserver =  BaseSimpleFunctionDialect.keyReplace(sql_sqlserver,"ETEAMS.","{$publicdb}.");
            sql_postgresql =  BaseSimpleFunctionDialect.keyReplace(sql_postgresql,"ETEAMS.","{$publicdb}.");
            type+= ResultBean.MAPPERFILE_PUBLICDB+",";
            rb.setHasChange("???");
            rb.setIsAuto("???");
        }
        //???????????????????????????????????? -->???????????????????????????refid sql???????????????
        if(sql_oracle.contains("eteams")||sql_oracle.contains("ETEAMS")){
            sql_oracle =  BaseSimpleFunctionDialect.keyReplace(sql_oracle,"ETEAMS.","{$publicdb}.");
            sql_sqlserver =  BaseSimpleFunctionDialect.keyReplace(sql_sqlserver,"ETEAMS.","{$publicdb}.");
            sql_postgresql =  BaseSimpleFunctionDialect.keyReplace(sql_postgresql,"ETEAMS.","{$publicdb}.");
            type+= ResultBean.MAPPERFILE_PUBLICDB+",";
            rb.setHasChange("???");
            rb.setIsAuto("???");
            need_sqlAdd = true;
        }
        //??????????????????????????????

        //dual?????????
        if(sql_sqlserver.contains(" DUAL")||sql_sqlserver.contains(" dual") ){
            sql_sqlserver = sql_sqlserver.replace("FROM DUAL","").replace("FROM dual","")
                    .replace("from DUAL","").replace("from dual","");
            sql_postgresql = sql_postgresql.replace("FROM DUAL","").replace("FROM dual","")
                    .replace("from DUAL","").replace("from dual","");
            type+="from dual,";
            rb.setHasChange("???");
            rb.setIsAuto("???");
            need_sqlAdd = true;
        }

        //sql??????????????????
        if(sql.contains(";")){
            String sql_conv = ConvertUtil.subLastSemi(sql);
            if(!sql_conv.equals(sql)) {
                sql_rst = ConvertUtil.subLastSemi(sql);
                sql_oracle = ConvertUtil.subLastSemi(sql_oracle);
                sql_sqlserver = ConvertUtil.subLastSemi(sql_sqlserver);
                sql_postgresql = ConvertUtil.subLastSemi(sql_postgresql);
                type += "Semi,";
                rb.setHasChange("???");
                rb.setIsAuto("???");
            }
        }

        //??????bactch insert values ==>oracle
        if(sql.contains("<insert")){
            String conv = ConvertUtil.convertOracleBatchInsert(sql_oracle);
            if(!conv.equals(sql_oracle)){
                sql_oracle = conv;
                type +=  "batchInsert,";
                rb.setHasChange("???");
                rb.setIsAuto("???");
                need_sqlAdd = true;
            }
        }

        //??????batch update
        if(sql.contains("<update")){
            String oracle_conv = ConvertUtil.converBatchUpdate(sql_oracle);
            if(!oracle_conv.equals(sql_oracle)){
                sql_oracle = oracle_conv;
                type +=  "batchUpdate,";
                rb.setHasChange("???");
                rb.setIsAuto("???");
                need_sqlAdd = true;
            }
            String sqlserver_conv = ConvertUtil.converBatchUpdate(sql_sqlserver);
            if(!sqlserver_conv.equals(sql_sqlserver)){
                sql_sqlserver = sqlserver_conv;
                type +=  "batchUpdate,";
                rb.setHasChange("???");
                rb.setIsAuto("???");
                need_sqlAdd = true;
            }
            String pg_conv = ConvertUtil.converBatchUpdate(sql_postgresql);
            if(!pg_conv.equals(sql_postgresql)){
                sql_postgresql = pg_conv;
                type +=  "batchUpdate,";
                rb.setHasChange("???");
                rb.setIsAuto("???");
                need_sqlAdd = true;
            }
        }

        //mysql?????????????????????
        for(String key:mysql_keys){
            if(sql_oracle.contains(key)||sql_oracle.contains(key.toLowerCase())){
                sql_oracle = sql_oracle.replace(key,key.replace("`","\"")).replace(key.toLowerCase(),key.replace("`","\""));
                sql_sqlserver = sql_sqlserver.replace(key,key.replace("`","\"")).replace(key.toLowerCase(),key.toLowerCase().replace("`","\""));
                sql_postgresql = sql_postgresql.replace(key,key.replace("`","\"")).replace(key.toLowerCase(),key.toLowerCase().replace("`","\""));
                type+= ResultBean.MYSQLKEYWORD+",";
                rb.setHasChange("???");
                rb.setIsAuto("???");
                need_sqlAdd = true;
            }
        }
        //mysql?????????????????????

        //?????????????????????
        if(sql_oracle.contains("`")){
            sql_oracle = sql_oracle.replace("`","");
            sql_sqlserver = sql_sqlserver.replace("`","");
            sql_postgresql = sql_postgresql.replace("`","");
            type+= ResultBean.BACKQUOTE+",";
            rb.setIsAuto("???");
            rb.setHasChange("???");
            need_sqlAdd = true;
        }
        //?????????????????????

        //??????????????????
        for(String key:mysql_autoFuncs) {
            if (sql_oracle.contains(key) || sql_oracle.contains(key.toLowerCase())) {
                rb.setHasChange("???");
                if (!"???".equals(rb.getIsAuto())) {
                    rb.setIsAuto("???");
                }
                need_sqlAdd = true;
                Map<String, String> map1 = FunctionHandler.functionHandle(key,sql_oracle,"oracle");
                Map<String, String> map2 = FunctionHandler.functionHandle(key,sql_sqlserver,"sqlserver");
                Map<String, String> map3 = FunctionHandler.functionHandle(key,sql_postgresql,"postgresql");
                sql_oracle = map1.get("sql");
                sql_sqlserver = map2.get("sql");
                sql_postgresql = map3.get("sql");
                String result1 = map1.get("result");
                String result2 = map2.get("result");
                String result3 = map3.get("result");
                if ("error".equals(result1)||"error".equals(result2)||"error".equals(result3)) {
                    rb.setIsAuto("???");
                    type += ResultBean.FUNCTION + "(" + key + ")),";
                }
            }
        }

        //???????????????????????????,???????????????
        for(String key: mysql_funcs){
            if(sql_oracle.contains(key)){
                type+= ResultBean.FUNCTION+"("+key+")),";
                rb.setIsAuto("???");
                rb.setHasChange("???");
                need_sqlAdd = true;
            }
        }
        //??????????????????

        //DDL????????????
        if(sql_oracle.contains("DROP INDEX")||sql_oracle.contains("ALTER TABLE")||
                sql_oracle.contains("drop index")||sql_oracle.contains("alter table")
                    ||sql_oracle.contains("DROP TABLE")||sql_oracle.contains("drop table")
                ||sql_oracle.contains("FORCE INDEX")||sql_oracle.contains("force index")
        ){
            boolean isauto = true;
            if(sql_oracle.contains("DROP TABLE")||sql_oracle.contains("drop table")){ //????????????if exists
                sql_oracle = BaseSimpleFunctionDialect.keyReplace(sql_oracle,"IF EXISTS","");
                sql_oracle = BaseSimpleFunctionDialect.keyReplace(sql_oracle,"IF NOT EXISTS","");
                sql_sqlserver = BaseSimpleFunctionDialect.keyReplace(sql_sqlserver,"IF EXISTS","");
                sql_sqlserver = BaseSimpleFunctionDialect.keyReplace(sql_sqlserver,"IF NOT EXISTS","");
                sql_postgresql = BaseSimpleFunctionDialect.keyReplace(sql_postgresql,"IF EXISTS","");
                sql_postgresql = BaseSimpleFunctionDialect.keyReplace(sql_postgresql,"IF NOT EXISTS","");
                type+= ResultBean.DDL+",";
                if(!"???".equals(rb.getIsAuto())) {
                    rb.setIsAuto("???");
                }
                rb.setHasChange("???");
                need_sqlAdd = true;
            }else if(sql_oracle.contains("FORCE INDEX(")||sql_oracle.contains("FORCE INDEX(".toLowerCase())){ // ????????????force index
                try {
                    String key = "FORCE INDEX(";
                    sql_oracle = ConvertUtil.commonConver(key,sql_oracle,"oracle").get("sql");
                    sql_sqlserver = ConvertUtil.commonConver(key,sql_sqlserver,"sqlserver").get("sql");
                    sql_postgresql = ConvertUtil.commonConver(key,sql_postgresql,"postgresql").get("sql");
                    type += ResultBean.DDL + ",";
                    if (!"???".equals(rb.getIsAuto())) {
                        rb.setIsAuto("???");
                    }
                    if(!isauto){
                        rb.setIsAuto("???");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    rb.setIsAuto("???");
                }
                rb.setHasChange("???");
                need_sqlAdd = true;
            }else if(sql_oracle.contains("FORCE INDEX (")||sql_oracle.contains("FORCE INDEX (".toLowerCase())){ // ????????????force index
                try {
                    String key = "FORCE INDEX (";
                    sql_oracle = ConvertUtil.commonConver(key,sql_oracle,"oracle").get("sql");
                    sql_sqlserver = ConvertUtil.commonConver(key,sql_sqlserver,"sqlserver").get("sql");
                    sql_postgresql = ConvertUtil.commonConver(key,sql_postgresql,"postgresql").get("sql");
                    type += ResultBean.DDL + ",";
                    if (!"???".equals(rb.getIsAuto())) {
                        rb.setIsAuto("???");
                    }
                    if(!isauto){
                        rb.setIsAuto("???");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    rb.setIsAuto("???");
                }
                rb.setHasChange("???");
                need_sqlAdd = true;
            }else {
                type += ResultBean.DDL + ",";
                rb.setIsAuto("???");
                rb.setHasChange("???");
                need_sqlAdd = true;
            }
        }
        //DDL????????????


        //?????????????????????
        if(sql_oracle.contains("INFORMATION_SCHEMA.TABLES")||sql_oracle.contains(("INFORMATION_SCHEMA.TABLES").toLowerCase())
                ||sql_oracle.contains("INFORMATION_SCHEMA.COLUMNS")||sql_oracle.contains(("INFORMATION_SCHEMA.COLUMNS").toLowerCase())
                ||sql_oracle.contains("information_schema.TABLES")||sql_oracle.contains("information_schema.COLUMNS")
        ){
            sql_sqlserver = sql_sqlserver.replace(" TABLE_SCHEMA"," TABLE_catalog").
                    replace(" TABLE_SCHEMA".toLowerCase()," TABLE_catalog");
            sql_oracle = sql_oracle.replace("INFORMATION_SCHEMA.TABLES","all_tables")
                    .replace("INFORMATION_SCHEMA.COLUMNS","all_tab_columns").replace("TABLE_SCHEMA","owner")
                    .replace("INFORMATION_SCHEMA.TABLES".toLowerCase(),"all_tables")
                    .replace("INFORMATION_SCHEMA.COLUMNS".toLowerCase(),"all_tab_columns").replace("TABLE_SCHEMA".toLowerCase(),"owner")
            .replace("information_schema.TABLES","all_tab_columns").replace("information_schema.COLUMNS","all_tab_columns");
            boolean isauto = true;
            try {
                String sql_oracle_ori = sql_oracle;
                sql_oracle = SystemTableHandler.getSystemTableSql(sql_oracle,sql_oracle_ori,"oracle");
                type += ResultBean.SYSTEM_TABLE + ",";
                if (!"???".equals(rb.getIsAuto())) {
                    rb.setIsAuto("???");
                }
                if (!isauto) {
                    rb.setIsAuto("???");
                }
            }catch (Exception e){
                e.printStackTrace();
                rb.setHasChange("???");
                rb.setIsAuto("???");
            }
            need_sqlAdd = true;
        }
        //?????????????????????

        //?????????????????????
        if(sql_oracle.contains("ENGINE=InnoDB")||sql_oracle.contains(" bigint")
                ||sql_oracle.contains(" BIGINT")||sql_oracle.contains(" AUTO_INCREMENT")
                ||sql_oracle.contains(" auto_increment")){ //?????????  ????????????
            type+= ResultBean.CREATE_TABLE+",";
            rb.setIsAuto("???");
            rb.setHasChange("???");
            need_sqlAdd = true;
        }
        //?????????????????????

        //??????????????????
        //??????????????????
        if(sql_oracle.contains(" limit ")||sql_oracle.contains(" LIMIT ")
                ||sql_oracle.contains("\tlimit ")||sql_oracle.contains("\tLIMIT ")
                ||sql_oracle.contains("\nlimit ")||sql_oracle.contains("\nLIMIT ")
                ||sql_oracle.contains("\rlimit ")||sql_oracle.contains("\rLIMIT ")) { //??????sql ????????????
            boolean isauto = true;
            try {
                int k = 0;
                String sql_oracle_ori = sql_oracle;
                String sql_sqlserver_ori = sql_sqlserver;
                String sql_postgresql_ori = sql_postgresql;
                sql_oracle = PageHandler.getPageSql(sql_oracle,sql_oracle_ori,"oracle");
                if(sql_oracle.equals(sql_oracle_ori)){
                    isauto = false;
                }
                sql_sqlserver = PageHandler.getPageSql(sql_sqlserver,sql_sqlserver_ori,"sqlserver");
                if(sql_sqlserver.equals(sql_sqlserver_ori)){
                    isauto = false;
                }
                sql_postgresql = PageHandler.getPageSql(sql_postgresql,sql_postgresql_ori,"postgresql");
                if(sql_postgresql.equals(sql_postgresql_ori)){
                    isauto = false;
                }
                if(!"???".equals(rb.getIsAuto())) {
                    rb.setIsAuto("???");
                }
                if(!isauto){
                    rb.setIsAuto("???");
                }
            }catch (Exception e){
                e.printStackTrace();
                rb.setIsAuto("???");
            }
            type+= ResultBean.SPLITPAGE+",";
            rb.setHasChange("???");
            need_sqlAdd = true;
        }
        rb.setType(type);
        //??????????????????

        //?????????????????????sql
        if(need_sqlAdd){
            boolean needSqlserver = true;
            boolean needOracle = true;
            boolean needPg = true;
            for(String ss:keysss){
                if(sqlserver_unqkey.equals(ss)&&!iscover){//??????????????????
                    needSqlserver = false;
                }
                if(oralce_unqkey.equals(ss)&&!iscover){//??????????????????
                    needOracle = false;
                }
                if(postgresql_unqkey.equals(ss)&&!iscover){//??????????????????
                    needPg = false;
                }
            }
            if(only_pg){
                needSqlserver = false;
                needOracle = false;
            }
            if(needOracle){
                sql_rst+=sql_oracle;
                rb.setSql_oracle(sql_oracle);
            }
            if(needSqlserver){
                sql_rst+=sql_sqlserver;
                rb.setSql_sqlserver(sql_sqlserver);
            }
            if(needPg){
                sql_rst+=sql_postgresql;
                rb.setSql_postgresql(sql_postgresql);
            }
            if(!needSqlserver&&!needOracle&&!needPg){
                if(!"???".equals(rb.getHasChange())) { //?????????????????????
                    rb.setHasChange("???");
                }
            }
            if("???".equals(rb.getIsAuto())){
               // System.out.println("###"+sql_oracle);
                //System.out.println("###"+sql_sqlserver);
               // System.out.println("###"+sql_postgresql);
            }
            rb.setSql_oracle(sql_oracle);
            rb.setSql_sqlserver(sql_sqlserver);
            rb.setSql_postgresql(sql_postgresql);
        }
        rb.setConvertedSql(sql_rst);
        return rb;
    }






}
