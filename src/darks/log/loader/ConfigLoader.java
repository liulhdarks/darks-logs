/**
 * 
 *Copyright 2014 The Darks Logs Project
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

package darks.log.loader;

import java.io.InputStream;

import darks.log.Category;
import darks.log.Logger;
import darks.log.exceptions.ConfigException;
import darks.log.utils.EnvUtils;

/**
 * Indicate to load logger configuration If you want logger to work in the
 * android environment, you must configure android application by
 * "Logger.Android.setApplication(...);" before loaded.
 * 
 * ConfigLoader.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class ConfigLoader
{

    public static final String CONFIG_FILE_NAME = "logd";

    public static final String CONFIG_EXT = ".properties";

    public static final String CONFIG_FILE = CONFIG_FILE_NAME + CONFIG_EXT;

    private Loader loader;

    public ConfigLoader()
    {
    }

    /**
     * Initialize configuration
     * 
     * @return If succeed to initialize, return true.
     */
    public boolean initConfig()
    {
        if (EnvUtils.isAndroidEnv())
        {
            if (Logger.Android == null 
                    || Logger.Android.getApplication() == null)
            {
                throw new ConfigException(
                        "Fail to load configuration. Android application has not been setted.");
            }
            loader = Logger.Android.getLoader();
        }
        else
        {
            loader = getDefaultLoader();
        }
        if (loader == null || !loader.loadConfig())
        {
            throw new ConfigException(
                    "Fail to load configuration. Cause loader fail to load config.");
        }
        initCategories();
        return true;
    }

    /**
     * Load config file from Java/src root
     * 
     * @return If succeed to load config, return true.
     */
    public Loader getDefaultLoader()
    {
        InputStream ins = this.getClass()
                .getResourceAsStream("/" + CONFIG_FILE);
        if (ins == null)
        {
            return null;
        }
        return new PropertiesLoader(ins);
    }

    

    private synchronized void initCategories()
    {
        Category rootCate = Logger.getConfig().getRoot();
        rootCate.buildAppenderArray();
        for (Category cate : Logger.getConfig().getCategories().values())
        {
            cate.buildAppenderArray();
        }
    }
}
