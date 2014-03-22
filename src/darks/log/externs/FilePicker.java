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

package darks.log.externs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Indicate to pick file path by custom pattern or get file from special place.
 * Example:
 * 
 * <pre>
 * public class CustomFilePicker extends FilePicker
 * {
 *     &#064;Override
 *      public OutputStream getOutputStream(String path, boolean append) throws IOException
 *      {
 *          Context ctx = ...
 *          File rootDir;
 *          if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
 *          {
 *              rootDir = Environment.getExternalStorageDirectory(); 
 *          }
 *          else
 *          {
 *              rootDir = ctx.getFilesDir();
 *          }
 *          return new FileOutputStream(path, append); 
 *      }
 * }
 * </pre>
 * 
 * FilePicker.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class FilePicker
{

    public FilePicker()
    {
    }

    /**
     * Get output stream
     * 
     * @param path File path
     * @param append If true, will append content to file
     * @return OutputStream object
     * @throws IOException IO Exception
     */
    public OutputStream getOutputStream(String path, boolean append)
            throws IOException
    {
        File file = checkExistFile(path, append);
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file, append);
        }
        catch (FileNotFoundException e)
        {
            File dir = file.getParentFile();
            if (!dir.exists() && dir.mkdirs())
            {
                fos = new FileOutputStream(file, append);
            }
            else
            {
                throw e;
            }
        }
        return fos;
    }

    /**
     * Check file whether exists. If file exists and append is false, a number
     * will be added to the end of file name.
     * 
     * @param path File path
     * @param append If true, will return file directly
     * @return File object
     */
    protected File checkExistFile(String path, boolean append)
    {
        File file = new File(path);
        if (append)
        {
            return file;
        }
        int index = 1;
        while (file.exists())
        {
            path += "." + (index++);
            file = new File(path);
        }
        return file;
    }
}
