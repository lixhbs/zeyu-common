package com.github.lixhbs.zeyucommon;

import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 煮酒泛舟.
 * @title Utils
 * @program czpf
 * @description Utils
 * @createtime 2019-09-06 22:36
 */
public class Utils {

    public static Map<String, String> bean2map(Object bean, Class<?> tClass) throws Exception {
        Map<String, String> map = new HashMap<>(1);
        //获取指定类（Person）的BeanInfo 对象
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass, Object.class);
        //获取所有的属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            Method getter = pd.getReadMethod();
            String value = String.valueOf(getter.invoke(bean));
            map.put(key, value);
        }
        return map;
    }

    public static JSONObject bean2JSONobject(Object bean, Class<?> tClass) throws Exception {
        JSONObject jsonObject = new JSONObject();
        //获取指定类（Person）的BeanInfo 对象
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass, Object.class);
        //获取所有的属性描述器
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            Method getter = pd.getReadMethod();
            String value = String.valueOf(getter.invoke(bean));
            jsonObject.put(key, value);
        }
        return jsonObject;
    }


}
