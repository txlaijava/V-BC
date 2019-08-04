package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommondUtil {
		private static Logger log =LoggerFactory.getLogger(CommondUtil.class);
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        //JSONObject jsonObject = null;
        StringBuffer buffer = null;
        try {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                //TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, null, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                URL url = new URL(requestUrl);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(ssf);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // 设置请求方式（GET/POST）
                conn.setRequestMethod(requestMethod);
                //conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                        OutputStream outputStream = conn.getOutputStream();
                        // 注意编码格式
                        outputStream.write(outputStr.getBytes("UTF-8"));
                        outputStream.close();
                }
                // 从输入流读取返回内容
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                        buffer.append(str);
                }
                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                //jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException ce) {
        	log.error("连接服务器错误:"+ce.getMessage());
        } catch (Exception e) {
        	log.error("创建ssl失败:"+e.getMessage());
        }
        return buffer.toString();
	}

	public static boolean time(Date currentTime,Date startTime,Date endTime){
		return currentTime.getTime()>startTime.getTime()&&currentTime.getTime()<endTime.getTime();
	}
	public static void main(String[] args) throws ParseException {
		String startTime="2015-08-22 12:23:23";
		String endTime="2015-08-28 02:30:23";
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(format.parse(endTime).getTime());
			System.out.println(format.parse(endTime).getTime()-new Date().getTime());
		//System.out.println(time(new Date(),format.parse(startTime),format.parse(endTime)));
	}
	/**
	 * 获取当前网络ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
					//根据网卡取本机配置的IP
					InetAddress inet=null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress= inet.getHostAddress();
				}
			}
			//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
				if(ipAddress.indexOf(",")>0){
					ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
				}
			}
			return ipAddress; 
	}
}
