package com.shopping.base.foundation.data;

import com.shopping.base.foundation.data.aggregate.Aggregation;
import com.shopping.base.foundation.form.BaseQueryForm;
import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.result.PaginationResult;
import com.shopping.base.foundation.result.QueryResult;
import com.shopping.base.utils.Utils;
import lombok.AllArgsConstructor;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * 基础DAO操作
 *
 * @param <T>
 * @param <ID>
 */
public abstract class BaseDao<T, ID extends Serializable> {


    private EntityManager em;

    private JpaEntityInformation<T, ID> entityInfo;

    private Class<T> entityClass;

    private Class<ID> idClass;

    private static final ConversionService conversionService = new DefaultConversionService();

    @SuppressWarnings("unchecked")
    public BaseDao() {
        //获取实体类基本信处
        Type type = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
        idClass = (Class<ID>) params[1];
    }

    public static ConversionService getConversionService() {
        return conversionService;
    }

    @Autowired
    @PersistenceContext(unitName = "primaryPersistenceUnit")
    @SuppressWarnings("unchecked")
    public void setEntityManager(EntityManager em) {
        this.em = em;
        this.entityInfo = (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(entityClass, em);
    }

    public void setUtf8Encoding() {
        Query query = em.createNativeQuery("SET NAMES utf8");
        query.executeUpdate();
    }

    public void setUtf8mb4Encoding() {
        Query query = em.createNativeQuery("SET NAMES utf8mb4");
        query.executeUpdate();
    }

    /**
     * 根据id 查询 返回对象
     *
     * @param id
     * @return T
     */
    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public void detachObj(T entity) {
        em.detach(entity);
    }

    /**
     * 根据id查询对象是否存在
     *
     * @param id
     * @return 如果存在返回 true 否则返回false
     */
    public boolean exists(ID id) {
        return em.find(entityClass, id) != null;
    }

    /**
     * 通过Ids列表查询对象列表
     *
     * @param ids
     * @return 返回查询出的所有列表
     */
    public List<T> findByIds(ID... ids) {
        List<T> list = new ArrayList<>(ids.length);
        for (Serializable id : ids) {
            T item = em.find(entityClass, id);
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 通过Ids列表查询对象列表
     *
     * @param ids
     * @return 返回查询出的所有列表
     */
    public List<T> findByIds(List<ID> ids) {
        List<T> list = new ArrayList<>(ids.size());
        for (Serializable id : ids) {
            T item = em.find(entityClass, id);
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 通过Ids列表查询对象列表
     *
     * @param ids
     * @return 返回查询出的所有列表
     */
    public Stream<T> findByIds(Stream<ID> ids) {
        return ids.map((id) -> {
            return em.find(entityClass, id);
        });
    }

    /**
     * 通过以 {eparator}分隔符 分割出字符串 然后转化成我们需要的idClass类型，然后查询
     *
     * @param ids
     * @param separator
     * @return
     */
    public List<T> findByStrIds(String ids, String separator) {
        // 使用分隔符分割出字符串数组
        String[] strs = ids.trim().split(separator);
        List<T> list = new ArrayList<>(strs.length);
        for (String str : strs) {
            if (str.length() == 0) {
                continue;
            }
            ID id = conversionService.convert(str, idClass);
            T item = em.find(entityClass, id);
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取所有对象
     *
     * @return
     */
    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        TypedQuery<T> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public T findByPropertyOne(String name, Object value) {
        List<T> propertys = this.findByProperty(name, value, null);
        if (propertys != null && propertys.size() > 0) {
            return propertys.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据某个属性获取对象List
     *
     * @param name  属性名称
     * @param value 属性名值
     * @return
     */
    public List<T> findByProperty(String name, Object value) {
        return this.findByProperty(name, value, null);
    }

    public List<T> findByProperty(String name, Object value, String orderBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.where(cb.equal(QueryFormHelper.getPath(root, name), value));
        if (!StringUtils.isEmpty(orderBy)) {
            List<Order> orders = QueryFormHelper.getOrdes(orderBy, root);
            query.orderBy(orders);
        }
        TypedQuery<T> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    /**
     * 根据某些属性获取对象List
     *
     * @param map 属性名称，属性值
     * @return
     */
    public List<T> findByMap(Map<Object, Object> map) {
        return this.findByMap(map, null);
    }

    /**
     * 根据某些属性获取对象List
     *
     * @param map     属性名称，属性值
     * @param orderBy 排序
     * @return
     */
    public List<T> findByMap(Map<Object, Object> map, String orderBy) {
        return createQueryByMap(map, entityClass, orderBy).list();
    }

    /**
     * 根据某些属性获取对象List
     *
     * @param map     属性名称，属性值
     * @param orderBy 排序
     * @param offset  开始位置
     * @param limit   结果集大小
     * @return
     */
    public List<T> findByMap(Map<Object, Object> map, String orderBy, int offset, int limit) {
        return createQueryByMap(map, entityClass, orderBy).list(offset, limit);
    }

    /**
     * 根据某些属性获取对象L
     *
     * @param name     属性名称
     * @param value    属性值
     * @param lockMode 对象锁类型
     * @return
     */
    public T findOneByProperty(String name, Object value, LockModeType lockMode) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.where(cb.equal(QueryFormHelper.getPath(root, name), value));
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setLockMode(lockMode);
        try {
            List<T> list = typedQuery.getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 根据某些属性获取对象L
     *
     * @param name  属性名称
     * @param value 属性值
     * @return
     */
    public T findOneByProperty(String name, Object value) {
        return this.findOneByProperty(name, value, LockModeType.NONE);
    }

    /**
     * 根据某属性获取对象，并对象加入读写锁
     *
     * @param name  属性名称
     * @param value 属性值
     * @return
     */
    public T findOneWithLockByProperty(String name, Object value) {
        return this.findOneByProperty(name, value, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * 根据某些属性获取对象，并对象加入读写锁
     *
     * @param map 属性名称，属性值
     * @return
     */
    public T findOneByMap(Map<Object, Object> map) {
        return this.findOneByMap(map, LockModeType.NONE);
    }

    public T findOneByMap(Map<Object, Object> map, String orderBy) {
        return createQueryByMap(map, entityClass, orderBy).single(LockModeType.NONE);
    }

    public T findOneByMap(Map<Object, Object> map, LockModeType lockMode) {
        return createQueryByMap(map, entityClass, null).single(lockMode);
    }

    public T findOneWithLockByMap(Map<Object, Object> map) {
        return this.findOneByMap(map, LockModeType.PESSIMISTIC_WRITE);
    }

    public T findWithLockById(ID id) {
        return em.find(entityClass, id, LockModeType.PESSIMISTIC_WRITE);
    }

    public <R> List<R> aggregate(Aggregation<R> aggregation) {
        CriteriaQuery<R> query = aggregation.createQuery(em, entityClass);
        TypedQuery<R> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public <S extends T> S save(S entity) {
        if (entityInfo.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    public <S extends T> S update(S entity) {
        return em.merge(entity);
    }

    public void delete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public void deleteAll() {
        for (T item : findAll()) {
            delete(item);
        }
    }

    public boolean deleteById(ID id) {
        T entity = em.find(entityClass, id);
        if (entity == null) {
            return false;
        }
        em.remove(entity);
        return true;
    }

    public void refresh(T entity) {
        em.refresh(entity);
    }

    public void refreshWithLock(T entity) {
        em.refresh(entity, LockModeType.PESSIMISTIC_WRITE);
    }

    public void refresh(T entity, LockModeType lockMode) {
        em.refresh(entity, lockMode);
    }

    public QueryResult<T> query(Specification<T> specification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Predicate predicate = specification.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }
        TypedQuery<T> typedQuery = em.createQuery(query);
        List<T> list = typedQuery.getResultList();
        return new QueryResult<T>(list, null);
    }

    public PaginationResult<T> query(Specification<T> specification, PaginationForm form) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Predicate predicate = specification.toPredicate(root, query, cb);
        if (predicate != null) {
            query.where(predicate);
        }
        int currentPage = form.getCurrentPage();
        int pageSize = form.getPageSize();
        int offset = (currentPage - 1) * pageSize;
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(pageSize);
        List<T> list = typedQuery.getResultList();
        // 统计总记录数
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        predicate = specification.toPredicate(countRoot, countQuery, cb);
        if (predicate != null) {
            countQuery.where(predicate);
        }
        countQuery.select(cb.count(countRoot));
        TypedQuery<Long> typedCountQuery = em.createQuery(countQuery);
        long total = 0;
        for (long value : typedCountQuery.getResultList()) {
            total += value;
        }
        return new PaginationResult<T>(total, pageSize, currentPage, list, null);
    }

    public QueryResult<T> query(BaseQueryForm form) {
        String orderBy = form.getOrderBy();
        QueryWrapper queryWrapper = QueryFormHelper.createQueryWrapper(form);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        queryWrapper.wrap(root, query, cb, true);// 加入查询和排序条件
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setLockMode(form.getLockMode());
        if (form instanceof PaginationForm) {
            PaginationForm pagination = (PaginationForm) form;
            int currentPage = pagination.getCurrentPage();
            int pageSize = pagination.getPageSize();
            int offset = (currentPage - 1) * pageSize;
            if (pageSize > 0) {
                typedQuery.setFirstResult(offset);
                typedQuery.setMaxResults(pageSize);
            }
            List<T> list = typedQuery.getResultList();
            long total = executeCountQuery(queryWrapper);
            return new PaginationResult<T>(total, pageSize, currentPage, list, orderBy);
        } else {
            List<T> list = typedQuery.getResultList();
            return new QueryResult<T>(list, orderBy);
        }
    }

    public PaginationResult<T> paging(PaginationForm form) {
        return (PaginationResult<T>) this.query(form);
    }

    public long count() {
        return executeCountQuery(null);
    }

    public long countByProperty(String name, Object value) {
        Map<Object, Object> map = new HashMap<>();
        map.put(name, value);
        return countByMap(map);
    }

    public long countByMap(Map<Object, Object> map) {
        return createQueryByMap(map, Long.class, null).count();
    }

    public long count(BaseQueryForm form) {
        QueryWrapper queryWrapper = QueryFormHelper.createQueryWrapper(form);
        return executeCountQuery(queryWrapper);
    }

    public double sumByProperty(String field, String name, Object value) {
        Map<Object, Object> map = new HashMap<Object, Object>(1);
        map.put(name, value);
        return this.sumByMap(field, map);
    }

    public double sumByMap(String expr, Map<Object, Object> map) {
        Double result = createQueryByMap(map, Double.class, null).sum(expr);
        return result == null ? 0d : result.doubleValue();
    }

    public <N extends Number> N maxByMap(String expr, Map<Object, Object> map, Class<N> resultType) {
        N result = createQueryByMap(map, resultType, null).max(expr, resultType);
        return result;
    }

    public <N extends Number> N minByMap(String expr, Map<Object, Object> map, Class<N> resultType) {
        N result = createQueryByMap(map, resultType, null).min(expr, resultType);
        return result;
    }


    /**
     * 执行原生SQL查询
     *
     * @param sql
     * @param params sql参数
     * @return 结果集并影射成Map
     */
    public List<Map<String, Object>> queryByNativeSQL(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    /**
     * 执行原生SQL分页查询
     *
     * @param sql
     * @param currentPage 当前页数
     * @param pageSize    每页条数
     * @param params      sql参数
     * @return 结果集并影射成entityClass
     */
    public List<?> queryByNativeSQL(String sql, int currentPage, int pageSize, Map<String, Object> params) {
        currentPage = currentPage > 0 ? currentPage : 1;
        pageSize = pageSize == 0 ? 12 : pageSize;
        sql = sql + " limit " + pageSize + " offset " + (currentPage - 1) * pageSize;
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    /**
     * 执行原生SQL分页查询
     *
     * @param sql
     * @param currentPage 当前页数
     * @param pageSize    每页条数
     * @param params      sql参数
     * @return 结果集并影射成entityClass
     */
    public List<T> queryByNativeSQLPage(String sql, int currentPage, int pageSize, Map<String, Object> params) {
        currentPage = currentPage > 0 ? currentPage : 1;
        pageSize = pageSize == 0 ? 12 : pageSize;
        sql = sql + " limit " + pageSize + " offset " + (currentPage - 1) * pageSize;
        Query query = em.createNativeQuery(sql, entityClass);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        return query.getResultList();
    }

    public List<Map<String, Object>> findBySqlPage(String sql, int currentPage, int pageSize, Map<String, Object> params) {
        currentPage = currentPage > 0 ? currentPage : 1;
        pageSize = pageSize == 0 ? 12 : pageSize;
        //sql = sql + " LIMIT "+(currentPage-1)*pageSize+",".$num;
        if (pageSize >= 0) {
            sql = sql + " limit " + pageSize + " offset " + (currentPage - 1) * pageSize;
        }

        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<Map<String, Object>> queryBySQL(String sql, Object... params) {
        Query query = em.createNativeQuery(sql, entityClass);
        if (params != null && Utils.isNotEmpty(params)) {
            int index = 0;
            for (Object object : params) {
                if (!"SQL_PASS".equals(object)) {
                    query.setParameter(index, object);
                    index++;
                }
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<Map<String, Object>> queryBySQLPage(String sql, int currentPage, int pageSize, Object... params) {

        currentPage = currentPage > 0 ? currentPage : 1;
        pageSize = pageSize == 0 ? 12 : pageSize;
        //sql = sql + " LIMIT "+(currentPage-1)*pageSize+",".$num;
        sql = sql + " limit " + pageSize + " offset " + (currentPage - 1) * pageSize;

        Query query = em.createNativeQuery(sql);
        if (params != null && Utils.isNotEmpty(params)) {
            int index = 0;
            for (Object object : params) {
                if (!"SQL_PASS".equals(object)) {
                    query.setParameter(index, object);
                    index++;
                }
            }
        }
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    /**
     * 执行原生SQL查询
     *
     * @param sql
     * @param entityClass 结果转换实体类
     * @param params      sql参数
     * @return 结果集并影射成entityClass
     */
    public List<?> queryByNativeSQL(String sql, Class entityClass, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql, entityClass);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        return query.getResultList();
    }

    /**
     * 执行原生SQL更新
     *
     * @param sql
     * @param params sql参数
     * @return 更新结果数量
     */

    public int updateByNativeSQL(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }
        return query.executeUpdate();
    }
    //////////////////////////////////////////////////// 私有方法
    //////////////////////////////////////////////////// //////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    private <R> QueryWraper<R> createQueryByMap(Map<Object, Object> map, Class<R> resultType, String orderBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> query = cb.createQuery(resultType);
        Root<T> root = query.from(entityClass);
        Predicate[] predicates = null;
        if (map != null) {
            int i = 0;
            predicates = new Predicate[map.size()];
            for (Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof AbstractExpression) {
                    Predicate predicate = ((AbstractExpression) value).buildJpaPredicate(cb, root, key);
                    predicates[i++] = predicate;
                } else {
                    String name = key.toString();
                    Expression<?> path = QueryFormHelper.getPath(root, name);
                    Predicate predicate = cb.equal(path, value);
                    predicates[i++] = predicate;
                }
            }
        }
        if (resultType == entityClass) {
            query.select((Selection<? extends R>) root);
        }
        if (!StringUtils.isEmpty(orderBy)) {
            List<Order> orders = QueryFormHelper.getOrdes(orderBy, root);
            query.orderBy(orders);
        }
        if (predicates != null) {
            query.where(predicates);
        }
        return new QueryWraper<>(query, cb, root);
    }

    private long executeCountQuery(QueryWrapper queryWrapper) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);
        query.select(cb.count(root));
        queryWrapper.wrap(root, query, cb, false);
        TypedQuery<Long> typedQuery = em.createQuery(query);
        long total = 0;
        for (long value : typedQuery.getResultList()) {
            total += value;
        }
        return total;
    }

    @AllArgsConstructor
    private class QueryWraper<R> {

        private CriteriaQuery<R> query;

        private CriteriaBuilder cb;

        private Root<T> root;

        @SuppressWarnings("unchecked")
        public long count() {
            long count = 0;
            query.select((Selection<? extends R>) cb.count(root));
            TypedQuery<R> typedQuery = em.createQuery(query);
            for (R value : typedQuery.getResultList()) {
                count += ((Number) value).longValue();
            }
            return count;
        }

        @SuppressWarnings("unchecked")
        public R sum(String expr) {
            Expression<Number> path = QueryFormHelper.getExpression(cb, root, expr);
            query.select((Selection<? extends R>) cb.toDouble(cb.sum(path)));
            TypedQuery<R> typedQuery = em.createQuery(query);
            return typedQuery.getSingleResult();
        }

        public R max(String expr, Class<R> resultType) {
            Expression<Number> path = QueryFormHelper.getExpression(cb, root, expr);
            query.select((Selection<? extends R>) cb.max(path).as(resultType));
            TypedQuery<R> typedQuery = em.createQuery(query);
            return typedQuery.getSingleResult();
        }

        public R min(String expr, Class<R> resultType) {
            Expression<Number> path = QueryFormHelper.getExpression(cb, root, expr);
            query.select((Selection<? extends R>) cb.min(path).as(resultType));
            TypedQuery<R> typedQuery = em.createQuery(query);
            return typedQuery.getSingleResult();
        }

        public List<R> list() {
            TypedQuery<R> typedQuery = em.createQuery(query);
            return typedQuery.getResultList();
        }

        public List<R> list(int offset, int size) {
            TypedQuery<R> typedQuery = em.createQuery(query);
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(size);
            return typedQuery.getResultList();
        }

        public R single(LockModeType lockMode) {
            TypedQuery<R> typedQuery = em.createQuery(query);
            typedQuery.setLockMode(lockMode);
            try {
                List<R> list = typedQuery.getResultList();
                if (list.isEmpty()) {
                    return null;
                } else {
                    return list.get(0);
                }
            } catch (NoResultException e) {
                return null;
            }
        }
    }

}
