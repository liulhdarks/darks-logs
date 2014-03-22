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

/**
 * Indicate to convert message by pattern
 * 
 * PatternConvertor.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class PatternConvertor
{

    private PatternConvertor next;

    private String token;

    public PatternConvertor()
    {

    }

    /**
     * Format message content
     * 
     * @param buf Message buffer
     * @param message Log message
     * @return If succeed to format, return true.
     */
    public abstract boolean format(StringBuilder buf, LogMessage message);

    public PatternConvertor getNext()
    {
        return next;
    }

    public void setNext(PatternConvertor next)
    {
        this.next = next;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

}
