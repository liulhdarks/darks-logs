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
import darks.log.utils.StringUtils;

/**
 * Indicate that it will output the simple message. Such as "level - message"
 * 
 * Example:
 * 
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=SimpleLayout
 * </pre>
 * 
 * SimpleLayout.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class SimpleLayout extends LoggerLayout
{

    public SimpleLayout()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogMessage message)
    {
        StringBuffer sbuf = new StringBuffer(128);
        sbuf.setLength(0);
        sbuf.append(message.getLevel().getName());
        sbuf.append(" - ");
        sbuf.append(message.getMessage());
        sbuf.append(StringUtils.LINE_RETURN);
        return sbuf.toString();
    }

}
