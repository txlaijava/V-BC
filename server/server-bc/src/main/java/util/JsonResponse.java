package util;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 处理结果Vo
 * 
 *
 *
 *
 * @param <T>
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class JsonResponse<T> implements Serializable {

    private static final long serialVersionUID = 2273610255200563857L;

    /**
     * 结果
     */
    private int res;
    
    /**
     * 结果
     */
    private String result;

    /**
     * 主键
     */
    private String privateKey;

    /**
     * code
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 对象
     */
    private T obj;

    /**
     * 结果集
     */
    private List<T> list;

    public JsonResponse(){
    	
    } 
    public JsonResponse(int res){
    	this.res=res;
    }
    public JsonResponse(int res,String result){
    	this.res=res;
    	this.result=result;
    }
    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}
