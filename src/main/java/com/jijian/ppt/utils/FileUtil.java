package com.jijian.ppt.utils;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/26 10:18
 */
@Component
public class FileUtil {

    /**
     * Linux服务器存储用户文件的文件夹
     * 上线时请更换地址
     * private static SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd/");
     * @Value("${file.tempaltePath}")
     * private static final String FileDirectory = "/a-minimalist/file/";
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd\\");
    private static final String FileDirectory = "C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\";


    /**
     * 生成文件路径
     * @param fileDetail
     * @return
     */
    public static void GenerateFilePath(FileDetail fileDetail){
        String ParentDirectory = FileDirectory+sdf.format(new Date());
        File folder = new File(ParentDirectory);
        if (!folder.isDirectory()) {
            //每天对应一个文件夹，如果不存在则新建一个文件夹
            folder.mkdirs();
        }
        String filePath =ParentDirectory+""+ UUID.randomUUID().toString()+".pptx";
        fileDetail.setFilePath(filePath);
    }

}
