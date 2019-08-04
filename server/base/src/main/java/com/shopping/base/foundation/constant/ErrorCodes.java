package com.shopping.base.foundation.constant;

/**
 *
 * @since 2017-10-26 错误代码
 */
public class ErrorCodes {

	public static final int BUSY = -1; // 系统繁忙，此时请开发者稍候再试

	public static final int OK = 0;// 请求成功
	/**
	 * 过期的
	 */
	public static final int EXPIRED = 4000; // 过期的
	public static final int EXPIRED_TOKEN = 4001; // 过期的令牌

	/* 无效的 **/
	public static final int INVALID_ARGUMENTS = 4100;// 无效的参数
	public static final int INVALID_SIGN = 4101;// 无效的签名
	public static final int INVALID_UPLOAD_FILE_TYPE = 4102;// 不合法的文件类型
	public static final int INVALID_UPLOAD_FILE_SIZE = 4103;// 不合法的文件大小
	public static final int INVALID_PASSWORD = 4104;// 无效的密码
	public static final int INVALID_CAPTCHA = 4105;// 无效的验证码
	public static final int INVALID_MUCH_TIME = 4106;// 验证码输入次数过多

	/* 缺少的 */
	public static final int MISSING_ARGUMENTS = 4200; // 缺少参数

	/* 重复的 */
	public static final int REPEAT = 4300; // 重复记录
	public static final int REPEAT_ACCOUNT = 4301; // 账号已存在

	/* 未找到的 */
	public static final int NOT_FOUND = 4400; // 没有找到记录
	public static final int NOT_FOUND_USER = 4401; // 不存在的用户
	public static final int NOT_FOUND_SERVICE = 4402; // 不存在的服务
	public static final int NOT_FOUND_RESOURCE = 4403; // 不存在的资源
	public static final int NOT_FOUND_FILE = 4404; // 不存在的资源
	/* 超时的 */
	public static final int TIMEOUT = 4500; // 超时

	/* 拒绝的 */
	public static final int DENIED_ACCESS = 4600; // 拒绝访问
	public static final int DENIED_UNAUTHORIZED = 4601; // 未授权:由于凭据无效,访问被拒绝
	public final static int DENIED_ACCOUNT_DISABLED = 4602; // 账号禁用
	public final static int DENIED_LOGIN_TOO_MANY = 4603; // 登录次数过多

	/* 非法的操作 */
	public static final int ILLEGAL_OPERATE = 4700; // 非法的操作
	public static final int ILLEGAL_ARGUMENT = 4701; // 非法的参数

	/* 不支持的 */
	public static final int NO_SUPPORT = 4800; // 不支持的服务

	/* 未注册的 */
	public static final int UNTRUSTED = 4900; // 未注册的
	public static final int UNREGISTERED_RESOURCE = 4901; // 未注册的资源

	/* 业务错误 */
	public static final int BUSINESS = 5000;// 业务异常
}
