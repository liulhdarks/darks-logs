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

import java.util.Map.Entry;

import darks.log.kernel.Kernel;
import darks.log.loader.ConfigLoader;

/**
 * Logger factory is used to create logger object. Recommend you to create
 * logger object by {@linkplain darks.log.Logger Logger} instead of
 * LoggerFactory. Example:
 * 
 * <pre>
 * static Logger log = LoggerFactory.getLogger(&quot;demo&quot;);
 * </pre>
 * 
 * LoggerFactory.java
 * 
 * @see Logger
 * @version 1.0.1
 * @author Liu lihua
 */
public final class LoggerFactory
{

    static ConfigLoader loader;

    static volatile boolean inited = false;

    static Logger rootLogger;

    static
    {
        loader = new ConfigLoader();
        try
        {
            inited = loader.initConfig();
        }
        catch (Exception e)
        {
            Kernel.logWarn(e.getMessage());
            inited = false;
        }
        rootLogger = createRootLogger();
    }

    private LoggerFactory()
    {
    }

    /**
     * Create logger object. You can use {@linkplain darks.log.Logger Logger}
     * instead of it.
     * 
     * @param tag Tag string
     * @return If succeed to be initialized, return
     *         {@linkplain darks.log.DefaultLogger DefaultLogger}. Otherwise
     *         return {@linkplain darks.log.InvalidLogger InvalidLogger}.
     */
    public static synchronized Logger getLogger(String tag)
    {
        if (!inited)
        {
            return new InvalidLogger();
        }
        LoggerConfig cfg = Logger.Config;
        Category category = getCategory(cfg, tag);
        category.setInherit(cfg.getInherit(category.getName()));
        category.buildAppenderArray();
        return new DefaultLogger(category, tag);
    }

    /**
     * Get root logger
     * 
     * @return Logger object
     */
    public static Logger getRootLogger()
    {
        return rootLogger;
    }

    private static Logger createRootLogger()
    {
        return new DefaultLogger(Logger.Config.getRoot(), "");
    }

    /**
     * Find category object from config object by tag string.
     * 
     * @param cfg Configuration object
     * @param tag Tag string
     * @return Category object
     */
    private static Category getCategory(LoggerConfig cfg, String tag)
    {
        Category category = cfg.getCategory(tag);
        if (category == null)
        {
            category = deepFindCategory(cfg, tag);
        }
        if (category == null)
        {
            return cfg.getRoot();
        }
        return category;
    }

    /**
     * The depth of search category by tag string
     * 
     * @param cfg Configuration object
     * @param tag Tag string
     * @return Category object
     */
    private static Category deepFindCategory(LoggerConfig cfg, String tag)
    {
        Category match = null;
        String maxKey = null;
        for (Entry<String, Category> entry : cfg.getCategories().entrySet())
        {
            String key = entry.getKey();
            if (tag.startsWith(key))
            {
                if (maxKey == null || maxKey.length() < key.length())
                {
                    maxKey = key;
                    match = entry.getValue();
                }
            }
        }
        return match;
    }
}
