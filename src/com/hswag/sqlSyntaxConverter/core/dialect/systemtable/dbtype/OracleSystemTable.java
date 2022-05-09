package com.hswag.sqlSyntaxConverter.core.dialect.systemtable.dbtype;

import com.hswag.sqlSyntaxConverter.core.dialect.systemtable.SystemTableHandler;

public class OracleSystemTable extends SystemTableHandler {
    private static String ownerREP_key = "~#OW_REP_KEY#~";
    @Override
    public String getSystemTableSqlInner(String sql, String sql_orignal) {
        int k = 0;
        while (sql.contains(" owner")) {
            k++;
            if (k > 500) {
                System.out.println("系统表规则处理异常");
                sql = sql_orignal;
                break;
            }
            String key = " owner";
            int pos11 = sql.indexOf(key);
            String sql_ori = sql;
            int cnt = 0;
            boolean isRight = false;
            String right = null;
            int qut_cnt = 0;
            int last_eqpos = -1;
            for (int i = pos11 + key.length(); i < sql.length(); i++) {
                String str = String.valueOf(sql.charAt(i));
                if (" ".equals(str)) {
                    cnt++;
                    if (cnt > 3) { //不允许=表达式有过多的空格
                        break;
                    }
                    if (right != null && qut_cnt == 0) {
                        sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                                + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                        break;
                    }
                } else if ("=".equals(str) && last_eqpos == -1) { // =表达式重置
                    cnt = 0;
                    last_eqpos = i;
                    isRight = true;
                } else if (qut_cnt == 0 && ")".equals(str) && right != null) { // =表达式重置
                    sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                    break;
                } else if ((i == sql.length() - 1) && qut_cnt == 0 && right != null) {
                    sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i + 1).trim() + ")";
                    break;
                } else {
                    if (last_eqpos > -1) { // =表达式
                        if (isRight && right == null) {
                            right = str;
                        } else {
                            right += str;
                            if ("(".equals(str)) {
                                qut_cnt++;
                            } else if (")".equals(str)) {
                                qut_cnt--;
                            }
                        }
                    } else {  //异常，没有等号且非空字符
                        break;
                    }
                }
            }
            if (sql_ori.equals(sql)) { //没有替换成功的情况下，直接结束，否则会死循环
                break;
            }
        }
        //sql = sql.replace(ownerREP_key, " owner");
        while (sql.contains(" OWNER")) {
            k++;
            if (k > 500) {
                System.out.println("系统表规则处理异常");
                sql = sql_orignal;
                break;
            }
            String key = " OWNER";
            int pos11 = sql.indexOf(key);
            String sql_ori = sql;
            int cnt = 0;
            boolean isRight = false;
            String right = null;
            int qut_cnt = 0;
            int last_eqpos = -1;
            for (int i = pos11 + key.length(); i < sql.length(); i++) {
                String str = String.valueOf(sql.charAt(i));
                if (" ".equals(str)) {
                    cnt++;
                    if (cnt > 3) { //不允许=表达式有过多的空格
                        break;
                    }
                    if (right != null && qut_cnt == 0) {
                        sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                                + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                        break;
                    }
                } else if ("=".equals(str) && last_eqpos == -1) { // =表达式重置
                    cnt = 0;
                    last_eqpos = i;
                    isRight = true;
                } else if (qut_cnt == 0 && ")".equals(str) && right != null) { // =表达式重置
                    sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                    break;
                } else if ((i == sql.length() - 1) && qut_cnt == 0 && right != null) {
                    sql = sql.substring(0, pos11 + key.length()).replace(key, ownerREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i + 1).trim() + ")";
                    break;
                } else {
                    if (last_eqpos > -1) { // =表达式
                        if (isRight && right == null) {
                            right = str;
                        } else {
                            right += str;
                            if ("(".equals(str)) {
                                qut_cnt++;
                            } else if (")".equals(str)) {
                                qut_cnt--;
                            }
                        }
                    } else {  //异常，没有等号且非空字符
                        break;
                    }
                }
            }
            if (sql_ori.equals(sql)) { //没有替换成功的情况下，直接结束，否则会死循环
                break;
            }
        }
        sql = sql.replace(ownerREP_key, " owner");
        String tabNameREP_key = "~#TB_REP_KEY#~";
        while (sql.contains(" table_name")) {
            k++;
            if (k > 500) {
                System.out.println("系统表规则处理异常");
                sql = sql_orignal;
                break;
            }
            String key = " table_name";
            int pos11 = sql.indexOf(key);
            String sql_ori = sql;
            int cnt = 0;
            boolean isRight = false;
            String right = null;
            int qut_cnt = 0;
            int last_eqpos = -1;
            for (int i = pos11 + key.length(); i < sql.length(); i++) {
                String str = String.valueOf(sql.charAt(i));
                if (" ".equals(str)) {
                    cnt++;
                    if (cnt > 3) { //不允许=表达式有过多的空格
                        break;
                    }
                    if (right != null && qut_cnt == 0) {
                        sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                                + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                        break;
                    }
                } else if ("=".equals(str) && last_eqpos == -1) { // =表达式重置
                    cnt = 0;
                    last_eqpos = i;
                    isRight = true;
                } else if (qut_cnt == 0 && ")".equals(str) && right != null) { // =表达式重置
                    sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                    break;
                } else if ((i == sql.length() - 1) && qut_cnt == 0 && right != null) {
                    sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i + 1).trim() + ")";
                    break;
                } else {
                    if (last_eqpos > -1) { // =表达式
                        if (isRight && right == null) {
                            right = str;
                        } else {
                            right += str;
                            if ("(".equals(str)) {
                                qut_cnt++;
                            } else if (")".equals(str)) {
                                qut_cnt--;
                            }
                        }
                    } else {  //异常，没有等号且非空字符
                        break;
                    }
                }
            }
            if (sql_ori.equals(sql)) { //没有替换成功的情况下，直接结束，否则会死循环
                break;
            }
        }
        while (sql.contains(" TABLE_NAME")) {
            k++;
            if (k > 500) {
                System.out.println("系统表规则处理异常");
                break;
            }
            String key = " TABLE_NAME";
            int pos11 = sql.indexOf(key);
            String sql_ori = sql;
            int cnt = 0;
            boolean isRight = false;
            String right = null;
            int qut_cnt = 0;
            int last_eqpos = -1;
            for (int i = pos11 + key.length(); i < sql.length(); i++) {
                String str = String.valueOf(sql.charAt(i));
                if (" ".equals(str)) {
                    cnt++;
                    if (cnt > 3) { //不允许=表达式有过多的空格
                        break;
                    }
                    if (right != null && qut_cnt == 0) {
                        sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                                + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                        break;
                    }
                } else if ("=".equals(str) && last_eqpos == -1) { // =表达式重置
                    cnt = 0;
                    last_eqpos = i;
                    isRight = true;
                } else if (qut_cnt == 0 && ")".equals(str) && right != null) { // =表达式重置
                    sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i).trim() + ")" + sql.substring(i, sql.length());
                    break;
                } else if ((i == sql.length() - 1) && qut_cnt == 0 && right != null) {
                    sql = sql.substring(0, pos11 + key.length()).replace(key, tabNameREP_key)
                            + "=UPPER(" + sql.substring(last_eqpos + 1, i + 1).trim() + ")";
                    break;
                } else {
                    if (last_eqpos > -1) { // =表达式
                        if (isRight && right == null) {
                            right = str;
                        } else {
                            right += str;
                            if ("(".equals(str)) {
                                qut_cnt++;
                            } else if (")".equals(str)) {
                                qut_cnt--;
                            }
                        }
                    } else {  //异常，没有等号且非空字符
                        break;
                    }
                }
            }
            if (sql_ori.equals(sql)) { //没有替换成功的情况下，直接结束，否则会死循环
                break;
            }
        }
        sql = sql.replace(tabNameREP_key, " TABLE_NAME");
        return sql;
    }
}
