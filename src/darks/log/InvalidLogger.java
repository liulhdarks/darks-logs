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

package darks.log;

import darks.log.appender.Appender;

/**
 * Indicate log is invalid. Cause invalid config or exception
 * 
 * InvalidLogger.java
 * @version 1.0.0
 * @author Liu lihua
 * 2014-3-21
 */
public class InvalidLogger extends Logger
{

	public InvalidLogger()
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void log(Level level, String msg, Throwable t)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAppender(Appender appender, boolean rooted)
	{
		
	}

}
