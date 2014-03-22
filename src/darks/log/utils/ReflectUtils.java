/**
 * 
 *Copyright 2014 The Darks Logs Project
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

package darks.log.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import darks.log.exceptions.ConfigException;
import darks.log.kernel.Kernel;
import darks.log.loader.ClassFinder;

/**
 * 
 * ReflectUtils.java
 * @version 1.0.0
 * @author Liu lihua
 */
public final class ReflectUtils
{
	private static final String LAYOUT_CLASS_DIR = "darks.log.layout";

	private static final String FILTER_CLASS_DIR = "darks.log.filter";
	
	private static final String CONVERTOR_CLASS_DIR = "darks.log.pattern";
	
	private static final String EXTERNS_CLASS_DIR = "darks.log.externs";
	
	static final String[] DEFAULT_DIRS = new String[]{
		LAYOUT_CLASS_DIR, FILTER_CLASS_DIR, CONVERTOR_CLASS_DIR, EXTERNS_CLASS_DIR
	};
	
    private ReflectUtils()
    {
        
    }
    
    public static Object newInstance(Class<?> clazz)
    {
        try
        {
	    	Constructor<?> cst = clazz.getConstructor();
	    	if (cst != null)
	    	{
	    		return cst.newInstance();
	    	}
        }
        catch (Exception e)
        {
            Kernel.logError(e.getMessage(), e);
        }
        return null;
    }
    
    public static Constructor<?> getConstructor(Class<?> clazz)
    {
        try
        {
            return clazz.getConstructor();
        }
        catch (Exception e)
        {
            Kernel.logError(e.getMessage(), e);
        }
        return null;
    }
    
    public static boolean setStringValue(Object obj, String name, String s)
    {
    	StringBuilder buf = new StringBuilder();
    	buf.append("set");
    	buf.append(name.substring(0, 1).toUpperCase());
    	buf.append(name.substring(1));
    	try
		{
    		Method method = getSetMethod(obj.getClass(), name);
    		if (method != null)
    		{
    			Class<?>[] paramTypes = method.getParameterTypes();
    			if (paramTypes == null || paramTypes.length == 0)
    			{
    				return false;
    			}
    			return setMethodValue(obj, method, paramTypes[0], s);
    		}
		}
		catch (Exception e)
		{
			Kernel.logError(e.getMessage(), e);
		}
    	return false;
    }
    
    public static boolean setMethodValue(Object obj, Method method, Class<?> paramType, String val)
    {
    	try
		{
        	if (int.class.equals(paramType) || Integer.class.equals(paramType))
        	{
        		method.invoke(obj, Integer.parseInt(val));
        	}
        	else if (short.class.equals(paramType) || Short.class.equals(paramType))
        	{
        		method.invoke(obj, Short.parseShort(val));
        	}
        	else if (boolean.class.equals(paramType) || Boolean.class.equals(paramType))
        	{
        		method.invoke(obj, Boolean.parseBoolean(val));
        	}
        	else if (byte.class.equals(paramType) || Byte.class.equals(paramType))
        	{
        		method.invoke(obj, Byte.parseByte(val));
        	}
        	else if (long.class.equals(paramType) || Long.class.equals(paramType))
        	{
        		method.invoke(obj, Long.parseLong(val));
        	}
        	else if (double.class.equals(paramType) || Double.class.equals(paramType))
        	{
        		method.invoke(obj, Double.parseDouble(val));
        	}
        	else if (float.class.equals(paramType) || Float.class.equals(paramType))
        	{
        		method.invoke(obj, Float.parseFloat(val));
        	}
        	else if (char.class.equals(paramType))
        	{
        		method.invoke(obj, val.charAt(0));
        	}
        	else if (String.class.equals(paramType))
        	{
        		method.invoke(obj, val);
        	}
        	else
        	{
        		Class<?> clazz = ClassFinder.findClass(val, DEFAULT_DIRS);
        		if (clazz == null)
        		{
        			throw new ConfigException("Cannot instance object class " + val);
        		}
        		method.invoke(obj, ReflectUtils.newInstance(clazz));
        	}
        	return true;
		}
		catch (Exception e)
		{
			Kernel.logWarn("Fail to set method value. Cause " + e.getMessage());
		}
    	return false;
    }
    
    public static Method getSetMethod(Class<?> clazz, String name)
    {
    	StringBuilder buf = new StringBuilder();
    	buf.append("set");
    	buf.append(name.substring(0, 1).toUpperCase());
    	buf.append(name.substring(1));
    	try
		{
    		String methodName = buf.toString();
    		Method[] methods = clazz.getMethods();
    		for (Method method : methods)
    		{
    			if (method.getName().equals(methodName))
    			{
    				return method;
    			}
    		}
		}
		catch (Exception e)
		{
			Kernel.logError(e.getMessage(), e);
		}
    	return null;
    }
    
    public static Method getSetMethod(Class<?> clazz, String name, Class<?>[] params)
    {
    	StringBuilder buf = new StringBuilder();
    	buf.append("set");
    	buf.append(name.substring(0, 1).toUpperCase());
    	buf.append(name.substring(1));
    	try
		{
        	return clazz.getMethod(buf.toString(), params);
		}
		catch (Exception e)
		{
			Kernel.logError(e.getMessage(), e);
		}
    	return null;
    }
    
    public static Method getGetMethod(Class<?> clazz, String name)
    {
    	StringBuilder buf = new StringBuilder();
    	buf.append("get");
    	buf.append(name.substring(0, 1).toUpperCase());
    	buf.append(name.substring(1));
    	try
		{
        	return clazz.getMethod(buf.toString());
		}
		catch (Exception e)
		{
			Kernel.logError(e.getMessage(), e);
		}
    	return null;
    }
    
    public static Object getGetMethodObject(Object obj, String name)
    {
    	Method method = getGetMethod(obj.getClass(), name);
    	if (method == null)
    	{
    		return null;
    	}
    	try
		{
			return method.invoke(obj);
		}
		catch (Exception e)
		{
			Kernel.logError(e.getMessage(), e);
		}
    	return null;
    }
    
    public static Method getDeepMethod(Class<?> clazz, String methodName, Class<?>[] paramsType)
    {
    	while (!Object.class.equals(clazz))
    	{
        	try
    		{
    			Method method = clazz.getDeclaredMethod(methodName, paramsType);
    			if (method != null)
    			{
    				return method;
    			}
    		}
    		catch (Exception e)
    		{
    			Kernel.logError(e.getMessage(), e);
    		}
        	clazz = clazz.getSuperclass();
    	}
    	return null;
    }
    
    
}
