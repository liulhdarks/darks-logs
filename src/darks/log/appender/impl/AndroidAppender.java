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

package darks.log.appender.impl;

import android.util.Log;
import darks.log.Level;
import darks.log.LogMessage;
import darks.log.appender.Appender;
import darks.log.kernel.Kernel;

/**
 * Appender for android. The appender wiil output log message in logcat. <br>
 * Example:
 * 
 * <pre>
 *  logd.appender.console=AndroidAppender
 *  logd.appender.console.layout=PatternLayout
 *  logd.appender.console.layout.convertor=DefaultPattern
 *  logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%f][%p] - %m%n
 * </pre>
 * 
 * AndroidAppender.java
 * 
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class AndroidAppender extends Appender
{

    public AndroidAppender()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(LogMessage msg, String log) throws Exception
    {
        if (log == null)
        {
            Kernel.logError("Android appender log is null");
            return;
        }
        Level level = msg.getLevel();
        Throwable e = msg.getThrowableInfo().getThrowable();
        log = getThrowMessage(log, e);
        if (level.equals(Level.DEBUG))
        {
            Log.d(msg.getNamespace(), log);
        }
        else if (level.equals(Level.ERROR))
        {
            Log.e(msg.getNamespace(), log);
        }
        else if (level.equals(Level.INFO))
        {
            Log.i(msg.getNamespace(), log);
        }
        else if (level.equals(Level.VERBOSE))
        {
            Log.v(msg.getNamespace(), log);
        }
        else if (level.equals(Level.WARN))
        {
            Log.w(msg.getNamespace(), log);
        }
        else if (level.equals(Level.TRACE))
        {
            Log.wtf(msg.getNamespace(), log);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needPattern()
    {
        return true;
    }

}
