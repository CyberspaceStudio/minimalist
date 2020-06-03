package com.jijian.ppt;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.POJO.Page;
import com.jijian.ppt.service.Impl.ImagePageServiceImpl;
import com.jijian.ppt.service.Impl.TransitionPageServiceImpl;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.PdfToImg;
import com.jijian.ppt.utils.Pptx2PdfUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import com.power.common.util.DateTimeUtil;
import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiDataDictionary;
import com.power.doc.model.ApiErrorCodeDictionary;
import com.power.doc.model.ApiReqHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@SpringBootTest
@Slf4j
class PptApplicationTests {

    @Resource
    private ImagePageServiceImpl imagePageService;

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
        config.setDataDictionaries(
                ApiDataDictionary.dict().setTitle("页面类型Id").setEnumClass(PageCategoryEnum.class)
                        .setCodeField("pageCategoryId") //字典码值字段名
                        .setDescField("pageCategoryDetail")
        );

        long start = System.currentTimeMillis();
        //生成HTML5文件
        HtmlApiDocBuilder.buildApiDoc(config);
        //since 1.8.1版本开始入口方法有变更
        HtmlApiDocBuilder.buildApiDoc(config);
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }

    @Test
    public void makeCoverPage() throws Exception {

//        //获取模板ppt文件的路径
//        String templateFilePath = "C:\\Users\\24605\\Desktop\\2.05.pptx";
//        String[] titles = {"a", "b","c","d","e"};
//        String[] subTitles = {"1"};
//        String[] paragraphs = {"A", "B", "C","D","E"};
//        //读取模板文件
//        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));
//
//        //获取模板的正文页
//        XSLFSlide slide = ppt.getSlides().get(3);
//        //读取用户文件
//        XMLSlideShow userFile = new XMLSlideShow(new FileInputStream(templateFilePath));
//        //读取模板文件的排版
//        XSLFSlideLayout layout = slide.getSlideLayout();
//        //将排版应用到用户文件
//        XSLFSlide newSlide = userFile.createSlide(layout);
//
//        List<XSLFShape> xslfTextShapeList = slide.getShapes();
//        int i = 0,j = 0,k = 0;
//        for ( XSLFShape shape : slide.getShapes())
//        {
//            if ( shape instanceof XSLFTextShape)
//            {
//                XSLFTextShape txtshape = (XSLFTextShape)shape ;
//                List<TextParagraph> list = ((TextShape) shape).getTextParagraphs();
//                for (TextParagraph textParagraph:
//                        list) {
//                    List<TextRun> textRuns = textParagraph.getTextRuns();
//                    for (TextRun textRun:
//                            textRuns) {
//                        String text = textRun.getRawText();
//                        if (text.equals("Title")){
//                            if (i < titles.length){
//                                textRun.setText(titles[i]);
//                                i++;
//                            }else {
//                                textRun.setText("");
//                            }
//                        }
//                        if (text.equals("subTitle")){
//                            if (j < subTitles.length){
//                                textRun.setText(subTitles[j]);
//                                j++;
//                            }else {
//                                textRun.setText("");
//                            }
//                        }
//                        if (text.equals("Paragraph")){
//                            if (k < paragraphs.length){
//                                textRun.setText(paragraphs[j]);
//                                k++;
//                            }else {
//                                textRun.setText("");
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
//        //导入上下文
//        newSlide.importContent(slide);
//        //输出文件
//        FileOutputStream out = new FileOutputStream(templateFilePath);
//        userFile.write(out);
//        out.close();
//        userFile.close();
    }
}
