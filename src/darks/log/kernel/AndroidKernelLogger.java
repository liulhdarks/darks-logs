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

import android.util.Log;

/**
 * 
 * AndroidKernelLogger.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class AndroidKernelLogger implements KernelLogger
{
    
    private static final String TAG = "darks-log";
    
    @Override
    public void debug(String msg)
    {
        Log.d(TAG, msg);
    }
    
    @Override
    public void debug(String msg, Throwable e)
    {
        Log.d(TAG, msg, e);
    }
    
    @Override
    public void info(String msg)
    {
        Log.i(TAG, msg);
    }
    
    @Override
    public void info(String msg, Throwable e)
    {
        Log.i(TAG, msg, e);
    }
    
    @Override
    public void warn(String msg)
    {
        Log.w(TAG, msg);
    }
    
    @Override
    public void warn(String msg, Throwable e)
    {
        Log.w(TAG, msg, e);
    }

    @Override
    public void error(String msg)
    {
        Log.e(TAG, msg);
    }

    @Override
    public void error(String msg, Throwable e)
    {
        Log.e(TAG, msg, e);
    }
    
}
