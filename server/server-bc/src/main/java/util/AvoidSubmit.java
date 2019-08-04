package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 防止操作频繁
 */
public class AvoidSubmit extends HandlerInterceptorAdapter{
	private Logger log=Logger.getLogger(AvoidSubmit.class);
	/**
	 * 处理之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		//500毫秒内 不允许重复提交
			if(invokeNum(request,1,0.5,"czbAvoidTime","czbAvoidCount")){
				log.error("重复提交");
				return false;
			}
			return true;
		}
	/**
	 * 处理之后
	 * request请求处理完成，或已返回响应（即页面已跳转）
	      此时便不再有重复提交的问题了，可以把session清除，以免浪费缓存
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
		  request.getSession().removeAttribute("czbAvoidTime");
		  request.getSession().removeAttribute("czbAvoidCount");
		  log.error("请求完成,清除session");
	}
	/**
	 * 
	 * @param request
	 * @return 默认 1分钟 1次
	 */
	public static boolean invokeNum(HttpServletRequest request){
		return invokeNum(request, 1, 60, "phoneCode","phoneCodeCount");
	}
	public static boolean invokeNum(HttpServletRequest request,String sessionKeyTime,String sessionKeyCount){
		return invokeNum(request, 1, 60, sessionKeyTime, sessionKeyCount);
	}
	/**
	 * 
	 * @param request
	 * @param num 表示规定周期内访问次数
	 * @param count 表示多少秒
	 * @return true 表示拦截成功,没通过       false 表示通过拦截 可以往下走
	 */
	public static boolean invokeNum(HttpServletRequest request,int num,double count,String sessionKeyTime,String sessionKeyCount){
			//有时间限制
			Object obj=request.getSession().getAttribute(sessionKeyTime);
			if(obj==null){
				request.getSession().setAttribute(sessionKeyTime,System.currentTimeMillis());
				return false;
			}else{
				long time=(Long)obj;
				if(System.currentTimeMillis()-time>1000*count){
					//超过规定时间 就更新 开始 调用的时间点和调用次数
					request.getSession().setAttribute(sessionKeyTime,System.currentTimeMillis());
					request.getSession().setAttribute(sessionKeyCount,0);
					return false;
				}
				return num(request,num,sessionKeyCount);
			}
	}
	//次数限制 num 次数
	public static boolean num(HttpServletRequest request,int number,String sessionKeyCount){
		int num=1;
		Object obj=request.getSession().getAttribute(sessionKeyCount);
		if(obj!=null){
			num=(Integer) obj;
			num++;
			request.getSession().setAttribute(sessionKeyCount,num);
		}else{
			request.getSession().setAttribute(sessionKeyCount,num);
		}
		return num>=number;
	}
}
