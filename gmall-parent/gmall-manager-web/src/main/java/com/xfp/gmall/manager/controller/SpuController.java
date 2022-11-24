package com.xfp.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsProductInfo;
import com.xfp.gmall.manager.service.SpuService;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class SpuController {

    @Reference
    private SpuService spuService;

    /*
    这个是NginxWeb服务器的地址
     */
    @Value("${fdfs.baseUrl}")
    private String fdsfBaseUrl;

    @Autowired
    private StorageClient storageClient;


    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(@RequestParam("catalog3Id")String catalog3Id ){
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException, MyException {
        String url=fdsfBaseUrl;
        byte[] bytes = multipartFile.getBytes();
        String originalFilename = multipartFile.getOriginalFilename();
        String ext_Name = originalFilename.substring(originalFilename.indexOf(".")+1);
        String[] upload_file = storageClient.upload_file(bytes, 0, bytes.length, ext_Name, null);
        for (String pth : upload_file) {
            url+="/"+pth;
        }
        return url;
    }

    @RequestMapping("/saveSpuInfo")
    public PmsProductInfo saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        PmsProductInfo productInfo = spuService.saveSpuInfo(pmsProductInfo);
        return productInfo;
    }

}

