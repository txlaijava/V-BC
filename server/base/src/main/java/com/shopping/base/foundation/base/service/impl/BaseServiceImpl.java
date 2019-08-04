package com.shopping.base.foundation.base.service.impl;

import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.data.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {

    private static final Log log = LogFactory.getLog(BaseServiceImpl.class);

    @Autowired
    protected BaseDao<T, ID> genericDAO;

    @Override
    public void save(T o) {
        genericDAO.save(o);
    }

    @Override
    public void delete(ID id) {
        T object = genericDAO.findById(id);
        if (object != null)
            try {
                genericDAO.delete(object);
            } catch (Exception e) {
                throw new RuntimeException("找不到被删除对象");
            }
    }

    @Override
    public void deleteByIds(ID[] ids) {
        for (ID id : ids) {
            this.delete(id);
        }
    }

    @Override
    public T update(T o) throws Exception {
        return genericDAO.update(o);
    }

    @Override
    public T getObjById(ID objId) {
        T t = this.genericDAO.findById(objId);
        if (t != null) {
            return t;
        }
        return null;
    }

    @Override
    public void detachObj(T o) {
        this.genericDAO.detachObj(o);
    }

    @Override
    public List<T> getObjByProperty(String propName, Object propValue) {
        List<T> t = this.genericDAO.findByProperty(propName, propValue);
        return t;
    }

    @Override
    public T getOneObjByProperty(String propName, Object propValue) {
        T t = this.genericDAO.findOneByProperty(propName, propValue);
        return t;
    }

    @Override
    public T getOneObjByProperty(Map<Object, Object> map) throws Exception {
        T t = this.genericDAO.findOneByMap(map);
        return t;
    }

    @Override
    public List<T> queryByNativeSQL(String sql, Map<String, Object> params) {
        return (List<T>) this.genericDAO.queryByNativeSQL(sql, params);
    }

    @Override
    public List<T> queryByNativeSQL(String sql, int currentPage, int pageSize, Map<String, Object> params) {
        return (List<T>) this.genericDAO.queryByNativeSQL(sql, currentPage, pageSize, params);
    }

    @Override
    public List<Map<String, Object>> queryBySQL(String sql, Object... params) {
        return (List<Map<String, Object>>) this.genericDAO.queryBySQL(sql, params);
    }

    @Override
    public List<Map<String, Object>> queryBySQLPage(String sql, int currentPage, int pageSize, Object... params) {
        return (List<Map<String, Object>>) this.genericDAO.queryBySQLPage(sql, currentPage, pageSize, params);
    }
}
