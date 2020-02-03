package com.mpri.aio.schoolmate.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 获取注解上的值（单个值）
* <p>Title: AnnotationUtils</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年10月22日
 */
public class AnnotationUtils {
	
	@SuppressWarnings("unchecked")
	public static String getAnnotationFiles(Class clazz,Class annotationClass) {
        Method[] methods = clazz.getDeclaredMethods();  
        if(methods != null){  
            for(Method method : methods){   
                if(method.getAnnotation(annotationClass) == null)  
                    continue;  
				Method[] me = method.getAnnotation(annotationClass).annotationType().getDeclaredMethods();  
                for(Method meth : me){  
                    try {  
                        String code = (String) meth.invoke(method.getAnnotation(annotationClass),null);  
                        return code;
                    } catch (IllegalAccessException e) {  
                        //// ex.printStackTrace();;  
                    } catch (IllegalArgumentException e) {  
                        //// ex.printStackTrace();;  
                    } catch (InvocationTargetException e) {  
                        //// ex.printStackTrace();;  
                    }  
                }  
            }  
        }
		return "";  
	}
}
