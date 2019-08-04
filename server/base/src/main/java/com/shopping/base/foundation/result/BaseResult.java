package com.shopping.base.foundation.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 项目名称：follow-common
 * 类名称：BaseResult
 * 类描述：BaseResult 统一返回结果类
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Data
public class BaseResult {
    /**
     * code : 返回状态码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;

    /**
     * success : 正常返回
     */
    private Boolean success;

    /**
     * token : 验证请求
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    /**
     * message : 返回信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * data : 返回数据
     */
    private Object data;

    private BaseResult(){

    }


    public static BaseResult SUCCESS(String token,String message,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(0);
        baseResult.setSuccess(true);
        baseResult.setToken(token);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public static BaseResult SUCCESS(String message,Object data){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(0);
        baseResult.setSuccess(true);
        baseResult.setToken("");
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public static BaseResult ERROR(Integer code,String message){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setSuccess(false);
        baseResult.setToken("");
        baseResult.setMessage(message);
        baseResult.setData(null);
        return baseResult;
    }

    public static BaseResult ERROR(ResultErrorCodeEnum codeEnum){
        BaseResult baseResult = ERROR(codeEnum.getCode(),codeEnum.getMsg());
        return baseResult;
    }
}
