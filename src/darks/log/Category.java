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

package darks.log;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import darks.log.appender.Appender;
import darks.log.appender.AppenderManager;
import darks.log.kernel.Kernel;

/**
 * Record logger's category information.Include appenders, level, name and so
 * on. Category object will inherit root category attributes.
 * 
 * Category.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-3-21
 */
public class Category
{
    /**
     * Category name
     */
    private String name;

    /**
     * Parent category
     */
    private Category parent;

    private Level level = Level.VERBOSE;

    /**
     * Appenders tags
     */
    private String[] appenders;

    private Map<String, Appender> appenderMap = new ConcurrentHashMap<String, Appender>();

    private boolean inherit = true;

    public Category()
    {
    }

    public Category(Category parent)
    {
        this.parent = parent;
    }

    /**
     * Build appenders list from appender tags array
     * 
     * @return appenders list
     */
    public synchronized Map<String, Appender> buildAppenderMap()
    {
        if (!appenderMap.isEmpty())
        {
            return appenderMap;
        }
        appenderMap.clear();
        if (appenders != null)
        {
            for (String app : appenders)
            {
                Appender appender = AppenderManager.getAppender(app);
                if (appender == null)
                {
                    Kernel.logWarn("Cannot find appender " + app);
                }
                else
                {
                    appenderMap.put(appender.getName(), appender);
                }
            }
        }
        return appenderMap;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Category getParent()
    {
        return parent;
    }

    public void setParent(Category parent)
    {
        this.parent = parent;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public String[] getAppenders()
    {
        return appenders;
    }

    public void setAppenders(String[] appenders)
    {
        this.appenders = appenders;
    }

    public boolean isInherit()
    {
        return inherit;
    }

    public void setInherit(boolean inherit)
    {
        this.inherit = inherit;
    }

    public Map<String, Appender> getAppenderMap()
    {
        return appenderMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "Category [name=" + name + ", parent=" + parent + ", level="
                + level + ", appenders=" + Arrays.toString(appenders)
                + ", inherit=" + inherit + "]";
    }

}
