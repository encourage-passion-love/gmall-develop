package com.xfp.gmall.manager.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.manager.bean.*;
import com.xfp.gmall.manager.mapper.*;
import com.xfp.gmall.order.manager.mapper.*;
import com.xfp.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Override
    public PmsSkuInfo saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.saveSkuInfo(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();
        List<PmsSkuAttrValue> pmsSkuAttrValues=new ArrayList<>();
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues=new ArrayList<>();
        List<PmsSkuImage> pmsSkuImages=new ArrayList<>();
        //添加平台属性
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValues.add(pmsSkuAttrValue);
        }
        //添加销售属性
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValues.add(pmsSkuSaleAttrValue);
        }
        //添加sku所有的image
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImages.add(pmsSkuImage);
        }
        //进行三张表操作添加数据就可以
        pmsSkuAttrValueMapper.saveSkuAttrValues(pmsSkuAttrValues);
        pmsSkuSaleAttrValueMapper.saveSkuSaleAttrValues(pmsSkuSaleAttrValues);
        pmsSkuImageMapper.saveSkuImages(pmsSkuImages);
        return pmsSkuInfo;
    }

    public PmsSkuInfo getSkuInfoFromDB(String skuId){
        PmsSkuInfo skuById = pmsSkuInfoMapper.findSkuById(skuId);
        //获取sku的图片列表
        List<PmsSkuImage> skuImgsBySkuId = pmsSkuImageMapper.findSkuImgsBySkuId(skuId);
        //获取sku的平台属性值
        List<PmsSkuAttrValue> attrvaluesBySkuId = pmsSkuAttrValueMapper.findAttrvaluesBySkuId(skuId);
        //获取sku的销售属性值
        List<PmsSkuSaleAttrValue> saleAttrvalueBySkuId = pmsSkuSaleAttrValueMapper.findSaleAttrvalueBySkuId(skuId);
        skuById.setSkuImageList(skuImgsBySkuId);
        skuById.setSkuAttrValueList(attrvaluesBySkuId);
        skuById.setSkuSaleAttrValueList(saleAttrvalueBySkuId);
        return skuById;
    }

    @Override
    public PmsSkuInfo findSkuInfoById(String skuId)throws Exception {
        //连接redis
        PmsSkuInfo pmsSkuInfo=null;
        Jedis jedis = null;
        String disLock="sku:"+skuId+":lock";

            pmsSkuInfo=new PmsSkuInfo();
            jedis=redisUtil.getJedis();
            String skuStr = jedis.get("sku:" + skuId + ":info");
            if(StringUtils.isNotBlank(skuStr)){
                //出出来数据了
                pmsSkuInfo = JSON.parseObject(skuStr,PmsSkuInfo.class);
            }else {
                String token= UUID.randomUUID().toString();
              try {
                  String set = jedis.set(disLock, token, "nx", "ex", 10*1000);
                  if(StringUtils.isNotBlank(set)&&"OK".equals(set)){
                      pmsSkuInfo = getSkuInfoFromDB(skuId);
                      if(pmsSkuInfo!=null){
                          jedis.set("sku:" + skuId + ":info",JSON.toJSONString(pmsSkuInfo));
                          //这个地方删除自己得锁就OK了
                          String disToken = jedis.get(disLock);
                          String script="if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                          jedis.eval(script, Collections.singletonList(disLock),Collections.singletonList(disToken));
                      }else {
                          //防止缓存穿透给数据库造成压力 所以在2分钟以内
                          //读取的都是缓存中的空字符串
                          jedis.setex("sku:" + skuId + ":info",60*2,JSON.toJSONString(""));
                      }
                  }
                  else {
                      Thread.sleep(3000);
                      return findSkuInfoById(skuId);
                  }
              }catch (Exception e){
                  e.printStackTrace();
                  jedis.del(disLock);
              }finally {
                  jedis.close();
              }
        }
       return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkusBySpuId(String spuId) {
        List<PmsSkuInfo> skus=pmsSkuInfoMapper.getSkusBySpuId(spuId);
        return skus;
    }

    @Override
    public List<PmsSkuInfo> getAllSkuInfo() {
        List<PmsSkuInfo> pmsSkuInfos=pmsSkuInfoMapper.getAllSkuInfo();
        return pmsSkuInfos;
    }

    @Override
    public List<PmsBaseAttrInfo> getPmsAttrListBySkuValueId(Set<String> valueIds) {
        String vIds = StringUtils.join(valueIds, ",");
        List<PmsBaseAttrInfo> attrs=pmsBaseAttrInfoMapper.getBaseAttrListBySkuValueIds(vIds);
        return attrs;
    }
}
