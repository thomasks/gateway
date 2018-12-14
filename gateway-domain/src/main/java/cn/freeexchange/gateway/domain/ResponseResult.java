package cn.freeexchange.gateway.domain;

import static java.lang.String.format;

import java.io.Serializable;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.utils.ThrowableUtils;

public class ResponseResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2785761092593214516L;
	
	//应答码
    public static final String CODE_SUCC = "000";//（最终）成功
    public static final String CODE_ING = "111";//处理中或请求成功（需要异步通知）
    public static final String CODE_PENDING = "222"; // 待发送请求
    public static final String CODE_ERROR = "999";//系统错误

    //应答码
    public static final String RESULT_SUCC = "SUCC";//（最终）成功
    public static final String RESULT_ING = "ING";//处理中或请求成功（需要异步通知）
    public static final String RESULT_PENDING = "PENDING";// 待发送请求
    public static final String RESULT_FAIL = "FAIL";//处理失败
    public static final String RESULT_ERROR = "ERROR";//系统错误

    //常量
    public static final ResponseResult SUCC = new ResponseResult(CODE_SUCC);
    public static final ResponseResult PENDING = new ResponseResult(CODE_PENDING, RESULT_PENDING);
    public static final ResponseResult ERROR = new ResponseResult(CODE_ERROR, RESULT_ERROR);

    private String code;
    private String msg;
    private Object[] args;

    @SuppressWarnings("unused")
	private ResponseResult() {
    }

    public ResponseResult(String code) {
        this.code = code;
    }

    public ResponseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(String code, String msg, Object[] args) {
        this.code = code;
        this.msg = msg;
        this.args = args;
    }

    public ResponseResult(Throwable throwable) {
        if (throwable instanceof BusinessException) {
            BusinessException be = (BusinessException) throwable;
            code = be.getBusinessCode();
            msg = be.getMessage();
            args = be.getArgStrs();
        } else {
            code = ResponseResult.CODE_ERROR;
            msg = ThrowableUtils.getRootMessage(throwable);
        }
    }

    public boolean isSuccess() {
        return CODE_SUCC.equals(code);
    }

    public boolean isUnknow() {
        return CODE_ERROR.equals(code);
    }

    public boolean isError() {
        return CODE_ERROR.equals(code);
    }

    public boolean isIng() {
        return CODE_ING.equals(code);
    }

    public boolean isFail() {
        return code != null
                && !CODE_SUCC.equals(code)
                && !CODE_ING.equals(code)
                && !CODE_ERROR.equals(code)
                && !CODE_PENDING.equals(code);
    }

    public boolean isFinish() {
        return isSuccess() || isFail();
    }

    public String getResult() {
        if (isSuccess()) return RESULT_SUCC;
        if (isIng()) return RESULT_ING;
        if (isError()) return RESULT_ERROR;
        return RESULT_FAIL;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getArg(int idx) {
        return args[idx];
    }

    @Override
    public String toString() {
        return format("%s:%s", code, msg);
    }

    public static ResponseResult merge(ResponseResult... responses) {
        ResponseResult current = null;
        for (ResponseResult response : responses) {
            if (response == null) continue;
            if (current == null) current = response;
            else if (current.isSuccess()) current = response;
        }
        return current;
    }

}
