package com.shopping.framework.sms.core;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.shopping.framework.sms.conf.JpushConfig;
import com.shopping.framework.sms.message.OrderMessage;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 极光推送
 */
@Log4j
@Component
public class ApiCloudPushMsg {

	private static final Logger logger = Logger.getLogger(ApiCloudPushMsg.class);

	@Autowired
	private JpushConfig jpushConfig;

	/**
	 * 消息单发
	 */
	public void pushMsgSingleDevice(String channel_id, OrderMessage orderMsg) {
		try{
			ClientConfig config = ClientConfig.getInstance();
			config.setApnsProduction(true);
			JPushClient jpushClient = null;
			//推送给用户
			if("rblc".equals(orderMsg.getPlatform_type())){
				jpushClient = new JPushClient(jpushConfig.getAppSecret(), jpushConfig.getAppKey(),null,config);
			}
			//推送给商户
			else if("rblc_seller".equals(orderMsg.getPlatform_type())){
				jpushClient = new JPushClient(jpushConfig.getAppSecret_seller(), jpushConfig.getAppKey_seller(),null,config);
			}
			// For push, all you need do is to build PushPayload object.
			PushPayload payload = PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.registrationId(channel_id))
					.setNotification(Notification.newBuilder()              //发送通知
							.setAlert(orderMsg.getMsgDesc())
							.addPlatformNotification(AndroidNotification.newBuilder()
									.setTitle(orderMsg.getTitle())
									.addExtra("title",orderMsg.getTitle())
									.addExtra("content",orderMsg.getMsgDesc())
									.addExtra("url",orderMsg.getMsgUrl())
									.addExtra("push_type",orderMsg.getOpenType())
									.addExtra("push_way",orderMsg.getPushWay())
									.build())
							.addPlatformNotification(IosNotification.newBuilder()
									.incrBadge(1)
									.addExtra("title",orderMsg.getTitle())
									.addExtra("content",orderMsg.getMsgDesc())
									.addExtra("url",orderMsg.getMsgUrl())
									.addExtra("push_type",orderMsg.getOpenType())
									.addExtra("push_way",orderMsg.getPushWay())
									.build())
							.build())
					.setOptions(Options.newBuilder().setApnsProduction(true).build())
					.build();

			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
		}catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		} catch (Exception ex){
			logger.error("error", ex);
		}
	}

	/**
	 * 消息多发
	 */
	public void pushMsgSingleDevice(List<String> registrationIds, OrderMessage orderMsg) {
		ClientConfig config = ClientConfig.getInstance();
		config.setApnsProduction(true);
		JPushClient jpushClient = new JPushClient(jpushConfig.getAppSecret(), jpushConfig.getAppKey(),null,config);
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.registrationId(registrationIds))
				.setNotification(Notification.newBuilder()              //发送通知
						.setAlert(orderMsg.getMsgDesc())
						.addPlatformNotification(AndroidNotification.newBuilder()
								.setTitle(orderMsg.getTitle())
								.addExtra("title",orderMsg.getTitle())
								.addExtra("content",orderMsg.getMsgDesc())
								.addExtra("url",orderMsg.getMsgUrl())
								.addExtra("push_type",orderMsg.getOpenType())
								.addExtra("push_way",orderMsg.getPushWay())
								.build())
						.addPlatformNotification(IosNotification.newBuilder()
								.incrBadge(1)
								.addExtra("title",orderMsg.getTitle())
								.addExtra("content",orderMsg.getMsgDesc())
								.addExtra("url",orderMsg.getMsgUrl())
								.addExtra("push_type",orderMsg.getOpenType())
								.addExtra("push_way",orderMsg.getPushWay())
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}

}
