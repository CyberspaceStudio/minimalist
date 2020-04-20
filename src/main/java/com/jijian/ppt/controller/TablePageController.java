package com.jijian.ppt.controller;

import com.jijian.ppt.service.TablePageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 图表页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/4/12 11:28
 */
@RestController
@RequestMapping("/ppt/table")
public class TablePageController {

    @Resource
    @Qualifier("tablePageServiceImpl")
    private TablePageService tablePageService;

    /**
     * 制作图表页
     * @param fileId 文件id
     * @param names 变量名称
     * @param values 变量值
     * @param chartTitle 表名
     * @param title 标题
     * @param paragraph 段落
     * @return
     */
    public UniversalResponseBody makeTablePage(Integer fileId, String[] names, String[] values, String chartTitle,String title,String paragraph) throws IOException {
        return tablePageService.makeTablePage(fileId, names, values, chartTitle,title,paragraph);
    }
}
