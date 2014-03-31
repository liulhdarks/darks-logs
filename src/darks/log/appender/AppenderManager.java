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

package darks.log.appender;

import java.util.HashMap;
import java.util.Map;

import darks.log.kernel.Kernel;

/**
 * Indicate to manage appenders Appender.
 * 
 * Manager.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class AppenderManager
{
    private static volatile Map<String, Appender> appenders;

    static
    {
        appenders = new HashMap<String, Appender>();
    }

    /**
     * Register appender. Appender name as key, appender object as value.
     * 
     * @param appender Appender object
     */
    public static boolean registerAppender(Appender appender)
    {
        if (appender == null || appender.getName() == null
                || "".equals(appender.getName()))
        {
            Kernel.logError("Fail to register appender which is null.");
            return false;
        }
        appenders.put(appender.getName(), appender);
        return true;
    }

    /**
     * Get appender object by appender name
     * 
     * @param name Appender name
     * @return Appender object
     */
    public static Appender getAppender(String name)
    {
        return appenders.get(name);
    }
}
