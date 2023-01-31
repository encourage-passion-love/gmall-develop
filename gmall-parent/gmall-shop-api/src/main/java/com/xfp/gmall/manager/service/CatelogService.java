package com.xfp.gmall.manager.service;


import com.xfp.gmall.manager.bean.PmsBaseCatalog3;
import com.xfp.gmall.manager.bean.PmsBaseCatalog1;
import com.xfp.gmall.manager.bean.PmsBaseCatalog2;

import java.util.List;

public interface CatelogService {
    public List<PmsBaseCatalog1> getCatelog1();
    public List<PmsBaseCatalog2> getCatelog2(String catelog1_id);
    public List<PmsBaseCatalog3> getCatelog3(String catelog2_id);
}
