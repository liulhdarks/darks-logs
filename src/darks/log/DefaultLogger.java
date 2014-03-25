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

import java.util.Date;

import darks.log.appender.Appender;

/**
 * Default logger object used to log message and do appenders by default
 * 
 * DefaultLogger.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-3-21
 */
public class DefaultLogger extends Logger
{

    private Category category;

    private String tag;

    /**
     * logger thread for async appender
     */
    private static volatile LoggerThread thread = new LoggerThread();

    private static Object mutex = new Object();

    public DefaultLogger(Category category, String tag)
    {
        this.category = category;
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Level level, Object msg, Throwable t)
    {
        if (category.getLevel().compare(level) > 0)
        {
            return;
        }
        StackTraceElement el = null;
        if (t == null)
        {
            el = new Throwable().getStackTrace()[3];
        }
        else
        {
            el = t.getStackTrace()[1];
        }
        ThrowableInfo info = new ThrowableInfo(el, t);
        LogMessage logMsg = buildMessage(level, msg.toString(), info);
        doLogger(logMsg);
    }

    /**
     * Dispacher log message for doing appenders directly or keep with holder
     * for log thread
     * 
     * @param logMsg log message
     */
    private void doLogger(LogMessage logMsg)
    {
        LoggerHolder holder = null;
        Category cate = category;
        while (cate != null)
        {
            for (Appender appender : cate.getAppenderList())
            {
                if (appender.isAsync())
                {
                    if (holder == null)
                    {
                        holder = new LoggerHolder(logMsg);
                    }
                    holder.addAppender(appender);
                }
                else
                {
                    appender.doAppend(logMsg);
                }
            }
            if (cate.isInherit())
            {
                cate = cate.getParent();
            }
            else
            {
                break;
            }
        }
        if (holder != null && !holder.isEmpty())
        {
            if (thread == null || !thread.isAlive())
            {
                synchronized (mutex)
                {
                    if (thread == null || !thread.isAlive())
                    {
                        thread = new LoggerThread();
                        thread.start();
                    }
                }
            }
            LoggerThread.getHolders().offer(holder);
        }
    }

    private LogMessage buildMessage(Level level, String msg, ThrowableInfo info)
    {
        LogMessage logMsg = new LogMessage();
        logMsg.setCategory(category);
        logMsg.setClassName(info.getCallerClass());
        logMsg.setDate(new Date());
        logMsg.setLevel(level);
        logMsg.setMessage(msg);
        logMsg.setNamespace(tag);
        logMsg.setThreadName(Thread.currentThread().getName());
        logMsg.setThrowableInfo(info);
        logMsg.setTimeStamp(System.currentTimeMillis());
        return logMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAppender(Appender appender, boolean rooted)
    {
        if (rooted)
        {
            Category cate = category;
            while (cate.getParent() != null)
            {
                cate = cate.getParent();
            }
            synchronized (cate)
            {
                cate.getAppenderList().add(appender);
            }
        }
        else
        {
            category.getAppenderList().add(appender);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled()
    {
        return category != null && category.getLevel().equals(Level.DEBUG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled()
    {
        return category != null && category.getLevel().equals(Level.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLevelEnabled(Level level)
    {
        return category != null && category.getLevel().equals(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInherit(boolean inheritRoot)
    {
        if (category != null)
        {
            category.setInherit(inheritRoot);
        }
    }

    
}
