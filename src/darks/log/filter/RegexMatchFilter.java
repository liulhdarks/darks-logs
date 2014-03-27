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

package darks.log.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import darks.log.LogMessage;
import darks.log.kernel.Kernel;

/**
 * Regex match filter will output log which messages match regex pattern.
 * <br>
 * Example:
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.console.filter=RegexMatchFilter
 *  logd.appender.console.filter.pattern=darks\d+
 *  logd.appender.console.filter.accept=true
 * </pre>
 * RegexMatchFilter.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class RegexMatchFilter extends LoggerFilter
{

    private Pattern regex;
    /**
     * Minimum level allowed
     */
    private String pattern;

    public RegexMatchFilter()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int decide(LogMessage msg)
    {
        if (regex == null)
        {
            return LoggerFilter.ALLOW;
        }
        Matcher matcher = regex.matcher(msg.getMessage());
        if (matcher.find())
        {
            return LoggerFilter.ALLOW;
        }
        return LoggerFilter.DENY;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
        if (pattern != null && !"".equals(pattern))
        {
            try
            {
                regex = Pattern.compile(pattern);
            }
            catch (PatternSyntaxException e)
            {
                Kernel.logError("Regex match filter's syntax is error. Cause " + e.getMessage());
            }
        }
    }
    
    
}
