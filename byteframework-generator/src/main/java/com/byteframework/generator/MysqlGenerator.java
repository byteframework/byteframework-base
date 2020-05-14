/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.byteframework.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * SQLServerGenerator
 *
 * @author sa
 * @since 2020-04-16
 */
public class MysqlGenerator {

    /**
     * @param args
     * @Title: 代码生成
     * @Description: 生成
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //输出文件路径
        gc.setOutputDir("E:\\byteframework\\gen");
        // 是否文件覆盖
        gc.setFileOverride(true);
        // 不需要ActiveRecord(实体类继承Model)特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML ColumnList
        gc.setBaseColumnList(true);
        // 作者
        gc.setAuthor("sa");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        // 默认service接口名IXXXService 自定义指定之后就不会用I开头了
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("myroot");
        dsc.setPassword("××××××");
        dsc.setUrl("jdbc:mysql://rm-bp12m3yhq3w7mvv27ho.mysql.rds.aliyuncs.com:3306/permission?serverTimezone=UTC&useSSL=false");
        mpg.setDataSource(dsc);

        // 策略配置 skipView
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[]{"psi_"});
        // 表名生成策略(下划线转驼峰)
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 需要生成的表名
        strategy.setInclude("psi_purchase","psi_sale","psi_inventory","psi_supplier","psi_customer","psi_product");

//        strategy.setSuperServiceClass(null);
//        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.byteframework.psi");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("impl");
        pc.setMapper("mapper");
        pc.setEntity("domain");
        pc.setXml("mapping");
        mpg.setPackageInfo(pc);

        /*TemplateConfig  templateConfig = new TemplateConfig();
//        templateConfig.setController("/resources/templates/controller3.java");
        mpg.setTemplate(templateConfig);*/

        // 执行生成
        mpg.execute();
    }

}
