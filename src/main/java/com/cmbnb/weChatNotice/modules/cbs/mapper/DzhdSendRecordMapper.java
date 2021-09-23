package com.cmbnb.weChatNotice.modules.cbs.mapper;


import com.cmbnb.weChatNotice.modules.cbs.model.DzhdSendRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DzhdSendRecordMapper {


    int addDzhdSendRecordList(List<DzhdSendRecord> recordList);


    List<String> getTodaySendBnkFlwList();

}
