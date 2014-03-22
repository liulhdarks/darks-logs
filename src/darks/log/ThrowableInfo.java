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

/**
 * Log message throwable information.
 * 
 * ThrowableInfo.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class ThrowableInfo implements Serializable
{

	private static final long serialVersionUID = -273979220631210906L;

	/**
	 * Exception object
	 */
	private Throwable throwable;

	/**
	 * Source line when logging
	 */
	private int source;

	/**
	 * Method which be logging in
	 */
	private String callerMethod;

	/**
	 * Class which be logging in
	 */
	private String callerClass;

	/**
	 * Source file when logging
	 */
	private String sourceFile;

	public ThrowableInfo()
	{

	}

	public ThrowableInfo(StackTraceElement el, Throwable t)
	{
		buildThrowableInfo(el, t);
	}

	/**
	 * Build throwable information by exception object.
	 * 
	 * @param el Stack trace element
	 * @param t Throwable object
	 */
	public void buildThrowableInfo(StackTraceElement el, Throwable t)
	{
		setCallerClass(el.getClassName());
		setCallerMethod(el.getMethodName());
		setSource(el.getLineNumber());
		setSourceFile(el.getFileName());
		setThrowable(t);
	}

	public Throwable getThrowable()
	{
		return throwable;
	}

	public void setThrowable(Throwable throwable)
	{
		this.throwable = throwable;
	}

	public int getSource()
	{
		return source;
	}

	public void setSource(int source)
	{
		this.source = source;
	}

	public String getCallerMethod()
	{
		return callerMethod;
	}

	public void setCallerMethod(String callerMethod)
	{
		this.callerMethod = callerMethod;
	}

	public String getCallerClass()
	{
		return callerClass;
	}

	public void setCallerClass(String callerClass)
	{
		this.callerClass = callerClass;
	}

	public String getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile(String sourceFile)
	{
		this.sourceFile = sourceFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "ThrowableInfo [throwable=" + throwable + ", source=" + source
				+ ", callerMethod=" + callerMethod + ", callerClass="
				+ callerClass + ", sourceFile=" + sourceFile + "]";
	}

	
}
