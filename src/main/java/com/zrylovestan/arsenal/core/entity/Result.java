package com.zrylovestan.arsenal.core.entity;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "返回结果类")
@Data
public class Result<T> {

    @ApiModelProperty(value = "请求状态")
    private Boolean success;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;



    public static <T> Result success(T data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("请求成功");
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }


    public static Result fail(String errMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage("errMsg");
        result.setData(null);
        return result;
    }

    public static Result fail() {
        return fail("请求失败");
    }

}
