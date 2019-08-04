/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.exception;


import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.result.ResultErrorCodeEnum;

/**
 * 应用程序错误
 * 
 *
 *
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 2156274765383407719L;

	private ActionResult<?> result;

	public ApplicationException() {
		result = ActionResult.error();
	}

	public ApplicationException(int errcode) {
		result = ActionResult.error(ResultErrorCodeEnum.SYS_ERROR);
	}

	public ApplicationException(String errmsg) {
		result = ActionResult.error(errmsg);
	}

	public ApplicationException(int errcode, String errmsg) {
		result = ActionResult.error(errcode, errmsg);
	}

	public ApplicationException(ActionResult<?> result) {
		this.result = result;
	}

	@Override
	public String getMessage() {
		return result.getMsg();
	}

	public ActionResult<?> getResult() {
		return result;
	}
}
