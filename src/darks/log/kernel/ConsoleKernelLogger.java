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

package darks.log.kernel;

/**
 * 
 * ConsoleKernelLogger.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class ConsoleKernelLogger implements KernelLogger
{
    
    private static final String TAG = "[DarksLog]";
    
    @Override
    public void debug(String msg)
    {
        System.out.println(TAG + msg);
    }
    
    @Override
    public void debug(String msg, Throwable e)
    {
        System.out.println(TAG + msg);
        e.printStackTrace(System.out);
    }
    
    @Override
    public void info(String msg)
    {
        System.out.println(TAG + msg);
    }
    
    @Override
    public void info(String msg, Throwable e)
    {
        System.out.println(TAG + msg);
        e.printStackTrace(System.out);
    }
    
    @Override
    public void warn(String msg)
    {
        System.out.println(TAG + msg);
    }
    
    @Override
    public void warn(String msg, Throwable e)
    {
        System.out.println(TAG + msg);
        e.printStackTrace(System.out);
    }

    @Override
    public void error(String msg)
    {
        System.err.println(TAG + msg);
    }

    @Override
    public void error(String msg, Throwable e)
    {
        System.err.println(TAG + msg);
        e.printStackTrace();
    }
    
}
