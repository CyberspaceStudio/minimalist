package com.jijian.ppt.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/4/30 20:51
 */
@Slf4j
@Component
public class PdfToImg {

    // 经过测试，dpi 为 96,100,105,120,150,200 中，105 显示效果较为清晰，体积稳定，dpi 越高图片体积越大，一般电脑显示分辨率为 96
    public static final float DEFAULT_DPI = 105;

    public static final String DEFAULT_IMAGE_FORMAT = ".png";

    /**
     * pdf 转图片，只生成一张图片
     * @param pdfPath PDF路径
     * @return pdf 页数
     */
    public static int pdfToImageAllToSingleImage(String pdfPath, String imageFilePath, int pageSize) throws Exception {
        BufferedImage imageResult;
        PDDocument pdDocument = PDDocument.load(new File(pdfPath));
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

        int pages = pdDocument.getNumberOfPages();
        int len = Math.min(pages, pageSize);
        int width = 0;
        int imageHeightTotal = 0;

        for (int i = 0; i < len; i++) {
            BufferedImage image = pdfRenderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
            imageHeightTotal += image.getHeight();
            if (i == 0) {
                width = image.getWidth();
            }
        }

        imageResult = new BufferedImage(width, imageHeightTotal, BufferedImage.TYPE_INT_RGB);
        int shiftHeight = 0;
        int[] singleImgRGB;

        for (int i = 0; i < len; i++) {
            BufferedImage image = pdfRenderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
            int imageHeight = image.getHeight();

            // 计算偏移高度
            if (i > 0) {
                shiftHeight += pdfRenderer.renderImageWithDPI(i - 1, DEFAULT_DPI, ImageType.RGB).getHeight();
            }

            singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
            // 写入流中
            imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
        }

        // 写图片
        ImageIO.write(imageResult, DEFAULT_IMAGE_FORMAT.replace(".", ""), new File(imageFilePath));
        pdDocument.close();
        return pages;
    }

    /**
     * pdf 转图片，每页生成一张图片
     * @param pdfPath PDF路径
     * @return 图片的路径
     */
    public List<String> pdfToImageOnePageOnImage(String pdfPath) throws Exception {

        List<String> imgUrls = new LinkedList<>();

        String url ="https://minimalist.net.cn";
        //pdf文件所在的文件夹
        String dir = pdfPath.substring(0, pdfPath.lastIndexOf("/"));
        log.info(dir);
        BufferedImage imageResult;

        log.info(pdfPath);

        PDDocument pdDocument = PDDocument.load(new File(pdfPath));

        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

        int pages = pdDocument.getNumberOfPages();

        int width;

        int shiftHeight = 0;

        int[] singleImgRGB;

        for (int i = 0; i < pages; i++) {

            BufferedImage image = pdfRenderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
            width = image.getWidth();
            int imageHeight = image.getHeight();
            imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
            singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
            // 写入流中
            imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);

            String filePath = dir+"/"+ i + DEFAULT_IMAGE_FORMAT;

            log.info("filePath"+filePath);

            String imgUrl = url + filePath;

            log.info("imgUrl"+imgUrl);
            //如果图片存在,则删除图片
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            imgUrls.add(imgUrl);
            // 写图片
            ImageIO.write(imageResult, DEFAULT_IMAGE_FORMAT.replace(".", ""), file);
        }
        pdDocument.close();
        return imgUrls;
    }
}

