package com.hswag;

import com.hswag.sqlSyntaxConverter.core.MapperExecutor;

public class Main {

    public static void main(String[] args) {
        String project_filepath = "D:\\Java\\Proj\\develop\\test\\ ";
        String result_filepath = "D:\\Java\\Proj\\develop\\out\\ ";
        MapperExecutor.execute(project_filepath, result_filepath);
    }
}
