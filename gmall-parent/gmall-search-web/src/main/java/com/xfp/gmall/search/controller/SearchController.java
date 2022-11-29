package com.xfp.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;
import com.xfp.gmall.manager.service.SkuSearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class SearchController {
    @Reference
    private SkuSearchService skuSearchService;
    @Autowired
    private JestClient jestClient;

    @RequestMapping("/search")
    public List<PmsSearchSkuInfo> getSearchInfo() throws InvocationTargetException, IllegalAccessException {
        List<PmsSearchSkuInfo> allSkuInfo = skuSearchService.getAllSkuInfo();
        return allSkuInfo;
    }
    @RequestMapping("/import")
    public String importDataFromSqlToES() throws InvocationTargetException, IllegalAccessException, IOException {

        List<PmsSearchSkuInfo> allSkuInfo = skuSearchService.getAllSkuInfo();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : allSkuInfo) {
            Index build = new Index.Builder(pmsSearchSkuInfo).index("gmallpms").type("pmsskuinfo").id(pmsSearchSkuInfo.getId()).build();
             jestClient.execute(build);
        }
        return "数据库数据导入ES索引库成功！！";
    }
}
