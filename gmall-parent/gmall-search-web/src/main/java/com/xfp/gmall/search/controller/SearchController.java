package com.xfp.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.manager.bean.*;
import com.xfp.gmall.manager.service.SkuSearchService;
import com.xfp.gmall.manager.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
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
        List<PmsBaseAttrInfo> attrs=skuService.getPmsAttrListBySkuValueId(valueIds);
        //去除已经选择的属性值的ID
        String[] delValueIds = pmsSearchParam.getValueId();
        Iterator<PmsBaseAttrInfo> iterator = attrs.iterator();
        while (iterator.hasNext()){
            PmsBaseAttrInfo next = iterator.next();
            List<PmsBaseAttrValue> attrValueList = next.getAttrValueList();
            if(attrValueList!=null&&attrValueList.size()>0){
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                   if(delValueIds!=null&&delValueIds.length>0){
                       for (String delValueId : delValueIds) {
                           if(delValueId.equals(pmsBaseAttrValue.getId())){
                               iterator.remove();
                           }
                       }
                   }
                }
            }
        }
        String urlParam = combineQueryOptions(pmsSearchParam);
        map.put("skuLsInfoList",pmsSearchSkuInfos);
        map.put("attrList",attrs);
        map.put("urlParam",urlParam);
        return "list";
    }

    public String combineQueryOptions(PmsSearchParam pmsSearchParam){
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String urlParam="";
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        if(StringUtils.isNotBlank(keyword)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam=urlParam+"&";
            }
            urlParam=urlParam+"keyword="+keyword;
        }
        if(StringUtils.isNotBlank(catalog3Id)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam=urlParam+"&";
            }
            urlParam=urlParam+"catalog3Id="+catalog3Id;
        }

        if(skuAttrValueList!=null&&skuAttrValueList.length>0){
            for (String pmsSkuAttrValue : skuAttrValueList) {
                if(StringUtils.isNotBlank(urlParam)){
                    urlParam=urlParam+"&";
                }
                urlParam=urlParam+"valueId="+pmsSkuAttrValue;
            }
        }
        return urlParam;
    }
}
