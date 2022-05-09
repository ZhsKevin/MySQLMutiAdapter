package com.hswag.sqlSyntaxConverter.core.config;

public class MapperReplaceConfig {

    private static boolean ONLY_PG = false;

    public static boolean isOnlyPg() {
        return ONLY_PG;
    }

    public static void setOnlyPg(boolean onlyPg) {
        ONLY_PG = onlyPg;
    }

}
