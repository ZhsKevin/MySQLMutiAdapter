package com.hswag.sqlSyntaxConverter.core;


import com.hswag.sqlSyntaxConverter.core.config.MapperReplaceConfig;
import com.hswag.sqlSyntaxConverter.entity.ResultBean;
import com.hswag.sqlSyntaxConverter.util.MapperFileUtil;
import com.hswag.sqlSyntaxConverter.validator.XMLValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperExecutor {

    private static List<String> FileList = new ArrayList<String>();

    private static List<String> keywords = new ArrayList<>();

    public static StringBuffer results = new StringBuffer();

    public static int success_all = 0;

    public static int fail_all = 0;

    public static int all_all = 0;

    public static String projectRootPath_ = "";


    static {
        keywords.add("<select") ;
        keywords.add("<update") ;
        keywords.add("<delete") ;
        keywords.add("<insert") ;
        keywords.add("<sql") ;
    }

    /**
     * 检查指定项目根目录下的mapper并转换mapper语法,转换过程结果记录在resultFilePath目录中<br>
     * @param projectRootPath  单个或多个项目的上一层目录（根目录）,例如:C:\Users\lrf\IdeaProjects\<br>
     * @param resultFilePath   转换结果记录目录,例如:C:\Users\lrf\results\<br>
     */
    public static void executeMapperConvert(String projectRootPath,String resultFilePath){
        success_all = 0;
        fail_all = 0;
        all_all = 0;
        File dir = new File(projectRootPath);
        String[] filenames = dir.list();
        projectRootPath_ = projectRootPath;
        for(String name : filenames){
            String path = projectRootPath+name;
            execute(path,resultFilePath,true);
        }
        results.append("总计"+"\t"+success_all+"\t"+fail_all+"\t"+all_all+"\n");
        MapperFileUtil.writeFile(resultFilePath+"results.txt",results.toString());
    }

    /**
     * 执行单个项目下的mapper文件转换处理<br>
     * @param project_filepath  单个项目的根目录,例如:C:\Users\lrf\IdeaProjects\weaver-common\<br>
     * @param result_filepath 转换结果记录目录,例如:C:\Users\lrf\results\<br>
     */
    public static void execute(String project_filepath,String result_filepath){
        execute(project_filepath,result_filepath,true);
    }



    private static void execute(String project_filepath,String result_filepath,boolean iscover){
        List<String> javaFilePaths = getXmlFileList(project_filepath);
        String excelFilePath = result_filepath;
        List<String> heads = new ArrayList<>();
        heads.add("项目名称");
        heads.add("文件路径");
        heads.add("sqlId");
        heads.add("处理类型");
        heads.add("自动转换");
        heads.add("Mysql");
        heads.add("oracle");
        heads.add("sqlserver");
        heads.add("postgresql");
        heads.add("转换后sql");
        List<Map<String,String>> datas = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        keys.add("project");
        keys.add("filepath");
        keys.add("sqlId");
        keys.add("type");
        keys.add("auto");
        keys.add("mysql");
        keys.add("oracle");
        keys.add("sqlserver");
        keys.add("postgresql");
        keys.add("convert_sql");
        String prefix = "result_";
        String projectName = null;
        for(String path:javaFilePaths){
            if(path==null||path.equals("")){
               continue;
            }
            File file = new File(path);
            BufferedReader reader = null;
            StringBuffer sb = new StringBuffer();
            boolean hasChange = false; //是否改变（有不兼容多数据库语法）
            boolean needInit = false; //是否需要自行判断处理（无法自动化处理）
            try {
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file),"UTF-8"));
                String tempString = "";
                String tempString_trim = "";
                String lastKey =null;
                StringBuffer sb2 = new StringBuffer();
                StringBuffer sb3 = new StringBuffer();
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
                            sb3.append(" "+tempString+" ").append("\n");
                            if(tempString_trim.endsWith(lastKey.replace("<","</")+">")){
                                lastKey =null;
                                String content = sb2.toString();
                                String content_withtrim = sb3.toString();
                                ResultBean rb = MapperReplace.handleMapperReplace(project_filepath,path,content,content_withtrim,iscover);
                                if("是".equals(rb.getHasChange())){
                                    hasChange = true;
                                    Map<String,String> map = new HashMap<>();
                                    map.put("project",rb.getProjectName());
                                    map.put("filepath",rb.getFilePath());
                                    map.put("sqlId",rb.getSqlId());
                                    map.put("type",rb.getType());
                                    map.put("auto",rb.getIsAuto());
                                    if(rb.getSql()!=null&&rb.getSql().length()>32000){
                                        map.put("mysql","sql长度超过32000");
                                    }else{
                                        map.put("mysql",rb.getSql());
                                    }
                                    if(rb.getSql_oracle()!=null&&rb.getSql_oracle().length()>32000){
                                        map.put("oracle","sql长度超过32000");
                                    }else{
                                        map.put("oracle",rb.getSql_oracle());
                                    }
                                    if(rb.getSql_sqlserver()!=null&&rb.getSql_sqlserver().length()>32000){
                                        map.put("sqlserver","sql长度超过32000");
                                    }else{
                                        map.put("sqlserver",rb.getSql_sqlserver());
                                    }
                                    if(rb.getSql_postgresql()!=null&&rb.getSql_postgresql().length()>32000){
                                        map.put("postgresql","sql长度超过32000");
                                    }else{
                                        map.put("postgresql",rb.getSql_postgresql());
                                    }
                                    if(rb.getConvertedSql()!=null&&rb.getConvertedSql().length()>32000){
                                        map.put("convert_sql","sql长度超过32000");
                                    }else{
                                        map.put("convert_sql",rb.getConvertedSql());
                                    }
                                    if(projectName==null&&rb.getProjectName()!=null&&!"".equals(rb.getProjectName())){
                                        projectName = rb.getProjectName();
                                    }
                                    datas.add(map);
                                    if("否".equals(rb.getIsAuto())){
                                        needInit = true;
                                    }
                                }
                                content = rb.getConvertedSql();
                                sb.append(content);
                                sb2 = new StringBuffer();
                                sb3 = new StringBuffer();
                            }
                        }
                    }else{
                        sb2.append(tempString).append("\n");
                        sb3.append(" "+tempString+" ").append("\n");
                        if(tempString_trim.endsWith(lastKey.replace("<","</")+">")){
                            lastKey =null;
                            String content = sb2.toString();
                            String content_withtrim = sb3.toString();
                            ResultBean rb = MapperReplace.handleMapperReplace(project_filepath,path,content,content_withtrim,iscover);
                            if("是".equals(rb.getHasChange())){
                                hasChange = true;
                                Map<String,String> map = new HashMap<>();
                                map.put("project",rb.getProjectName());
                                map.put("filepath",rb.getFilePath());
                                map.put("sqlId",rb.getSqlId());
                                map.put("type",rb.getType());
                                map.put("auto",rb.getIsAuto());
                                if(rb.getSql()!=null&&rb.getSql().length()>32000){
                                    map.put("mysql","sql长度超过32000");
                                }else{
                                    map.put("mysql",rb.getSql());
                                }
                                if(rb.getSql_oracle()!=null&&rb.getSql_oracle().length()>32000){
                                    map.put("oracle","sql长度超过32000");
                                }else{
                                    map.put("oracle",rb.getSql_oracle());
                                }
                                if(rb.getSql_sqlserver()!=null&&rb.getSql_sqlserver().length()>32000){
                                    map.put("sqlserver","sql长度超过32000");
                                }else{
                                    map.put("sqlserver",rb.getSql_sqlserver());
                                }
                                if(rb.getSql_postgresql()!=null&&rb.getSql_postgresql().length()>32000){
                                    map.put("postgresql","sql长度超过32000");
                                }else{
                                    map.put("postgresql",rb.getSql_postgresql());
                                }
                                if(rb.getConvertedSql()!=null&&rb.getConvertedSql().length()>32000){
                                    map.put("convert_sql","sql长度超过32000");
                                }else{
                                    map.put("convert_sql",rb.getConvertedSql());
                                }
                                if(projectName==null&&rb.getProjectName()!=null&&!"".equals(rb.getProjectName())){
                                    projectName = rb.getProjectName();
                                }
                                datas.add(map);
                                if("否".equals(rb.getIsAuto())){
                                    needInit = true;
                                }
                            }
                            content = rb.getConvertedSql();
                            sb.append(content);
                            sb2 = new StringBuffer();
                            sb3 = new StringBuffer();
                        }
                    }
                }
                reader.close();
            } catch (IOException e) {
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
            if(projectRootPath_==null||"".equals(projectRootPath_)){
                projectRootPath_ = "";
                String[] strs = project_filepath.split("\\\\");
                for(int i=0;i<strs.length-1;i++){
                    projectRootPath_+=strs[i]+"\\";
                }
            }
             if(hasChange){
                int content_KBsize= sb.toString().getBytes().length/1024;
                if(content_KBsize>2048){  //生成文件过大，可能存在问题 大于2048KB
                    System.out.println("===error File.size="+(content_KBsize+1)+"KB==="+path);
                    MapperFileUtil.renameFile(path, path.replace(projectRootPath_, result_filepath));
                    MapperFileUtil.writeFile(path.replace(projectRootPath_, result_filepath).replace(".xml","_autoGenerate.xml"), sb.toString());
                    MapperFileUtil.writeFile_append(result_filepath+"error_"+projectName+".txt","===error Auto-convert File.size="+(content_KBsize+1)+"KB==="+path+"\n");
                }else if(XMLValidator.validMapper(sb.toString())!=null) { //转换有错误
                    System.out.println("===error XML Valid File==="+path+" with MSG:"+ XMLValidator.validMapper(sb.toString()));
                    MapperFileUtil.renameFile(path, path.replace(projectRootPath_, result_filepath));
                    MapperFileUtil.writeFile(path.replace(projectRootPath_, result_filepath).replace(".xml","_autoGenerate.xml"), sb.toString());
                    MapperFileUtil.writeFile_append(result_filepath+"error_"+projectName+".txt","===error Auto-convert File.size="+(content_KBsize+1)+"KB==="+path+"\n");
                }else{
                    MapperFileUtil.writeFile(path, sb.toString());
                    MapperFileUtil.writeFile(path.replace(projectRootPath_, result_filepath+"successFiles"+File.separator), sb.toString());
                }

            }
            if(needInit){
               MapperFileUtil.writeFile(path.replace(projectRootPath_, result_filepath).replace(".xml","_autoGenerate.xml"), sb.toString());
               MapperFileUtil.renameFile(path, path.replace(projectRootPath_,  result_filepath+"fixFiles"+File.separator));
            }
        }
        if(projectName!=null){
            prefix+=projectName+"_";
        }
        MapperFileUtil.exportExcel(excelFilePath,heads,datas,keys,prefix);
        int success = 0;
        int fail = 0;
        int total = 0;
        for(Map<String,String> m :datas){
            String auto = m.get("auto");
            if("否".equals(auto)){
                fail++;
                fail_all++;
            }else{
                success++;
                success_all++;
            }
            total++;
            all_all++;
        }
        results.append(projectName+"\t"+success+"\t"+fail+"\t"+total+"\n");
    }


    private static void scanFiles(File rootPath, String fileType){
        if(rootPath.isDirectory()){
            for(File file:rootPath.listFiles()){
                if(file.isFile()&&file.getName().endsWith(fileType)){
                    if(file.getPath()!=null&&!file.getPath().equals("")){
                        FileList.add(file.getPath());
                    }
                }else if(file.isDirectory()){
                    scanFiles(file,fileType);
                }
            }
        }
    }

    private static List<String> getFileList(String rootPath,String type){
        FileList.clear();
        scanFiles(new File(rootPath),type);
        return FileList ;
    }


    private static List<String> getXmlFileList(String rootPath){
        return getFileList(rootPath,".xml");
    }


    private static String validXML(String filepath){
        String content = MapperFileUtil.readFromFile(filepath);
        return XMLValidator.validMapper(filepath,content);
    }


    /**
     * 检查指定项目根目录下的mapper并转换mapper语法,转换过程结果记录在resultFilePath目录中--仅处理Pg<br>
     * @param projectRootPath  单个或多个项目的上一层目录（根目录）,例如:C:\Users\lrf\IdeaProjects\<br>
     * @param resultFilePath   转换结果记录目录,例如:C:\Users\lrf\results\<br>
     */
    public static void executeMapperConvert_pg(String projectRootPath,String resultFilePath){
        MapperReplaceConfig.setOnlyPg(true);
        success_all = 0;
        fail_all = 0;
        all_all = 0;
        File dir = new File(projectRootPath);
        String[] filenames = dir.list();
        projectRootPath_ = projectRootPath;
        for(String name : filenames){
            String path = projectRootPath+name;
            execute(path,resultFilePath,true);
        }
        results.append("总计"+"\t"+success_all+"\t"+fail_all+"\t"+all_all+"\n");
        MapperFileUtil.writeFile(resultFilePath+"results.txt",results.toString());
    }

}
