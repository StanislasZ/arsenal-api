package com.zrylovestan.arsenal.core.util.constants;

public enum ErrorEnum {
    /*
     * 错误信息
     * */
    E_400(400, "请求处理异常，请稍后再试"),
    E_500(500, "请求方式有误,请检查 GET/POST"),
    E_501(501, "请求路径不存在"),
    E_502(502, "权限不足"),
    E_10008(10008, "角色删除失败,尚有用户属于此角色"),
    E_10009(10009, "账户已存在"),


    E_20011(20011, "owk登陆已过期，请重新登陆"),


    E_90003(90003, "缺少必填参数"),
    E_90004(90004, "shiro认证失败"),

    E_601(601, "已存在该题库名称！"),
    E_602(602, "未选择题库！"),
    E_603(603, "未选择问题！"),
    E_604(604, "问题类型必填，且必须是 单选题/多选题/判断题  ！"),
    E_605(605, "题目的第一行的问题内容必须填写  ！"),
    E_606(606, "每行的问题序号都要填写  ！"),
    E_607(607, "每行的 是否为正确答案列 都要填写  ！"),
    E_608(608, "每行的 答案内容列 都要填写  ！"),



    E_609(609, "单选题答案只能有一个 ！"),
    E_610(610, "多选题至少要有两个正确答案  ！"),
    E_611(611, "判断题只能有一个正确项  ！"),

    E_612(612, "单选题的候选答案必须在2-4个  ！"),
    E_613(613, "多选题的候选答案必须在2-4个  ！"),
    E_614(614, "判断题的候选答案必须在2-4个  ！"),
    E_615(615, "问题类型错误  ！"),
    E_616(616, "已有考卷关联该问题，无法修改  ！"),


    E_701(701, "已存在该身份证号！"),
    E_702(702, "已存在该手机号！"),
    E_703(703, "未选择问题！"),
    E_704(704, "身份证号不正确！"),
    E_705(705, "身份证号不可为空！"),


    E_801(801, "已存在该考试名称！"),
    E_802(802, "结束时间不能早于开始时间！"),
    E_803(803, "考试不存在！"),
    E_804(804, "考试状态不正确！"),
    E_805(805, "规则不正确，无对应的考题！"),
    E_806(806, "试卷状态不正确！！"),
    E_807(807, "考试id不正确！！"),
    E_808(808, "您已达到最大考试次数！！"),


    E_901(901, "信息有误，请核对后重新填写！"),
    E_902(902, "该账号已被禁用！"),
    E_903(903, "token校验错误！"),
    E_904(904, "登录已过期，请重新登录");







    private Integer errorCode;

    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}