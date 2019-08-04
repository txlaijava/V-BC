package com.shopping.base.utils.socket;

import com.shopping.base.utils.CommUtils;
import org.apache.log4j.Logger;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016-09-29.
 */
public class SessionUtils {

    public static Map<String, Map<String, Object>> clients = new ConcurrentHashMap<>();  //Long  ==  store_id

    private static Logger logger = Logger.getLogger(SessionUtils.class);

    /**
     * 存储session
     *
     * @param key
     * @param data    加密后的数据
     * @param session
     */
    public static void put(String key, String data, Session session) {
        Map map = new HashMap();
        map.put("session", session);
        map.put("data", data);
        clients.put(key, map);
    }

    /**
     * 获取session
     *
     * @param key
     * @return
     */
    public static Session getSession(String key) {
        if (SessionUtils.hasConnection(key)) {
            Map<String, Object> map = clients.get(key);
            if (map.containsKey("session")) {
                return (Session) map.get("session");
            }
        }
        return null;
    }

    /**
     * 获取session
     *
     * @param key
     * @return
     */
    public static Map<String, Object> getMap(String key) {
        if (SessionUtils.hasConnection(key)) {
            return clients.get(key);
        }
        return null;
    }

    /**
     * 移除Session
     *
     * @param key
     */
    public static void remove(String key) {
        clients.remove(key);
    }

    /**
     * 判断是否有连接
     *
     * @param storeId
     * @return
     */
    public static boolean hasConnection(String storeId) {
        return clients.containsKey(storeId);
    }

    /**
     * 将数据传回客户端
     * 异步的方式
     *
     * @param storeId
     * @param message
     */
    public static synchronized void sendMsg(String storeId, String message) {
        if (SessionUtils.hasConnection(storeId) && CommUtils.isNotNull(SessionUtils.getSession(storeId))) {
            try {
                SessionUtils.getSession(storeId).getBasicRemote().sendText(message);
            } catch (Exception e) {
                logger.info(e);
            }
        } else {
            logger.info(storeId + " Connection does not exist");
        }

    }

}