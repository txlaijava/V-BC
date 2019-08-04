package com.shopping.framework.sms.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * APP交互消息体 
 */
@Data
public class MoblieMessage implements Serializable {
	 
	/**
	 * errorCode: 0表示正常，非零表示有异常
	 */
	private String errorCode = "0";

	/**
	 * 消息内容
	 */
	private String errorMessage;
	/**
	 * list:用来封装返回到客户端的数据对象
	 */
	private List list;
}
