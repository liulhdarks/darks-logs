/**
 * 
 *Copyright 2014 The Darks Logs Project
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

package darks.log.pattern;

import darks.log.LogMessage;
import darks.log.kernel.Kernel;
import darks.log.pattern.parser.PatternConvertor;
import darks.log.pattern.parser.PatternParser;

/**
 * Indicate to format message by default pattern.
 * 
 * Note:
 * 
 * <pre>
 *  %n, %N: Output a return character.
 *  %m, %M: Output the log message content.
 *  %d, %D: Output date by format pattern. Such as "%d{yyyy-MM-dd HH:mm:ss}".
 *  %c:     Output the namespace or tags. 
 *          You can use {layer number} to output the namespace's layer specified. 
 *          Such as %c{1}, If tag is "darks.log.DemoMain", it will be "DemoMain".
 *  %C:     Output the class name.
 *          You can use {layer number} to output the classname's layer specified. 
 *          Such as %C{2}, If class name is "darks.log.DemoMain", it will be "log.DemoMain".
 *  %f, %F: Output the source file name.
 *  %L:     Output the source code line.
 *  %l:     Output the event information include caller class, thread name, source file and source line.
 *  %p, %P: Output the log level.
 *  %r, %R: Output the cost time from startup.
 *  %t, %T: Output the thread name.
 * </pre>
 * 
 * Example:
 * 
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.convertor=DefaultPattern
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%f][%p] - %m%n
 * </pre>
 * 
 * DefaultPattern.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class DefaultPattern implements ConvertPattern
{

    private static final String DEFAULT_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} [%f][%p] - %m%n";

    private PatternParser parser;

    private String pattern = DEFAULT_PATTERN;

    private PatternConvertor convertor;

    public DefaultPattern()
    {
        resetDefault();
    }

    public DefaultPattern(String pattern)
    {
        setPattern(pattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogMessage message)
    {
        if (parser == null)
        {
            return null;
        }
        StringBuilder buf = new StringBuilder(1024);
        PatternConvertor cur = convertor;
        while (cur != null)
        {
            if (!cur.format(buf, message))
            {
                Kernel.logError("Log message pattern conversion error.");
                return null;
            }
            cur = cur.getNext();
        }
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setPattern(String pattern)
    {
        this.pattern = pattern;
        parser = new PatternParser(pattern);
        if (!parser.validate())
        {
            resetDefault();
            Kernel.logWarn("Log pattern is invalid.pattern:" + pattern);
            return false;
        }
        convertor = parser.parseConvertor();
        if (convertor == null)
        {
            return false;
        }
        return true;
    }

    private void resetDefault()
    {
        parser = new PatternParser(DEFAULT_PATTERN);
    }

    public String getPattern()
    {
        return pattern;
    }

}
