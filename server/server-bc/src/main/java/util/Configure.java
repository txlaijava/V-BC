package util;

public class Configure {
	public static final String WX_PlACE_ORDER="https://api.mch.weixin.qq.com/pay/unifiedorder";//微信统一下单接口
	
	public static final String NOTIFY_URL="/wxpay/notify"; //微信支付回调地址
	
	public static final String APP_ID="";//微信公众号appid
	
	public static final String APP_SECRET=""; //微信公众号密钥

	public static final String MINI_APP_ID="wx6ef749458ea86394";//微信小程序appid

	public static final String MINI_APP_SECRET="e7d53eb13114ce8f1cc6aa62ca16cf85"; //微信小程序密钥

	public static final String WX_GET_CODE_URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";//微信获取codeurl
	
	public static final String WX_GET_ACCESS_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APP_ID&secret=APP_SECRET&code=USE_CODE&grant_type=authorization_code";//获取用户openid地址
	
	public static final String WX_GET_USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPEN_ID&lang=zh_CN";

	public static String MINI_APP_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	
	public static final String KEY="erjoaugao213hihg441oi214oiio123f";//支付密钥
	
	public static final String PAY_ID="1496582592";//商户id

	//交易类型，小程序支付的固定值为JSAPI
	public static final String TRADETYPE = "JSAPI";

	//小程序支付回调地址
	public static final String WX_CALLBACK_URL = "http://rblc.natapp4.cc/pay/wxNotify";

	public static final String WX_GET_ORDER_INFO = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	
	 
}
