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

/**
 * 
 * LoggerBuffer.java
 * @version 1.0.0
 * @author Liu lihua
 */
public final class LoggerBuffer
{

    StringBuilder buf;

    Logger log;

    public LoggerBuffer(Logger log)
    {
        this.log = log;
        buf = new StringBuilder(64);
    }
    
    public LoggerBuffer(Logger log, Object msg)
    {
        this.log = log;
        buf = new StringBuilder(64);
        buf.append(msg);
    }
    
    public void info()
    {
        log.log(Level.INFO, buf.toString());
    }
    
    public void info(Throwable t)
    {
        log.log(Level.INFO, buf.toString(), t);
    }
    
    public void debug()
    {
        log.log(Level.DEBUG, buf.toString());
    }
    
    public void debug(Throwable t)
    {
        log.log(Level.DEBUG, buf.toString(), t);
    }
    
    public void warn()
    {
        log.log(Level.WARN, buf.toString());
    }
    
    public void warn(Throwable t)
    {
        log.log(Level.WARN, buf.toString(), t);
    }
    
    public void error()
    {
        log.log(Level.ERROR, buf.toString());
    }
    
    public void error(Throwable t)
    {
        log.log(Level.ERROR, buf.toString(), t);
    }
    
    public void verbose()
    {
        log.log(Level.VERBOSE, buf.toString());
    }
    
    public void verbose(Throwable t)
    {
        log.log(Level.VERBOSE, buf.toString(), t);
    }
    
    public void trace()
    {
        log.log(Level.TRACE, buf.toString());
    }
    
    public void trace(Throwable t)
    {
        log.log(Level.TRACE, buf.toString(), t);
    }
    
    public void log(Level level)
    {
        log.log(level, buf.toString());
    }
    
    public void log(Level level, Throwable t)
    {
        log.log(level, buf.toString(), t);
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
        buf.append(String.format(format, objs));
        return this;
    }
    
    public LoggerBuffer append(Object obj)
    {
        return append(String.valueOf(obj));
    }

    public LoggerBuffer append(String str)
    {
        buf.append(str);
        return this;
    }
    
    public LoggerBuffer append(StringBuffer sb)
    {
        buf.append(sb);
        return this;
    }

    /**
     */
    public LoggerBuffer append(CharSequence s)
    {
        buf.append(s);
        return this;
    }

    /**
     */
    public LoggerBuffer append(CharSequence s, int start, int end)
    {
        buf.append(s, start, end);
        return this;
    }

    public LoggerBuffer append(char str[])
    {
        buf.append(str);
        return this;
    }

    public LoggerBuffer append(char str[], int offset, int len)
    {
        buf.append(str, offset, len);
        return this;
    }

    public LoggerBuffer append(boolean b)
    {
        buf.append(b);
        return this;
    }

    public LoggerBuffer append(char c)
    {
        buf.append(c);
        return this;
    }

    public LoggerBuffer append(int i)
    {
        buf.append(i);
        return this;
    }

    public LoggerBuffer append(long lng)
    {
        buf.append(lng);
        return this;
    }

    public LoggerBuffer append(float f)
    {
        buf.append(f);
        return this;
    }

    public LoggerBuffer append(double d)
    {
        buf.append(d);
        return this;
    }
}
