package com.cmbnb.weChatNotice.modules.cbs.model;


import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.Appayinfz;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 1.12 接口返回内容 包装
 *
 */
@Data
public class PaySettleData {

    private List<Appayinfz> appayinfzList;

    private Map<String, String> busNbrErpCm1Map;
}
