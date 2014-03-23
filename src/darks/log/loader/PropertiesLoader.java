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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import darks.log.Category;
import darks.log.Level;
import darks.log.Logger;
import darks.log.LoggerConfig;
import darks.log.appender.Appender;
import darks.log.appender.AppenderManager;
import darks.log.exceptions.ConfigException;
import darks.log.kernel.Kernel;
import darks.log.utils.IoUtils;
import darks.log.utils.ReflectUtils;

/**
 * Indicate to load config file from properties file.
 * 
 * PropertiesLoader.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class PropertiesLoader extends Loader
{

    private static final String PREFFIX = "logd.";

    private static final int PREFFIX_LEN = PREFFIX.length();

    private static final String PARAM_ROOT = "root";

    private static final String PARAM_CATEGORY = "category";

    private static final String PARAM_INHERIT = "inherit";

    private static final String PARAM_ADDITIVITY = "additivity";

    private static final String PARAM_APPENDER = "appender";

    private static final String PARAM_LOGGER = "logger";

    private static final String APPENDER_CLASS_DIR = "darks.log.appender.impl";

    private BufferedReader reader;

    public PropertiesLoader(InputStream ins)
    {
        super(ins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadConfig()
    {
        reader = new BufferedReader(new InputStreamReader(getInputStream()));
        try
        {
            readConfig();
            return true;
        }
        catch (Exception e)
        {
            Kernel.logError("Fail to load properties logger config. Cause "
                    + e.getMessage());
        }
        finally
        {
            IoUtils.closeIO(reader);
        }
        return false;
    }

    private void readConfig() throws IOException
    {
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            if (line.startsWith("#"))
            {
                continue;
            }
            if (!line.startsWith(PREFFIX) || line.length() <= PREFFIX_LEN)
            {
                throw new ConfigException("logger config has a invalid line:"
                        + line);
            }
            readLine(line);
        }
    }

    private void readLine(String line)
    {
        String[] keyval = line.split("=");
        if (keyval.length != 2)
        {
            throw new ConfigException("logger config has a invalid line:"
                    + line);
        }
        String key = keyval[0].trim();
        String val = keyval[1].trim();
        if (key.isEmpty() || val.isEmpty())
        {
            throw new ConfigException("logger config has a invalid line:"
                    + line);
        }
        String[] args = key.split("\\.");
        if (args.length < 2)
        {
            throw new ConfigException("logger config has a invalid line:"
                    + line);
        }
        String mainParam = validateParam(args[1], line);
        readMainParam(mainParam, args, val);
    }

    private String validateParam(String param, String line)
    {
        param = param.trim();
        if (param.isEmpty())
        {
            throw new ConfigException("logger config has a invalid param '"
                    + param + "' in line:" + line);
        }
        return param;
    }

    private void readMainParam(String param, String[] args, String val)
    {
        LoggerConfig confg = Logger.getConfig();
        if (PARAM_ROOT.equalsIgnoreCase(param))
        {
            confg.setRoot(readCategory(true, args, val));
        }
        else if (PARAM_CATEGORY.equalsIgnoreCase(param)
                || PARAM_LOGGER.equalsIgnoreCase(param))
        {
            confg.addCategory(readCategory(false, args, val));
        }
        else if (PARAM_INHERIT.equalsIgnoreCase(param)
                || PARAM_ADDITIVITY.equalsIgnoreCase(param))
        {
            readInherit(args, val);
        }
        else if (PARAM_APPENDER.equalsIgnoreCase(param))
        {
            readAppender(args, val);
        }
    }

    private Category readCategory(boolean root, String[] args, String val)
    {
        Category cate = new Category();
        if (!root)
        {
            if (args.length < 3)
            {
                throw new ConfigException("Logger category lost the name.");
            }
            cate.setName(getString(args, 2));
            Category rootCate = Logger.getConfig().getRoot();
            if (rootCate == null)
            {
                throw new ConfigException("Logger root doesn't be configurd.");
            }
            cate.setParent(rootCate);
        }

        String[] cfgs = val.split(",");
        String levelKey = cfgs[0].trim();
        Level level = Level.getLevel(levelKey);
        if (level == null)
        {
            throw new ConfigException("Logger level '" + levelKey
                    + "' doesn't exists.");
        }
        cate.setLevel(level);
        int len = cfgs.length;
        if (len > 1)
        {
            int size = len - 1;
            String[] appenders = new String[size];
            for (int i = 0; i < size; i++)
            {
                String appender = cfgs[i + 1].trim();
                if (appender.isEmpty())
                {
                    throw new ConfigException(
                            "Logger category appender cannot be empty.");
                }
                appenders[i] = appender;
            }
            cate.setAppenders(appenders);
        }
        return cate;
    }

    /**
     * Read inherit or additivity config.
     * 
     * @param args
     * @param val
     */
    private void readInherit(String[] args, String val)
    {
        LoggerConfig cfg = Logger.getConfig();
        boolean bool = Boolean.parseBoolean(val);
        if (args.length == 2) // for global setting inherit
        {
            cfg.setRootInherit(bool);
            return;
        }
        if (args.length < 3) // for one category
                             // setting inherit
        {
            throw new ConfigException(
                    "Cannot find logger category inherit/additivity category name.");
        }
        String name = getString(args, 2);
        cfg.addInherit(name, bool);
    }

    private void readAppender(String[] args, String val)
    {
        if (args.length == 3) // set appender class and name
        {
            readNewAppender(args[2], val);
        }
        else if (args.length > 3) // set appender paramters
        {
            readAppenderParams(args, val);
        }
    }

    private void readNewAppender(String name, String className)
    {
        name = name.trim();
        if (name.isEmpty())
        {
            throw new ConfigException(
                    "Cannot find logger appender's name, which is empty.");
        }
        Class<?> clazz = ClassFinder.findClass(className, APPENDER_CLASS_DIR);
        if (clazz == null)
        {
            throw new ConfigException("Cannot find logger appender's class '"
                    + className + "'");
        }
        Appender appender = (Appender) ReflectUtils.newInstance(clazz);
        if (appender == null)
        {
            throw new ConfigException("Cannot instance class '" + clazz + "'");
        }
        appender.setName(name);
        AppenderManager.registerAppender(appender);
    }

    @SuppressWarnings("unchecked")
    private void readAppenderParams(String[] args, String val)
    {
        String name = args[2].trim();
        if (name.isEmpty())
        {
            throw new ConfigException(
                    "Cannot find logger appender's name, which is empty.");
        }
        Appender appender = AppenderManager.getAppender(name);
        if (appender == null)
        {
            throw new ConfigException("Cannot find logger appender by name '"
                    + name + "'.");
        }
        Object baseObj = appender;
        int size = args.length;
        for (int i = 3; i < size - 1; i++)
        {
            String field = args[i].trim();
            if (field.isEmpty())
            {
                throw new ConfigException(
                        "Appender's params setting has a invalid field");
            }
            baseObj = ReflectUtils.getGetMethodObject(baseObj, args[i]);
            if (baseObj == null)
            {
                throw new ConfigException("Appender's params setting field '"
                        + args[i] + "' is null.");
            }
            if (baseObj instanceof Map<?, ?>)
            {
                putMapValue((Map<String, String>) baseObj, i + 1, args, val);
                return;
            }
        }
        name = args[size - 1];
        if (!ReflectUtils.setStringValue(baseObj, name, val))
        {
            throw new ConfigException("Fail to set appender param value for "
                    + baseObj.getClass() + "'s key " + name);
        }
    }

    private boolean putMapValue(Map<String, String> map, int index, String[] args, String val)
    {
        StringBuilder buf = new StringBuilder();
        int argLen = args.length;
        for (int i = index; i < argLen; i++)
        {
            buf.append(args[i]);
            if (i != argLen - 1)
            {
                buf.append('.');
            }
        }
        String key = buf.toString().trim();
        if ("".equals(key))
        {
            return false;
        }
        map.put(key, val);
        return true;
    }
    
    private String getString(String[] args, int start)
    {
        return getString(args, start, args.length - 1);
    }

    private String getString(String[] args, int start, int end)
    {
        if (end < start)
        {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = start; i <= end; i++)
        {
            String tmp = args[i].trim();
            if (tmp.isEmpty())
            {
                throw new ConfigException("logger config name is empty.");
            }
            buf.append(tmp);
            buf.append('.');
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }
}
