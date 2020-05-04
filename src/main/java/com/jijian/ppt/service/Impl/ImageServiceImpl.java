package com.jijian.ppt.service.Impl;

import com.jijian.ppt.service.ImageService;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 21:46
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {


    private static String imageDirectory =  "/a-minimalist/image";
    private static String url = "https://minimalist.net.cn";

    @Override
    public UniversalResponseBody<String> uploadImage(MultipartFile uploadFile, HttpServletRequest req) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+".png";
        String filePath =imageDirectory+"/"+ newName;
        String imageUrl =  url+filePath;
        log.info("图片上传"+imageUrl);
        return new UniversalResponseBody<String>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),imageUrl);
    }
}
