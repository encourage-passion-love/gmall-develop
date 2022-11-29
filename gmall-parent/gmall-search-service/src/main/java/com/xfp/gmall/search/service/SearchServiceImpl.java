package com.xfp.gmall.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuSearchService;
import com.xfp.gmall.manager.service.SkuService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl  implements SkuSearchService {

    @Reference
    private SkuService skuService;

    @Override
    public List<PmsSearchSkuInfo> getAllSkuInfo() throws InvocationTargetException, IllegalAccessException {
        List<PmsSearchSkuInfo> searchSkuInfos=new ArrayList<>();
        List<PmsSkuInfo> allSkuInfo = skuService.getAllSkuInfo();
        for (PmsSkuInfo pmsSkuInfo : allSkuInfo) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            pmsSearchSkuInfo.setProductId(pmsSkuInfo.getSpuId());
            BeanUtils.copyProperties(pmsSearchSkuInfo,pmsSkuInfo);
            searchSkuInfos.add(pmsSearchSkuInfo);
        }
        return searchSkuInfos;
    }
}
