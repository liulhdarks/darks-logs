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
 * If you want to catch android crash log automatically, you can register crash
 * handler. Parameter callback can be called after ANR happened.<br>
 * Example:
 * 
 * <pre>
 *  Logger.Android.setApplication(this, true);
 * </pre>
 * 
 * Or
 * 
 * <pre>
 *  Logger.Android.setApplication(this);
 *  Logger.Android.registerCrashHandler();
 * </pre>
 * 
 * Or
 * 
 * <pre>
 *  Logger.Android.setApplication(this);
 *  Logger.Android.registerCrashHandler(new CustomCrashCallBack());
 * </pre>
 * 
 * If you want to buffer, append or format log message, you can use
 * LoggerBuffer. Example:
 * 
 * <pre>
 * static Logger log = Logger.getLogger(&quot;demo&quot;);
 * log.append(&quot;darks&quot;).append('-').append(&quot;logs&quot;).info();
 * log.append(2014).append(1).append(1).debug(e);
 * log.buffer(2014, &quot; is&quot;, &quot; a good year&quot;).debug();
 * log.format(&quot;darks-logs was created in %d by %s&quot;, 2014, &quot;Liu Lihua&quot;).error(e);
 * </pre>
 * 
 * Logger.java
 * 
 * @version 1.0.2
 * @author Liu lihua
 */
public abstract class Logger
{

    /**
     * Android configuration
     */
    public static AndroidConfig Android;

    /**
     * Logger configuration
     */
    public static LoggerConfig Config = new LoggerConfig();

    static
    {
        if (EnvUtils.isAndroidEnv())
        {
            Android = new AndroidConfig();
        }
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
    public void info(Object msg)
    {
        log(Level.INFO, msg);
    }

    /**
     * Output log message and exception stack information by level INFO.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void info(Object msg, Throwable t)
    {
        log(Level.INFO, msg, t);
    }

    /**
     * Output log message by level DEBUG.
     * 
     * @param msg Log message
     */
    public void debug(Object msg)
    {
        log(Level.DEBUG, msg);
    }

    /**
     * Output log message and exception stack information by level DEBUG.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void debug(Object msg, Throwable t)
    {
        log(Level.DEBUG, msg, t);
    }

    /**
     * Output log message by level WARN.
     * 
     * @param msg Log message
     */
    public void warn(Object msg)
    {
        log(Level.WARN, msg);
    }

    /**
     * Output log message and exception stack information by level WARN.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void warn(Object msg, Throwable t)
    {
        log(Level.WARN, msg, t);
    }

    /**
     * Output log message by level ERROR.
     * 
     * @param msg Log message
     */
    public void error(Object msg)
    {
        log(Level.ERROR, msg);
    }

    /**
     * Output log message and exception stack information by level ERROR.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void error(Object msg, Throwable t)
    {
        log(Level.ERROR, msg, t);
    }

    /**
     * Output log message by level TRACE.
     * 
     * @param msg Log message
     */
    public void trace(Object msg)
    {
        log(Level.TRACE, msg);
    }

    /**
     * Output log message and exception stack information by level TRACE.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void trace(Object msg, Throwable t)
    {
        log(Level.TRACE, msg, t);
    }

    /**
     * Output log message by level VERBOSE.
     * 
     * @param msg Log message
     */
    public void verbose(Object msg)
    {
        log(Level.VERBOSE, msg);
    }

    /**
     * Output log message and exception stack information by level VERBOSE.
     * 
     * @param msg Log message
     * @param t Throwable object
     */
    public void verbose(Object msg, Throwable t)
    {
        log(Level.VERBOSE, msg, t);
    }

    /**
     * Append log message by string buffer.
     * 
     * @return Logger string buffer
     */
    public LoggerBuffer append()
    {
        return new LoggerBuffer(this);
    }

    /**
     * Append log message by string buffer.
     * 
     * @param msg Log message
     * @return Logger string buffer
     */
    public LoggerBuffer append(Object msg)
    {
        return new LoggerBuffer(this, msg);
    }

    /**
     * Buffer log message string.
     * 
     * @param msgs Message arrays
     * @return Logger buffer
     */
    public LoggerBuffer buffer(Object... msgs)
    {
        LoggerBuffer buf = new LoggerBuffer(this);
        for (Object msg : msgs)
        {
            buf.append(msg);
        }
        return buf;
    }

    /**
     * Format log message by pattern. It call String.format method to format log
     * message.
     * 
     * @param format Format string.
     * @param objs
     * @return Logger string buffer
     * @see java.util.Formatter
     */
    public LoggerBuffer format(String format, Object... objs)
    {
        return new LoggerBuffer(this).format(format, objs);
    }

    /**
     * Output log message by target level.
     * 
     * @param level Level object
     * @param msg Log message
     */
    public void log(Level level, Object msg)
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
    public abstract void log(Level level, Object msg, Throwable t);

    /**
     * Add appender object
     * 
     * @param appender Appender object
     */
    public abstract void addAppender(Appender appender);

    /**
     * Check if debug mode enabled.
     * 
     * @return If debug mode enabled, return true.
     */
    public boolean isDebugEnabled()
    {
        return false;
    }

    /**
     * Check if info mode enabled.
     * 
     * @return If info mode enabled, return true.
     */
    public boolean isInfoEnabled()
    {
        return false;
    }

    /**
     * Check if level's mode enabled.
     * 
     * @param level Target level
     * @return If level's mode enabled, return true.
     */
    public boolean isLevelEnabled(Level level)
    {
        return false;
    }

    /**
     * If parameter inheritRoot is true, children logger won't inherit root
     * logger.
     * 
     * @param inheritRoot Whether inherit root logger.
     */
    public void setInherit(boolean inheritRoot)
    {
    }

    /**
     * Get root logger
     * 
     * @return Logger object
     */
    public static Logger getRootLogger()
    {
        return LoggerFactory.getRootLogger();
    }
}
