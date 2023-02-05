package com.zrylovestan.arsenal.core.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于EasyExcel 解析数据.
 * @param <T>
 */
public class EasyExcelDataListener<T> extends AnalysisEventListener<T> {


    public static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelDataListener.class);

    private static final int BATCH_COUNT = 5;

    List<T> list = new ArrayList<>();

    private InsertAction insertAction;


    public EasyExcelDataListener(InsertAction<T> insertAction){
        this.insertAction = insertAction;
    }

    public void setInsertAction(InsertAction<T> insertAction) {
        this.insertAction = insertAction;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
        LOGGER.info("解析到的数据:");
        LOGGER.info(t.toString());
        if(list.size() >= BATCH_COUNT) {
            insertAction.batchInsert(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
         insertAction.batchInsert(list);
         LOGGER.info("所有的数据都已经插入完成. ");
    }


    //内部接口
    public interface InsertAction<T> {
        void batchInsert(List<T> list);
    }


}
