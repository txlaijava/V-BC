package com.shopping.base.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gfj on 2018/3/16.
 */
public class BeanUtilEx extends BeanUtils {

    private static Map cache = new HashMap();

    protected Logger logger = Logger.getLogger(this.getClass());

    private BeanUtilEx() {
    }

    public static void copyProperties(Object target, Object source) throws InvocationTargetException,
            IllegalAccessException {
        // 支持对日期copy
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

    }


    public static void populates(Object target,Map<String,Object> source) throws InvocationTargetException, IllegalAccessException {
        // 支持对日期copy
        org.apache.commons.beanutils.BeanUtils.populate(target, source);

    }


    /**
     *
     *
     * Map转换层Bean，使用泛型免去了类型转换的麻烦。
     * @param <T>
     * @param map
     * @param class1
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> class1) {
        T bean = null;
        try {
            bean = class1.newInstance();
            BeanUtilEx.populates(bean, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param obj
     */
    public static Map<String, Object> transBean2Map(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();

            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);

                map.put(key, value);
            }

        }
        return map;
    }

}