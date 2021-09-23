package com.cmbnb.weChatNotice;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.core.util.EncUtils;

import com.cmbnb.weChatNotice.core.util.FileUtils;
import com.cmbnb.weChatNotice.core.util.restTemplate.RestTemplateUtils;
import com.cmbnb.weChatNotice.modules.cbs.bean.CbsConfig;
import com.cmbnb.weChatNotice.modules.cbs.mapper.DzhdSendRecordMapper;
import com.cmbnb.weChatNotice.modules.cbs.mapper.KsInfoMapper;
import com.cmbnb.weChatNotice.modules.cbs.model.DzhdInfo;
import com.cmbnb.weChatNotice.modules.cbs.model.DzhdSendRecord;
import com.cmbnb.weChatNotice.modules.cbs.model.PaySettleData;
import com.cmbnb.weChatNotice.modules.cbs.service.CbsService;
import com.cmbnb.weChatNotice.modules.cbs.util.CheckNumUtil;
import com.cmbnb.weChatNotice.modules.cbs.util.XmlUtil;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.dcprtrdp52.Ntprtmsgy;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercrcqry51.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.ercurdtl28.*;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.Cicltothy;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.Ciothseqy;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.ErothcltDataReq78;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erothclt78.ErothcltDataResp78;
import com.cmbnb.weChatNotice.modules.cbs.xmlModel.erpayqry112.Apbusnbrx;
import com.cmbnb.weChatNotice.modules.task.application.SchedulingRunnable;
import com.cmbnb.weChatNotice.modules.task.config.CronTaskRegistrar;
import com.cmbnb.weChatNotice.modules.wechat.bean.WeChatConfig;
import com.cmbnb.weChatNotice.modules.wechat.entity.WeChatText;
import com.cmbnb.weChatNotice.modules.wechat.entity.request.MediaUploadQo;
import com.cmbnb.weChatNotice.modules.wechat.entity.request.MsgSendTextQo;
import com.cmbnb.weChatNotice.modules.wechat.entity.response.AccessTokenResp;
import com.cmbnb.weChatNotice.modules.wechat.entity.response.MediaUploadResp;
import com.cmbnb.weChatNotice.modules.wechat.entity.response.MsgSendResp;
import com.cmbnb.weChatNotice.modules.wechat.service.WeChatService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HesCorpWeChatNoticeApiApplicationTests {


    @Autowired
    EncUtils encUtils;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    WeChatService weChatService;

    @Autowired
    CbsService cbsService;


    private String accessTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    CbsConfig cbsConfig;

    @Autowired
    CheckNumUtil checkNumUtil;

    @Autowired
    KsInfoMapper ksInfoMapper;

    @Autowired
    DzhdSendRecordMapper dzhdSendRecordMapper;

    @Autowired
    FileUtils fileUtils;


//    @Value("${ksxt.msg.mngUser:295461}")
//    private String mngUser;

    @Value("${config.fkCron}")
    private String fkCron;


    @Test
    public void test2() {
//        Item item = itemDao.getOneById(654);
//        System.out.println("item = " + item);
    }


    @Test
    public void testTask() throws InterruptedException {
        SchedulingRunnable task = new SchedulingRunnable("cbsService", "taskNoParams", null);
        cronTaskRegistrar.addCronTask(task, fkCron);

        // 便于观察
        Thread.sleep(3000000);
    }



    @Test
    public void testSendMsgToUser() {
//        String url =  weChatConfig.getMsgSendUrl() +"?access_token=" + weChatConfig.getAccess_token();
        String url =  weChatConfig.getMsgSendUrl() +"?access_token=11111111111";

        String touser = "ZhouQinHui";

        MsgSendTextQo qo = new MsgSendTextQo();
        qo.setTouser(touser);
        qo.setText(new WeChatText("0915测试发送"));

        MsgSendResp resp = RestTemplateUtils.postHttps(url, qo, MsgSendResp.class, MediaType.APPLICATION_JSON);
        System.out.println("resp = " + resp);

    }

    @Test
    public void getAccessToken() {
        String url = accessTokenUrl + "?corpid=" + weChatConfig.getCorpid() + "&corpsecret=" + weChatConfig.getCorpsecret();
//        String url = accessTokenUrl + "?corpid=11111111&corpsecret=222222222";
        System.out.println("getAccessToken, url = " + url);


//        String resp = RestTemplateUtils.getStringFromHttps(url);
        AccessTokenResp resp = RestTemplateUtils.getEntityFromHttps(url, AccessTokenResp.class);
        System.out.println("resp = " + resp);
    }


    @Test
    public void testGetCheckCode() {
        System.out.println(checkNumUtil.GetCheckSumWithCRC32("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<CBSERPPGK>\n" +
                "    <INFO>\n" +
                "        <FUNNAM>TEST</FUNNAM>\n" +
                "    </INFO>\n" +
                "    <ERCURDTLA>\n" +
                "        <ITMDIR>A</ITMDIR>\n" +
                "        <VTLFLG>0</VTLFLG>\n" +
                "    </ERCURDTLA>\n" +
                "</CBSERPPGK>"));



    }


    /**
     * 请求2.8 当日明细数据对接(ERCURDTL)
     */
    @Test
    public void testSendRequestToCbs() {

        List<Ercurdtlz> todayDetailList = cbsService.getTodayDetail();

        System.out.println(JSON.toJSONString(todayDetailList));


    }


    /**
     * 请求7.8 导出客商账户资料(EROTHCLT)
     */
    @Test
    public void testErothclt() {
        //1. 生成报文
        ErothcltDataReq78 erothcltDataReq = new ErothcltDataReq78();
        InfoReq info = new InfoReq();
        info.setFunNam("EROTHCLT");
        erothcltDataReq.setInfo(info);


        Ciothseqy ciothseqy = new Ciothseqy();
        ciothseqy.setOthNbr("3346");



//        erothcltDataReq.setCiothseqy(ciothseqy);



        Pgk pgk = new Pgk();
        //先设置data
        pgk.setDataByType(erothcltDataReq, "GBK");
        //再根据data 获取校验码
        pgk.setCheckCode(checkNumUtil.GetCheckSumWithCRC32(pgk.getData()));
        String xml = XmlUtil.convert2Xml(pgk, "GBK");

        System.out.println("请求报文 = ");
        System.out.println(xml);

        //2. 请求接口，获得返回的报文
        String respStr = cbsService.httpRequest(cbsConfig.getRequestHttpUrl(), "GET", xml);

        System.out.println("-------- ");
        System.out.println("-------- ");
        System.out.println("返回报文为： ");
        System.out.println("-------- ");
        System.out.println(respStr);

        Pgk pgkResp = XmlUtil.convert2JavaBean(respStr, Pgk.class);

        ErothcltDataResp78 dataResp = pgkResp.getDataByType(ErothcltDataResp78.class);

        System.out.println("list.size = " + dataResp.getCicltothyList().size());
        System.out.println("dataResp.CicltothyList 前50条 = ");
//        dataResp.getCicltothyList().forEach(System.out::println);

//        for (int i = 0; i < 50; ++i) {
//            System.out.println(dataResp.getCicltothyList().get(i));
//        }

    }

    @Test
    public void updateKsInfo() {

        cbsService.updateKsInfo();
    }

    @Test
    public void addOneKsInfo() {
        Cicltothy ksInfo = new Cicltothy();
        ksInfo.setBmiNbr("123");
        ksInfo.setRm1Inf("rm1inf");
        ksInfo.setOthNam("名字111");

        List<Cicltothy> cicltothyList = new ArrayList<>();
        cicltothyList.add(ksInfo);
        ksInfoMapper.addKsInfoList(cicltothyList);


    }

    @Test
    public void getKsInfoCnt() {
        System.out.println(ksInfoMapper.getKsInfoCount());
    }

    @Test
    public void testGetTodayDetail() {
        cbsService.getTodayDetail();
    }





    /**
     * 测 5.1接口
     */
    @Test
    public void testGetTodayDzhd() {
        List<Dcdrqryz1> todayDzhdList = cbsService.getTodayDzhd();

//        System.out.println(JSON.toJSONString(todayDzhdList));

        DzhdInfo dzhdInfo = cbsService.getDzhdPdfList(todayDzhdList);

        JSONObject istNbrImPathMap = dzhdInfo.getIstNbrImPathMap();
        System.out.println("istNbrImPathMap = " + istNbrImPathMap);




//        System.out.println(JSON.toJSONString(dzhdPdfList));

    }


    @Test
    public void testHandleTodayDetailAndNotice() {
        cbsService.handleTodayDetailAndNotice();
    }


    @Test
    public void testAddDzhdSendRecord() {
        DzhdSendRecord record = new DzhdSendRecord();
        record.setBnkFlw("22222");
        record.setSendDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<DzhdSendRecord> list = new ArrayList<>();
        list.add(record);
        dzhdSendRecordMapper.addDzhdSendRecordList(list);
    }


    @Test
    public void getTodaySendBnkFlwList() {
            List<String> bnkFlwList = dzhdSendRecordMapper.getTodaySendBnkFlwList();
        System.out.println(bnkFlwList);
    }


    @Test
    public void testMediaUpload() {

        String url =  weChatConfig.getMediaUploadUrl() +"?access_token=1MoBbzYaNuZhUqQrl6b1luE0LMYUvo3MKMOGyHuN5AbO1YL242NtgqBzzTi69HSfo_q_t2ldZvjxWQJSiAjlbCcuypfN6Uba8sC4ExtTnWoxxHmPw8SNQr6noC7RAH3m4h7sbEdt2-zL6p3SDEBMODkEwn2ooShQnNy-3JRFpggbBYI_9ULl8aXVdRTlK8UAdgt6dySi3uss0PmTgSZD5w&type=file";


        File file = new File("D:\\Program Files\\cbs\\CMB\\FMMonitor\\SysData\\U1010\\CUR_789A000000143.pdf");

        MultiValueMap<String, Object> qo = new LinkedMultiValueMap<>();
        qo.add("file", new FileSystemResource(file));


        MediaUploadResp resp = RestTemplateUtils.postHttps(url, qo, MediaUploadResp.class,  MediaType.MULTIPART_FORM_DATA);
        System.out.println("testMediaUpload...  resp = " + resp);
    }


    @Test
    public void testFile() {

        String imPath = "D:\\Program Files\\cbs\\CMB\\FMMonitor\\SysData\\U1010\\CUR_789A000000143.pdf";
        File file = new File(imPath);
        System.out.println(file);
        System.out.println(file.getAbsolutePath());

        String newName = "testName";
        String extension = fileUtils.getFileExtension(file);
        newName = newName + (extension.equals("")? "": "." + extension);


        newName = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)) + File.separator + newName;
        System.out.println("newName = " + newName);

        System.out.println(fileUtils.rename(imPath, newName));
    }

    /**
     * 测试2.8返回的数据中 拿到
     */
    @Test
    public void test28And112() {
        List<Ercurdtlz> listFrom28 = cbsService.getTodayDetail();

        System.out.println("listFrom28.size  =" + listFrom28.size());

        //把 Ercurdtlz List（28返回） 转化 成  APBUSNBRX List (112请求)

        List<Ercurdtlz> todayDetailFkList = listFrom28
                .stream()
                .filter(ele -> !StringUtils.isEmpty(ele.getItmDir())
                        && ele.getItmDir().equals("1")
                        && !StringUtils.isEmpty(ele.getBnkFlw())
                        && !StringUtils.isEmpty(ele.getPayNbr()))
                .collect(Collectors.toList());

        System.out.println("todayDetailFkList.size  =" + todayDetailFkList.size());



        PaySettleData dataFrom112 = cbsService.getPaySettleDataList(todayDetailFkList);
//
        System.out.println("dataFrom112 = " + JSON.toJSONString(dataFrom112));

    }

    @Test
    public void testBatchExecute() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        CommonUtils.batchExecute(list, 1, smallList -> {
            if (smallList.contains("a")) {
                System.out.println("有a, return");
                return;
            }
            System.out.println(smallList);
        });

    }

}
