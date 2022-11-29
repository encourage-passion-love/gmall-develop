package com.xfp.gmall.search.service;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SkuSearchService {


    public List<PmsSearchSkuInfo> getAllSkuInfo() throws InvocationTargetException, IllegalAccessException ;

}
