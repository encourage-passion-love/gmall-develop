package com.xfp.gmall.manager.service;

import com.xfp.gmall.manager.bean.PmsProductInfo;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SkuService {

    public PmsSkuInfo saveSkuInfo( PmsSkuInfo pmsSkuInfo);


}
