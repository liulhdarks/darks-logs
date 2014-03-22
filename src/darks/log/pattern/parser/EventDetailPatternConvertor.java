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

package darks.log.pattern.parser;

import darks.log.LogMessage;
import darks.log.ThrowableInfo;

/**
 * Format event detail information
 * 
 * EventDetailPatternConvertor.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class EventDetailPatternConvertor extends PatternConvertor
{

    public EventDetailPatternConvertor()
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean format(StringBuilder buf, LogMessage message)
    {
        ThrowableInfo info = message.getThrowableInfo();
        if (message == null || info == null)
        {
            return false;
        }
        buf.append(info.getCallerClass()).append('.')
                .append(message.getThreadName()).append('(')
                .append(info.getSourceFile()).append(':')
                .append(info.getSource()).append(')');
        return true;
    }

}
