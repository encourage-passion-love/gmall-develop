package com.xfp.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsBaseCatalog3;
import com.xfp.gmall.manager.bean.PmsBaseCatalog1;
import com.xfp.gmall.manager.service.CatelogService;
import com.xfp.gmall.manager.bean.PmsBaseCatalog2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatelogController {

    @Reference
    private CatelogService catelogService;

    @RequestMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> catelog1 = catelogService.getCatelog1();
        return catelog1;
    }
    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam("catalog1Id") String catelog1Id){
        List<PmsBaseCatalog2> catelog2 = catelogService.getCatelog2(catelog1Id);
        return catelog2;
    }
    @RequestMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam("catalog2Id") String catelog2Id){
        List<PmsBaseCatalog3> catelog3 = catelogService.getCatelog3(catelog2Id);
        return catelog3;
    }
}
