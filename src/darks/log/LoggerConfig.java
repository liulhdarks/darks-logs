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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import darks.log.exceptions.ConfigException;
import darks.log.loader.Loader;

/**
 * Logger configuration
 * 
 * LoggerConfig.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-3-21
 */
public class LoggerConfig
{

	private Category root;

	private Map<String, Category> categories;

	private Map<String, Boolean> inherits;

	private boolean rootInherit = true;

	private Loader customLoader;
	
	public LoggerConfig()
	{
		root = new Category();
		categories = new HashMap<String, Category>();
		inherits = new HashMap<String, Boolean>();
	}

	public Category getRoot()
	{
		return root;
	}

	public void setRoot(Category root)
	{
		this.root = root;
	}

	/**
	 * Add category object
	 * 
	 * @param category Category object
	 */
	public void addCategory(Category category)
	{
		if (category == null)
		{
			throw new ConfigException("Invalid category which is " + category);
		}
		categories.put(category.getName(), category);
	}

	public Category getCategory(String name)
	{
		return categories.get(name);
	}

	public void addInherit(String name, boolean inherit)
	{
		inherits.put(name, inherit);
	}

	/**
	 * Get inherit value by checking the closest tag name.
	 * 
	 * @param name Tag name
	 * @return true or false
	 */
	public boolean getInherit(String name)
	{
	    if (name == null)
	    {
	        return false;
	    }
		Boolean match = null;
		String maxKey = null;
		for (Entry<String, Boolean> entry : inherits.entrySet())
		{
			String key = entry.getKey();
			if (name.startsWith(key))
			{
				if (maxKey == null || maxKey.length() < key.length())
				{
					maxKey = key;
					match = entry.getValue();
				}
			}
		}
		if (match == null)
		{
			return rootInherit;
		}
		return match;
	}

	public boolean isRootInherit()
	{
		return rootInherit;
	}

	public void setRootInherit(boolean rootInherit)
	{
		this.rootInherit = rootInherit;
	}

	public Map<String, Category> getCategories()
	{
		return categories;
	}

    public Loader getCustomLoader()
    {
        return customLoader;
    }

    public void setCustomLoader(Loader customLoader)
    {
        this.customLoader = customLoader;
    }

}
