package com.xfp.gmall.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.xfp.gmall.manager.bean.PmsSearchParam;
import com.xfp.gmall.manager.bean.PmsSearchSkuInfo;
import com.xfp.gmall.manager.bean.PmsSkuAttrValue;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuSearchService;
import com.xfp.gmall.manager.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl  implements SkuSearchService {

    @Reference
    private SkuService skuService;
    @Autowired
    private JestClient jestClient;

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

    @Override
    public List<PmsSearchSkuInfo> getDataFromES(PmsSearchParam pmsSearchParam) {
        //根据参数获取ES里面的数据
        List<PmsSearchSkuInfo> infos=new ArrayList<>();
        String dslQueryStr = getDslQueryStr(pmsSearchParam);
        Search build = new Search.Builder(dslQueryStr).addIndex("gmallpms").addType("pmsskuinfo").build();
        try {
            SearchResult execute = jestClient.execute(build);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo source = hit.source;
                infos.add(source);
            }
            return infos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public String getDslQueryStr(PmsSearchParam pmsSearchParam){
        //在拼接条件的时候需要判断是否为空
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName",pmsSearchParam.getKeyword());
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if(StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",pmsSearchParam.getCatalog3Id());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(pmsSearchParam.getSkuAttrValueList()!=null&&pmsSearchParam.getSkuAttrValueList().size()>0)
        {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchParam.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", pmsSkuAttrValue.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);
        searchSourceBuilder = searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder.toString();
    }


}
