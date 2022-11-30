package com.xfp.gmall.manager.service;
import com.xfp.gmall.manager.bean.PmsSearchParam;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SkuSearchService {

    public List<PmsSearchSkuInfo> getAllSkuInfo() throws InvocationTargetException, IllegalAccessException;
    public List<PmsSearchSkuInfo> getDataFromES(PmsSearchParam pmsSearchParam);
}
