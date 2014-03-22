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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import darks.log.appender.Appender;
import darks.log.kernel.Kernel;

/**
 * Logger thread is used to process async appenders.
 * 
 * LoggerThread.java
 * @version 1.0.0
 * @author Liu lihua
 * 2014-3-21
 */
public class LoggerThread extends Thread
{

	private static Queue<LoggerHolder> holders = new ConcurrentLinkedQueue<LoggerHolder>();
	
	private static final int SLEEP_DELAY_COUNT = 256;
	
	public LoggerThread()
	{
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run()
	{
		try
		{
			while (!isInterrupted())
			{
				Thread.sleep(100);
				LoggerHolder holder = null;
				int count = 0;
				while ((holder = holders.poll()) != null)
				{
					for (Appender appender : holder.getAppenders())
					{
						appender.doAppend(holder.getMsg());
					}
					count++;
					if (count % SLEEP_DELAY_COUNT == 0)
					{
						Thread.sleep(100);
					}
				}
			}
		}
		catch (Exception e)
		{
			Kernel.logError("Logger thread shutdown. Cause " + e.getMessage(), e);
		}
	}

	public static Queue<LoggerHolder> getHolders()
	{
		return holders;
	}
	
	
}
