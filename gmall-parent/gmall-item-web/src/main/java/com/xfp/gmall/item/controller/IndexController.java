package com.xfp.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.service.SpuService;
import com.xfp.gmall.manager.bean.PmsProductSaleAttr;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Reference
    private SkuService skuService;
    @Reference
    private SpuService spuService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("数据1"+i);
        }
        modelMap.put("key","hello thymeleaf !!");
        modelMap.put("list",list);
        return "index";
    }


    @RequestMapping("{skuId}.html")
    public String  skuInfo(@PathVariable String skuId,ModelMap modelMap){
        PmsSkuInfo skuInfo=skuService.findSkuInfoById(skuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs=spuService.spuSaleAttrListCheckBySku(skuInfo.getSpuId(),skuId);
        modelMap.put("skuInfo",skuInfo);
        modelMap.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        return "item";
    }

}
