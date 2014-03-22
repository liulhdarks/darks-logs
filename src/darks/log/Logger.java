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

import darks.log.appender.Appender;
import darks.log.utils.EnvUtils;

/**
 * Main logger class for developer
 * 
 * Create a logger object by class object or tag string. Example:
 * 
 * <pre>
 * static final Logger log = Logger.getLogger(&quot;demo&quot;);
 * 
 * static final Logger log = Logger.getLogger(&quot;darks.tag.demo&quot;);
 * 
 * static final Logger log = Logger.getLogger(Demo.class);
 * </pre>
 * 
 * For using Logger before, you must configure logd.properties, which must be
 * put in Java/src, Android/assets and other classpath which can be found. <br>
 * Example:
 * 
 * <pre>
 *  logd.root=info,console
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.convertor=DefaultPattern
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 * </pre>
 * 
 * If you want to use logger in android environment, you must configure android
 * application for finding configuration file. In order to ensure the setting
 * before logger used, Recommend you to configure it in android
 * {@linkplain android.app.Application Application} onCreate method. <br>
 * Example:
 * 
 * <pre>
 * 	Logger.Android.setApplication(...);
 * </pre>
 * 
 * Logger.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-3-21
 */
public abstract class Logger
{

    /**
     * Android configration
     */
    public static AndroidConfig Android;

    private static LoggerConfig config;

    static
    {
        if (EnvUtils.isAndroidEnv())
        {
            Android = new AndroidConfig();
        }
        config = new LoggerConfig();
    }

    /**
     * Get the logger by tag class. Example:
     * 
     * <pre>
     * static Logger log = Logger.getLogger(Demo.class);
     * </pre>
     * 
     * @param clazz Tag class
     * @return Logger object
     */
    public static Logger getLogger(Class<?> clazz)
    {
        return getLogger(clazz.getName());
    }

    /**
     * Get the logger by tag string. Example:
     * 
     * <pre>
     * static Logger log = Logger.getLogger(&quot;demo&quot;);
     * </pre>
     * 
     * @param tag Tag string
     * @return Logger object
     */
    public static Logger getLogger(String tag)
    {
        return LoggerFactory.getLogger(tag);
    }

    /**
     * Output log message by level INFO.
     * 
     * @param msg log message
     */
    public void info(String msg)
    {
        log(Level.INFO, msg);
    }

    /**
     * Output log message and exception stack information by level INFO.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void info(String msg, Throwable t)
    {
        log(Level.INFO, msg, t);
    }

    /**
     * Output log message by level DEBUG.
     * 
     * @param msg Log message
     */
    public void debug(String msg)
    {
        log(Level.DEBUG, msg);
    }

    /**
     * Output log message and exception stack information by level DEBUG.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void debug(String msg, Throwable t)
    {
        log(Level.DEBUG, msg, t);
    }

    /**
     * Output log message by level WARN.
     * 
     * @param msg Log message
     */
    public void warn(String msg)
    {
        log(Level.WARN, msg);
    }

    /**
     * Output log message and exception stack information by level WARN.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void warn(String msg, Throwable t)
    {
        log(Level.WARN, msg, t);
    }

    /**
     * Output log message by level ERROR.
     * 
     * @param msg Log message
     */
    public void error(String msg)
    {
        log(Level.ERROR, msg);
    }

    /**
     * Output log message and exception stack information by level ERROR.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void error(String msg, Throwable t)
    {
        log(Level.ERROR, msg, t);
    }

    /**
     * Output log message by level TRACE.
     * 
     * @param msg Log message
     */
    public void trace(String msg)
    {
        log(Level.TRACE, msg);
    }

    /**
     * Output log message and exception stack information by level TRACE.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void trace(String msg, Throwable t)
    {
        log(Level.TRACE, msg, t);
    }

    /**
     * Output log message by level VERBOSE.
     * 
     * @param msg Log message
     */
    public void verbose(String msg)
    {
        log(Level.VERBOSE, msg);
    }

    /**
     * Output log message and exception stack information by level VERBOSE.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void verbose(String msg, Throwable t)
    {
        log(Level.VERBOSE, msg, t);
    }

    /**
     * Output log message by target level.
     * 
     * @param level Level object
     * @param msg Log message
     */
    public void log(Level level, String msg)
    {
        log(level, msg, null);
    }

    /**
     * Output log message and exception stack information by target level.
     * 
     * @param level Level object
     * @param msg Log message
     * @param t Throwable object
     */
    public abstract void log(Level level, String msg, Throwable t);

    /**
     * Add appender object
     * 
     * @param appender Appender object
     * @param rooted If true, appender will be added to root logger. otherwise
     *            be added to current logger.
     */
    public abstract void addAppender(Appender appender, boolean rooted);

    public static LoggerConfig getConfig()
    {
        return config;
    }

}
