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
import java.util.Date;

import darks.log.appender.Appender;
import darks.log.externs.FilePicker;
import darks.log.kernel.Kernel;
import darks.log.layout.LoggerLayout;
import darks.log.utils.EnvUtils;
import darks.log.utils.StorageUtils;
import darks.log.utils.StringUtils;
import darks.log.utils.TimeUtils;

/**
 * Appender for file. If you want to create dynamical file name, you can use
 * ${PROPERTY_VARIABLE} to get system property value such as ${user.dir}. If you
 * want to include the date or time in file name, you can use ${D[DATE_PATTERN]}
 * such as ${Dyyyy_MM_dd_HH_mm_ss}.<br>
 * Example:
 * 
 * <pre>
 *  logd.appender.FILE=FileAppender
 *  logd.appender.FILE.layout=PatternLayout
 *  logd.appender.FILE.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} %c{1} - %m%n
 *  logd.appender.FILE.fileName=${user.dir}/log_${Dyyyy_MM_dd_HH_mm_ss_SS}.txt
 *  logd.appender.FILE.buffered=true
 * </pre>
 * 
 * FileAppender.java
 * 
 * @see StreamAppender
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class FileAppender extends StreamAppender
{
    private static final String DEFAULT_DATE_PATTERN = "yyyyMMddHHmmss";

    private static final int DEFAULT_BUF_SIZE = 8192;

    private static final String EXTERN_STORAGE = "sdcard";

    private static final String CUR_TIME = "cur_time";

    private static final String CUR_DATE = "cur_date";

    /**
     * If true, will append content to file
     */
    protected boolean fileAppend = true;

    /**
     * File name
     */
    protected String fileName = null;

    /**
     * If true, will use buffer IO.
     */
    protected boolean buffered = true;

    /**
     * Buffer size
     */
    protected int bufferSize = DEFAULT_BUF_SIZE;

    /**
     * Indicate to pick file path. default
     * {@linkplain darks.log.externs.FilePicker FilePicker}.
     */
    protected FilePicker filePicker = new FilePicker();

    static
    {
        initEnv();
    }

    public FileAppender()
    {
    }

    public FileAppender(LoggerLayout layout)
    {
        super(layout);
    }

    public FileAppender(OutputStream outStream)
    {
        super(outStream);
    }

    public FileAppender(LoggerLayout layout, OutputStream outStream)
    {
        super(layout, outStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateHandler()
    {
        if (fileName != null)
        {
            try
            {
                createFile(fileName, fileAppend, buffered, bufferSize);
            }
            catch (IOException e)
            {
                Kernel.logError("File appender create file " + fileName
                        + " failed.");
            }
        }
        else
        {
            Kernel.logWarn("File appender's file name is null");
        }
    }

    /**
     * Create file by paramters
     * 
     * @param path File path
     * @param append If true, append content to stream.
     * @param bufIO If true, immediateFlush will be false.
     * @param bufSize Buffer size
     * @return File path after converted
     * @throws IOException IOException object
     */
    public String createFile(String path, boolean append, boolean bufIO,
            int bufSize) throws IOException
    {
        if (bufIO)
        {
            immediateFlush = false;
        }
        resetTimeProperty();
        path = StringUtils.replacePropertyVar(path);
        OutputStream fos = filePicker.getOutputStream(path, append);
        if (fos != null)
        {
            OutputStream out = fos;
            if (buffered)
            {
                out = new BufferedOutputStream(fos, bufferSize);
            }
            setOutStream(expandOutStream(path, out));
        }
        return path;
    }

    /**
     * Expand output stream before set output stream.
     * 
     * @param path File path
     * @param outStream OutputStream object
     * @return OutputStream object after converted
     */
    public OutputStream expandOutStream(String path, OutputStream outStream)
    {
        return outStream;
    }

    private void resetTimeProperty()
    {
        System.setProperty(CUR_TIME, String.valueOf(System.currentTimeMillis()));
        System.setProperty(CUR_DATE,
                TimeUtils.getFormatter(DEFAULT_DATE_PATTERN).format(new Date()));
    }

    private static void initEnv()
    {
        if (EnvUtils.isAndroidEnv())
        {
            System.setProperty(EXTERN_STORAGE,
                    StorageUtils.getAbsoluteSdcardPath());
        }
    }

    public boolean isFileAppend()
    {
        return fileAppend;
    }

    public void setFileAppend(boolean fileAppend)
    {
        this.fileAppend = fileAppend;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public boolean isBuffered()
    {
        return buffered;
    }

    public void setBuffered(boolean buffered)
    {
        this.buffered = buffered;
    }

    public int getBufferSize()
    {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    public FilePicker getFilePicker()
    {
        return filePicker;
    }

    public void setFilePicker(FilePicker filePicker)
    {
        this.filePicker = filePicker;
    }

}
