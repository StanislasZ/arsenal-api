package com.zrylovestan.arsenal.modules.wechat.service;


import com.zrylovestan.arsenal.core.entity.ObjListResp;
import com.zrylovestan.arsenal.core.util.CommonUtils;
import com.zrylovestan.arsenal.core.util.FileUtils;
import com.zrylovestan.arsenal.core.util.StringTools;
import com.zrylovestan.arsenal.core.util.restTemplate.RestTemplateUtils;

import com.zrylovestan.arsenal.modules.wechat.bean.WeChatConfig;
import com.zrylovestan.arsenal.modules.wechat.entity.WeChatFile;
import com.zrylovestan.arsenal.modules.wechat.entity.WeChatText;
import com.zrylovestan.arsenal.modules.wechat.entity.WechatUser;
import com.zrylovestan.arsenal.modules.wechat.entity.request.MsgSendFileQo;
import com.zrylovestan.arsenal.modules.wechat.entity.request.MsgSendFileToGroupQo;
import com.zrylovestan.arsenal.modules.wechat.entity.request.MsgSendTextQo;
import com.zrylovestan.arsenal.modules.wechat.entity.request.WechatUserQo;
import com.zrylovestan.arsenal.modules.wechat.entity.response.AccessTokenResp;
import com.zrylovestan.arsenal.modules.wechat.entity.response.MediaUploadResp;
import com.zrylovestan.arsenal.modules.wechat.entity.response.MsgSendFileToGroupResp;
import com.zrylovestan.arsenal.modules.wechat.entity.response.MsgSendResp;
import com.zrylovestan.arsenal.modules.wechat.mapper.WechatUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeChatService {


    @Autowired
    FileUtils fileUtils;

    @Autowired
    WechatUserMapper wechatUserMapper;


    @Autowired
    WeChatConfig weChatConfig;



    private static Map<String, Object> configCache = new HashMap<>();

    private final static String ACCESS_TOKEN = "accessToken";

    public String doGetAccessToken() {
        String url = weChatConfig.getAccessTokenUrl() + "?corpid=" + weChatConfig.getCorpid() + "&corpsecret=" + weChatConfig.getCorpsecret();
        log.info("getAccessToken, url = " + url);

        AccessTokenResp accessTokenResp = RestTemplateUtils.getEntityFromHttps(url, AccessTokenResp.class);
        log.info("accessTokenResp = " + accessTokenResp);
        if(accessTokenResp.getErrcode() != 0) {
           throw new RuntimeException(accessTokenResp.toString());
        }
        configCache.put(ACCESS_TOKEN, accessTokenResp.getAccess_token());
        return accessTokenResp.getAccess_token();
    }

    public String getAccessToken() {
        String accessToken = configCache.get(ACCESS_TOKEN) == null? null:configCache.get(ACCESS_TOKEN).toString();
        if(StringUtils.isEmpty(accessToken)) {
            accessToken = doGetAccessToken();
        }
        return accessToken;
    }


    public MsgSendResp doSendFileToUser(String accessToken, String touser, String media_id) {
        String url =  weChatConfig.getMsgSendUrl() +"?access_token=" +accessToken;


        MsgSendFileQo qo = new MsgSendFileQo();
        qo.setTouser(touser);
        qo.setFile(new WeChatFile(media_id));
        qo.setMsgtype("file");
        qo.setAgentid(weChatConfig.getAgentId());

        MsgSendResp resp = RestTemplateUtils.postHttps(url, qo, MsgSendResp.class,  MediaType.APPLICATION_JSON);
        log.info("sendMsgToUser...  resp = " + resp);

        return resp;
    }

    public void sendFileToUser(String touser, String media_id) throws Exception {
        String accessToken = getAccessToken();
        MsgSendResp resp = doSendFileToUser(accessToken, touser, media_id);
        //??????accessToken????????? ????????????accessToken
        if (resp.getErrcode() == 40014 || resp.getErrcode() == 41001 || resp.getErrcode() == 42001) {
            doGetAccessToken();
            resp = doSendFileToUser(getAccessToken(), touser, media_id);
        }

        if (resp.getErrcode() != 0) {
            log.error("????????????????????????????????????????????????????????????.  resp = {}", resp);
            //????????????
            throw new Exception("????????????????????????????????????????????????????????????...  errmsg = " + resp.getErrmsg());
        }

    }




    /**
     * ?????? ?????? ???????????? ??? ??????
     * ???????????????????????????
     * @param accessToken
     * @param touser
     * @return
     */
    public MsgSendResp doSendMsgToUser(String accessToken, String touser) {
        String url =  weChatConfig.getMsgSendUrl() +"?access_token=" +accessToken;

//        String touser = "ZhouQinHui";

        MsgSendTextQo qo = new MsgSendTextQo();
        qo.setTouser(touser);
        qo.setText(new WeChatText("0915????????????"));

        MsgSendResp resp = RestTemplateUtils.postHttps(url, qo, MsgSendResp.class,  MediaType.APPLICATION_JSON);
        log.info("sendMsgToUser...  resp = " + resp);

        return resp;
    }

    /**
     * ?????? ?????? ???????????? ??? ?????? ???????????????????????????
     * @param touser
     * @throws Exception
     */
    public void sendMsgToUser(String touser) throws Exception {
        String accessToken = getAccessToken();
        MsgSendResp resp = doSendMsgToUser(accessToken, touser);
        //??????accessToken????????? ????????????accessToken
        if (resp.getErrcode() == 40014 || resp.getErrcode() == 41001 || resp.getErrcode() == 42001) {
            doGetAccessToken();
            resp = doSendMsgToUser(getAccessToken(), touser);
        }
        if (resp.getErrcode() != 0) {
            //????????????
            log.error("????????????????????????????????????????????????????????????.  resp = {}", resp);
            throw new Exception("????????????????????????????????????????????????????????????...  errmsg = " + resp.getErrmsg());
        }
    }

    /**
     * ???????????????????????? media_id
     * @param imPath
     * @return
     */
    public MediaUploadResp doUploadMedia(String accessToken, String imPath, String filename, String bnkFlw) {
        String url =  weChatConfig.getMediaUploadUrl() +"?access_token=" +accessToken + "&type=file";
        File file = new File(imPath);

        String extension = fileUtils.getFileExtension(file);
        String newFilename = filename + "-" + bnkFlw + (extension.equals("")?"": "." +extension);

        newFilename = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)) + File.separator + newFilename;
        File newFile = new File(newFilename);
        //?????????
        fileUtils.rename(file, newFile);

        MultiValueMap<String, Object> qo = new LinkedMultiValueMap<>();
        qo.add("file", new FileSystemResource(newFile));
        qo.add("filename", filename + extension);
        MediaUploadResp resp = RestTemplateUtils.postHttps(url, qo, MediaUploadResp.class,  MediaType.MULTIPART_FORM_DATA);

        log.info("MediaUploadResp = " + resp);
        return resp;
    }



    public String uploadMediaAndGetId(String imPath, String filename, String bnkFlw) throws Exception {
        String accessToken = getAccessToken();
        MediaUploadResp resp = doUploadMedia(accessToken, imPath, filename, bnkFlw);
        //??????accessToken????????? ????????????accessToken
        if (resp.getErrcode() == 40014 || resp.getErrcode() == 41001 || resp.getErrcode() == 42001) {
            doGetAccessToken();
            resp = doUploadMedia(getAccessToken(), imPath, filename, bnkFlw);
        }

        if (resp.getErrcode() != 0) {
            //????????????
            log.error("????????????????????????????????????????????????.  resp = {}", resp);
            throw new Exception("????????????????????????????????????????????????...  errmsg = " + resp.getErrmsg());
        }
        return resp.getMedia_id();


    }


    /**
     * ??????????????? ??????
     * @param accessToken
     * @param media_id
     * @param chatid
     */
    public MsgSendFileToGroupResp doSendFileToGroup(String accessToken, String media_id, String chatid) {
        String url =  weChatConfig.getMsgSendToGroupUrl() +"?access_token=" +accessToken;

        MsgSendFileToGroupQo qo = new MsgSendFileToGroupQo();
        qo.setChatid(chatid);
        WeChatFile weChatFile = new WeChatFile();
        weChatFile.setMedia_id(media_id);
        qo.setFile(weChatFile);
        qo.setMsgtype("file");

        MsgSendFileToGroupResp resp = RestTemplateUtils.postHttps(url, qo, MsgSendFileToGroupResp.class,  MediaType.APPLICATION_JSON);

        log.info("MsgSendFileToGroupResp = " + resp);
        return resp;

    }


    public void sendFileToGroup(String media_id, String chatid) throws Exception {
        String accessToken = getAccessToken();
        MsgSendFileToGroupResp resp = doSendFileToGroup(accessToken, media_id, chatid);
        //??????accessToken????????? ????????????accessToken
        if (resp.getErrcode() == 40014 || resp.getErrcode() == 41001 || resp.getErrcode() == 42001) {
            doGetAccessToken();
            resp = doSendFileToGroup(getAccessToken(), media_id, chatid);
        }

        if (resp.getErrcode() != 0) {
            //????????????
            log.error("?????????????????????????????????????????????????????????.  resp = {}", resp);
            throw new Exception("?????????????????????????????????????????????????????????...  errmsg = " + resp.getErrmsg());
        }


    }

    /**
     * ?????? wechat_user???
     * ?????????????????? ??? delete_flag?????????true
     * ?????????
     * @param file
     */
    @Transactional
    public void updateWechatUserFromExcel(MultipartFile file) throws IOException {
        List<WechatUser> userList = new ArrayList<>();
        CommonUtils.readExcel(file.getInputStream(), WechatUser.class, list -> {
            userList.addAll(list);
        });

        userList.forEach(user -> {
            user.setDeleteFlag(false);
            user.setName(StringTools.trimAllBlank(user.getName()));
            user.setWechatId(StringTools.trimAllBlank(user.getWechatId()));
        });

        List<WechatUser> userListWithDistinctId = userList
                .stream()
                .filter(ele -> !StringTools.existEmpty(ele.getName(), ele.getWechatId()))
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(WechatUser::getWechatId))), ArrayList::new));

        log.info("userListWithDistinctId = {}", userListWithDistinctId);


        //????????????????????? delete_flag ?????? true
        wechatUserMapper.updateDeleteFlag(true);

        //??????
        wechatUserMapper.insertList(userListWithDistinctId);

    }


    public ObjListResp<WechatUser> getWechatUserList(WechatUserQo qo) {
        CommonUtils.fillPageParam(qo);
        ObjListResp<WechatUser> resp = new ObjListResp<>();
        resp.setTotalCount(wechatUserMapper.getWechatUserCnt(qo));
        resp.setList(wechatUserMapper.getWechatUserList(qo));


        return resp;
    }

    /**
     * ????????????
     * @return
     */
    public List<WechatUser> getWechatUserExportList() {
        return wechatUserMapper.getWechatUserExportList();
    }
}
