package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.TablePageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *图表页相关接口
 * @author 武泽中
 * @version 1.0
 * @date 2020/4/12
 */
@Service
@Slf4j
public class TablePageServiceImpl implements TablePageService {
    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;

    @Override
    public UniversalResponseBody<FileDetail> makeTablePage(Integer fileId, String[] names, String[] values, String chartTitle,String title,String paragraph) throws IOException {
        //读取文件详细信息
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);

        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());

        //读取模板文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));

        //获取模板的图文页
        XSLFSlide templateSlide = ppt.getSlides().get(PageCategoryEnum.PICTURE_PAGE.getPageCategoryId());
        //读取用户文件
        XMLSlideShow userFile = new XMLSlideShow(new FileInputStream(fileDetail.getFilePath()));
        //读取模板文件的排版
        XSLFSlideLayout layout = templateSlide.getSlideLayout();
        //将排版应用到用户文件
        XSLFSlide slide = userFile.createSlide(layout);

        for ( XSLFShape shape : templateSlide.getShapes())
        {
            if ( shape instanceof XSLFTextShape)
            {
                XSLFTextShape txtshape = (XSLFTextShape)shape ;
                if (((XSLFTextShape) shape).getText().contains("Title")){
                    txtshape.setText(title);
                }
                if (((XSLFTextShape) shape).getText().contains("{Paragraph}")){
                    txtshape.setText(paragraph);
                }
            }
        }
        slide.importContent(templateSlide);
        //遍历图表页元素找到图表
        XSLFChart chart = null;
        for (POIXMLDocumentPart part : slide.getRelations()) {
            if (part instanceof XSLFChart) {
                chart = (XSLFChart) part;
                break;
            }
        }
        if (chart == null) {
            return new UniversalResponseBody<FileDetail>(ResponseResultEnum.FAILED.getCode(),ResponseResultEnum.FAILED.getMsg(),fileDetail);
        }

        //把图表绑定到Excel workbook中
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();

        CTPieChart pieChart = plotArea.getPieChartArray(0);
        //获取图表的系列
        CTPieSer ser = pieChart.getSerArray(0);

        // 图表标题/种类总称
        CTSerTx tx = ser.getTx();
        tx.getStrRef().getStrCache().getPtArray(0).setV(chartTitle);
        sheet.createRow(0).createCell(1).setCellValue(chartTitle);
        String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);

        // 数据区域
        CTAxDataSource cat = ser.getCat();
        CTStrData strData = cat.getStrRef().getStrCache();

        //获取图表的值
        CTNumDataSource val = ser.getVal();
        CTNumData numData = val.getNumRef().getNumCache();

        strData.setPtArray(null);  // unset old axis text
        numData.setPtArray(null);  // unset old values

        //设置数据域
        int idx = 0;
        int rownum = 1;
        String ln;
        for (int i=0; i<names.length; i++) {
            CTNumVal numVal = numData.addNewPt();
            numVal.setIdx(idx);
            numVal.setV(values[i]);

            CTStrVal sVal = strData.addNewPt();
            sVal.setIdx(idx);
            sVal.setV(names[i]);

            idx++;
            XSSFRow row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(names[i]);
            row.createCell(1).setCellValue(Double.valueOf(values[i]));
        }
        numData.getPtCount().setVal(idx);
        strData.getPtCount().setVal(idx);

        String numDataRange = new CellRangeAddress(1, rownum-1, 1, 1).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
        cat.getStrRef().setF(axisDataRange);
        ser.addNewDLbls().addNewShowLeaderLines();// 有无此行代码,图上是否显示文字

        //更新嵌入的workbook
        OutputStream xlsOut = chart.getPackagePart().getOutputStream();
        wb.write(xlsOut);
        xlsOut.close();
        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
