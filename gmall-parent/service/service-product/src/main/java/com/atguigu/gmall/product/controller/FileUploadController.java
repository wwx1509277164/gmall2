package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @create 2020-05-28 20:57
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {
    @RequestMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        String path = ClassUtils.getDefaultClassLoader()
                .getResource("tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerService = trackerClient.getConnection();
        StorageClient1 storageClient1 = new StorageClient1(trackerService, null);
        String extension = FilenameUtils.getExtension(filename);
        String fileId = storageClient1.upload_file1(bytes, extension, null);
        System.out.println("http://192.168.200.128/"+fileId);
        return Result.ok("http://192.168.200.128:8080/"+fileId);
    }
}
