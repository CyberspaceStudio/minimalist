package com.jijian.ppt.utils.pptUtil;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/25 10:28
 */
@Component
public class PPTtest {



    private FileUtil fileUtil = new FileUtil();

    public void test4()
    {
        try
        {
            //打开ppt文件
            XMLSlideShow ppt = new XMLSlideShow( new FileInputStream("C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\template.pptx") );
            for( XSLFSlide slide : ppt.getSlides() )
            {
                for( XSLFShape shape : slide.getShapes() )
                {
                    if ( shape instanceof XSLFTextShape )
                    {
                       XSLFTextShape txtshape = (XSLFTextShape)shape ;

                       //如果ppt中这段文字中包含{year}
                        if (((XSLFTextShape) shape).getText().contains("{year}")){
                            txtshape.setText("2011");
                        }
                        if (((XSLFTextShape) shape).getText().contains("month")){
                            txtshape.setText("09");
                        }
                    }
                    else if ( shape instanceof XSLFConnectorShape )
                    {
                        XSLFConnectorShape connectorShape = (XSLFConnectorShape)shape ;
                    }
                    else if ( shape instanceof XSLFPictureShape )
                    {
                        XSLFPictureShape picShape = (XSLFPictureShape)shape ;
                    }
                }
            }
            FileDetail fileDetial = new FileDetail();
            fileUtil.GenerateFilePath(fileDetial);
            FileOutputStream out = new FileOutputStream(fileDetial.getFilePath()) ;
            ppt.write( out ) ;
            out.close() ;
        }
        catch (FileNotFoundException e)
        {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 目录页修改
     * @param contents
     * @param fileId
     */
    public UniversalResponseBody contentsPage(String [] contents,Integer fileId) throws IOException {
        if (contents.length == 0){
            //参数为空
            return new UniversalResponseBody(ResponseResultEnum.PARAM_IS_BLANK.getCode(),ResponseResultEnum.PARAM_IS_BLANK.getMsg());
        }
        //打开ppt文件
        XMLSlideShow ppt = new XMLSlideShow( new FileInputStream("C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\template.pptx") );
        XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
        XSLFSlideLayout slidelayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        XSLFSlide slide = ppt.createSlide(slidelayout);
        //XSLFSlide slide = ppt.createSlide();
        XSLFTextShape title = slide.getPlaceholder(1);
        XSLFTextRun textRun=title.addNewTextParagraph().addNewTextRun();
        textRun.setText("Tutorials point");
        FileDetail fileDetial = new FileDetail();
        FileUtil fileUtil = new FileUtil();
        fileUtil.GenerateFilePath(fileDetial);
        FileOutputStream out = new FileOutputStream(fileDetial.getFilePath()) ;
        ppt.write( out ) ;
        out.close() ;
        return null;
    }


}
