package com.xfp.gmall.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsProductImage;
import com.xfp.gmall.manager.bean.PmsProductInfo;
import com.xfp.gmall.manager.bean.PmsProductSaleAttr;
import com.xfp.gmall.manager.bean.PmsProductSaleAttrValue;
import com.xfp.gmall.manager.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuMapper.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @Override
    public PmsProductInfo saveSpuInfo(PmsProductInfo pmsProductInfo) {
        //添加到productinfo表里面的信息
        pmsProductInfoMapper.saveProductInfo(pmsProductInfo);
        //添加到productimage表里面
        List<PmsProductImage> pmsImages=new ArrayList<>();
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(pmsProductInfo.getId());
            pmsImages.add(pmsProductImage);
        }
        pmsProductImageMapper.savePmsProductImages(pmsImages);
        //添加到saleAttr表里面
        List<PmsProductSaleAttr> pmsProductSaleAttrs=new ArrayList<>();
        List<PmsProductSaleAttrValue> pmsProductSaleAttrValues=new ArrayList<>();
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            pmsProductSaleAttrs.add(pmsProductSaleAttr);
        }
        //插入属性
        pmsProductSaleAttrMapper.savePmsSaleAttrs(pmsProductSaleAttrs);
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue saleAttrValue : spuSaleAttrValueList) {
                saleAttrValue.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrValues.add(saleAttrValue);
            }
        }
        //添加到saleAttrValue表里面去
        pmsProductSaleAttrValueMapper.savePmsSaleAttrValues(pmsProductSaleAttrValues);
        return pmsProductInfo;
    }
}
