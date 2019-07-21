package com.chenghe.sys.controller;

import com.chenghe.common.UploadResponse;
import com.chenghe.sys.interceptor.Repeat;
import com.youguu.core.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * describe:
 *
 * @author: shi_lei@suixingpay.com
 * @date: 2019/07/21 18:59:18:59
 * @version: V1.0
 * @review:
 */
@Controller
@RequestMapping("upload")
public class UploadImageController {
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    @Repeat
    public UploadResponse uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        UploadResponse rs = new UploadResponse();

        if (null != file) {
            String myFileName = file.getOriginalFilename();// 文件原名称
            SimpleDateFormat dft = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = dft.format(new Date()) + Integer.toHexString(new Random().nextInt()) + "." + myFileName.substring(myFileName.lastIndexOf(".") + 1);

            Properties properties = PropertiesUtil.getProperties("properties/config.properties");
            String path = properties.getProperty("img.path");
            String imageUrl = properties.getProperty("imageUrl");

            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            String midPath = yyyyMMdd.format(new Date());
            File fileDir = new File(path + midPath);
            if (!fileDir.exists()) { //如果不存在 则创建
                fileDir.mkdirs();
            }
            path = path + midPath + File.separator + fileName;
            File localFile = new File(path);
            try {
                file.transferTo(localFile);

                rs.setStatus(true);
                rs.setMsg("上传成功");
                String httpUrl = imageUrl + File.separator + midPath + File.separator + fileName;
                rs.setImageUrl(httpUrl);

            } catch (IllegalStateException e) {
                rs.setStatus(false);
                rs.setMsg("服务器异常");
            } catch (Exception e) {
                rs.setStatus(false);
                rs.setMsg("服务器异常");
            }
        } else {
            rs.setStatus(false);
            rs.setMsg("文件为空");
        }

        return rs;
    }
}
