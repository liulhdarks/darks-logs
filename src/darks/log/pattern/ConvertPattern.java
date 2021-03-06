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

/**
 * Indicate to convert message to target pattern format.
 * 
 * ConvertPattern.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public interface ConvertPattern
{

    /**
     * Set pattern string
     * 
     * @param pattern Pattern string
     */
    public boolean setPattern(String pattern);

    /**
     * Format message by pattern
     * 
     * @param message Log message 
     * @return Log message after formated
     */
    public String format(LogMessage message);

}
