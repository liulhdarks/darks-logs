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

import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import darks.log.kernel.Kernel;
import darks.log.loader.ConfigLoader;
import darks.log.loader.Loader;
import darks.log.loader.PropertiesLoader;

/**
 * Configure android application required when logs find configuration file.
 * 
 * AndroidConfig.java
 * @version 1.0.0
 * @author Liu lihua
 */
public final class AndroidConfig
{

	private Application application;
	
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
     * Get loader or android environment
     * 
     * @return If succeed to get loader, return loader object. Otherwise return null;.
     */
    public Loader getLoader()
    {
        Context ctx = application;
        if (ctx == null)
        {
            return null;
        }
        return getAssetConfig(ctx);
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
            Kernel.logError("Fail to get Asset config. Cause "
                    + e.getMessage());
        }
        return null;
    }
}
