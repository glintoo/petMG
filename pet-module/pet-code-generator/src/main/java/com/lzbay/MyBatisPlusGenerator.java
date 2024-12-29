package com.lzbay;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusGenerator {

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/guli?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true" +
                "&useAffectedRows=true&serverTimezone=Asia/Shanghai&autoReconnect=true&useSSL=false" +
                "&allowPublicKeyRetrieval=true";
        /*FastAutoGenerator.create(url, "root", "Root#123")
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入报名？")))
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables((scanner.apply(
                                "请输入表名，多个英文逗号分隔？所有请输入all")))).controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
                .execute();*/


       FastAutoGenerator.create(url, "root", "Root#123")
               .globalConfig(builder -> {
                   builder.author("lzbay")
                           .enableSwagger()
                           .fileOverride()
                           .outputDir("~/download");
               })
               .dataSourceConfig(builder -> builder.typeConvertHandler(((globalConfig, typeRegistry, metaInfo) -> {
                   int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                   if(typeCode == Types.SMALLINT){
                       return DbColumnType.INTEGER;
                   }
                   return typeRegistry.getColumnType(metaInfo);
               })))
               .packageConfig(builder -> {
                   builder.parent("com.lzbay") //父包名
                           .moduleName("system") //设置父包模块名
                           .pathInfo(Collections.singletonMap(OutputFile.xml, "~/download")); //设置输入目录
               })
               .strategyConfig(builder -> {
                   builder.addInclude("t_simple") //设置需要生成的表明
                           //多个英文逗号分隔？所有请输入all
                           // .addInclude(getTables("t_simple,t_one,t_two"))
                           // .addInclude(getTables("all"))
                           .addTablePrefix("t_","c_")
                           .entityBuilder().enableLombok().addTableFills(new Column("create_time", FieldFill.INSERT)); //设置过滤表前缀
               })
               .templateEngine(new FreemarkerTemplateEngine()) //使用Freemarker引擎模版，默认的是Velocity引擎模版
               .execute();
    }
}
