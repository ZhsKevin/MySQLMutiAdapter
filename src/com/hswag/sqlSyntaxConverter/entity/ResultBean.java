package com.hswag.sqlSyntaxConverter.entity;

public class ResultBean {
    private String projectName;

    private String filePath;

    private String sqlId;

    private String type;

    private String sql;

    private String sql_oracle;

    private String sql_sqlserver;

    private String sql_postgresql;

    private String convertedSql;

    private String modifyAdvice;

    private String isAuto;

    private String hasChange;

    private String subCommSql;

    private String replacedRefSql;

    public String getSql_postgresql() {
        return sql_postgresql;
    }

    public void setSql_postgresql(String sql_postgresql) {
        this.sql_postgresql = sql_postgresql;
    }

    public String getSubCommSql() {
        return subCommSql;
    }

    public void setSubCommSql(String subCommSql) {
        this.subCommSql = subCommSql;
    }

    public String getReplacedRefSql() {
        return replacedRefSql;
    }

    public void setReplacedRefSql(String replacedRefSql) {
        this.replacedRefSql = replacedRefSql;
    }

    public static final String JAVAFILE_PUBLICDB = "javafile_publicdb";
    public static final String MAPPERFILE_PUBLICDB = "mapperfile_publicdb";
    public static final String MYSQLKEYWORD = "mysql_keyword";
    public static final String BACKQUOTE  = "backquote";
    public static final String FUNCTION = "function";
    public static final String DDL = "ddl";
    public static final String SYSTEM_TABLE = "system_table";
    public static final String CREATE_TABLE = "create_table";
    public static final String SPLITPAGE = "splitpage";
    public static final String AUTOREPLACE = "autoreplace";
    public static final String NONE = "none";
    public static final String NOREFSQL = "no_refIdsql";
    public static final String ERRORSQL = "error_Sql";

    @Override
    public String toString() {
        return "ResultBean{" +
                "projectName='" + projectName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", sqlId='" + sqlId + '\'' +
                ", type='" + type + '\'' +
                ", sql='" + sql + '\'' +
                ", sql_oracle='" + sql_oracle + '\'' +
                ", sql_sqlserver='" + sql_sqlserver + '\'' +
                ", sql_postgresql='" + sql_postgresql + '\'' +
                ", convertedSql='" + convertedSql + '\'' +
                ", modifyAdvice='" + modifyAdvice + '\'' +
                ", isAuto='" + isAuto + '\'' +
                ", hasChange='" + hasChange + '\'' +
                ", subCommSql='" + subCommSql + '\'' +
                ", replacedRefSql='" + replacedRefSql + '\'' +
                '}';
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql_oracle() {
        return sql_oracle;
    }

    public void setSql_oracle(String sql_oracle) {
        this.sql_oracle = sql_oracle;
    }

    public String getSql_sqlserver() {
        return sql_sqlserver;
    }

    public void setSql_sqlserver(String sql_sqlserver) {
        this.sql_sqlserver = sql_sqlserver;
    }

    public String getConvertedSql() {
        return convertedSql;
    }

    public void setConvertedSql(String convertedSql) {
        this.convertedSql = convertedSql;
    }

    public String getModifyAdvice() {
        return modifyAdvice;
    }

    public void setModifyAdvice(String modifyAdvice) {
        this.modifyAdvice = modifyAdvice;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public String getHasChange() {
        return hasChange;
    }

    public void setHasChange(String hasChange) {
        this.hasChange = hasChange;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }
}
