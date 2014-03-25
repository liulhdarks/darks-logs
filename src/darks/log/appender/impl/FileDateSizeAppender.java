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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import darks.log.LogMessage;
import darks.log.appender.Appender;
import darks.log.appender.io.RecordOutputStream;
import darks.log.kernel.Kernel;
import darks.log.layout.LoggerLayout;

/**
 * Appender for file. The appender can keep log files in custom days(Default 7
 * days). If current log file's size is out of custom max size(Default 10MB), it
 * will create the new one. <br>
 * Example:
 * 
 * <pre>
 *  logd.appender.FILE=FileDateSizeAppender
 *  logd.appender.FILE.layout=PatternLayout
 *  logd.appender.FILE.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.FILE.fileName=${user.dir}/log_${Dyyyy_MM_dd_HH_mm_ss_SS}.txt
 *  logd.appender.FILE.buffered=true
 *  logd.appender.FILE.maxSize=10485760
 *  logd.appender.FILE.keepDay=7
 * </pre>
 * 
 * FileDateSizeAppender.java
 * 
 * @see FileAppender
 * @see StreamAppender
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class FileDateSizeAppender extends FileAppender
{

    /**
     * Max log file size
     */
    private long maxSize = 10485760L;

    /**
     * Keep custom days.
     */
    private int keepDay = 7;

    public FileDateSizeAppender()
    {
    }

    public FileDateSizeAppender(LoggerLayout layout)
    {
        super(layout);
    }

    public FileDateSizeAppender(OutputStream outStream)
    {
        super(outStream);
    }

    public FileDateSizeAppender(LoggerLayout layout, OutputStream outStream)
    {
        super(layout, outStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void expandAppend(LogMessage msg, String log) throws Exception
    {
        RecordOutputStream ros = (RecordOutputStream) getOutStream();
        if (ros != null)
        {
            long size = ros.getCount();
            if (size > maxSize)
            {
                closeStream();
                try
                {
                    createFile(fileName, fileAppend, buffered, bufferSize);
                }
                catch (IOException e)
                {
                    Kernel.logError("File date size appender create file "
                            + fileName + " failed.");
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream expandOutStream(String path, OutputStream outStream)
    {
        OutputStream out = super.expandOutStream(path, outStream);
        File file = new File(path);
        cleanOutDateFile(file.getParentFile());
        long initSize = file.exists() ? file.length() : 0;
        return new RecordOutputStream(out, initSize);
    }

    private void cleanOutDateFile(File dir)
    {
        long curTime = System.currentTimeMillis();
        File[] files = dir.listFiles();
        if (files == null || files.length == 0)
        {
            return;
        }
        int keepTime = keepDay * 24 * 60 * 60 * 1000;
        for (File file : files)
        {
            if (file.isFile() && curTime - file.lastModified() > keepTime)
            {
                file.delete();
            }
        }
    }

    public long getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize(long maxSize)
    {
        this.maxSize = maxSize;
    }

    public int getKeepDay()
    {
        return keepDay;
    }

    public void setKeepDay(int keepDay)
    {
        this.keepDay = keepDay;
    }

}
