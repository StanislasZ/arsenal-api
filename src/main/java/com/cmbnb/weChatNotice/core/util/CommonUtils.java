package com.cmbnb.weChatNotice.core.util;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.base.CommonJsonException;
import com.cmbnb.weChatNotice.core.entity.ObjListResp;
import com.cmbnb.weChatNotice.core.entity.Page;
import com.cmbnb.weChatNotice.core.util.constants.Constants;
import com.cmbnb.weChatNotice.core.util.constants.ErrorEnum;
import com.cmbnt.owk.web.util.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: hxy
 * @description: 本后台接口系统常用的json工具类
 * @date: 2017/10/24 10:12
 */
public class CommonUtils {

    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("msg", errorEnum.getErrorMsg());
        resultJson.put("data", new JSONObject());
        return resultJson;
    }

    public static JSONObject errorJson(Exception ex) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 999);
        resultJson.put("msg", ex.getMessage());
        resultJson.put("data", new JSONObject());
        return resultJson;
    }

    public static JSONObject errorJson(String code, String message) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", code);
        resultJson.put("msg", message);
        resultJson.put("data", new JSONObject());
        return resultJson;
    }

    public static JSONObject errorJson(String message) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 999);
        resultJson.put("msg", message);
        resultJson.put("data", new JSONObject());
        return resultJson;
    }

    /**
     * 返回一个data为空对象的成功消息的json
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }
    /**
     * 返回一个返回码为200的json
     */
    public static JSONObject successJson(Object data) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", Constants.SUCCESS_CODE);
        resultJson.put("msg", Constants.SUCCESS_MSG);
        resultJson.put("data", data);
        return resultJson;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringTools.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringTools.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringTools.isNullOrEmpty(missCol)) {
                jsonObject.clear();
                jsonObject.put("code", ErrorEnum.E_90003.getErrorCode());
                jsonObject.put("msg", "缺少必填参数:" + missCol.trim());
                jsonObject.put("data", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        }
    }


    /**
     * 查询分页结果后的封装工具方法
     *
     * @param list 查询分页对象list
     */
    public static <T> JSONObject successPage(List<T> list) {
        JSONObject result = successJson();
        JSONObject data = new JSONObject();
        data.put("list", list);
        result.put("data", data);
        return result;
    }

    /**
     * 获取总页数
     *
     * @param pageRow   每页行数
     * @param itemCount 结果的总条数
     */
    private static int getPageCounts(int pageRow, int itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageRow > 0 ?
                itemCount / pageRow + 1 :
                itemCount / pageRow;
    }

    /**
     * 查询分页结果后的封装工具方法
     *
     * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
     * @param list        查询分页对象list
     * @param totalCount  查询出记录的总条数
     */
    public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, int totalCount) {
        int pageRow = requestJson.getIntValue("pageRow");
        int totalPage = getPageCounts(pageRow, totalCount);
        JSONObject result = successJson();
        JSONObject data = new JSONObject();
        data.put("list", list);
        data.put("totalCount", totalCount);
        data.put("totalPage", totalPage);
        result.put("data", data);
        return result;
    }

    public static <T> JSONObject successPage(ObjListResp<T> resp) {
        JSONObject result = successJson();
        result.put("data", resp);
        return result;
    }

    /**
     * 分页查询之前的处理参数
     * 没有传pageRow参数时,默认每页10条.
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }

    public static void fillPageParam(Page pageObj) {
        fillPageParam(pageObj, 10);
    }

    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param paramObject    查询条件json
     * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
     */
    private static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    /**
     * 在分页查询之前,为查询对象里加上分页参数
     * @param pageObj： 继承了Page的对象
     * @param defaultPageRow： 默认的每页条数
     */
    private static void fillPageParam(Page pageObj, int defaultPageRow) {
        int pageNum = pageObj.getPageNum();
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = pageObj.getPageRow();
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        pageObj.setOffSet((pageNum - 1) * pageRow);
        pageObj.setPageRow(pageRow);
        pageObj.setPageNum(pageNum);
    }

    /**
     * 将request参数值转为json
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    public static String formatDateToDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }


    //------copy from LiJian
    /**
     * 封装 Response
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> Response wrapResponse(Supplier<T> supplier) {
        try {
            return new Response(true, supplier.get());
        } catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "服务器错误!");
        }
    }

    /**
     * nullTo 用于处理null 值的情况.
     * @param t
     * @param ifNull
     * @param <T>
     * @return
     */
    public static <T> T nullTo(T t, T ifNull) {
        return t == null ? ifNull : t;
    }

    /**
     * 使用 -- 替换掉空字串.
     * @param str
     * @return
     */
    public static String nullTo(String str) {
        return StringUtils.isEmpty(str)? "--" : str;
    }

    /**
     * 格式化date.
     * @param date
     * @param ptn
     * @return
     */
    public static String formatDate(Date date, String ptn) {
        return new SimpleDateFormat(ptn).format(date);
    }

    /**
     * 格式化 date  yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatSimpleDate(Date date){
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 批量执行的框架.
     * @param list 要处理的list.
     * @param batchSize 批次大小。
     * @param consumer 自定义的处理方式.
     * @param <T> 要处理的类型.
     */
    public static <T>  void batchExecute(List<T> list, Integer batchSize, Consumer<List<T>> consumer) {
        if(CollectionUtils.isEmpty(list) || batchSize < 1) return;
        int pages = (int)Math.ceil((double)list.size() / (double)batchSize);
        for (int i = 0; i < pages; i++) {
            int fromIndex = i * batchSize;
            int toIndex = (i + 1) * batchSize > list.size() ? list.size() : (i + 1) * batchSize;
            List<T> subList = list.subList(fromIndex, toIndex);
            consumer.accept(subList);
        }
    }

    /**
     * 获取批次执行的次数.
     * @param list
     * @param batchSize
     * @return
     */
    public static  int getBatchExecuteTimes(List list, Integer batchSize) {
        if(CollectionUtils.isEmpty(list) || batchSize < 1) return 0;
        return (int)Math.ceil((double)list.size() / (double)batchSize);
    }



    /**
     * 写 Excel.
     * @param fileName 保存文件的路径。
     * @param t Easy Excel 的对象类型.
     * @param list 要写入的ExcelData
     * @param <T>
     * @throws Exception
     */
    public static <T>  void  writeExcel(String fileName, Class<T> t, List<T> list) throws IOException {
        ExcelWriter excelWriter = EasyExcel.write(fileName, t).build();
        WriteSheet sheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(list, sheet);
        excelWriter.finish();
    }

    /**
     * 读取 Excel.
     * @param in 输入流.
     * @param clazz Easy Excel 的对象模型.
     * @param insertAction 操作解析后的Excel List(T)
     * @param <T>
     * @throws Exception
     */
    public static <T> void readExcel(InputStream in, Class<T> clazz, EasyExcelDataListener.InsertAction<T> insertAction) throws IOException {
        EasyExcel.read(in, clazz, new EasyExcelDataListener<T>(insertAction)).sheet().doRead();
    }


    /**
     * 返回可以下载的附件内容.
     * @param in  文件所在的输入流.
     * @param fileName 下载后显示的文件名.
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> getAttachmentResponseEntity(InputStream in, String fileName) throws IOException{
        try(InputStream is = in) {
            byte[] data = new byte[is.available()];
            is.read(data);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (IOException ex){
            throw ex;
        }
    }

    /**
     * 批量插入数据库时，把list拆成多个小的list
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> getSmallLists(List<T> list, int batchSize) {

        List<List<T>> rlt = new ArrayList<>();
        return null;

    }




    public static Object checkNull(Object vo) {
        //获取所有属性
        Field[] fields = vo.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            //设置后可访问属性
            fields[i].setAccessible(true);
            //获取属性名字
            String name = fields[i].getName();
            //获取属性类型
            String type = fields[i].getGenericType().toString();
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());

//            System.out.println("name = " + name + ", type = " + type);
        }

        return null;
    }


}
