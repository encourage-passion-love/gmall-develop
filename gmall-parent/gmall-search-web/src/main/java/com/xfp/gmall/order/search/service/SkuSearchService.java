package com.xfp.gmall.order.search.service;
import com.xfp.gmall.manager.bean.PmsSearchParam;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SkuSearchService {


    public List<PmsSearchSkuInfo> getAllSkuInfo() throws InvocationTargetException, IllegalAccessException ;
    public List<PmsSearchSkuInfo> getDataFromES(PmsSearchParam pmsSearchParam);
}