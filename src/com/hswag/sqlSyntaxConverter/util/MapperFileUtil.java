package com.hswag.sqlSyntaxConverter.util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MapperFileUtil {

    public static void writeFile(String fileName,String result){
        if(result==null||"".equals(result)){
            return ;
        }
        int pos = fileName.lastIndexOf("\\");
        String directory = "";
        if(pos>-1){
            directory = fileName.substring(0,pos);
            File dir = new File(directory);
            if(!dir.exists()){
                dir.mkdirs();
            }
        }
        Writer writer = null;
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(file), "UTF-8"));
            writer.write(result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void writeFile_append(String fileName,String result){
        if(result==null||"".equals(result)){
            return ;
        }
        int pos = fileName.lastIndexOf("\\");
        String directory = "";
        if(pos>-1){
            directory = fileName.substring(0,pos);
            File dir = new File(directory);
            if(!dir.exists()){
                dir.mkdirs();
            }
        }
        Writer writer = null;
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(file,true), "UTF-8"));
            writer.write(result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String readFromFile(String path){
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file),"UTF-8"));
            String tempString = "";
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString+"\n");
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
        return sb.toString();
    }





    public static void renameFile(String oldFilePath,String newFilePath){
        String content = readFromFile(oldFilePath);
        writeFile(newFilePath,content);
    }
    /**
     *
     * @param filePath  excel文件的路径
     * @param heads	excel文件表头
     * @param datas	excel文件内容
     * @param keys   ID 列表
     * @param prefix	excel文件名称前缀
     * @return
     */
    public static String exportExcel(String filePath , List<String> heads, List<Map<String,String>> datas, List<String> keys, String prefix){
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdirs();
        }
        String filename = prefix+new Date().getTime()+".xls";
        try{
            WritableWorkbook wwb = Workbook.createWorkbook(new File(filePath+filename));
            WritableSheet ws = wwb.createSheet("Sheet1", 0);// 建立工作簿
            //写表头
            for(int i=0;i<heads.size();i++){
                ws.addCell(new Label(i, 0, heads.get(i)));// 放入工作簿
            }
            //写内容
            for(int i=0;i<datas.size();i++){
                if(datas.get(i)==null)continue;
                for(int j=0;j<keys.size();j++){
                    ws.addCell(new Label(j,i+1,datas.get(i).get(keys.get(j))));
                }
            }
            // 写入Exel工作表
            wwb.write();
            // 关闭Excel工作薄对象
            wwb.close();
            return filename;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
