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

package darks.log.appender.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The stream will wrap normal output stream to record file bytes size.
 * 
 * RecordOutputStream.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class RecordOutputStream extends OutputStream
{

    private OutputStream out;

    private long count;

    public RecordOutputStream(OutputStream out)
    {
        this.out = out;
        count = 0;
    }

    public RecordOutputStream(OutputStream out, long initSize)
    {
        this.out = out;
        count = initSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException
    {
        if (out != null)
        {
            out.write(b);
            count++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException
    {
        if (out != null)
        {
            out.write(b);
            count += b.length;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        if (out != null)
        {
            out.write(b, off, len);
            count += len;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException
    {
        super.flush();
        if (out != null)
        {
            out.flush();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException
    {
        super.close();
        if (out != null)
        {
            out.close();
        }
        count = 0;
    }

    public long getCount()
    {
        return count;
    }

}
