package com.shopping.base.utils;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.nutz.json.JsonFormat;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

	private static Logger logger = Logger.getLogger(JsonUtil.class);

	public static void writeJson(HttpServletResponse response, Object json) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(json));
		} catch (Exception e) {
			logger.error("json序列化有误", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) {
		try {
			return org.nutz.json.Json.fromJson(HashMap.class, json);
		} catch (Exception e) {
			logger.error("解释JSON有误", e);
		}
		return null;

	}

	public static String toJsonString(Map<String, Object> map) {
		try {
			return org.nutz.json.Json.toJson(map, JsonFormat.compact());
		} catch (Exception e) {
			logger.error("json序列化有误", e);
		}
		return null;
	}

	public static void main(String[] args) {
		String json = "[\"\\\"http:\\\\\\\\oslzugbh0.bkt.clouddn.com\\\\15035001350000\\\"\",\"\\\"http:\\\\\\\\oslzugbh0.bkt.clouddn.com\\\\15035001350001\\\"\"]";

		System.err.println("json:"+ JSON.parse(json));
	}
}