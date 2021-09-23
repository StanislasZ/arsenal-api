package com.cmbnb.weChatNotice.modules.cbs.model;

import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52.Dcprtrcpz;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52.Ntprtmsgy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 5.2 返回内容 对应类
 */
@Data
public class DzhdInfo {

    List<Ntprtmsgy> ntprtmsgyRltList;
    List<Dcprtrcpz> dcprtrcpzRltList;
    JSONObject istNbrImPathMap;



}
