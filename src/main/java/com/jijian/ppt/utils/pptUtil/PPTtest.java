package com.jijian.ppt.utils.pptUtil;

import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/25 10:28
 */
@Component
public class PPTtest {

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

            FileOutputStream out = new FileOutputStream("C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\template1.pptx") ;
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




}
