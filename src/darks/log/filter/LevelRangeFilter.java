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

import darks.log.Level;
import darks.log.LogMessage;

/**
 * Level range filter will output log which level between levelMin and levelMax.
 * <br>
 * Example:
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.console.filter=LevelRangeFilter
 *  logd.appender.console.filter.levelMin=debug
 *  logd.appender.console.filter.levelMax=info
 *  logd.appender.console.filter.accept=true
 * </pre>
 * LevelRangeFilter.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class LevelRangeFilter extends LoggerFilter
{

    /**
     * Minimum level allowed
     */
    private Level levelMin;

    /**
     * Maximum level allowed
     */
    private Level levelMax;

    public LevelRangeFilter()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int decide(LogMessage msg)
    {
        if (levelMin == null || levelMax == null)
        {
            return LoggerFilter.ALLOW;
        }
        Level lv = msg.getLevel();
        if (lv.compare(levelMin) >= 0 && lv.compare(levelMax) <= 0)
        {
            return LoggerFilter.ALLOW;
        }
        return LoggerFilter.DENY;
    }

    public Level getLevelMin()
    {
        return levelMin;
    }

    public void setLevelMin(String levelMin)
    {
        this.levelMin = Level.getLevel(levelMin);
    }

    public Level getLevelMax()
    {
        return levelMax;
    }

    public void setLevelMax(String levelMax)
    {
        this.levelMax = Level.getLevel(levelMax);
    }

}
