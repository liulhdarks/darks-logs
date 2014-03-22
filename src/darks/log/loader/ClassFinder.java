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

package darks.log.loader;

/**
 * Indicate to find class from target directories.
 * 
 * ClassFinder.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class ClassFinder
{

    private ClassFinder()
    {
    }

    /**
     * Find class from target directories.
     * 
     * @param className Class name
     * @param defaultDirs Target directories
     * @return If has found, return class object. Otherwise return null.
     */
    public static Class<?> findClass(String className, String[] defaultDirs)
    {
        for (String dir : defaultDirs)
        {
            Class<?> clazz = findClass(className, dir);
            if (clazz != null)
            {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Find class from target directory.
     * 
     * @param className Class name
     * @param defaultDir Target directory
     * @return If has found, return class object. Otherwise return null.
     */
    public static Class<?> findClass(String className, String defaultDir)
    {
        if (className == null || className.isEmpty())
        {
            return null;
        }
        int index = className.indexOf('.');
        if (index > 0)
        {
            return loadClass(className);
        }
        else
        {
            return loadClass(defaultDir + '.' + className);
        }
    }

    private static Class<?> loadClass(String className)
    {
        try
        {
            return Class.forName(className);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
