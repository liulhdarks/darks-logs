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

package darks.log.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import darks.log.kernel.Kernel;

/**
 * 
 * JavaDateFormater.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class JavaDateFormater extends DateFormater
{
    java.text.DateFormat df;
    
    public JavaDateFormater(String pattern)
    {
        super(pattern);
        try
		{
            df = new SimpleDateFormat(pattern);
		}
		catch (Exception e)
		{
			Kernel.logWarn("Fail to create date formater. Cause " + e.getMessage());
			df = null;
		}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String format(Date date)
    {
    	if (df != null)
    	{
            return df.format(date);
    	}
    	else
    	{
    		return "";
    	}
    }
}
