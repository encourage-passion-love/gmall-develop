package com.xfp.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.PmsBaseAttrInfo;
import com.xfp.gmall.manager.bean.PmsSearchParam;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuAttrValue;
import com.xfp.gmall.manager.service.SkuSearchService;
import com.xfp.gmall.manager.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    @Reference
    private SkuSearchService skuSearchService;
    @Autowired
    private JestClient jestClient;

    @Reference
    private SkuService skuService;

    @RequestMapping("/search")
    @ResponseBody
    public List<PmsSearchSkuInfo> getSearchInfo() throws InvocationTargetException, IllegalAccessException {
        List<PmsSearchSkuInfo> allSkuInfo = skuSearchService.getAllSkuInfo();
        return allSkuInfo;
    }
    @RequestMapping("/import")
    @ResponseBody
    public String importDataFromSqlToES() throws InvocationTargetException, IllegalAccessException, IOException {

        List<PmsSearchSkuInfo> allSkuInfo = skuSearchService.getAllSkuInfo();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : allSkuInfo) {
            Index build = new Index.Builder(pmsSearchSkuInfo).index("gmallpms").type("pmsskuinfo").id(pmsSearchSkuInfo.getId()).build();
             jestClient.execute(build);
        }
        return "数据库数据导入ES索引库成功！！";
    }
    @RequestMapping("list.html")
    public String  list( PmsSearchParam pmsSearchParam, ModelMap map){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=skuSearchService.getDataFromES(pmsSearchParam);
        Set<String> valueIds = new HashSet<>();
        if(pmsSearchSkuInfos!=null&&pmsSearchSkuInfos.size()>0){
            for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
                List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
                for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                    String valueId = pmsSkuAttrValue.getValueId();
                    valueIds.add(valueId);
                }
            }
        }
        //使用valueIds去对接数据库平台属性表的数据
        List<PmsBaseAttrInfo> attrs=skuService.getPmsAttrListBySkuValueId(valueIds);
        map.put("skuLsInfoList",pmsSearchSkuInfos);
        map.put("attrList",attrs);
        return "list";
    }
}
