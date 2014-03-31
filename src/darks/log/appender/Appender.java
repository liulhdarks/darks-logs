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

import java.io.PrintWriter;
import java.io.StringWriter;

import darks.log.LogMessage;
import darks.log.filter.LoggerFilter;
import darks.log.kernel.Kernel;
import darks.log.layout.LoggerLayout;
import darks.log.utils.IoUtils;
import darks.log.utils.StringUtils;

/**
 * Appender interface for Custom log output. Example:
 * 
 * <pre>
 * public class CustomAppender extends Appender
 * {
 * 
 *     &#064;Override
 *     public void append(LogMessage msg, String log) throws Exception
 *     {
 *  	   ...
 *     }
 * 
 *     &#064;Override
 *     public boolean needPattern()
 *     {
 *         return true;
 *     }
 * 
 * }
 * </pre>
 * 
 * Appender.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class Appender
{

    private String name;

    /**
     * Logger layout can format log message. You can use
     * {@linkplain darks.log.PatternLayout PatternLayout},
     * {@linkplain darks.log.SimpleLayout SimpleLayout} or custom layout
     */
    private LoggerLayout layout;

    /**
     * Logger filter can filter log message such as level.
     */
    private LoggerFilter filter;

    /**
     * If true, indicate that log message will be output in logger thread.
     */
    private boolean async = false;

    private boolean activated = false;

    public Appender()
    {

    }

    public Appender(LoggerLayout layout)
    {
        this.layout = layout;
    }

    private boolean checkActivate()
    {
        if (!activated)
        {
            try
            {
                activated = activateHandler();
            }
            catch (Exception e)
            {
                Kernel.logError("Fail to active appender " + this.getClass()
                        + ". Cause " + e.getMessage());
            }
        }
        return activated;
    }

    /**
     * The method will be called before first of doAppend called.
     */
    public boolean activateHandler()
    {
        return true;
    }

    /**
     * Handle log message
     * 
     * @param msg Log message
     */
    public synchronized void doAppend(LogMessage msg)
    {
        checkActivate();
        if (filter != null && filter.filter(msg) != LoggerFilter.ALLOW)
        {
            return;
        }
        String log = null;
        if (layout != null && needPattern() && layout.getConvertor() != null)
        {
            log = layout.format(msg);
        }
        try
        {
            append(msg, log);
        }
        catch (Exception e)
        {
            Kernel.logError(e.getMessage(), e);
        }
    }

    /**
     * Append log message
     * 
     * @param msg Log message
     * @param log if needPattern() return true, log will be formatted by layout.
     *            Otherwise return LogMessage.message
     * @throws Exception Exception information
     */
    public abstract void append(LogMessage msg, String log) throws Exception;

    /**
     * Whether need format log message string by layout.
     * 
     * @return If need, return true default.
     */
    public abstract boolean needPattern();

    /**
     * Buffer log message string with exception stack trace message target.
     * 
     * @param log Log message string
     * @param e Exception object
     * @return Log message with exception trace.
     */
    public String getThrowMessage(String log, Throwable e)
    {
        if (e != null)
        {
            StringWriter sw = new StringWriter(64);
            sw.append(log);
            if (!log.endsWith(StringUtils.LINE_RETURN))
            {
                sw.append(StringUtils.LINE_RETURN);
            }
            PrintWriter pw = null;
            try
            {
                pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
            }
            finally
            {
                IoUtils.closeIO(pw);
            }
            return sw.toString();
        }
        else
        {
            return log;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LoggerLayout getLayout()
    {
        return layout;
    }

    public void setLayout(LoggerLayout layout)
    {
        this.layout = layout;
    }

    public LoggerFilter getFilter()
    {
        return filter;
    }

    public void setFilter(LoggerFilter filter)
    {
        this.filter = filter;
    }

    public boolean isAsync()
    {
        return async;
    }

    public void setAsync(boolean async)
    {
        this.async = async;
    }

}
