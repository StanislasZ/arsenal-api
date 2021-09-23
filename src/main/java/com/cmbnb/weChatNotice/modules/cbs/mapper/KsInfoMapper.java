package com.cmbnb.weChatNotice.modules.cbs.mapper;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.Cicltothy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KsInfoMapper {

    int getKsInfoCount();

    int addKsInfoList(List<Cicltothy> list);

    int deleteAllKsInfo();
}
