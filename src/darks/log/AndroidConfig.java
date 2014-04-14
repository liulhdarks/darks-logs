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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler.Callback;
import darks.log.externs.AndroidCrashHandler;
import darks.log.kernel.Kernel;
import darks.log.loader.ConfigLoader;
import darks.log.loader.Loader;
import darks.log.loader.PropertiesLoader;
import darks.log.utils.StorageUtils;

/**
 * Configure android application required when logs find configuration file.
 * 
 * AndroidConfig.java
 * 
 * @version 1.0.1
 * @author Liu lihua
 */
public final class AndroidConfig
{

    private Application application;
    
    private String configPath;

    AndroidConfig()
    {

    }

    public Application getApplication()
    {
        return application;
    }

    public void setApplication(Application application)
    {
        this.application = application;
    }

    /**
     * Set android application
     * 
     * @param application Android application
     * @param regCrash If true, it will register crash logger's crash handler
     */
    public void setApplication(Application application, boolean regCrash)
    {
        this.application = application;
        if (regCrash)
        {
            registerCrashHandler(null);
        }
    }

    /**
     * Register crash handler. It can catch ANR error automatically and use
     * logger to output message. <br>
     * You can use registerCrashHandler(Callback callback) to register the callback.
     */
    public void registerCrashHandler()
    {
        registerCrashHandler(null);
    }

    /**
     * Register crash handler. It can catch ANR error automatically and use
     * logger to output message.
     * 
     * @param callback Call back object.When ANR error happened, it will use
     *            callback to notify developers. If it's null, it won't call
     *            back.
     */
    public void registerCrashHandler(Callback callback)
    {
        if (application == null)
        {
            return;
        }
        AndroidCrashHandler.getInstance().setup(application, callback);
    }

    /**
     * Get loader or android environment
     * 
     * @return If succeed to get loader, return loader object. Otherwise return
     *         null;.
     */
    public Loader getLoader()
    {
        Context ctx = application;
        if (ctx == null)
        {
            return null;
        }
        Loader loader = null;
        if (configPath != null && !"".equals(configPath))
        {
            loader = getSdcardConfig(ctx);
        }
        if (loader == null)
        {
            loader = getAssetConfig(ctx);
        }
        return loader;
    }

    /**
     * Get config from android assets
     * 
     * @param ctx Android context/application
     * @return If succeed to get config, return Loader.
     */
    private Loader getAssetConfig(Context ctx)
    {
        AssetManager am = ctx.getAssets();
        try
        {
            InputStream ins = am.open(ConfigLoader.CONFIG_FILE);
            if (ins == null)
            {
                return null;
            }
            return new PropertiesLoader(ins);
        }
        catch (IOException e)
        {
            Kernel.logError("Fail to get Asset config. Cause " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get config from android sdcard or memory.
     * 
     * @param ctx Android context/application
     * @return If succeed to get config, return Loader.
     */
    private Loader getSdcardConfig(Context ctx)
    {
        try
        {
            File file = new File(configPath);
            if (!file.exists())
            {
                file = new File(StorageUtils.getAbsoluteSdcardPath(), configPath);
                if (!file.exists())
                {
                    return null;
                }
            }
            FileInputStream fis = new FileInputStream(file);
            return new PropertiesLoader(fis);
        }
        catch (IOException e)
        {
            Kernel.logError("Fail to get sdcard config. Cause " + e.getMessage());
        }
        return null;
    }

    public String getConfigPath()
    {
        return configPath;
    }

    public void setConfigPath(String configPath)
    {
        this.configPath = configPath;
    }

}
