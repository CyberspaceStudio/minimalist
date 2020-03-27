package com.jijian.ppt.utils;

import com.jijian.ppt.POJO.FileDetail;
import org.springframework.stereotype.Component;

import java.io.File;
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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd\\");

    /**
     * 服务器存储用户文件的文件夹
     * 上线时请更换地址
     * private static final String FileDirectory = "/a-minimalist/file/";
     */

    private static final String FileDirectory = "C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\";

    /**
     * 生成文件路径
     * @param fileDetail
     * @return
     */
    public void GenerateFilePath(FileDetail fileDetail){

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
