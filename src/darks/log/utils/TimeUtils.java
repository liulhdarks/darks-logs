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

package darks.log.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import darks.log.utils.time.AndroidDateFormater;
import darks.log.utils.time.DateFormater;
import darks.log.utils.time.JavaDateFormater;



/**
 * TimeUtils.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class TimeUtils
{
    private static volatile boolean androidFlag = false;
    
    private static ConcurrentMap<String, DateFormater> formatter;
    
    static
    {
    	androidFlag = EnvUtils.isAndroidEnv();
        formatter = new ConcurrentHashMap<String, DateFormater>();
    }
    
    public static DateFormater getFormatter(String pattern)
    {
    	DateFormater df = formatter.get(pattern);
    	if (df != null)
    	{
    		return df;
    	}
    	synchronized (pattern)
		{
            if (androidFlag)
            {
            	df = new AndroidDateFormater(pattern);
            }
            else
            {
            	df = new JavaDateFormater(pattern);
            }
            formatter.put(pattern, df);
		}
        return df;
    }

}