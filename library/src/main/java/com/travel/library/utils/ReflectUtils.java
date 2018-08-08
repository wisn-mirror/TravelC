package com.travel.library.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Wisn on 2018/5/15 下午2:46.
 */
public class ReflectUtils {
    public static Method getMethod(String classPath, String methodName, Class[] classes) {
        try {
            Class<?> threadClazz = Class.forName(classPath);
            return threadClazz.getMethod(methodName, classes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void invokeInnerCallBack(String classPath, String innerClassPath, String methodName,
                                           Class[] classes, Object[] targetargs, InvocationHandler invocationHandler) {
        try {
            Class<?> threadClazz = Class.forName(classPath);
            Class<?> mCallback = Class.forName(innerClassPath);
            Object mObj = Proxy.newProxyInstance(ReflectUtils.class.getClassLoader(), new Class[]{mCallback}, invocationHandler);
            Method mMethod = threadClazz.getDeclaredMethod(methodName, classes);
            mMethod.invoke(threadClazz.newInstance(), targetargs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
