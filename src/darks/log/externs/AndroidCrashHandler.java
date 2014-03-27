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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Handler.Callback;
import android.os.Message;
import darks.log.Logger;

/**
 * 
 * CrashHandler.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class AndroidCrashHandler implements UncaughtExceptionHandler
{

    private static Logger log = Logger.getLogger(AndroidCrashHandler.class);

    private Thread.UncaughtExceptionHandler defaultHandler;

    private static AndroidCrashHandler instance;

    private Context context;

    private Map<String, String> infos = new HashMap<String, String>();

    private Callback callback;

    private AndroidCrashHandler()
    {
    }

    public static synchronized AndroidCrashHandler getInstance()
    {
        if (instance == null)
        {
            instance = new AndroidCrashHandler();
        }
        return instance;
    }

    /**
     * Setup crash parameters.
     * 
     * @param context context
     * @param callback Call back object.When ANR error happened, it will use
     *            callback to notify developers.
     */
    public void setup(Context context, Callback callback)
    {
        this.callback = callback;
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex)
    {
        if (!handleException(ex) && defaultHandler != null)
        {
            defaultHandler.uncaughtException(thread, ex);
        }
        else
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex)
    {
        if (ex == null)
        {
            return false;
        }
        collectDeviceInfo(context);
        logCrash(ex);
        if (callback != null)
        {
            Message msg = new Message();
            msg.obj = ex;
            callback.handleMessage(msg);
        }
        return true;
    }

    private void collectDeviceInfo(Context ctx)
    {
        try
        {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null)
            {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                infos.put("VersionName", versionName);
                infos.put("VersionCode", versionCode);
            }
        }
        catch (NameNotFoundException e)
        {
            log.error("Error occured when collect package information.", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            }
            catch (Exception e)
            {
                log.error("Error occured when collect crash information.", e);
            }
        }
    }

    private void logCrash(Throwable ex)
    {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet())
        {
            sb.append(entry.getKey()).append('=').append(entry.getValue())
                    .append('\n');
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        try
        {
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null)
            {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
        }
        finally
        {
            printWriter.close();
        }
        sb.append(writer.toString());
        log.error(sb);
    }
}