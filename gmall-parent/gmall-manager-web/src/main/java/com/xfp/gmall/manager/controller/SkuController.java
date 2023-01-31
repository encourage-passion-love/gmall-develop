package com.xfp.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkuController {

    @Reference
    private SkuService skuService;

    @RequestMapping("/saveSkuInfo")
    public PmsSkuInfo saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        PmsSkuInfo skuInfo = skuService.saveSkuInfo(pmsSkuInfo);
        return skuInfo;
    }
}
