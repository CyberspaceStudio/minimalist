package com.jijian.ppt.utils;

import com.jijian.ppt.POJO.FileDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * PPT相关工具类
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/26 10:18
 */
@Slf4j
public class FileUtil {

    /**
     * Linux服务器存储用户文件的文件夹
     * 上线时请更换地址
     */
     //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd\\");
     //private static final String FileDirectory = "C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\";
     private static SimpleDateFormat sdf = new SimpleDateFormat("/yyyMMdd/");
     private  static String FileDirectory = "/a-minimalist/file";


    /**
     * openOffice连接地址和端口，不需要更改
     */
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8100;

    /**
     * 生成文件路径
     * @param fileDetail
     * @return
     */
    public  static void GenerateFilePath(FileDetail fileDetail){
        String uuid = UUID.randomUUID().toString();
        String ParentDirectory = FileDirectory+sdf.format(new Date())+uuid;
        File folder = new File(ParentDirectory);
        if (!folder.isDirectory()) {
            //每个文件对应一个日期下的一个文件夹，如果不存在则新建一个文件夹
            folder.mkdirs();
        }
        //最后的存储路径为/a-minimalist/file/uuid/uuid.pptx
        String filePath =ParentDirectory+"/" +uuid+".pptx";
        fileDetail.setFilePath(filePath);
    }

    /**
     * 利用POI库将PPTX转换为图片，但此方法存在严重的丢失
     * @param filePath
     * @return
     * @throws IOException
     */
    public List<String> pptxPreviewByPOI(String filePath) throws IOException {
        List<String> urls = new LinkedList<>();
        FileInputStream is = new FileInputStream(filePath);
        XMLSlideShow ppt = new XMLSlideShow(is);
        is.close();
        String imgUrl = "https://minimalist.net.cn/image";
        String dir = filePath.substring(0, filePath.lastIndexOf("/"));
        Dimension pgsize = ppt.getPageSize();
        System.out.println(pgsize.width + "--" + pgsize.height);
        for (int i = 0; i < ppt.getSlides().size(); i++) {
            try {
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                // render
                ppt.getSlides().get(i).draw(graphics);

                // save the output
                String filename = dir + "/" + (i + 1) + ".jpg";
                System.out.println(filename);
                urls.add(filename);
                FileOutputStream out = new FileOutputStream(filename);
                javax.imageio.ImageIO.write(img, "png", out);
                out.close();
            } catch (Exception e) {
                System.out.println("第" + i + "张ppt转换出错");
            }
        }

        return urls;
    }

}
