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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Indicate the log message's level.
 * 
 * The low level wiil ouput upper level message. For example:
 * 
 * <pre>
 * TRACE &gt; ERROR &gt; WARN &gt; INFO &gt; DEBUG &gt; VERBOSE
 * </pre>
 * 
 * Level.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-3-21
 */
public class Level implements Serializable
{

	private static final long serialVersionUID = 6561230519046683707L;

	public static final String LEVEL_INFO = "INFO";

	public static final String LEVEL_DEBUG = "DEBUG";

	public static final String LEVEL_WARN = "WARN";

	public static final String LEVEL_ERROR = "ERROR";

	public static final String LEVEL_TRACE = "TRACE";

	public static final String LEVEL_VERBOSE = "VERBOSE";

	public static final Level TRACE = new Level(LEVEL_TRACE, 600, 0x0020);

	public static final Level ERROR = new Level(LEVEL_ERROR, 500, 0x0010);

	public static final Level WARN = new Level(LEVEL_WARN, 400, 0x0008);

	public static final Level INFO = new Level(LEVEL_INFO, 300, 0x0004);

	public static final Level DEBUG = new Level(LEVEL_DEBUG, 200, 0x0002);

	public static final Level VERBOSE = new Level(LEVEL_VERBOSE, 100, 0x0001);

	private static Map<String, Level> levels;

	/**
	 * Level name
	 */
	private String name;

	/**
	 * Level decided to output sequence
	 */
	private int level;

	/**
	 * Level code
	 */
	private int code;

	static
	{
		levels = new HashMap<String, Level>();
		registerLevel(TRACE);
		registerLevel(ERROR);
		registerLevel(WARN);
		registerLevel(INFO);
		registerLevel(DEBUG);
		registerLevel(VERBOSE);
	}

	public Level()
	{
	}

	/**
	 * Level constructor
	 * 
	 * @param name Level name
	 * @param level Decided sequence
	 */
	public Level(String name, int level)
	{
		this.name = name;
		this.level = level;
	}

	/**
	 * Level constructor
	 * 
	 * @param name Level name
	 * @param level Decided sequence
	 * @param code Level code
	 */
	public Level(String name, int level, int code)
	{
		this.name = name;
		this.level = level;
		this.code = code;
	}

	/**
	 * Register level object
	 * 
	 * @param level Level certified.
	 */
	public static void registerLevel(Level level)
	{
		levels.put(level.getName().toUpperCase(), level);
	}

	/**
	 * Get level object by level name as key word
	 * 
	 * @param key Level name
	 * @return Level object
	 */
	public static Level getLevel(String key)
	{
		return levels.get(key.toUpperCase());
	}

	/**
	 * Compare level between two levels.
	 * 
	 * @param lv1 Level source
	 * @param lv2 Level target
	 * @return If lv1 is greater than lv2, it wiil return > 0. If lv1 is less
	 *         than lv2, return < 0. otherwise return 0.
	 */
	public static int compare(Level lv1, Level lv2)
	{
		return lv1.getLevel() - lv2.getLevel();
	}

	/**
	 * Compare level between this and target level.
	 * 
	 * @param target Level target
	 * @return If this level is greater than target, it wiil return > 0. If this
	 *         level is less than target, return < 0. otherwise return 0.
	 */
	public int compare(Level target)
	{
		return this.getLevel() - target.getLevel();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + level;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Level other = (Level) obj;
		if (code != other.code)
			return false;
		if (level != other.level)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "Level [code=" + code + ", level=" + level + ", name=" + name
				+ "]";
	}

}
