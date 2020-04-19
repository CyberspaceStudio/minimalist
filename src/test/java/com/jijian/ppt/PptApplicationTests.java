package com.jijian.ppt;

import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.power.common.util.DateTimeUtil;
import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiErrorCodeDictionary;
import com.power.doc.model.ApiReqHeader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class PptApplicationTests {

    @Test
    void contextLoads() throws IOException {

    }

    //生成API-H5文档
    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl("https://minimalist.net.cn/minimalist");

        //设置为严格模式，Smart-doc将降至要求每个Controller暴露的接口写上标准文档注释
        config.setStrict(true);

        //当把AllInOne设置为true时，Smart-doc将会把所有接口生成到一个Markdown、HHTML或者AsciiDoc中
        config.setAllInOne(true);

        //HTML5文档，建议直接放到src/main/resources/static/doc下，Smart-doc提供一个配置常量HTML_DOC_OUT_PATH
        config.setOutPath(DocGlobalConstants.HTML_DOC_OUT_PATH);

        // 设置接口包扫描路径过滤，如果不配置则Smart-doc默认扫描所有的接口类
        // 配置多个报名有英文逗号隔开
        config.setPackageFilters("com.jijian.ppt.controller");

        //设置公共请求头.如果不需要请求头，则无需设置
        config.setRequestHeaders(
                ApiReqHeader.header().setName("token").setType("string")
                        .setDesc("登录token").setRequired(true)
        );

        //1.7.9 优化了错误码处理，用于下面替代遍历枚举设置错误码
        config.setErrorCodeDictionaries(
                ApiErrorCodeDictionary.dict().setEnumClass(ResponseResultEnum.class)
                        .setCodeField("code") //错误码值字段名
                        .setDescField("msg")//错误码描述
        );
        /*config.setDataDictionaries(
                ApiDataDictionary.dict().setTitle("文件类别Id").setEnumClass(FileCategoryEnum.class)
                        .setCodeField("fileCategoryId") //字典码值字段名
                        .setDescField("fileCategoryDetail")
        );*/

        long start = System.currentTimeMillis();
        //生成HTML5文件
        HtmlApiDocBuilder.buildApiDoc(config);
        //since 1.8.1版本开始入口方法有变更
        HtmlApiDocBuilder.buildApiDoc(config);
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }



}
