package com.cmbnb.weChatNotice.modules.cbs.service;

import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.core.util.DateUtil;
import com.cmbnb.weChatNotice.core.util.StringTools;
import com.cmbnb.weChatNotice.modules.cbs.bean.CbsConfig;
import com.cmbnb.weChatNotice.modules.cbs.mapper.DzhdSendRecordMapper;
import com.cmbnb.weChatNotice.modules.cbs.mapper.KsInfoMapper;
import com.cmbnb.weChatNotice.modules.cbs.model.DzhdInfo;
import com.cmbnb.weChatNotice.modules.cbs.model.DzhdSendRecord;
import com.cmbnb.weChatNotice.modules.cbs.model.PaySettleData;
import com.cmbnb.weChatNotice.modules.cbs.util.CheckNumUtil;
import com.cmbnb.weChatNotice.modules.cbs.util.XmlUtil;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.InfoReq;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.Pgk;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.Cicltothy;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.Ciothseqy;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.ErothcltDataReq78;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.ErothcltDataResp78;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.Apbusnbrx;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.Appayinfz;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.ErpayqryDataReq112;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.ErpayqryDataResp112;
import com.cmbnb.weChatNotice.modules.wechat.bean.WeChatConfig;
import com.cmbnb.weChatNotice.modules.wechat.mapper.WechatUserMapper;
import com.cmbnb.weChatNotice.modules.wechat.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("cbsService")
@Slf4j
public class CbsService {

    @Autowired
    KsInfoMapper ksInfoMapper;

    @Autowired
    DzhdSendRecordMapper dzhdSendRecordMapper;

    @Autowired
    CbsConfig cbsConfig;

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    CheckNumUtil checkNumUtil;

    @Autowired
    WeChatService weChatService;

    @Autowired
    WechatUserMapper wechatUserMapper;




    //处理http请求  requestUrl为请求地址  requestMethod请求方式，值为"GET"或"POST"
    public String httpRequest(String requestUrl,String requestMethod,String outputStr){

//		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        StringBuffer buffer=null;
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
//			InputStreamReader isr=new InputStreamReader(is,"utf-8");
            InputStreamReader isr=new InputStreamReader(is,"GBK");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }


    /**
     * 处理当日明细数据， 按  收款 付款 区分， 最后通知客户
     */
    public void handleTodayDetailAndNotice() {
        System.out.println("handleTodayDetailAndNotice....");

        //获取今日已经发送成功过电子回单的    bnkFlw的列表
        List<String> todaySendBnkFlwList = dzhdSendRecordMapper.getTodaySendBnkFlwList();
        System.out.println("todaySendBnkFlwList = " + todaySendBnkFlwList);

        // 先拿到今日明细
        List<Ercurdtlz> todayDetailList = getTodayDetail();


        //当日明细中， 收款的
        List<Ercurdtlz> todayDetailSkList = todayDetailList
                .stream()
                .filter(ele ->  !StringUtils.isEmpty(ele.getItmDir())
                        && ele.getItmDir().equals("2")
                        && !StringUtils.isEmpty(ele.getBnkFlw())
                        && !todaySendBnkFlwList.contains(ele.getBnkFlw()))
                .collect(Collectors.toList());


        //当日明细中， 付款的
        List<Ercurdtlz> todayDetailFkList = todayDetailList
                .stream()
                .filter(ele -> !StringUtils.isEmpty(ele.getItmDir())
                        && ele.getItmDir().equals("1")
                        && !StringUtils.isEmpty(ele.getBnkFlw())
                        && !StringUtils.isEmpty(ele.getPayNbr())
                        && !todaySendBnkFlwList.contains(ele.getBnkFlw()))
                .collect(Collectors.toList());

        System.out.println("todayDetailSkList 收款 List.size = " + todayDetailSkList.size());
        System.out.println("todayDetailSkList 付款 List.size = " + todayDetailFkList.size());

        if (todayDetailSkList.size() == 0 && todayDetailFkList.size() == 0) {
            System.out.println("今天到现在，过滤后的 收付款 列表 都为空");
            return;
        }

        //5.1接口  拿到电子回单数据
        List<Dcdrqryz1> todayDzhdList = getTodayDzhd();
        System.out.println("todayDzhdList = ");
        todayDzhdList.forEach(System.out::println);

        //把todayDzhdList 转化 成  Map， key为list元素的bnkFlw， 值为元素
        Map<String, List<Dcdrqryz1>> bnkFlwDcdrqryz1Map = todayDzhdList
                .stream()
                .collect(Collectors.groupingBy(Dcdrqryz1::getBnkFlw));
        System.out.println("bnkFlwDcdrqryz1Map = " + bnkFlwDcdrqryz1Map);

        //5.2接口  主要拿到电子回单的 pdf文件路径
        DzhdInfo todayDzhdInfo = getDzhdPdfList(todayDzhdList);

        //处理 2.8返回的当日 收款 列表
        handleTodayDetailSkList(todayDetailSkList, bnkFlwDcdrqryz1Map, todayDzhdInfo);


        PaySettleData psd = getPaySettleDataList(todayDetailFkList);


        //处理 2.8返回的当日 付款 列表
        handleTodayDetailFkList(todayDetailFkList, bnkFlwDcdrqryz1Map, todayDzhdInfo, psd);



    }

    /**
     * 处理 当日明细中  付款的
     *
     * 遍历，  拿到每个 文件路径， 微信里发给个人（name从 112返回拿）
     * @param todayDetailFkListFrom28  ： 2.8接口返回 的收款列表
     * @param bnkFlwDcdrqryz1Map：    5.1返回的 电子回单信息 转成 的Map
     * @param todayDzhdInfoFrom52   5.2返回 的 电子回单路径信息
     * @param psdFrom112    1.12返回的 业务流水号-ERP备注信息
     */
    public void handleTodayDetailFkList(List<Ercurdtlz> todayDetailFkListFrom28,
                                        Map<String, List<Dcdrqryz1>> bnkFlwDcdrqryz1Map,
                                        DzhdInfo todayDzhdInfoFrom52,
                                        PaySettleData psdFrom112) {

        System.out.println("handleTodayDetailFkList");


        //拿到 打印实例号 -  图片路径  的 对应关系
        JSONObject istNbrImPathMap = todayDzhdInfoFrom52.getIstNbrImPathMap();
        System.out.println("istNbrImPathMap = " + istNbrImPathMap);

        for (Ercurdtlz fk: todayDetailFkListFrom28) {

            System.out.println("fk = " + fk);

            String bnkFlw = fk.getBnkFlw();
            String payNbr = fk.getPayNbr();
            System.out.println("bnkFlw = " + bnkFlw + ", 对应 payNbr = " + payNbr);

            //用 bnkFlw 到 bnkFlwDcdrqryz1Map 找到 Dcdrqryz1 （5.1返回）  对象
            List<Dcdrqryz1> dcdrqryz1List = bnkFlwDcdrqryz1Map.get(bnkFlw);
            if (CollectionUtils.isEmpty(dcdrqryz1List)) continue;

            //拿到5.1返回对象
            Dcdrqryz1 todayDzhd = dcdrqryz1List.get(0);
            String imPath = istNbrImPathMap.getString(todayDzhd.getIstNbr());
            if (StringUtils.isEmpty(imPath)) continue;

            //微信里推送文件时， 展示的文件名
            String wechatFileName = todayDzhd.getRcvEan();
            if (StringUtils.isEmpty(wechatFileName)) {
                wechatFileName = "付款电子回单";
            }

            //拿 微信里 要发送到谁  的 姓名


            Map<String, String> busNbrErpCm1Map = psdFrom112.getBusNbrErpCm1Map();


            //最后去掉
            //todo
//            for (Map.Entry<String, String> entry : busNbrErpCm1Map.entrySet()) {
//                System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
//                busNbrErpCm1Map.put(entry.getKey(), "张1");
//            }



            String wechatSendUserName = busNbrErpCm1Map.get(payNbr);
            System.out.println("wechatSendUserName = " + wechatSendUserName);
            if (StringUtils.isEmpty(wechatSendUserName)) {
                System.out.println("没找到 ErpCm1 备注");
                continue;
            }
            //用wechatSendUserName 找到 对应的 wechar userId， 万一有重名，用List接收
            List<String> ids = wechatUserMapper.getWechatUserList(wechatSendUserName)
                    .stream()
                    .map(ele -> ele.getWechatId())
                    .distinct()
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(ids)) {
                System.out.println(wechatSendUserName + " 在数据库中 找不到 对应微信id");
                continue;
            }

            try {
                String media_id = weChatService.uploadMediaAndGetId(imPath, wechatFileName, bnkFlw);
                System.out.println("media_id = " + media_id + ", 微信里发文件时展示的文件名 = " + wechatFileName);

                //发送文件 到 企业微信的 某个人 或几个人
                String touser = "";
                for (int i = 0; i < ids.size(); ++i) {
                    if (i != ids.size() - 1) {
                        touser = touser + ids.get(i) + "|";
                    } else {
                        touser = touser + ids.get(i);
                    }
                }
                System.out.println("touser = " + touser);
                //发送文件到个人
                weChatService.sendFileToUser(touser, media_id);

                //发送成功后，更新 dzhd_send_record表
                System.out.println("发送成功，更新record表");
                DzhdSendRecord record = new DzhdSendRecord();
                record.setBnkFlw(bnkFlw);
                record.setSendDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                List<DzhdSendRecord> records = new ArrayList<>();
                records.add(record);
                dzhdSendRecordMapper.addDzhdSendRecordList(records);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            
        }


    }


    /**
     * 处理 当日明细中 收款的
     *
     * 遍历， 拿到每个的 文件路径，把文件上传到微信
     *
     * 再发到群里
     *
     * @param todayDetailSkListFrom28
     */
    public void handleTodayDetailSkList(List<Ercurdtlz> todayDetailSkListFrom28,
                                        Map<String, List<Dcdrqryz1>> bnkFlwDcdrqryz1Map,
                                        DzhdInfo todayDzhdInfoFrom52) {

        System.out.println("todayDetailSkListFrom28 .size = " + todayDetailSkListFrom28.size());
        System.out.println("-------");
        System.out.println("todayDetailSkListFrom28 = " );
        todayDetailSkListFrom28.forEach(System.out::println);
        System.out.println("-------");


        //拿到 打印实例号 -  图片路径  的 对应关系
        JSONObject istNbrImPathMap = todayDzhdInfoFrom52.getIstNbrImPathMap();
        System.out.println("istNbrImPathMap = " + istNbrImPathMap);


        for (Ercurdtlz sk: todayDetailSkListFrom28) {

            System.out.println("sk = " + sk);

            String bnkFlw = sk.getBnkFlw();

            //用 bnkFlw 到 bnkFlwDcdrqryz1Map 找到 Dcdrqryz1 （5.1返回）  对象
            List<Dcdrqryz1> dcdrqryz1List = bnkFlwDcdrqryz1Map.get(bnkFlw);
            if (CollectionUtils.isEmpty(dcdrqryz1List)) continue;

            //拿到5.1返回对象
            Dcdrqryz1 todayDzhd = dcdrqryz1List.get(0);
            String imPath = istNbrImPathMap.getString(todayDzhd.getIstNbr());
            if (StringUtils.isEmpty(imPath)) continue;

            //微信里推送文件时， 展示的文件名
            String wechatFileName = todayDzhd.getRcvEan();
            if (StringUtils.isEmpty(wechatFileName)) {
                wechatFileName = "收款电子回单";
            }



            System.out.println("bnkFlw = " + bnkFlw + ", 对应 istNbr = " + todayDzhd.getIstNbr() + ", 对应 imPath = " + imPath);


            File file = new File(imPath);
            System.out.println(file);


            try {
                String media_id = weChatService.uploadMediaAndGetId(imPath, wechatFileName, bnkFlw);
                System.out.println("media_id = " + media_id + ", 微信里发文件时展示的文件名 = " + wechatFileName);

                //企业微信群发
                weChatService.sendFileToGroup(media_id, weChatConfig.getSkGroupChatId());
                //发送成功后，更新 dzhd_send_record表
                System.out.println("发送成功，更新record表");
                DzhdSendRecord record = new DzhdSendRecord();
                record.setBnkFlw(bnkFlw);
                record.setSendDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                List<DzhdSendRecord> records = new ArrayList<>();
                records.add(record);
                dzhdSendRecordMapper.addDzhdSendRecordList(records);
            } catch (Exception ex) {
                ex.printStackTrace();
            }




        }


    }



    /**
     * 1.12 获取支付结算 数据
     * @param listFrom28
     */
    public PaySettleData getPaySettleDataList(List<Ercurdtlz> listFrom28) {
        PaySettleData paySettleData = new PaySettleData();


        List<Appayinfz> rltAppayinfzList = new ArrayList<>();
        Map<String, String> busNbrErpCm1Map = new HashMap<>();

        CommonUtils.batchExecute(listFrom28, 400, smallList -> {
            //生成报文
            ErpayqryDataReq112 dataReq = new ErpayqryDataReq112();
            InfoReq info = new InfoReq();
            info.setFunNam("ERPAYQRY");
            dataReq.setInfo(info);

            //把2.8返回 map 到 1.12的请求
            List<Apbusnbrx> apbusnbrxList = smallList
                    .stream()
                    .filter(ele -> !StringUtils.isEmpty(ele.getPayNbr()))
                    .map(ele -> {
                        Apbusnbrx ap = new Apbusnbrx();
                        ap.setBusNbr(ele.getPayNbr());
                        ap.setOptStu("DP");
                        return ap;
                    })
                    .collect(Collectors.toList());

            if (apbusnbrxList.size() == 0) {
                System.out.println("这部分 Apbusnbrx List.size = 0 , 不请求，直接return");
                return;
            }

            System.out.println("转化成 112的请求报文中的 Apbusnbrx List  = ");
            apbusnbrxList.forEach(System.out::println);
            //设置 ApbusnbrxList
            dataReq.setApbusnbrxList(apbusnbrxList);

            Pgk pgk = new Pgk();
            //先设置data
            pgk.setDataByType(dataReq, "GBK");
            //再根据data 获取校验码
            pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
            String xml = XmlUtil.convert2Xml(pgk, "GBK");

            System.out.println("    1.12请求报文 = ");
            System.out.println(xml);


            //2. 请求接口，获得返回的报文
            String respStr = httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);
            //把返回报文 解析成  对象
            System.out.println("1.12返回报文 = " + respStr);

            Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

            ErpayqryDataResp112 dataResp = pgkResp.getDataByType(ErpayqryDataResp112.class);
            System.out.println("返回报文data部分 解析成 对象后，  dataResp = "  + dataResp);

            List<Appayinfz> appayinfzList = dataResp.getAppayinfzList();
            System.out.println("返回报文中 解析出 appayinfzList.size = " + appayinfzList.size() + ", 具体如下");
            appayinfzList.forEach(System.out::println);

            //过滤掉 业务流水号 为空的
            List<Appayinfz> validAppayinfzList = appayinfzList
                    .stream()
                    .filter(ele -> !StringTools.existEmpty(ele.getBusNbr()))
                    .collect(Collectors.toList());
            System.out.println("过滤后， size = " + validAppayinfzList.size());


            rltAppayinfzList.addAll(validAppayinfzList);

            //填充 业务流水号 - ERP备注1  的map
            for (Appayinfz appayinfz : validAppayinfzList) {
                busNbrErpCm1Map.put(appayinfz.getBusNbr(), appayinfz.getErpCm1());
            }

        });

        paySettleData.setAppayinfzList(rltAppayinfzList);
        paySettleData.setBusNbrErpCm1Map(busNbrErpCm1Map);

        return paySettleData;
    }


    /**
     * 调用 5.2 电子回单打印(DCPRTRDP)
     * 生成pdf文件， 路径在 返回报文的 IMPATH字段中 存储
     * @param todayDzhdList：   5.1接口返回的 当日电子回单数据列表
     * @return
     */
    public DzhdInfo getDzhdPdfList(List<Dcdrqryz1> todayDzhdList) {
//        JSONObject rlt = new JSONObject();
        List<Ntprtmsgy> ntprtmsgyRltList = new ArrayList<>();
        List<Dcprtrcpz> dcprtrcpzRltList = new ArrayList<>();
        JSONObject istNbrImPathMap = new JSONObject();

        //怕报文太长报错， 分批操作， 先设置为400个一批， 后面可改为外部配置批次大小
        CommonUtils.batchExecute(todayDzhdList, 400, smallList -> {


            //生成报文
            DcprtrdpDataReq52 dataReq = new DcprtrdpDataReq52();
            InfoReq info = new InfoReq();
            info.setFunNam("DCPRTRDP");
            dataReq.setInfo(info);

            //设置 DCPRTRCPX 列表
            //把某批次的 电子回单数据列表， 先过滤掉  accNbr, sqrNb1, istNbr为空的
            //再 生成 5.2接口 请求报文 需要的List
            List<Dcprtrcpx> dcprtrcpxList = smallList
                    .stream()
                    .filter(ele -> !StringTools.existEmpty(ele.getSqrNb1(), ele.getIstNbr(), ele.getAccNbr()))
                    .map(dcdrqryz1 -> {
                        Dcprtrcpx dcprtrcpx = new Dcprtrcpx();
                        BeanUtils.copyProperties(dcdrqryz1, dcprtrcpx);
                        dcprtrcpx.setPrtFlg("2");
                        return dcprtrcpx;
                    }).collect(Collectors.toList());
//            System.out.println("5.2接口 请求报文中 dcprtrcpxList =");
//            dcprtrcpxList.forEach(System.out::println);
            dataReq.setDcprtrcpxList(dcprtrcpxList);


            Pgk pgk = new Pgk();
            //先设置data
            pgk.setDataByType(dataReq, "GBK");
            //再根据data 获取校验码
            pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
            String xml = XmlUtil.convert2Xml(pgk, "GBK");

//            System.out.println("    5.2请求报文 = ");
//            System.out.println(xml);


            //2. 请求接口，获得返回的报文
            String respStr = httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);

//            System.out.println("    -------- ");
//            System.out.println("    -------- ");
//            System.out.println("    5.2返回报文为： ");
//            System.out.println("    -------- ");
//            System.out.println(respStr);

            //把返回报文 解析成  对象

            Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

            DcprtrdpDataResp52 dataResp = pgkResp.getDataByType(DcprtrdpDataResp52.class);
            System.out.println("返回报文data部分 解析成 对象后，  dataResp = "  + dataResp);

            List<Ntprtmsgy> ntprtmsgyList = dataResp.getNtprtmsgyList();
            System.out.println("返回报文中 解析出 ntprtmsgyList = ");
            ntprtmsgyList.forEach(System.out::println);

            List<Ntprtmsgy> validDzhdPdfList = ntprtmsgyList
                    .stream()
                    .filter(ele -> ele.getErrCod().equals("0000000"))
                    .collect(Collectors.toList());
            ntprtmsgyRltList.addAll(validDzhdPdfList);

            List<String> validIstNbrList = ntprtmsgyList
                    .stream()
                    .filter(ele -> ele.getErrCod().equals("0000000"))
                    .map(ele -> ele.getIstNbr())
                    .collect(Collectors.toList());


            List<Dcprtrcpz> dcprtrcpzList = dataResp.getDcprtrcpzList();
//            System.out.println("返回报文中 解析出 dcprtrcpzList = ");
//            dcprtrcpzList.forEach(System.out::println);
            dcprtrcpzRltList.addAll(dcprtrcpzList);

            //处理2个list， 生成一个 key为 打印实例号 ， 值为回单路径的 map

            for (Dcprtrcpz dcprtrcpz: dcprtrcpzList) {
                if (StringTools.existEmpty(dcprtrcpz.getImPath(), dcprtrcpz.getIstNbr())) continue;
                if (!validIstNbrList.contains(dcprtrcpz.getIstNbr())) continue;
                istNbrImPathMap.put(dcprtrcpz.getIstNbr(), dcprtrcpz.getImPath());
            }





        });

//        System.out.println("5.2最终返回的 电子回单pdfList .size = " + ntprtmsgyRltList.size());

//        rlt.put("ntprtmsgyList", ntprtmsgyRltList);
//        rlt.put("dcprtrcpzRltList", dcprtrcpzRltList);
//        rlt.put("istNbrImPathMap", istNbrImPathMap);

        DzhdInfo dzhdInfo = new DzhdInfo();
        dzhdInfo.setNtprtmsgyRltList(ntprtmsgyRltList);
        dzhdInfo.setDcprtrcpzRltList(dcprtrcpzRltList);
        dzhdInfo.setIstNbrImPathMap(istNbrImPathMap);


        return dzhdInfo;
    }



    /**
     * 请求 5.1 ERP导出当日电子回单数据(ERCRCQRY)
     * @return
     */
    public List<Dcdrqryz1> getTodayDzhd() {

        List<Dcdrqryz1> rltList = new ArrayList<>();
        String actNbrStr = cbsConfig.getActNbr();
        String[] actNbrArr = actNbrStr.split(",");

        for (int i = 0; i < actNbrArr.length; i++) {
            System.out.println("*******************************************************");
            System.out.println("*******************************************************");

            //用这个交易账户进行请求
            int batchIndex = 0;
            String sqrNb1 = "---";   //续传号

            //若数据量大，则使用续传号持续请求
            while (!StringUtils.isEmpty(sqrNb1)) {

                System.out.println("actNbr = " + actNbrArr[i] + ", batchIndex = " + batchIndex);

                //1. 生成报文
                ErcrcqryDataReq51 dataReq = new ErcrcqryDataReq51();
                InfoReq info = new InfoReq();
                info.setFunNam("ERCRCQRY");
                dataReq.setInfo(info);

                //设置DCDRCQRYA
                Dcdrcqrya dcdrcqrya = new Dcdrcqrya();
                dcdrcqrya.setAccNbr(actNbrArr[i]);
                dataReq.setDcdrcqrya(dcdrcqrya);

                //设置DCDRCQRYX
                Dcdrcqryx dcdrcqryx = new Dcdrcqryx();
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                dcdrcqryx.setStaDat(today);
                dcdrcqryx.setEndDat(today);

                //测试环境
//                dcdrcqryx.setStaDat("2021-09-18");
//                dcdrcqryx.setEndDat("2050-12-31");

                dataReq.setDcdrcqryx(dcdrcqryx);


                //设置续传
                Dcdrcseqz dcdrcseqz = new Dcdrcseqz();
                dcdrcseqz.setSqrNb1(sqrNb1);
                if (batchIndex > 0) {
                    dataReq.setDcdrcseqz(dcdrcseqz);
                }

                Pgk pgk = new Pgk();
                //先设置data
                pgk.setDataByType(dataReq, "GBK");
                //再根据data 获取校验码
                pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
                String xml = XmlUtil.convert2Xml(pgk, "GBK");

//                System.out.println("    5.1请求报文 = ");
//                System.out.println(xml);


                //2. 请求接口，获得返回的报文
                String respStr = httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);

                System.out.println("    -------- ");
                System.out.println("    -------- ");
                System.out.println("    5.1返回报文为： ");
                System.out.println("    -------- ");
                System.out.println(respStr);

                //3. 把返回报文 解析成  对象

                Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

                ErcrcqryDataResp51 dataResp = pgkResp.getDataByType(ErcrcqryDataResp51.class);
//                System.out.println("返回报文data部分 解析成 对象后，  dataResp = "  + dataResp);


                List<Dcdrqryz1> ercurdtlzList = dataResp.getDcdrqryz1List();
                System.out.println("    5.1返回未过滤 list.size = " + (ercurdtlzList == null? 0:ercurdtlzList.size()));
                if (!CollectionUtils.isEmpty(ercurdtlzList)) {

//                    System.out.println("    列表具体如下： ");
                    ercurdtlzList.forEach(System.out::println);

                    //过滤掉 BNKFLW 和 ISTNBR为空的
                    ercurdtlzList = ercurdtlzList
                            .stream()
                            .filter(ele -> !StringTools.existEmpty(ele.getIstNbr(), ele.getBnkFlw()))
                            .collect(Collectors.toList());

                    rltList.addAll(ercurdtlzList);
                }



                //更新续传流水号
                if (dataResp.getDcdrcseqz() == null) {
//                    System.out.println("    返回报文中没有续传号");
                    sqrNb1 = "";
                } else {
                    sqrNb1 = dataResp.getDcdrcseqz().getSqrNb1();
//                    System.out.println("    把续传流水号设置为" + sqrNb1);
                }
                //更新索引
                ++ batchIndex;
                System.out.println("-------- ");
                System.out.println("-------- ");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("当日电子回单数据 List.size = " + rltList.size());
        return rltList;
    }


    /**
     * 获取当日明细  2.8
     * @return
     */
    public List<Ercurdtlz> getTodayDetail() {

        List<Ercurdtlz> rltList = new ArrayList<>();

        String actNbrStr = cbsConfig.getActNbr();
        String[] actNbrArr = actNbrStr.split(",");

        for (int i = 0; i < actNbrArr.length; i++) {
//            System.out.println("*******************************************************");
//            System.out.println("*******************************************************");
            if (StringUtils.isEmpty(actNbrArr[i])) continue;

            //用这个交易账户进行请求
            int batchIndex = 0;
            String dtlSeq = "---";

            //若数据量大，则使用续传号持续请求
            while (!StringUtils.isEmpty(dtlSeq)) {

//                System.out.println("actNbr = " + actNbrArr[i] + ", batchIndex = " + batchIndex);

                //1. 生成报文
                ErcurdtlDataReq28 ercurdtlDataReq = new ErcurdtlDataReq28();
                InfoReq info = new InfoReq();
                info.setFunNam("ERCURDTL");
                ercurdtlDataReq.setInfo(info);

                //设置ERCURDTLA
                Ercurdtla ercurdtla = new Ercurdtla();
                ercurdtla.setItmDir("A");  //全部
                ercurdtla.setVtlFlg("0");
                ercurdtlDataReq.setErcurdtla(ercurdtla);

                //设置ERCURDTLB
                Ercurdtlb ercurdtlb = new Ercurdtlb();
                ercurdtlb.setActNbr(actNbrArr[i]);
                ercurdtlDataReq.setErcurdtlb(ercurdtlb);

                //设置续传
                Erdtlseqz erdtlseqz = new Erdtlseqz();
                erdtlseqz.setDtlSeq(dtlSeq);
                if (batchIndex > 0) {
                    ercurdtlDataReq.setErdtlseqz(erdtlseqz);
                }

                Pgk pgk = new Pgk();
                //先设置data
                pgk.setDataByType(ercurdtlDataReq, "GBK");
                //再根据data 获取校验码
                pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
                String xml = XmlUtil.convert2Xml(pgk, "GBK");

//                System.out.println("    2.8请求报文 = ");
//                System.out.println(xml);


                //2. 请求接口，获得返回的报文
                String respStr = httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);
                System.out.println("2.8返回报文= " + respStr);
                //3. 把返回报文 解析成  对象

                Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

                ErcurdtlDataResp28 dataResp = pgkResp.getDataByType(ErcurdtlDataResp28.class);
                List<Ercurdtlz> ercurdtlzList = dataResp.getErcurdtlzList();

                System.out.println("    2.8返回未过滤的list.size = " + ercurdtlzList.size());
//                System.out.println("    列表（未经过滤）具体如下： ");
//                dataResp.getErcurdtlzList().forEach(System.out::println);


                //这里过滤掉不是当天的交易
                List<Ercurdtlz> ercurdtlzTodayList = ercurdtlzList
                        .stream()
                        .filter(ele ->  DateUtil.isToday(ele.getBnkTim()))
                        .collect(Collectors.toList());
                System.out.println("    过滤掉不是当天的交易后， todayList.size = " + ercurdtlzTodayList.size());
                if (ercurdtlzTodayList.size() > 0) {
//                    System.out.println("todayList  = ");
//                    ercurdtlzTodayList.forEach(System.out::println);
                }


                rltList.addAll(ercurdtlzTodayList);

                //更新续传流水号
                if (dataResp.getErdtlseqz() == null) {
//                    System.out.println("    返回报文中没有续传号");
                    dtlSeq = "";
                } else {
                    dtlSeq = dataResp.getErdtlseqz().getDtlSeq();
//                    System.out.println("    把续传流水号设置为" + dtlSeq);
                }
                //更新索引
                ++ batchIndex;
//                System.out.println("-------- ");
//                System.out.println("-------- ");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
//        System.out.println("最终rltList.size = " + rltList.size());
        return rltList;
    }





    /**
     * 更新客商信息
     *
     *  先删除所有数据，再请求接口，插入数据库
     */
    public void updateKsInfo() {
        //先删除所有
        ksInfoMapper.deleteAllKsInfo();

        int batchIndex = 0;
        String othNbr = "---";

        while (!StringUtils.isEmpty(othNbr)) {

            ErothcltDataReq78 erothcltDataReq = new ErothcltDataReq78();
            InfoReq info = new InfoReq();
            info.setFunNam("EROTHCLT");
            erothcltDataReq.setInfo(info);


            Ciothseqy ciothseqy = new Ciothseqy();
            ciothseqy.setOthNbr(othNbr);

            if (batchIndex > 0) {
                erothcltDataReq.setCiothseqy(ciothseqy);
            }
            Pgk pgk = new Pgk();
            //先设置data
            pgk.setDataByType(erothcltDataReq, "GBK");
            //再根据data 获取校验码
            pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
            String xml = XmlUtil.convert2Xml(pgk, "GBK");

            System.out.println("batchIndex = "+batchIndex+" , 请求报文 = ");
            System.out.println(xml);

            //2. 请求接口，获得返回的报文
            String respStr = httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);

            System.out.println("    -------- ");
            System.out.println("    -------- ");
            System.out.println("    返回报文为： ");
            System.out.println("    -------- ");
//            System.out.println(respStr);

            Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

            ErothcltDataResp78 dataResp = pgkResp.getDataByType(ErothcltDataResp78.class);
            List<Cicltothy> cicltothyList = dataResp.getCicltothyList();
            System.out.println("    该批次数据 size = " + cicltothyList.size());
            List<Cicltothy> cicltothyListHasBmiNbr = cicltothyList.stream().filter(ele -> !StringUtils.isEmpty(ele.getBmiNbr())).collect(Collectors.toList());
            System.out.println("    把客商编号为空的过滤后， size = " + cicltothyListHasBmiNbr.size());


            CommonUtils.batchExecute(cicltothyList, 50, items -> {
                ksInfoMapper.addKsInfoList(items);
            });


            //更新续传流水号
            if (dataResp.getCiothseqy() == null) {
                othNbr = "";
            } else {
                othNbr = dataResp.getCiothseqy().getOthNbr();
            }

            System.out.println("把续传流水号设置为" + othNbr);

            //更新索引
            ++ batchIndex;
            System.out.println("-------- ");
            System.out.println("-------- ");

            System.out.println("*******************************************************");
            System.out.println("*******************************************************");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }




}
