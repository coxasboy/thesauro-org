/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thesauro.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Matheus
 */
public class AttributeCoppier {
    
    private static final ThesauroLogger LOGGER = ThesauroLogger.getLogger(AttributeCoppier.class);
    
    public static void copyAttributesWithSameName(Object objectFrom, Object objectTo) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class classFrom = objectFrom.getClass();
        Class classTo = objectTo.getClass();
        Method [] methods = classFrom.getMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("get")){
                try{
                    Method toMethod = getMethodByName(classTo, "set"+method.getName().substring(3));
                    if(toMethod!=null){
                        Object [] methodArgs = new Object[0];
                        Object currentInstanceValue = method.invoke(objectFrom, methodArgs);
                        Class<?> [] clazzes = toMethod.getParameterTypes();
                        Class<?> clazz = clazzes[0];
                        if(currentInstanceValue!=null){
                            if(isThisObjectALongIntByteShortDoubleFloatCharBooleanOrString(objectTo)){
                                toMethod.invoke(objectTo, clazz.cast(currentInstanceValue));
                            }
                            else{
                                Object newInstance = currentInstanceValue.getClass().newInstance();
                                copyAttributesWithSameName(currentInstanceValue, newInstance);
                                toMethod.invoke(objectTo, clazz.cast(newInstance));
                            }                            
                        }
                    }
                }
                catch(Exception e){
                    LOGGER.error("Error copying attributes",e);
                }
            }
        }
    }
    
    private static boolean isThisObjectALongIntByteShortDoubleFloatCharBooleanOrString(Object object){
        if(object instanceof Long){
            return true;
        }
        if(object instanceof Integer){
            return true;
        }
        if(object instanceof Byte){
            return true;
        }
        if(object instanceof Short){
            return true;
        }
        if(object instanceof Double){
            return true;
        }
        if(object instanceof Float){
            return true;
        }
        if(object instanceof Character){
            return true;
        }
        if(object instanceof Boolean){
            return true;
        }
        if(object instanceof String){
            return true;
        }
        return false;
    }
    
    private static Method getMethodByName(Class classTo, String methodName){
        Method [] methods = classTo.getMethods();
        for (Method method : methods) {
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }
    
    
}
