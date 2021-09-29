package com.cmbnb.weChatNotice.modules.clientInfo.service;


import com.cmbnb.weChatNotice.core.base.CommonJsonException;
import com.cmbnb.weChatNotice.core.entity.ObjListResp;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.core.util.StringTools;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.ClientInfo;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.request.ClientInfoQo;
import com.cmbnb.weChatNotice.modules.clientInfo.mapper.ClientInfoMapper;
import com.cmbnb.weChatNotice.modules.wechat.entity.WechatUser;
import com.cmbnb.weChatNotice.modules.wechat.entity.request.WechatUserQo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientInfoService {

    @Autowired
    ClientInfoMapper clientInfoMapper;


    /**
     * 更新 client_info 表
     * 先把所有数据 的 delete_flag列置为true
     * 再添加
     * @param file
     */
    @Transactional
    public void updateClientInfoFromExcel(MultipartFile file) throws IOException {
        List<ClientInfo> clientInfos = new ArrayList<>();
        CommonUtils.readExcel(file.getInputStream(), ClientInfo.class, list -> {
            clientInfos.addAll(list);
        });
        clientInfos.forEach(clientInfo -> {
            clientInfo.setName(StringTools.trimAllBlank(clientInfo.getName()));
            clientInfo.setDeleteFlag(false);
            clientInfo.setAddDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        });
//        List<ClientInfo> validClientInfos = clientInfos
//                .stream()
//                .filter(ele -> !StringUtils.isEmpty(ele.getName()))
//                .collect(Collectors.toList());

        List<ClientInfo> clientInfosWithDistinctName = clientInfos
                .stream()
                .filter(ele -> !StringUtils.isEmpty(ele.getName()))
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(ClientInfo::getName))), ArrayList::new));



        List<String> nameList = clientInfosWithDistinctName
                .stream()
                .map(ele -> ele.getName())
                .collect(Collectors.toList());


        if (nameList.contains("") || nameList.contains(null)) {
            throw new CommonJsonException("上传失败，企业全称存在空值，请检查。");
        }
        if (nameList.stream().distinct().count() != nameList.stream().count()) {
            throw new CommonJsonException("上传失败，企业全称存在重复行，请检查。");
        }





        System.out.println("clientInfosWithDistinctName.size  = " + clientInfosWithDistinctName.size());
//        clientInfosWithDistinctName.forEach(System.out::println);

        //先把所有数据的 delete_flag 改为 true
        clientInfoMapper.updateDeleteFlag(true);

        //后加
        clientInfoMapper.insertList(clientInfosWithDistinctName);

    }



    public ObjListResp<ClientInfo> getClientInfoList(ClientInfoQo qo) {
        CommonUtils.fillPageParam(qo);
        ObjListResp<ClientInfo> resp = new ObjListResp<>();
        resp.setTotalCount(clientInfoMapper.getClientInfoCnt(qo));
        resp.setList(clientInfoMapper.getClientInfoList(qo));
        return resp;
    }


    /**
     * 前端导出
     * @return
     */
    public List<ClientInfo> getExportList() {
        return clientInfoMapper.getExportList();
    }
}
