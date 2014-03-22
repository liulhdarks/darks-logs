/**
 * 
 *Copyright 2014 The Darks Logs Project (Liu lihua)
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

package darks.log.kernel;

import java.lang.reflect.Constructor;

import darks.log.utils.ReflectUtils;

/**
 * 
 * Kernel.java
 * @version 1.0.0
 * @author Liu lihua
 */
public final class Kernel
{
    
    private static KernelLogger logger;
    
    static
    {
        if (!checkLogger("android.util.Log"))
        {
            logger = new ConsoleKernelLogger();
        }
    }
    
    private Kernel()
    {
        
    }
    
    public static void logInfo(String msg)
    {
        logger.info(msg);
    }
    
    public static void logInfo(String msg, Throwable e)
    {
        logger.info(msg, e);
    }
    
    public static void logWarn(String msg)
    {
        logger.warn(msg);
    }
    
    public static void logWarn(String msg, Throwable e)
    {
        logger.warn(msg, e);
    }
    
    public static void logDebug(String msg)
    {
        logger.debug(msg);
    }
    
    public static void logDebug(String msg, Throwable e)
    {
        logger.debug(msg, e);
    }
    
    public static void logError(String msg)
    {
        logger.error(msg);
    }
    
    public static void logError(String msg, Throwable e)
    {
        logger.error(msg, e);
    }
    
    private static boolean checkLogger(String className)
    {
        try
        {
            Class<?> clazz = Class.forName(className);
            Constructor<?> cst = ReflectUtils.getConstructor(clazz);
            if (cst == null)
            {
                return false;
            }
            logger = (KernelLogger) cst.newInstance();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        
    }
}
