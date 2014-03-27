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

import java.util.LinkedList;
import java.util.List;

import darks.log.Level;
import darks.log.LogMessage;

/**
 * Level match filter will output log which level has been contained by levels.
 * <br>
 * Example:
 * <pre>
 *  logd.appender.console=ConsoleAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.console.filter=LevelMatchFilter
 *  #It will only output the message which level is debug or info.
 *  logd.appender.console.filter.levels=debug,info
 *  logd.appender.console.filter.accept=true
 * </pre>
 * LevelMatchFilter.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class LevelMatchFilter extends LoggerFilter
{

    /**
     * Target levels.
     */
    private Level[] levels;

    public LevelMatchFilter()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int decide(LogMessage msg)
    {
        if (levels == null || levels.length == 0)
        {
            return LoggerFilter.ALLOW;
        }
        for (Level level : levels)
        {
            if (level.equals(msg.getLevel()))
            {
                return LoggerFilter.ALLOW;
            }
        }
        return LoggerFilter.DENY;
    }

    public Level[] getLevels()
    {
        return levels;
    }

    public void setLevels(String strLevel)
    {
        if (strLevel == null || "".equals(strLevel.trim()))
        {
            return;
        }
        strLevel = strLevel.trim();
        String[] args = strLevel.split(",");
        if (args.length == 0)
        {
            return;
        }
        int len = args.length;
        List<Level> list = new LinkedList<Level>();
        for (int i = 0; i < len; i++)
        {
            Level lv = Level.getLevel(args[i].trim());
            if (lv != null)
            {
                list.add(lv);
            }
        }
        levels = new Level[list.size()];
        list.toArray(levels);
    }

    
}
