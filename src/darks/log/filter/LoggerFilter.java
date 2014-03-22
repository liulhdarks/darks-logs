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

import darks.log.LogMessage;

/**
 * Filter will decide whether allow output log message.<br/>
 * Example:
 * <pre>
 * public class CustomFilter extends LoggerFilter
 * {
 *     public int decide(LogMessage msg)
 *     {
 *        ...
 *        return ALLOW;
 *     }
 * }
 * </pre>
 * 
 * LoggerFilter.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class LoggerFilter
{

    public static final int ALLOW = 1;

    public static final int DENY = -1;

    /**
     * Whether use filter setting.If false, the filter will be invalid.
     */
    private boolean accept = false;

    public LoggerFilter()
    {
    }

    /**
     * Filter message
     * 
     * @param msg log message
     * @return ALLOW or DENY
     */
    public int filter(LogMessage msg)
    {
        if (!accept)
        {
            return ALLOW;
        }
        if (decide(msg) == ALLOW)
        {
            return ALLOW;
        }
        return DENY;
    }

    /**
     * Decide log message whether allow output.
     * 
     * @param msg log message
     * @return ALLOW or DENY
     */
    public abstract int decide(LogMessage msg);

    public boolean isAccept()
    {
        return accept;
    }

    public void setAccept(boolean accept)
    {
        this.accept = accept;
    }

}
