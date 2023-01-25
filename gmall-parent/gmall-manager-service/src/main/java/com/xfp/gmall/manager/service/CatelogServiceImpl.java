package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsBaseCatalog1;
import com.xfp.gmall.order.manager.mapper.Catelog2Mapper;
import com.xfp.gmall.manager.bean.PmsBaseCatalog2;
import com.xfp.gmall.manager.bean.PmsBaseCatalog3;
import com.xfp.gmall.order.manager.mapper.Catelog1Mapper;
import com.xfp.gmall.order.manager.mapper.Catelog3Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatelogServiceImpl implements CatelogService {

    @Autowired
    private Catelog1Mapper catelog1Mapper;
    @Autowired
    private Catelog2Mapper catelog2Mapper;
    @Autowired
    private Catelog3Mapper catelog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatelog1() {
        List<PmsBaseCatalog1> cateLog1 = catelog1Mapper.getCateLog1();
        return cateLog1;
    }

    @Override
    public List<PmsBaseCatalog2> getCatelog2(String catelog1_id) {
        List<PmsBaseCatalog2> cateLog2 = catelog2Mapper.getCateLog2(catelog1_id);
        return cateLog2;
    }


    @Override
    public List<PmsBaseCatalog3> getCatelog3(String catelog2_id) {
        List<PmsBaseCatalog3> cateLog3 = catelog3Mapper.getCateLog3(catelog2_id);
        return cateLog3;
    }
}
