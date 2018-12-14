package cn.freeexchange.gateway.domain;

import static com.alibaba.fastjson.JSON.toJSONString;

import cn.freeexchange.common.base.exception.BusinessException;


public class BaseResponseBody {
	 //应答码
    public static final String CODE_SUCC = "000";//成功
    public static final String MSG_SUCC = "操作成功";
    public static final String CODE_SYSTEM_ERROR_CODE = "999";
    public static final String CODE_SYSTEM_ERROR_DESC = "系统错误";

    private String responseCode = CODE_SUCC;
    private String responseMsg = MSG_SUCC;

    public BaseResponseBody() {
    }

    public BaseResponseBody(ResponseResult responseResult) {
        this.responseCode = responseResult.getCode();
        this.responseMsg = responseResult.getMsg();
    }

    public BaseResponseBody(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public BaseResponseBody(Throwable throwable) {
        if (throwable instanceof BusinessException) {
            BusinessException be = (BusinessException) throwable;
            responseCode = be.getBusinessCode();
            responseMsg = be.getMessage();
        } else {
            responseCode = CODE_SYSTEM_ERROR_CODE;
            responseMsg = CODE_SYSTEM_ERROR_DESC;
        }
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String toJson() {
        return toJSONString(this);
    }

    public void setErrorInfo(String code, String msg) {
        this.responseCode = code;
        this.responseMsg = msg;
    }
    
}
