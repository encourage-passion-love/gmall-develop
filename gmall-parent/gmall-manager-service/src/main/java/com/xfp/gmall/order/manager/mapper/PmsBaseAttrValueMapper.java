package com.xfp.gmall.order.manager.mapper;

import java.util.List;
import com.xfp.gmall.manager.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Param;

public interface PmsBaseAttrValueMapper {

    public void saveAttrValues(List<PmsBaseAttrValue> attrValueList);

    public List<PmsBaseAttrValue> getAttrValueList(@Param("attrId") String attrId);

    public void deleteAttrValueByAttrId(@Param("attrId")String attrId);

    public List<PmsBaseAttrValue> findAttrValuesByAttrId(@Param("attrId") String attrId);

}
