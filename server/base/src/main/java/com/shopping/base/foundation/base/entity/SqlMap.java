package com.shopping.base.foundation.base.entity;

import com.shopping.base.foundation.util.JaxbReadXml;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * XML转sql实体
 */
@XmlRootElement
public class SqlMap {
    Map<String, String> sqls = new HashMap<>();

    public Map<String, String> getSqls() {
        return sqls;
    }

    public void setSqls(Map<String, String> sqls) {
        this.sqls = sqls;
    }

    public String getSql(String name) {
        return sqls.get(name).replaceAll("\r|\n", "");
    }

    public String getSql(String name,Map<String,String> sqlparm) {
        String sql = sqls.get(name).replaceAll("\r|\n", "");
        if(sqlparm!=null && sqlparm.size()>0){
            for(String param:sqlparm.keySet() ){
                String condition = "::" + param;
                String condistionVal = sqlparm.get(param);
                //查找是否有这个条件参数
                if(sql.indexOf(condition)>=0){
                    //进行替换
                    sql = sql.replaceAll(condition,condistionVal);
                }else{
                    sql = sql.replaceAll(condition,"");
                }
            }
        }
        return sql;
    }

    public static SqlMap load(String name){
        SqlMap sqlMap = null;
        try {
            sqlMap = JaxbReadXml.readString(SqlMap.class, name);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlMap;
    }

    /**
     * xml自定义条件转义
     * @return
     */
    public String sqlCondition(String sql,Map<String,String> sqlparm){
        if(sqlparm!=null && sqlparm.size()>0){
            for(String param:sqlparm.keySet() ){
                String condition = "::" + param;
                String condistionVal = sqlparm.get(param);
                //查找是否有这个条件参数
                if(sql.indexOf(condition)>=0){
                    //进行替换
                    sql = sql.replaceAll(condition,condistionVal);
                }else{
                    sql = sql.replaceAll(condition,"");
                }
            }
        }
        return sql;
    }
}