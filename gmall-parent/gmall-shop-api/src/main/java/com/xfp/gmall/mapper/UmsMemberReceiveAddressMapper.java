package com.xfp.gmall.mapper;



import com.xfp.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UmsMemberReceiveAddressMapper {
    List<UmsMemberReceiveAddress> selectAllUserReceiverAddress(long member_id);
}
