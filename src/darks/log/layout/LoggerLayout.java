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
import darks.log.pattern.DefaultPattern;

/**
 * Logger layout.You can use it to format message or custom how to output
 * message.
 * 
 * Example:
 * 
 * <pre>
 * public class CustomLayout extends LoggerLayout
 * {
 *     public String format(LogMessage message)
 *      {
 *          ...
 *      }
 * }
 * </pre>
 * 
 * LoggerLayout.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class LoggerLayout
{

    private ConvertPattern convertor = new DefaultPattern();

    private String pattern;

    public LoggerLayout()
    {
    }

    /**
     * Format log message with convertor
     * 
     * @param message log message
     * @return log message after converted
     */
    public abstract String format(LogMessage message);

    public ConvertPattern getConvertor()
    {
        return convertor;
    }

    public void setConvertor(ConvertPattern convertor)
    {
        this.convertor = convertor;
        if (pattern != null)
        {
            convertor.setPattern(pattern);
        }
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
        convertor.setPattern(pattern);
    }

}
