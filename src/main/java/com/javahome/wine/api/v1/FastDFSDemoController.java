package com.javahome.wine.api.v1;

import com.javahome.wine.util.FastDFSClient;
import com.javahome.wine.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequestMapping("/v1/fastDFSDemo")
@Slf4j
@Validated
public class FastDFSDemoController {

    /**
     * 文件上传 Demo，展示了如何使用 FastDFSClient 将前端传来的
     * MultipartFile 上传到服务器的 FastDFS，注意：需要先在
     * fdfs_client.conf 下配置相关信息。
     *
     * @param multiPartFile 文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile multiPartFile) {
        if (multiPartFile.isEmpty()) {
            // 文件为空，返回错误页面
            return "error";
        }

        try {
            // 获取上传文件的原始名称
            String originalFilename = multiPartFile.getOriginalFilename();
            // 生成新的文件名
            String fileName = UUID.randomUUID().toString() + getExtension(originalFilename);
            System.out.println("fileName = " + fileName);
            // 上传文件到服务器
            String[] result = FastDFSClient.uploadFile(multiPartFile.getInputStream(), fileName);
            if (null == result) {
                // 上传失败
                return "error";
            }
            // 打印上传结果
            System.out.println(Arrays.toString(result));

            // 文件上传成功，返回上传结果
            return Arrays.toString(result);

        } catch (IOException e) {
            // 发生异常，返回错误页面
            return "error";
        }
    }

    /**
     * 展示了如何使用 groupName + fileName 展示图片。
     *
     * @param model 传递参数
     * @param groupName 组名，例如：group1
     * @param fileName 文件名，例如：M00/00/00/CgAQEGSeYkKAH32oAAAQt42q3UE099.png
     * @return 展示页面
     */
    @RequestMapping("/show")
    public String handleFileShow(Model model, String groupName, String fileName) {
        model.addAttribute("groupName", groupName);
        model.addAttribute("fileName", fileName);
        return "v1/fastDFSDemo/show";
    }

    private static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0) {
            return "";
        } else {
            return filename.substring(dotIndex);
        }
    }
}
