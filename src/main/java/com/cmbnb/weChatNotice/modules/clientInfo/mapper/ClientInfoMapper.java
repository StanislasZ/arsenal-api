package com.cmbnb.weChatNotice.modules.clientInfo.mapper;


import com.cmbnb.weChatNotice.modules.clientInfo.entity.ClientInfo;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.request.ClientInfoQo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientInfoMapper {



    int getClientInfoCnt(ClientInfoQo qo);


    List<ClientInfo> getClientInfoList(ClientInfoQo qo);

    int updateDeleteFlag(boolean flag);

    int insertList(List<ClientInfo> list);


    List<ClientInfo> getExportList();
}
