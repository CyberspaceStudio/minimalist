package com.jijian.ppt.utils;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 利用jodconverter(基于OpenOffice服务)将文件(*.doc、*.docx、*.xls、*.ppt)转化为html格式或者pdf格式，
 * 使用前请检查OpenOffice服务是否已经开启, OpenOffice进程名称：soffice.exe | soffice.bin
 *
 * @author yjclsx
 */
@Slf4j
@Component
public class Pptx2PdfUtil {

    /**
     * 转换文件成pdf
     *
     * @throws IOException
     */
    public String fileToPdf(String filePath) throws IOException {
        File pptFile = new File(filePath);
        //文件所处的文件夹
        String dir = filePath.substring(0, filePath.lastIndexOf("/"));
        log.info("转pdf"+dir);

        //文件的UUID
        String uuid = dir.substring(dir.lastIndexOf("/"));
        log.info("uuid"+uuid);
        String pdfFileName = null;

        pdfFileName = dir+ uuid + ".pdf";

        log.info("pdfFileName"+pdfFileName);

        File pdfOutputFile = new File(pdfFileName);
        //如果此文件存在pdf，则删除原来的文件，新建一个文件
        if (pdfOutputFile.exists()) {
            pdfOutputFile.delete();
        }
        pdfOutputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
        }
        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(pptFile,pdfOutputFile);
        connection.disconnect();
        return pdfFileName;
    }

}