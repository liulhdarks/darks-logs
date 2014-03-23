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

package darks.log.appender.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import darks.log.LogMessage;
import darks.log.appender.Appender;
import darks.log.pattern.ConvertPattern;
import darks.log.pattern.DefaultPattern;

/**
 * StorgeAppender will output log message to stream. You cannot use
 * StorgeAppender directly in logd.properties. You should create a sub class
 * which extends StorgeAppender.<br/>
 * Example:
 * 
 * <pre>
 * public class CustomDBAppender extends StorgeAppender
 * {
 *     &#064;Override
 *     public boolean activateHandler()
 *     {
 *         return super.activateHandler();
 *     }
 * 
 *     &#064;Override
 *     public void append(LogMessage msg, String log) throws Exception
 *     {
 *         ...
 *     }
 * }
 * </pre>
 * 
 * StorgeAppender.java
 * @see Appender
 * @version 1.0.0
 * @author Liu lihua
 */
public class StorgeAppender extends Appender
{

    /**
     * Table columns mapping. JDBC column name as key, message pattern as value. 
     */
    private Map<String, String> columnsMap = new HashMap<String, String>();

    private List<ConvertPattern> convertors = new ArrayList<ConvertPattern>();

    /**
     * Insert SQL statement built automatically
     */
    private String sql;

    /**
     * Database table name
     */
    private String table;

    public StorgeAppender()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateHandler()
    {
        boolean inited = true;
        sql = buildSQL();
        if (sql == null)
        {
            inited = false;
        }
        return inited;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(LogMessage msg, String log) throws Exception
    {

    }

    protected String buildSQL()
    {
        if (columnsMap.isEmpty() || table == null || "".equals(table))
        {
            return null;
        }
        StringBuilder valBuf = new StringBuilder();
        StringBuilder buf = new StringBuilder(64);
        buf.append("insert into ");
        buf.append(table);
        buf.append(" (");

        valBuf.append(" values(");
        for (Entry<String, String> entry : columnsMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || "".equals(key))
            {
                return null;
            }
            buf.append(key);
            buf.append(',');
            valBuf.append("?,");
            ConvertPattern cp = new DefaultPattern();
            if (!cp.setPattern(value))
            {
                return null;
            }
            convertors.add(cp);
        }
        buf.setLength(buf.length() - 1);
        valBuf.setLength(valBuf.length() - 1);
        buf.append(')');
        valBuf.append(')');
        buf.append(valBuf);
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needPattern()
    {
        return false;
    }

    public Map<String, String> getColumnsMap()
    {
        return columnsMap;
    }

    public List<ConvertPattern> getConvertors()
    {
        return convertors;
    }

    public String getTable()
    {
        return table;
    }

    public void setTable(String table)
    {
        this.table = table;
    }

    public String getSql()
    {
        return sql;
    }

}
