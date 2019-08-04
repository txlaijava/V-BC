package com.shopping.wx.util;

import com.baidu.aip.speech.AipSpeech;
import com.shopping.base.utils.CommUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 语音识别
 * @author gfj
 * @date  2018/3/7.
 */
@Component
@Log4j
public class BaiduSpeechUtil {

    private static final String APP_ID = "10897196";
    private static final String API_KEY = "gvK12Vi97cL8fLlzUlsiKyYE";
    private static final String SECRET_KEY = "RaZ58YcjrqPS2gSyRiyZQ6S3Bwrg83zg";

    private static final int RATE = 16000;

    private BaiduSpeechUtil() {
    }

    private static AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

    public static AipSpeech getAipSpeech() {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }


    /**
     * 接口函数说明：语音识别提供三个接口
     * JSONObject asr(String path, String format, int RATE, HashMap<String, Object> options);
     * JSONObject asr(byte[] data, String format, int RATE, HashMap<String, Object> options);
     * JSONObject asr(String url, String callback, String format, int RATE, HashMap<String, Object> options);
     *
     *
     * 参数--------------类型--------------描述--------------是否必须
     * path/data    String/byte[]	语音文件所在路径或二进制数据,语音文件的格式,pcm或者 wav 或者 amr。不区分大小写      是
     * url	        String	        语音下载地址	    是
     * callback	    String	        识别结果回调地址	否
     * format	    String	        包括pcm（不压缩）、wav、opus、speex、amr       是
     * RATE	        int	            采样率，支持 8000 或者 16000        是
     * cuid	        String	        用户唯一标识，用来区分用户，填写机器 MAC 地址或 IMEI 码，长度为60以内       否
     * lan	        String	        语种选择，中文=zh、粤语=ct、英文=en，不区分大小写，默认中文	        否
     * ptc	        int	            协议号，下行识别结果选择，默认 nbest 结果        否
     */

    /*====================================================================================================================*/

    /**
     * 对本地语音文件进行识别
     * @param client 语音识别的Java客户端
     * @param path   语音本地文件
     * @param format 文件类型
     * @throws Exception
     */
    public static SpeechStateMsg fileSpeech(AipSpeech client,String path, String format) throws Exception{

        JSONObject asrRes = client.asr(path,format, RATE, null);
        SpeechStateMsg stateMsg = new SpeechStateMsg(asrRes);
        return stateMsg;
    }


    /**
     * 对语音二进制数据进行识别
     * @param client 语音识别的Java客户端
     * @param path   语音二进制数据
     * @param format 文件类型
     * @throws Exception
     */
    public static SpeechStateMsg fileSpeech(AipSpeech client, byte[] path, String format) throws Exception{
        /**
         * readFileByBytes仅为获取二进制数据示例
         */
        JSONObject asrRes = client.asr(path, format, RATE, null);
        SpeechStateMsg stateMsg = new SpeechStateMsg(asrRes);
        return stateMsg;
    }

    /**
     * 对网络上音频进行识别
     * 该方式需要传递2个参数，均需要填写2个url。
     * url参数中填写可以识别的音频文件地址，如 http://www.yourdomain.com/res/16k_test.pcm, 根据这个地址，百度服务器会自动下载音频文件。
     * callback参数, 填写用户服务器的回调地址，如http://www.yourdomain.com/post-dump.php， 百度服务器会对这个地址发起http POST 请求，内容为json字符串。
     * 用户首先将url和callback参数传递给百度服务器。此时百度服务器会返回一个sn确认。 之后百度服务器异步下载这个url参数里的音频文件后。在识别结束后，回调用户callback参数的地址。使用之前sn，可以配对。
     * @param client    语音识别的Java客户端
     * @param url       网络上音频地址
     * @param format    音频类型
     * @param callback  识别回调地址
     * @throws Exception
     */
    public static SpeechStateMsg urlSpeech(AipSpeech client,String url, String format,String callback) throws Exception{
        JSONObject asrRes = client.asr(url,"", format, RATE, null);
        SpeechStateMsg stateMsg = new SpeechStateMsg(asrRes);
        return stateMsg;
    }

    public enum SpeechStateEnum {
        /**
         * 状态信息
         */
        UNKNOWN_ERROR(-1,"未知错误","请检查相关逻辑","请检查相关逻辑"),
        SUCCESS(0,"success","识别成功",""),
        PARAMS_ERROR(3300,"用户输入错误","输入参数不正确","请仔细核对文档及参照demo，核对输入参数"),
        POOR_AUDIO_QUALITY(3301,"用户输入错误","音频质量过差","音频质量过差,请上传清晰的音频"),
        AUTHENTI_FAILED(3302,"用户输入错误","鉴权失败","token字段校验失败。请使用正确的API_KEY 和 SECRET_KEY生成"),
        SERVER_BACKEND_PROBLEM(3303,"服务端问题","语音服务器后端问题","请将api返回结果反馈至论坛或者QQ群"),
        POST_QPS_OVERRUN(3304,"用户请求超限","用户的请求QPS超限","请降低识别api请求频率 （qps以appId计算，移动端如果共用则累计）"),
        DAY_PV_POST_OVERRUN(3305,"用户请求超限","用户的日pv（日请求量）超限","请“申请提高配额”，如果暂未通过，请降低日请求量"),
        SERVER_BACKEND_IDENTIFY_ERROR(3307,"用户输入错误","语音服务器后端识别出错问题","目前请确保16000的采样率音频时长低于30s，8000的采样率音频时长低于60s。如果仍有问题，请将api返回结果反馈至论坛或者QQ群"),
        AUDIO_TOOLONG(3308,"用户输入错误","音频过长","音频时长不超过60s，请将音频时长截取为60s以下"),
        AUDIO_DATA_PROBLEM(3309,"用户输入错误","音频数据问题","服务端无法将音频转为pcm格式，可能是长度问题，音频格式问题等。 请将输入的音频时长截取为60s以下，并核对下音频的编码，是否是8K或者16K， 16bits，单声道。"),
        AUDIO_FILE_TOOLARGE(3310,"用户输入错误","输入的音频文件过大","语音文件共有3种输入方式： json 里的speech 参数（base64后）； 直接post 二进制数据，及callback参数里url。 分别对应三种情况：json超过10M；直接post的语音文件超过10M；callback里回调url的音频文件超过10M"),
        SAMPLINGRATE_RATE_NOT_FIND(3311,"用户输入错误","采样率RATE参数不在选项里","目前RATE参数仅提供8000,16000两种，填写4000即会有此错误"),
        AUDIO_FORMAT_NOT_FIND(3312,"用户输入错误","音频格式format参数不在选项里","目前格式仅仅支持pcm，wav或amr，如填写mp3即会有此错误");

        private Integer code;

        private String serverMsg;

        private String meaning;

        private String solve;

        SpeechStateEnum(Integer code,String serverMsg,String meaning,String solve){
            this.code = code;
            this.serverMsg = serverMsg;
            this.meaning = meaning;
            this.solve = solve;
        }

        public static SpeechStateEnum getSexEnumByCode(Integer code){
            for(SpeechStateEnum stateEnum : SpeechStateEnum.values()){
                if(StringUtils.equals(CommUtils.null2String(code), CommUtils.null2String(stateEnum.getCode()))){
                    return stateEnum;
                }
            }
            return null;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getServerMsg() {
            return serverMsg;
        }

        public void setServerMsg(String serverMsg) {
            this.serverMsg = serverMsg;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public String getSolve() {
            return solve;
        }

        public void setSolve(String solve) {
            this.solve = solve;
        }
    }

    @Data
    public static class SpeechStateMsg{
        private SpeechStateEnum errNo;
        private String errMsg;
        private String corpusNo;
        private String sn;
        private String result;

        public SpeechStateMsg(JSONObject json) throws JSONException {
            // {"error_msg":"No permission to access data","error_code":6}
            //{"error_msg":"read image file error","error_code":"SDK102"}
            System.out.println(json.toString());
            this.errNo = SpeechStateEnum.getSexEnumByCode(CommUtils.null2Int(json.has("err_no") ? json.get("err_no") : "-1"));
            this.errMsg = CommUtils.null2String(json.has("err_msg") ? json.get("err_msg") : "");
            this.corpusNo = CommUtils.null2String(json.has("corpus_no") ? json.get("corpus_no") : "");
            this.sn = CommUtils.null2String(json.has("sn") ? json.get("sn") : "");
            this.result = CommUtils.null2String(json.has("result") ? json.get("result") : "");
        }
    }

    public static void main(String[] args) throws Exception {
        SpeechStateMsg stateMsg = BaiduSpeechUtil.fileSpeech(BaiduSpeechUtil.getAipSpeech(), "/Users/wangpenglin/Desktop/fu.pcm", "pcm");
        System.out.println(stateMsg.getCorpusNo());
    }
}
