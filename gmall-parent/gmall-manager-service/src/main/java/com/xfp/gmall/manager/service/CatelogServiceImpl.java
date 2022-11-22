package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.mapper.Catelog1Mapper;
import com.xfp.gmall.manager.bean.PmsBaseCatalog1;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatelogServiceImpl implements CatelogService {

    @Autowired
    private Catelog1Mapper catelog1Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatelog1() {
        List<PmsBaseCatalog1> cateLog1 = catelog1Mapper.getCateLog1();
        return cateLog1;
    }
}
