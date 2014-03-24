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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import darks.log.LogMessage;
import darks.log.appender.Appender;
import darks.log.layout.LoggerLayout;
import darks.log.utils.IoUtils;

/**
 * Appender for stream. The appender will output log message to stream. You
 * cannot use StreamAppender directly in logd.properties.<br>
 * Example:
 * 
 * <pre>
 * public class CustomAppender extends StreamAppender
 * {
 *     &#064;Override
 *      public void activateHandler()
 *      {
 *          ...
 *      }
 * 
 *     &#064;Override
 *      protected void expandAppend(LogMessage msg, String log) throws Exception
 *      {
 *          ...
 *      }
 * }
 * </pre>
 * 
 * StreamAppender.java
 * 
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class StreamAppender extends Appender
{

    /**
     * Write log string to stream
     */
    public static final int TYPE_LOG_STRING = 0;

    public static final String STYPE_LOG_STRING = "string";

    /**
     * Write log message object with pattern if you have setted 'needPattern'
     */
    public static final int TYPE_LOG_OBJECT = 1;

    public static final String STYPE_LOG_OBJECT = "object";

    /**
     * Head reserve bytes
     */
    private static final int BUF_SIZE_RESERVE = 6;

    /**
     * Whether flush stream immediately
     */
    boolean immediateFlush = false;

    /**
     * Whether wrap log message with head bytes.
     */
    boolean wrapBytes = false;

    /**
     * Meesage output type. Such as TYPE_LOG_STRING and TYPE_LOG_OBJECT.
     */
    int msgType = TYPE_LOG_STRING;

    /**
     * Encoding character
     */
    String encoding;

    /**
     * Bytes stream use LITTLE-ENDIAN
     */
    boolean littleEndian = false;

    static byte[] headIdentify = new byte[] { (byte) 0xFB, (byte) 0xFA };

    OutputStream outStream;

    public StreamAppender()
    {
    }

    public StreamAppender(LoggerLayout layout)
    {
        super(layout);
    }

    public StreamAppender(OutputStream outStream)
    {
        this.outStream = outStream;
    }

    public StreamAppender(LoggerLayout layout, OutputStream outStream)
    {
        super(layout);
        this.outStream = outStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(LogMessage msg, String log) throws Exception
    {
        if (outStream == null)
        {
            return;
        }

        byte[] data = null;
        switch (msgType)
        {
        case TYPE_LOG_OBJECT:
            data = IoUtils.getObjectBytes(msg);
            break;
        default:
            String logFully = getThrowMessage(log, msg.getThrowableInfo().getThrowable());
            if (encoding == null || "".equals(encoding.trim()))
            {
                data = logFully.getBytes();
            }
            else
            {
                data = logFully.getBytes(encoding);
            }
            
            break;
        }
        if (wrapBytes)
        {
            data = wrapMessage(data);
        }
        outStream.write(data);
        if (isImmediateFlush())
        {
            outStream.flush();
        }
        expandAppend(msg, log);
    }

    /**
     * Expand handler after append
     * 
     * @param msg Log message object
     * @param log Log string
     * @throws Exception Exception information
     */
    protected void expandAppend(LogMessage msg, String log) throws Exception
    {

    }

    /**
     * Wrap message with head bytes and message total length.
     * 
     * @param bytes Bytes array
     * @return Bytes array
     * @throws IOException IO exception object
     */
    private byte[] wrapMessage(byte[] bytes) throws IOException
    {
        if (bytes == null)
        {
            return null;
        }
        int length = bytes.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(length
                + BUF_SIZE_RESERVE);
        DataOutputStream dos = null;
        try
        {
            dos = new DataOutputStream(baos);
            dos.write(headIdentify);
            writeIntByEndian(dos, length);
            dos.write(bytes);
            dos.flush();
        }
        finally
        {
            IoUtils.closeIO(dos);
        }
        return baos.toByteArray();
    }

    private void writeIntByEndian(DataOutputStream out, int v)
            throws IOException
    {
        if (littleEndian)
        {
            out.write((v >>> 0) & 0xFF);
            out.write((v >>> 8) & 0xFF);
            out.write((v >>> 16) & 0xFF);
            out.write((v >>> 24) & 0xFF);
        }
        else
        {
            out.write((v >>> 24) & 0xFF);
            out.write((v >>> 16) & 0xFF);
            out.write((v >>> 8) & 0xFF);
            out.write((v >>> 0) & 0xFF);
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

    public boolean isImmediateFlush()
    {
        return immediateFlush;
    }

    public void setImmediateFlush(boolean immediateFlush)
    {
        this.immediateFlush = immediateFlush;
    }

    public boolean isWrapBytes()
    {
        return wrapBytes;
    }

    public void setWrapBytes(boolean wrapBytes)
    {
        this.wrapBytes = wrapBytes;
    }

    public int getMsgType()
    {
        return msgType;
    }

    public void setMsgType(String strMsgType)
    {
        if (strMsgType != null && !strMsgType.trim().isEmpty())
        {
            strMsgType = strMsgType.trim();
            if (STYPE_LOG_STRING.equalsIgnoreCase(strMsgType))
            {
                msgType = TYPE_LOG_STRING;
            }
            else if (STYPE_LOG_OBJECT.equalsIgnoreCase(strMsgType))
            {
                msgType = TYPE_LOG_OBJECT;
            }
        }
    }

    public void closeStream()
    {
        IoUtils.closeIO(outStream);
        outStream = null;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public boolean isLittleEndian()
    {
        return littleEndian;
    }

    public void setLittleEndian(boolean littleEndian)
    {
        this.littleEndian = littleEndian;
    }

    public void setOutStream(OutputStream outStream)
    {
        if (this.outStream != null)
        {
            IoUtils.closeIO(this.outStream);
            this.outStream = null;
        }
        this.outStream = outStream;
    }

    public OutputStream getOutStream()
    {
        return outStream;
    }

}
