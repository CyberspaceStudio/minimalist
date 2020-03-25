package com.jijian.ppt.utils.pptUtil;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 整个ppt操作的类
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/25 14:34
 */
@Component
public class PptUtil {

    /**
     * 根据文件id去打开相应的ppt
     * @param fileId
     * @return
     */
    public XMLSlideShow openPpt(Integer fileId) throws IOException {
        //根据文件id去查找相应的文件路径
        String fileUrl = null;
        File file = new File(fileUrl);
        InputStream inputStream = new FileInputStream(file);
        XMLSlideShow xmlSlideShow = new XMLSlideShow(inputStream);
        return xmlSlideShow;
    }

}
