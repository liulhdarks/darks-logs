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

import java.io.Serializable;
import java.util.Date;

/**
 * Logger message object.
 * 
 * LogMessage.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class LogMessage implements Serializable
{

	private static final long serialVersionUID = 8759561564189181268L;

	private static long startupTime = System.currentTimeMillis();

	/**
	 * Log message date
	 */
	private Date date;

	/**
	 * Log namespace or tag string
	 */
	private String namespace;

	private String className;

	private String threadName;

	private ThrowableInfo throwableInfo;

	private long timeStamp;

	private String message;

	private Level level;

	private Category category;


	public LogMessage()
	{
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getThreadName()
	{
		return threadName;
	}

	public void setThreadName(String threadName)
	{
		this.threadName = threadName;
	}

	public ThrowableInfo getThrowableInfo()
	{
		return throwableInfo;
	}

	public void setThrowableInfo(ThrowableInfo throwableInfo)
	{
		this.throwableInfo = throwableInfo;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public static long getStartupTime()
	{
		return startupTime;
	}

	public Level getLevel()
	{
		return level;
	}

	public void setLevel(Level level)
	{
		this.level = level;
	}

	public String getNamespace()
	{
		return namespace;
	}

	public void setNamespace(String namespace)
	{
		this.namespace = namespace;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "LogMessage [date=" + date + ", namespace=" + namespace
				+ ", className=" + className + ", threadName=" + threadName
				+ ", throwableInfo=" + throwableInfo + ", timeStamp="
				+ timeStamp + ", message=" + message + ", level=" + level
				+ ", category=" + category + "]";
	}

	
}
