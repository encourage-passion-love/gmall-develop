package com.xfp.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsProductInfo;
import com.xfp.gmall.manager.service.SpuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpuController {
    @Reference
    private SpuService spuService;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(@RequestParam("catalog3Id")String catalog3Id ){
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }
}
