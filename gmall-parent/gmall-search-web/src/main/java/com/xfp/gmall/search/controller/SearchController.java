package com.xfp.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xfp.gmall.gmall.manager.*;
import com.xfp.gmall.manager.bean.*;
import com.xfp.gmall.manager.service.SkuService;
import com.xfp.gmall.manager.service.SkuSearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    @RequestMapping("/index")
    public String index() {
        return "index";
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
    public String list(PmsSearchParam pmsSearchParam, ModelMap map) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = skuSearchService.getDataFromES(pmsSearchParam);
        Set<String> valueIds = new HashSet<>();
        if (pmsSearchSkuInfos != null && pmsSearchSkuInfos.size() > 0) {
            for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
                List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
                for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                    String valueId = pmsSkuAttrValue.getValueId();
                    valueIds.add(valueId);
                }
            }
        }
        map.put("skuLsInfoList", pmsSearchSkuInfos);
        List<PmsBaseAttrInfo> attrs = skuService.getPmsAttrListBySkuValueId(valueIds);
        //去除已经选择的属性值的ID
        String[] delValueIds = pmsSearchParam.getValueId();
        map.put("attrList", attrs);
        List<PmsSearchCrumb> crumbs = new ArrayList<>();
        if (delValueIds != null && delValueIds.length > 0) {
            for (String delValueId : delValueIds) {
                Iterator<PmsBaseAttrInfo> iterator = attrs.iterator();
                String urlParams = combineQueryOptionsOne(pmsSearchParam, delValueId);
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueId(delValueId);
                pmsSearchCrumb.setUrlParam(urlParams);
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo next = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = next.getAttrValueList();
                    if (attrValueList != null && attrValueList.size() > 0) {
                        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                            if (delValueId.equals(pmsBaseAttrValue.getId())) {
                                pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                                iterator.remove();
                            }
                        }
                    }
                }
                crumbs.add(pmsSearchCrumb);
            }
            map.put("attrValueSelectedList", crumbs);
        }
        String urlParam = combineQueryOptions(pmsSearchParam);
        map.put("urlParam", urlParam);
        String keyword = pmsSearchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            map.put("keyword", keyword);
        }
        return "list";
    }

    public String combineQueryOptions(PmsSearchParam pmsSearchParam) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String urlParam = "";
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (skuAttrValueList != null && skuAttrValueList.length > 0) {
            for (String pmsSkuAttrValue : skuAttrValueList) {
                if (StringUtils.isNotBlank(urlParam)) {
                    urlParam = urlParam + "&";
                }
                urlParam = urlParam + "valueId=" + pmsSkuAttrValue;
            }
        }
        return urlParam;
    }

    public String combineQueryOptionsOne(PmsSearchParam pmsSearchParam, String delValueId) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String urlParam = "";
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (skuAttrValueList != null && skuAttrValueList.length > 0) {
            for (String pmsSkuAttrValue : skuAttrValueList) {
                if (delValueId != null) {
                    if (!delValueId.equals(pmsSkuAttrValue)) {
                        if (StringUtils.isNotBlank(urlParam)) {
                            urlParam = urlParam + "&";
                        }
                        urlParam = urlParam + "valueId=" + pmsSkuAttrValue;
                    }
                }
            }
        }
        return urlParam;
    }
}
