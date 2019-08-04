package com.shopping.wx.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.utils.Utils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.wx.conf.Const;
import com.shopping.wx.token.authorization.manager.JwtTokenUtils;
import com.shopping.wx.token.config.ResultStatus;
import com.shopping.wx.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityFilter implements Filter {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static Set<String> GreenUrlSet = new HashSet<String>();

	private static Set<String> NeedAccountSet = new HashSet<String>();

	/**
	 * 需要用户已经绑定微信的链接
	 */
	private static Set<String> NeedBindWx = new HashSet<String>();

	private RedisService getRedisService(HttpServletRequest request){
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return (RedisService)factory.getBean("redisServiceImpl");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		GreenUrlSet.add("/bc");
		GreenUrlSet.add("/demo");
		GreenUrlSet.add("/login");
		GreenUrlSet.add("/wx");
		GreenUrlSet.add("/alipay");
		GreenUrlSet.add("/index");
		GreenUrlSet.add("/bestHeadlines");
		GreenUrlSet.add("/druid");
		GreenUrlSet.add("/upload_voice_hb");
		GreenUrlSet.add("/voiceHb");
		GreenUrlSet.add("/user/get_user_token");
		GreenUrlSet.add("/user/getOpenId");
		GreenUrlSet.add("/car/outCarInfo");
		GreenUrlSet.add("/pay/wxNotify");
		GreenUrlSet.add("/pay/alipayNotify");
		GreenUrlSet.add("/pay/wxOrderQuery");
		GreenUrlSet.add("/pay/app_pay_success");
		GreenUrlSet.add("/car/parkRecordInfo");
		GreenUrlSet.add("/car/carOrderPay");
		GreenUrlSet.add("/car/carSuccess");
		GreenUrlSet.add("/car/checkOut.htm");
		GreenUrlSet.add("/car/checkIn.htm");
		GreenUrlSet.add("/car/booking");
		GreenUrlSet.add("/car/bookingInfo");
        GreenUrlSet.add("/car/remaining");
		GreenUrlSet.add("/coupon/coupon_get");
		GreenUrlSet.add("/seller/");
		GreenUrlSet.add("/pay/");

		GreenUrlSet.add("/vip/query_recharge_rule");
		GreenUrlSet.add("/vip/query_store_vip");
		GreenUrlSet.add("/vip/add_vip");

		GreenUrlSet.add("/marketing/actBargainingDetail");
		GreenUrlSet.add("/marketing/actCutGoodsInformation");
		GreenUrlSet.add("/marketing/queryGoodsId");
		GreenUrlSet.add("/marketing/fightGroupsGoodsInformation");
		GreenUrlSet.add("/marketing/fightGroupsGoodsUserInformation");
		GreenUrlSet.add("/coupon/getCoupons");

		GreenUrlSet.add("/like/goodsLike");
		GreenUrlSet.add("/comment");
		GreenUrlSet.add("/video");

		NeedAccountSet.add("/account");
		NeedAccountSet.add("/hbRecharge");


		//<editor-fold desc="需要微信绑定微信的链接">
		NeedBindWx.add("");
		//</editor-fold>
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) srequest;
		String uri = request.getRequestURI();
		if (containsSuffix(uri)  || greenUrlCheck(uri) || containsKey(uri) || containsSwagger(uri)) {
			logger.info("don't check url , " + request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
			return;
		}else{
			String authHeader = request.getHeader("Token");
			if (StringUtils.isEmpty(authHeader)) {
				logger.info("Signature verification does not exist.");
				print(sresponse, ResultStatus.JWT_ERRCODE_NULL.getCode(),ResultStatus.JWT_ERRCODE_NULL.getEgMessage());
			}else{
				//验证JWT的签名，返回CheckResult对象
				CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
				if (checkResult.isSuccess()) {
					// 获得用户id
					String getKey = checkResult.getClaims().getId();
					filterChain.doFilter(srequest, sresponse);
				} else {
					ResultStatus resultStatus = ResultStatus.getEnumByCode(checkResult.getErrCode());
					if(Utils.isNotEmpty(resultStatus)){
						print(sresponse,checkResult.getErrCode(),resultStatus.getEgMessage());
					}
				}
			}
		}
	}


	public void print(ServletResponse servletResponse,Object errorCode,String errorMesg) throws IOException{
		JSONObject json = new JSONObject();
		json.put("error",errorCode);
		json.put("error_msg",errorMesg);
		servletResponse.getWriter().write(JSON.toJSONString(json));
	}

	/**
	 * 检查无需登录前缀
	 * @param url
	 * @return
	 */
	private boolean greenUrlCheck(String url){
		for(String greenUrl : GreenUrlSet){
			if(url.startsWith(greenUrl)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 需要账户登录
	 * @param url
	 * @return
	 */
	private boolean needAccountUrlCheck(String url){
		for(String greenUrl : NeedAccountSet){
			if(url.startsWith(greenUrl)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param url
	 * @return
	 * @author neo
	 */
	private boolean containsSuffix(String url) {
		if (url.endsWith(".js")
				|| url.endsWith(".css")
				|| url.endsWith(".jpg")
				|| url.endsWith(".gif")
				|| url.endsWith(".png")
				|| url.endsWith(".html")
				|| url.endsWith(".eot")
				|| url.endsWith(".svg")
				|| url.endsWith(".ttf")
				|| url.endsWith(".woff")
				|| url.endsWith(".ico")
				|| url.endsWith(".woff2")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param url
	 * @return
	 * @author neo
	 */
	private boolean containsKey(String url) {
		if (url.contains("/media/")){
			return true;
		} else {
			return false;
		}
	}

	private boolean containsSwagger(String url) {
		if (url.contains("swagger") || url.contains("/v2/api-docs")){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public String codeToString(String str) {
		String strString = str;
		try {
			byte tempB[] = strString.getBytes("ISO-8859-1");
			strString = new String(tempB);
			return strString;
		} catch (Exception e) {
			return strString;
		}
	}

	public String getRef(HttpServletRequest request){
		String referer = "";
		String param = this.codeToString(request.getQueryString());
		if(StringUtils.isNotBlank(request.getContextPath())){
			referer = referer + request.getContextPath();
		}
		if(StringUtils.isNotBlank(request.getServletPath())){
			referer = referer + request.getServletPath();
		}
		if(StringUtils.isNotBlank(param)){
			referer = referer + "?" + param;
		}
		request.getSession().setAttribute(Const.LAST_REFERER, referer);
		return referer;
	}
}