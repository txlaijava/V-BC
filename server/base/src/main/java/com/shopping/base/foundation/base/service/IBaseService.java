package com.shopping.base.foundation.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public interface IBaseService<T, ID extends Serializable> {

    void save(T o) throws Exception;

    void delete(ID id) throws Exception;

    void deleteByIds(ID[] ids) throws Exception;

    /**
     * 对象更新
     *
     * @param o
     * @throws Exception
     */
    T update(T o) throws Exception;

    /**
     * 主键查找
     *
     * @param objId
     * @return
     */
    T getObjById(ID objId) throws Exception;

    /**
     * 该方法的作用是把持久化对象变成托管状态。变成托管状态后，Hibernate就不会再去自动更新该实体。
     *
     * @param o
     * @throws Exception
     */
    void detachObj(T o) throws Exception;

    /**
     * 根据字段找对象
     *
     * @param propName
     * @param propValue
     * @return
     */
    List<T> getObjByProperty(String propName, Object propValue) throws Exception;

    /**
     * 根据字段找单个对象
     *
     * @param propName
     * @param propValue
     * @return
     * @throws Exception
     */
    T getOneObjByProperty(String propName, Object propValue) throws Exception;

    /**
     * 根据字段找单个对象
     *
     * @param map
     * @return
     * @throws Exception
     */
    T getOneObjByProperty(Map<Object, Object> map) throws Exception;

    /**
     * SQL查询【返回实体bean 集合】
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    List<T> queryByNativeSQL(String sql, Map<String, Object> params) throws Exception;

    /**
     * SQL 分页查询【返回实体bean 集合】
     *
     * @param sql
     * @param currentPage 当前页数
     * @param pageSize    每页条数
     * @param params
     * @return
     * @throws Exception
     */
    List<T> queryByNativeSQL(String sql, int currentPage, int pageSize, Map<String, Object> params);

    List<Map<String, Object>> queryBySQL(String sql, Object... params) throws Exception;

    /**
     * SQL分页查询
     *
     * @param sql
     * @param currentPage 当前页数
     * @param pageSize    每页记录数
     * @param params      查询条件
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryBySQLPage(String sql, int currentPage, int pageSize, Object... params) throws Exception;
}
