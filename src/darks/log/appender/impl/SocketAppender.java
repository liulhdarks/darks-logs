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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import darks.log.appender.Appender;
import darks.log.kernel.Kernel;

/**
 * Appender for network. The appender will send log message through TCP protocol.<br>
 * Example:
 * 
 * <pre>
 *  logd.appender.SOCKET=SocketAppender
 *  logd.appender.SOCKET.layout=PatternLayout
 *  logd.appender.SOCKET.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.SOCKET.serverHost=127.0.0.1
 *  logd.appender.SOCKET.serverPort=8686
 *  logd.appender.SOCKET.keepAlive=true
 *  logd.appender.SOCKET.tcpNoDelay=true
 *  logd.appender.SOCKET.wrapBytes=true
 * </pre>
 * 
 * SocketAppender.java
 * 
 * @see StreamAppender
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class SocketAppender extends StreamAppender
{

    /**
     * Remote server host/IP
     */
	private String serverHost;
	
	/**
	 * Remote server port
	 */
	private int serverPort;
	
	/**
	 * Connect timeout
	 */
	private int connectTimeout = 15000;
	
	/**
	 * If true, socket will keep alive
	 */
	private boolean keepAlive = true;
	
	/**
	 * If true, socket's tcpNoDelay will be set to true
	 */
	private boolean tcpNoDelay = true;
	
	/**
	 * Socket send buffer size
	 */
	private int sendBufferSize = 8096;
	
	private Socket socket;
	
	volatile boolean inited = false;
	
	public SocketAppender()
	{
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean activateHandler()
	{
		return connect();
	}
	
	/**
	 * Connect to server
	 */
	public boolean connect()
	{
		if (socket == null || !socket.isConnected())
		{
			try
			{
				inited = false;
				socket = new Socket();
				socket.setKeepAlive(keepAlive);
				socket.setTcpNoDelay(tcpNoDelay);
				socket.setSendBufferSize(sendBufferSize);
				socket.connect(new InetSocketAddress(serverHost, serverPort), connectTimeout);
				initIO();
				inited = true;
			}
			catch (Exception e)
			{
				Kernel.logError("Fail to active socket appender. Cause " + e.getMessage(), e);
			}
		}
		return inited;
	}
	
	private void initIO() throws IOException
	{
		OutputStream out = socket.getOutputStream();
		if (!isImmediateFlush())
		{
			out = new BufferedOutputStream(out);
		}
		setOutStream(out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean needPattern()
	{
		return true;
	}


	public String getServerHost()
	{
		return serverHost;
	}


	public void setServerHost(String serverHost)
	{
		this.serverHost = serverHost;
	}


	public int getServerPort()
	{
		return serverPort;
	}


	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}


	public int getConnectTimeout()
	{
		return connectTimeout;
	}


	public void setConnectTimeout(int connectTimeout)
	{
		this.connectTimeout = connectTimeout;
	}


	public boolean isKeepAlive()
	{
		return keepAlive;
	}


	public void setKeepAlive(boolean keepAlive)
	{
		this.keepAlive = keepAlive;
	}


	public boolean isTcpNoDelay()
	{
		return tcpNoDelay;
	}


	public void setTcpNoDelay(boolean tcpNoDelay)
	{
		this.tcpNoDelay = tcpNoDelay;
	}


	public int getSendBufferSize()
	{
		return sendBufferSize;
	}


	public void setSendBufferSize(int sendBufferSize)
	{
		this.sendBufferSize = sendBufferSize;
	}

	
}
