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

package darks.log.layout;

import darks.log.LogMessage;
import darks.log.pattern.ConvertPattern;
import darks.log.utils.StringUtils;

/**
 * Indicate that use pattern to format message.
 * 
 * Example:
 * 
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.convertor=DefaultPattern
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 * </pre>
 * 
 * PatternLayout.java
 * 
 * @see ConvertPattern
 * @version 1.0.0
 * @author Liu lihua
 */
public class PatternLayout extends LoggerLayout
{

    public PatternLayout()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogMessage message)
    {
        ConvertPattern cp = getConvertor();
        if (cp == null)
        {
            return message.getMessage() + StringUtils.LINE_RETURN;
        }
        return cp.format(message);
    }
}
