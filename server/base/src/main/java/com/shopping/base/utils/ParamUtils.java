package com.shopping.base.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParamUtils {

	public static final Logger logger = Logger.getLogger(ParamUtils.class);
	
	public static Map<String,String> getparams(HttpServletRequest request){
		Enumeration<String> enume = request.getParameterNames();
		Map<String,String> paramMap = new HashMap<String,String>();
		while (enume.hasMoreElements()) { 
			String propertyName = enume.nextElement();
			String propertyValue = request.getParameter(propertyName);
			paramMap.put(propertyName, propertyValue);
		}
		return paramMap;
	}

	/**
	 * 获取Map 对象中的属性
	 * @param key
	 * @param map
	 * @return
	 */
	public static String getParamsKey(String key, Map map){
		String value = "";
		if(Utils.isNotEmpty(map)){
			if(map.containsKey(key) && Utils.isNotEmpty(map.get(key))){
				value = map.get(key).toString();
			}
		}
		return value;
	}
}
