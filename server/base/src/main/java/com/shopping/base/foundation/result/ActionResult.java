package com.shopping.base.foundation.result;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shopping.base.foundation.constant.ErrorCodes;
import com.shopping.base.foundation.exception.ApplicationException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回结果封装
 * 
 *
 */
@Data
@NoArgsConstructor
public class ActionResult<T> implements Serializable {

	/**
	 * 返回码 0 为成功 其余失败
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer code;

	/**
	 * 返回错误内容
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String msg;

	/**
	 * 返回数据
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Setter(AccessLevel.PROTECTED)
	private T data;

	private static ErrorMessageSource messageSource = new ErrorMessageSource();

	/**
	 * 是否是正常结果
	 * @return
	 */
	@JsonIgnore
	public boolean isOk() {
		return code == null || code == 0;
	}

	/**
	 * 空数据的正常返回
	 * @param <T>
	 * @return
	 */
	public static <T> ActionResult<T> ok() {
		ActionResult<T> ret = new ActionResult<T>();
		ret.code = 0;
		ret.msg = "ok";
		return ret;
	}

	/**
	 * 正常返回数据
	 * @param data 返回数据
	 * @param <T>
	 * @return
	 */
	public static <T> ActionResult<T> ok(T data) {
		ActionResult<T> ret = ok();
		ret.data = data;
		return ret;
	}
	/**
	 * 正常返回数据
	 * @param data 返回数据
	 * @param message 返回的提示信息
	 * @return
	 */
	public static <T> ActionResult<T> ok(T data, String message) {
		ActionResult<T> ret = ok();
		ret.data = data;
		ret.setMsg(message);
		return ret;
	}
	/**
	 * 错误返回数据
	 * @param code 错误代码
	 * @param message 错误提示信息
	 * @return
	 */
	public static <T> ActionResult<T> error(int code, String message) {
		ActionResult<T> ret = new ActionResult<T>();
		ret.code = code;
		ret.msg = message;
		return ret;
	}
	/**
	 * 错误返回数据
	 * @param code 错误代码
	 * @param message 错误提示信息
	 * @param data 错误数据
	 * @return
	 */
	public static <T> ActionResult<T> error(int code, String message, T data) {
		ActionResult<T> ret = new ActionResult<T>();
		ret.code = code;
		ret.msg = message;
		ret.data = data;
		return ret;
	}
	/**
	 * 错误返回数据
 	* @param code 错误代码
 	*/
	public static <T> ActionResult<T> error(ResultErrorCodeEnum code) {
		ActionResult<T> ret = new ActionResult<T>();
		ret.code = code.getCode();
		ret.msg = code.getMsg();
		return ret;
	}
	/**
	 * 错误返回数据
	 * @param message 错误代码
	 */
	public static <T> ActionResult<T> error(String message) {
		return error(ErrorCodes.BUSINESS, message);
	}
	/**
	 * 错误返回数据 使用默认代码和错误信息
	 */
	public static <T> ActionResult<T> error() {
		return error(ResultErrorCodeEnum.SYS_ERROR);
	}

	public static <T> ActionResult<T> error(ActionResult<?> result) {
		return error(result.getCode(), result.getMsg());
	}

	/**
	 * 返回时间
	 * @return
	 */
	public long getTimestamp() {
		return System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "[errcode=" + code + ", errmsg=" + msg + "]";
	}

	public T throwExIfError() {
		if (isOk()) {
			return data;
		}
		throw new ApplicationException(this);
	}

}
