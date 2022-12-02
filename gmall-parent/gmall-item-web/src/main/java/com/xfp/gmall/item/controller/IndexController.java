package com.xfp.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.manager.service.SkuService;
import com.xfp.gmall.manager.bean.PmsSkuSaleAttrValue;
import com.xfp.gmall.manager.service.SpuService;
import com.xfp.gmall.manager.bean.PmsProductSaleAttr;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String  skuInfo(@PathVariable String skuId,ModelMap modelMap) throws Exception {
        PmsSkuInfo skuInfo=skuService.findSkuInfoById(skuId);
        String spuId=skuInfo.getSpuId();
        List<PmsProductSaleAttr> pmsProductSaleAttrs=spuService.spuSaleAttrListCheckBySku(spuId,skuId);
        List<PmsSkuInfo> skuS=skuService.getSkusBySpuId(spuId);
        Map<String,String> skuSaleAttrHash=new HashMap<>();
        for (PmsSkuInfo pmsSkuInfo : skuS) {
            String skuid=pmsSkuInfo.getId();
            String key="";
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
            for (int i = 0; i <skuSaleAttrValueList.size() ; i++) {
                String saleAttrValueId = skuSaleAttrValueList.get(i).getSaleAttrValueId();
                if(i!=(skuSaleAttrValueList.size()-1)){
                    key+=saleAttrValueId+"|";
                }else {
                    key+=saleAttrValueId;
                }
            }
            skuSaleAttrHash.put(key,skuid);
        }
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        modelMap.put("skuInfo",skuInfo);
        modelMap.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        modelMap.put("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);
        return "item";
    }

}
