package com.hswag.sqlSyntaxConverter.test;


import com.hswag.sqlSyntaxConverter.core.MapperExecutor;

public class Test {
    public static void main(String[] args) {
        String project_filepath = "C:\\Users\\lrf\\IdeaProjects\\";
       // String project_filepath = "C:\\Users\\lrf\\multidb_develop\\";
        String resultFilePath = "C:\\Users\\lrf\\results\\";
         MapperExecutor.executeMapperConvert(project_filepath,resultFilePath);
    }
}

