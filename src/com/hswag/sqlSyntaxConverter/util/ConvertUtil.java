package com.hswag.sqlSyntaxConverter.util;

import com.hswag.sqlSyntaxConverter.entity.ResultBean;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtil {
    private static List<String> keywords = new ArrayList<>();

    static {
        keywords.add("<select");
        keywords.add("<update");
        keywords.add("<delete");
        keywords.add("<insert");
        keywords.add("<sql");
    }

    /**
     *  去掉sql结尾的分号<br>
     * @param sql
     * @return
     */
    public static String subLastSemi(String sql){
        String lastKey = null;
        for(String key:keywords){
            if(sql.trim().startsWith(key)){
                lastKey = key; //匹配符
                break;
            }
        }
        if(lastKey!=null){
            int offset1 = sql.indexOf(lastKey);
            int offset2 = sql.indexOf(lastKey.replace("<","</"));
            if(offset2>offset1&&offset1>-1) {
                String str1 = sql.substring(0, offset1);
                String str2 = sql.substring(offset1, offset2);
                if(str2.trim().endsWith(";")){
                    int lastpos = str2.lastIndexOf(";");
                    if(lastpos>-1){
                        str2 = str2.substring(0,lastpos);
                    }
                }else{
                    return sql;
                }
                String str3 = sql.substring(offset2, sql.length());
                return str1+str2+"\n"+str3;
            }else{
                return sql;
            }
        }
        return sql;
    }


    /**
     *  去掉sql中的注释<br>
     * @param sql
     * @return
     */
    public static ResultBean subComment(ResultBean rb,String type ,String sql,String sql_rst){
        ResultBean rst = rb;
        int cnntr =0;
        boolean flag = true;
        while(sql.contains("<!--")){
            cnntr++;
            if(cnntr>10000){
                type+=ResultBean.ERRORSQL+",";
                rst.setHasChange("是");
                rst.setIsAuto("否");
                rst.setType(type);
                rst.setConvertedSql(sql_rst);
                return rst ;
            }
            int pos_11 = sql.indexOf("<!--");
            int pos_12 = sql.indexOf("-->",pos_11);
            if(pos_12<0){
                type+=ResultBean.ERRORSQL+",";
                rst.setHasChange("是");
                rst.setIsAuto("否");
                rst.setType(type);
                rst.setConvertedSql(sql_rst);
                return rst ;
            }
            try {
                sql = sql.substring(0, pos_11) + sql.substring(pos_12 + "-->".length(), sql.length());
            }catch (Exception e){
                e.printStackTrace();
                flag = false;
                break;
            }
        }
        if(flag) {
            rst.setSubCommSql(sql);
        }else{
            rst.setConvertedSql(sql_rst);
        }
        return rst ;
    }

    /**
     * 获取sql中的sqlId<br>
     * @param sql
     * @return
     */
    public static String getSqlId(String sql){
        int pos1 = sql.indexOf("id=\"");
        int pos2 = sql.indexOf("\"",pos1);
        int pos3 = sql.indexOf("\"",pos1+5);
        String sqlId = null;
        if(pos3>pos2&&pos2>-1&&pos1>-1){
            sqlId = sql.substring(pos2+1,pos3);
            sqlId = sqlId.replace("\"","").replace("'","");
           // System.out.println("==sqlId=="+sqlId);
        }
        return sqlId;
    }

    /**
     *  替换这个sql的refId Sql内容<br>
     * @param rb
     * @param type
     * @param sql
     * @param sql_rst
     * @param refs
     * @return
     */
    public static ResultBean replaceRefSql(ResultBean rb,String type,String sql,String sql_rst,Map<String,String> refs){
        int k = 0;
        while (sql.indexOf("<include") > -1) { //替换ref
            k++;
            if(k>500){
                System.out.println("==ref replace error=="+sql+"####"+refs.keySet());
                type+= ResultBean.NOREFSQL+",";
                rb.setHasChange("是");
                rb.setIsAuto("否");
                rb.setType(type);
                rb.setConvertedSql(sql_rst);
                return rb ;
            }
            int pos_1 = sql.indexOf("<include");
            int pos_2 = sql.indexOf("/>", pos_1);
            String tmpkey ="/>";
            if(pos_2<0){
                pos_2 = sql.indexOf("</include>",pos_1);
                tmpkey ="</include>";
            }else{
                int pos22 = sql.indexOf("</include>",pos_1);
                if(pos22>-1&&pos22<pos_2){
                    pos_2 = pos22;
                    tmpkey ="</include>";
                }
            }
            if (pos_2 > pos_1 && pos_1 > -1) {
                String ref = sql.substring(pos_1, pos_2 + tmpkey.length());
                int pos_3 = ref.indexOf("refid");
                if(pos_3>-1) {
                    try {
                        String refid = "";
                        if(tmpkey.length()>2){
                            refid = ref.substring(pos_3 + "refid".length(), ref.length()-tmpkey.length()).trim()
                                    .replace("=","").replace(">","");
                        }else{
                            refid = ref.substring(pos_3 + "refid".length(), ref.length()-tmpkey.length()).trim()
                                    .replace("=","");
                        }
                        refid = refid.replace("\"","").replace("'","");
                        if(refid.indexOf(".")>-1){
                            String[] strs = refid.split("\\.");
                            int len = strs.length;
                            refid = strs[len-1];
                        }
                        if (refs.get(refid) != null) {
                            sql = sql.replace(ref, refs.get(refid));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        rb.setReplacedRefSql(sql);
        return rb;
    }

    /**
     * 获取当前path路径文件的所有适配多数据库的sqlId和内容<br>
     * @param path
     * @return
     */
    public static Map<String,String> sqlMap(String path) {
        Map<String,String> refSqls = new HashMap<>();
        if(path==null||path.equals("")){
            return refSqls;
        }
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file),"UTF-8"));
            String tempString = "";
            String tempString_trim = "";
            String lastKey =null;
            StringBuffer sb2 = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                tempString_trim = tempString.trim();
                if(lastKey==null){ //无匹配符
                    for(String key:keywords){
                        if(tempString_trim.startsWith(key)){
                            lastKey = key; //匹配符
                            break;
                        }
                    }
                    if(lastKey==null){ //未匹配到
                        sb.append(tempString).append("\n");
                    }else{ // 有匹配符
                        sb2.append(tempString).append("\n");
                    }
                    if(lastKey!=null&&tempString_trim.endsWith(lastKey.replace("<","</")+">")){
                        String content = sb2.toString();
                        int pos0 = content.indexOf(lastKey);
                        int pos1 = content.indexOf(">");
                        int pos5 = content.indexOf(lastKey.replace("<","</"));
                       // String sql_content = content.substring(pos1+1,pos5);
                        String sql_content = content;
                        if(pos1>pos0&&pos0>-1){
                            String head = content.substring(pos0,pos1);
                            if(head.indexOf("databaseId")>-1){
                                int pos2 = content.indexOf("id=\"");
                                int pos3 = content.indexOf("\"",pos2);
                                int pos4 = content.indexOf("\"",pos2+5);
                                int off_set1 = content.indexOf("databaseId=\"");
                                String tmp = null;
                                if(off_set1>-1){
                                    int off_set2 = content.indexOf("\"",off_set1+"databaseId=\"".length());
                                    if(off_set2>-1){
                                        tmp = content.substring(off_set1,off_set2+1).replace("\"","");
                                    }
                                }
                                String sqlId = null;
                                if(pos4>pos3){
                                    sqlId = content.substring(pos3+1,pos4);
                                    if(tmp!=null){
                                        sqlId = sqlId+"_"+tmp;
                                    }
                                }
                                if(sqlId!=null){
                                    //System.out.println(sqlId+":"+sql_content);
                                    refSqls.put(sqlId.trim().replace("\"","").replace("'",""),sql_content);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                        lastKey =null;
                    }
                }else{
                    sb2.append(tempString).append("\n");
                    if(tempString.indexOf(lastKey.replace("<","</"))>-1){
                        String content = sb2.toString();
                        int pos0 = content.indexOf(lastKey);
                        int pos1 = content.indexOf(">");
                        int pos5 = content.indexOf(lastKey.replace("<","</"));
                       // String sql_content = content.substring(pos1+1,pos5);
                        String sql_content = content;
                       /* String oralce_unqkey = sqlId+"_databaseId=oracle";
                        String sqlserver_unqkey = sqlId+"_databaseId=sqlserver";
                        String postgresql_unqkey = sqlId+"_databaseId=postgresql";*/
                        if(pos1>pos0&&pos0>-1){
                            String head = content.substring(pos0,pos1);
                            if(head.indexOf("databaseId=\"")>-1){
                                int pos2 = content.indexOf("id=\"");
                                int pos3 = content.indexOf("\"",pos2);
                                int pos4 = content.indexOf("\"",pos2+5);
                                int off_set1 = content.indexOf("databaseId=\"");
                                String tmp = null;
                                if(off_set1>-1){
                                    int off_set2 = content.indexOf("\"",off_set1+"databaseId=\"".length());
                                    if(off_set2>-1){
                                        tmp = content.substring(off_set1,off_set2+1).replace("\"","");
                                    }
                                }
                                String sqlId = null;
                                if(pos4>pos3){
                                    sqlId = content.substring(pos3+1,pos4);
                                    if(tmp!=null){
                                        sqlId = sqlId+"_"+tmp;
                                    }
                                }
                                if(sqlId!=null){
                                   // System.out.println(sqlId+":"+sql_content);
                                    refSqls.put(sqlId.trim().replace("\"","").replace("'",""),sql_content);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                        lastKey =null;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return refSqls;
        }
    }



    /**
     * 获取当前path路径文件的所有sqlId和内容<br>
     * @param path
     * @return
     */
    public static Map<String,String> refSqls(String path) {
        Map<String,String> refSqls = new HashMap<>();
        if(path==null||path.equals("")){
            return refSqls;
        }
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file),"UTF-8"));
            String tempString = "";
            String tempString_trim = "";
            String lastKey =null;
            StringBuffer sb2 = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                tempString_trim = tempString.trim();
                if(lastKey==null){ //无匹配符
                    for(String key:keywords){
                        if(tempString_trim.startsWith(key)){
                            lastKey = key; //匹配符
                            break;
                        }
                    }
                    if(lastKey==null){ //未匹配到
                        sb.append(tempString).append("\n");
                    }else{ // 有匹配符
                        sb2.append(tempString).append("\n");
                    }
                    if(lastKey!=null&&tempString_trim.endsWith(lastKey.replace("<","</")+">")){
                        String content = sb2.toString();
                        int pos0 = content.indexOf(lastKey);
                        int pos1 = content.indexOf(">");
                        int pos5 = content.indexOf(lastKey.replace("<","</"));
                        String sql_content = content.substring(pos1+1,pos5);
                        if(pos1>pos0&&pos0>-1){
                            String head = content.substring(pos0,pos1);
                            if(head.indexOf("databaseId")<0){
                                int pos2 = content.indexOf("id=\"");
                                int pos3 = content.indexOf("\"",pos2);
                                int pos4 = content.indexOf("\"",pos2+5);
                                String sqlId = null;
                                if(pos4>pos3){
                                    sqlId = content.substring(pos3+1,pos4);
                                }
                                if(sqlId!=null){
                                    refSqls.put(sqlId.trim().replace("\"","").replace("'",""),sql_content);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                        lastKey =null;
                    }
                }else{
                    sb2.append(tempString).append("\n");
                    if(tempString.indexOf(lastKey.replace("<","</"))>-1){
                        String content = sb2.toString();
                        int pos0 = content.indexOf(lastKey);
                        int pos1 = content.indexOf(">");
                        int pos5 = content.indexOf(lastKey.replace("<","</"));
                        String sql_content = content.substring(pos1+1,pos5);
                        if(pos1>pos0&&pos0>-1){
                            String head = content.substring(pos0,pos1);
                            if(head.indexOf("databaseId")<0){
                                int pos2 = content.indexOf("id=\"");
                                int pos3 = content.indexOf("\"",pos2);
                                int pos4 = content.indexOf("\"",pos2+5);
                                String sqlId = null;
                                if(pos4>pos3){
                                    sqlId = content.substring(pos3+1,pos4);
                                }
                                if(sqlId!=null){
                                    refSqls.put(sqlId.trim().replace("\"","").replace("'",""),sql_content);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                        lastKey =null;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return refSqls;
        }
    }

    /**
     * 获取当前path路径文件的所有sqlId,添加dbtype变成唯一ID<br>
     * @param path
     * @return
     */
    public static List<String> getIds(String path) {
        List<String> keyss = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String tempString = "";
            String tempString_trim = "";
            String lastKey = null;
            StringBuffer sb2 = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                tempString_trim = tempString.trim();
                if (lastKey == null) { //无匹配符
                    for (String key : keywords) {
                        if (tempString_trim.startsWith(key)) {
                            lastKey = key; //匹配符
                            break;
                        }
                    }
                    if (lastKey == null) { //未匹配到
                        sb.append(tempString).append("\n");
                    } else { // 有匹配符
                        sb2.append(tempString).append("\n");
                    }
                    if(lastKey!=null&&tempString_trim.endsWith(lastKey.replace("<","</")+">")){
                        lastKey = null;
                        String content = sb2.toString();
                        int pos1 = content.indexOf("id=\"");
                        int pos2 = content.indexOf("\"",pos1);
                        int pos3 = content.indexOf("\"",pos1+5);
                        String sqlId = null;
                        if(pos3>pos2){
                            sqlId = content.substring(pos2+1,pos3);
                        }
                        int pos4 = content.indexOf(">");
                        if(pos4>0){
                            String ss = content.substring(0,pos4);
                            int pos5 = ss.indexOf("databaseId=\"");
                            if(pos5>-1){
                                int pos6 = content.indexOf("\"",pos5);
                                int pos7 = content.indexOf("\"",pos5+12);
                                if(pos7>pos6){
                                    String unkey = sqlId+"_databaseId="+content.substring(pos6,pos7+1);
                                    if(unkey!=null){
                                        unkey = unkey.replace("\"","").replace("'","").trim();
                                    }
                                    keyss.add(unkey);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                    }
                } else {
                    sb2.append(tempString).append("\n");
                    if (tempString_trim.startsWith(lastKey.replace("<", "</"))) {
                        lastKey = null;
                        String content = sb2.toString();
                        int pos1 = content.indexOf("id=\"");
                        int pos2 = content.indexOf("\"",pos1);
                        int pos3 = content.indexOf("\"",pos1+5);
                        String sqlId = null;
                        if(pos3>pos2){
                            sqlId = content.substring(pos2+1,pos3);
                        }
                        int pos4 = content.indexOf(">");
                        if(pos4>0){
                            String ss = content.substring(0,pos4);
                            int pos5 = ss.indexOf("databaseId=\"");
                            if(pos5>-1){
                                int pos6 = content.indexOf("\"",pos5);
                                int pos7 = content.indexOf("\"",pos5+12);
                                if(pos7>pos6){
                                    String unkey = sqlId+"_databaseId="+content.substring(pos6,pos7+1);
                                    if(unkey!=null){
                                        unkey = unkey.replace("\"","").replace("'","").trim();
                                    }
                                    keyss.add(unkey);
                                }
                            }
                        }
                        sb2 = new StringBuffer();
                    }
                }
            }
            reader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return keyss;
    }

    /**
     * 替换数组中参数的关键字为指定值<br>
     * @param strs
     * @param keyword
     * @param rep
     * @return
     */
    public static String[] clearPrams(String[] strs,String keyword,String rep){
        if(strs==null) {
            return strs;
        }
        for(int i=0;i<strs.length;i++){
            strs[i] = strs[i].replace(keyword, rep).replace(keyword.toLowerCase(), rep);
        }
        return strs;
    }

    /**
     *  通用规则转换<br>
     * @param key
     * @param sql
     * @param databaseId
     * @return
     */
    public static Map<String,String> commonConver(String key, String sql, String databaseId) {
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
                    sql = functionConvertInner(databaseId,key,strs,sql,startFunPoint,closeFunPoint);
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
                    sql = functionConvertInner(databaseId,key,strs,sql,startFunPoint,closeFunPoint);
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

    /**
     * 获取函数的起止位置<br>
     * @param key
     * @param sql
     * @return
     */
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

    /**
     * 获取函数的参数<br>
     * @param startFunPoint
     * @param key
     * @param sql
     * @param closeFunPoint
     * @return
     */
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
            //sql = sql.replace(orderby_REP," ORDER BY ").replace(oracle_lastday_REP,"last_day");
        }else if("sqlserver".equals(databaseId)||"postgresql".equals(databaseId)){
            /*sql = sql.replace(WEEK_REP,"Week").replace(MONTH_REP,"month").replace(YEAR_REP,"year")
                    .replace(DATEDIFF_REP,"datediff").replace(DATE_REP,"date").replace(RTRIM_REP,"rtrim")
                    .replace(LTRIM_REP,"ltrim");*/
        }
        return sql;
    }

    /**
     *  函数转换处理实现<br>
     * @param databaseId
     * @param key
     * @param strs
     * @param sql
     * @param startFunPoint
     * @param closeFunPoint
     * @return
     */
    private static String functionConvertInner(String databaseId,String key,String[] strs,String sql,int startFunPoint,int closeFunPoint){
        if("FORCE INDEX(".equals(key)||"FORCE INDEX (".equals(key)){
            sql = sql.substring(0, startFunPoint)+ " " +
                    ((closeFunPoint+1)<sql.length()?(sql.substring(closeFunPoint + 1, sql.length())):"");
        }
        return sql;
    }

    /**
     *  将orderby中的表别名去掉,table.FiledName，只保留filedName<br>
     * @param orderby
     * @return
     */
    public static String rep_orderby(String orderby){
        while(orderby.indexOf(".")>-1){
            int pos1 = orderby.indexOf(".");
            String tmp = orderby.substring(0,pos1);
            int pos2 = -1;
            int pos3 = tmp.lastIndexOf(" ");
            int pos4 = tmp.lastIndexOf(",");
            if(pos4>pos3){
                pos2 = pos4;
            }else{
                pos2 = pos3;
            }
            if(pos2>-1&pos2<pos1) {
                String rep = orderby.substring(pos2+1,pos1+1);
                orderby = orderby.replace(rep,"");
            }else{
                break;
            }
        }
        return orderby;
    }

    /**
     *  sqlserver 批量插入语句，需要将from dual去掉<br>
     * @param sql
     * @return
     */
    public static String convertSqlserverBatchInsert(String sql){
        String lastKey = "<insert";
        if(sql.trim().startsWith(lastKey)){
            String sql_lower = sql.toLowerCase();
            if(sql_lower.contains("<foreach")){
                int offset1 = sql_lower.indexOf("<foreach");
                int offset2 = sql_lower.indexOf(">",offset1);
                int offset3 = sql_lower.indexOf("</foreach");
                if(offset3>offset2&&offset2>offset1){
                    String str3 = sql.substring(offset2+1,offset3);
                    if(str3.toLowerCase().trim().startsWith("select")){
                        return  sql.replace("FROM DUAL","").replace("FROM dual","")
                                .replace("from DUAL","").replace("from dual","");
                    }
                }
            }
        }
        return sql;
    }

    /**
     *  mysql批量插入sql为oracle<br>
     * @param sql
     * @return
     */
    public static String convertOracleBatchInsert(String sql){
        String lastKey = "<insert";
        if(sql.trim().startsWith(lastKey)){
            String sql_lower = sql.toLowerCase();
            if(sql_lower.contains("<foreach")){
                int offset1 = sql_lower.indexOf("<foreach");
                int offset2 = sql_lower.indexOf(">",offset1);
                int offset3 = sql_lower.indexOf("</foreach");
                if(offset3>offset2&&offset2>offset1){
                    String str1 = sql.substring(0,offset1);
                    String str2 = sql.substring(offset1,offset2+1);
                    String str3 = sql.substring(offset2+1,offset3);
                    String str4 = sql.substring(offset3,sql.length());
                    if(str1.toLowerCase().trim().endsWith("values")){
                        String str1_lowver= str1.toLowerCase();
                        int pos_1 = str1_lowver.lastIndexOf("values");
                        str1 = str1.substring(0,pos_1);
                        String str2_lowver = str2.toLowerCase();
                        int pos_2 = str2_lowver.indexOf("separator=\",\"");
                        if(pos_2>-1){
                            str2 = str2.substring(0,pos_2)+"separator=\"union all\""+str2.substring(pos_2+("separator=\",\"").length(),str2.length());
                        }
                        String str3_trim = str3.trim();
                        if(str3_trim.startsWith("(")&&str3_trim.endsWith(")")){
                            int pos_3 = str3.indexOf("(");
                            int pos_4 = str3.lastIndexOf(")");
                            str3 = str3.substring(0,pos_3)+"select "+str3.substring(pos_3+1,pos_4)+" from dual "+str3.substring(pos_4+1,str3.length());
                        }
                        return str1+"\n"+str2+str3+str4;
                    }
                }
            }
        }
        return sql;
    }


    /**
     *  mysql批量更新sql转换<br>
     * @param sql
     * @return
     */
    public static String converBatchUpdate(String sql){
        String lastKey = "<update";
        if(sql.trim().startsWith(lastKey)){
            String sql_lower = sql.toLowerCase();
            if(sql_lower.contains("<foreach")){
                int offset1 = sql_lower.indexOf("<foreach");
                int offset2 = sql_lower.indexOf(">",offset1);
                if(offset2>offset1&&offset1>-1){
                    String str1 = sql.substring(0,offset1);
                    String str2 = sql.substring(offset1,offset2+1);
                    String str3 =  sql.substring(offset2+1);
                    String str2_lower = str2.toLowerCase();
                    if(sql_lower.indexOf("open=\"(")>-1||sql_lower.indexOf("close=\"(")>-1){
                        return sql;
                    }
                    int off_set1 = str2_lower.indexOf("open=\"\"");
                    int off_set2 = str2_lower.indexOf("close=\"\"");
                    boolean flag = true;
                    if(off_set1>-1&&off_set2>-1){
                        str2 = str2.substring(0,off_set1)+"open=\"begin\""+str2.substring(off_set1+"open=\"\"".length());
                    }else{
                        flag = false;
                    }
                    str2_lower = str2.toLowerCase();
                    off_set2 = str2_lower.indexOf("close=\"\"");
                    if(off_set1>-1&&off_set2>-1){
                        str2 = str2.substring(0,off_set2)+"close=\";end;\""+str2.substring(off_set2+"close=\"\"".length());
                    }else{
                        flag = false;
                    }
                    if(!flag){
                        return sql;
                    }else {
                        return str1 + "\n" + str2 + str3;
                    }
                }
            }
        }
        return sql;
    }


    /**
     * 解析拆分mysql分页语句<br>
     * @param pos
     * @param sql
     * @param key
     * @param limit1
     * @param limit2
     * @return
     */
    public static Map<String,String> setLimit(int pos , String sql, String key, String limit1, String limit2){
        boolean check = false;
        boolean limit1_complete = false;
        int cntt = 0;
        int cnt2 = 0;
        String suff_if = "";
        String suffix = "";
        String pre_if = "";
        Map<String,String> rstmap = new HashMap<>();
        for(int j=pos+key.length();j<sql.length();j++){
            String tmp = String.valueOf(sql.charAt(j));
            if(limit1==null){
                if(" ".equals(tmp)){
                    continue;
                }else{
                    limit1=tmp;
                }
            }else if(limit1!=null){
                if("(".equals(tmp)){
                    cntt++;
                }else if(")".equals(tmp)){
                    cntt--;
                }
                if(cntt<=0&&!limit1_complete){
                    if(" ".equals(tmp)||")".equals(tmp)||",".equals(tmp)||"<".equals(tmp)){
                        limit1_complete = true;
                        if(cntt<0||"<".equals(tmp)){ // 反括号及<结束
                            if(limit1!=null&&limit1.endsWith(";")){
                                limit1 = limit1.substring(0,limit1.length()-1);
                            }
                            if(limit2!=null&&limit2.endsWith(";")){
                                limit2 = limit2.substring(0,limit2.length()-1);
                            }
                            rstmap.put("limit1",limit1);
                            rstmap.put("limit2",limit2);
                            rstmap.put("possss",j+"");
                            String check_str =sql.substring(j,sql.length());
                            if(check_str.indexOf("</if>")>0){
                                int ck1 = check_str.indexOf("</if>");
                                int ck_x =check_str.indexOf("</");
                                int ck2 = check_str.indexOf("<if");
                                if((ck2<0||ck1<ck2)&&ck_x==ck1){
                                    check = true;
                                    suff_if = check_str.substring(0,ck1+"</if>".length());
                                    suffix = check_str.substring(ck1+"</if>".length(),check_str.length());
                                }
                            }
                            if(check){
                                String check_tmp = sql.substring(0,pos) ;
                                int ck3 = check_tmp.lastIndexOf("<if");
                                int ck4 = check_tmp.indexOf(">",ck3);
                                pre_if = check_tmp.substring(ck3,ck4+">".length());
                            }
                            rstmap.put("check",check==true?"true":"false");
                            rstmap.put("pre_if",pre_if);
                            rstmap.put("suff_if",suff_if);
                            rstmap.put("suffix",suffix);
                            return rstmap;
                        }else {
                            continue;
                        }
                    }else{
                        limit1+= tmp;
                    }
                } else{
                    if(!limit1_complete) {
                        limit1+= tmp;
                    }else{ // limit1 已完成,处理limit2
                        if(limit2==null){
                            if(cnt2>2||"<".equals(tmp)){ //超出3个以上空位还没有第二个limit参数 或者是<结束
                                if(limit1!=null&&limit1.endsWith(";")){
                                    limit1 = limit1.substring(0,limit1.length()-1);
                                }
                                if(limit2!=null&&limit2.endsWith(";")){
                                    limit2 = limit2.substring(0,limit2.length()-1);
                                }
                                rstmap.put("limit1",limit1);
                                rstmap.put("limit2",limit2);
                                rstmap.put("possss",j+"");
                                String check_str =sql.substring(j,sql.length());
                                if(check_str.indexOf("</if>")>0){
                                    int ck1 = check_str.indexOf("</if>");
                                    int ck_x =check_str.indexOf("</");
                                    int ck2 = check_str.indexOf("<if");
                                    if((ck2<0||ck1<ck2)&&ck_x==ck1){
                                        check = true;
                                        suff_if = check_str.substring(0,ck1+"</if>".length());
                                        suffix = check_str.substring(ck1+"</if>".length(),check_str.length());
                                    }
                                }
                                if(check){
                                    String check_tmp = sql.substring(0,pos) ;
                                    int ck3 = check_tmp.lastIndexOf("<if");
                                    int ck4 = check_tmp.indexOf(">",ck3);
                                    pre_if = check_tmp.substring(ck3,ck4+">".length());
                                }
                                rstmap.put("check",check==true?"true":"false");
                                rstmap.put("pre_if",pre_if);
                                rstmap.put("suff_if",suff_if);
                                rstmap.put("suffix",suffix);
                                return rstmap;
                                //return j; //结束
                            }
                            if(" ".equals(tmp)){
                                cnt2++;
                                continue;
                            }else{
                                limit2 = tmp;
                            }
                        }else{
                            if(cntt<=0){
                                if(" ".equals(tmp)||")".equals(tmp)||"<".equals(tmp)){
                                    if(limit1!=null&&limit1.endsWith(";")){
                                        limit1 = limit1.substring(0,limit1.length()-1);
                                    }
                                    if(limit2!=null&&limit2.endsWith(";")){
                                        limit2 = limit2.substring(0,limit2.length()-1);
                                    }
                                    rstmap.put("limit1",limit1);
                                    rstmap.put("limit2",limit2);
                                    rstmap.put("possss",j+"");
                                    String check_str =sql.substring(j,sql.length());
                                    if(check_str.indexOf("</if>")>0){
                                        int ck1 = check_str.indexOf("</if>");
                                        int ck_x =check_str.indexOf("</");
                                        int ck2 = check_str.indexOf("<if");
                                        if((ck2<0||ck1<ck2)&&ck_x==ck1){
                                            check = true;
                                            suff_if = check_str.substring(0,ck1+"</if>".length());
                                            suffix = check_str.substring(ck1+"</if>".length(),check_str.length());
                                        }
                                    }
                                    if(check){
                                        String check_tmp = sql.substring(0,pos) ;
                                        int ck3 = check_tmp.lastIndexOf("<if");
                                        int ck4 = check_tmp.indexOf(">",ck3);
                                        pre_if = check_tmp.substring(ck3,ck4+">".length());
                                    }
                                    rstmap.put("check",check==true?"true":"false");
                                    rstmap.put("pre_if",pre_if);
                                    rstmap.put("suff_if",suff_if);
                                    rstmap.put("suffix",suffix);
                                    return rstmap;
                                }else{
                                    limit2+= tmp;
                                }
                            }else{
                                limit2+= tmp;
                            }
                        }
                    }
                }
            }
        }
        if(limit1!=null&&limit1.endsWith(";")){
            limit1 = limit1.substring(0,limit1.length()-1);
        }
        if(limit2!=null&&limit2.endsWith(";")){
            limit2 = limit2.substring(0,limit2.length()-1);
        }
        rstmap.put("limit1",limit1);
        rstmap.put("limit2",limit2);
        rstmap.put("check","false");
        rstmap.put("possss",sql.length()-1+"");
        return rstmap;
    }

}
