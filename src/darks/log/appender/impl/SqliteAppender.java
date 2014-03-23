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

import java.util.List;

import darks.log.LogMessage;
import darks.log.Logger;
import darks.log.pattern.ConvertPattern;
import darks.log.utils.EnvUtils;
import darks.log.utils.SqliteUtils;

/**
 * Use android sqlite database to save log records. It will build the insert SQL
 * statement automatically through columnsMap and table fields.<br/>
 * Example:
 * 
 * <pre>
 * logd.appender.SQLITE=SqliteAppender
 * logd.appender.SQLITE.layout=PatternLayout
 * logd.appender.SQLITE.dbName=db_records
 * logd.appender.SQLITE.dbVersion=1
 * logd.appender.SQLITE.table=t_logs
 * logd.appender.SQLITE.createSQL=create table if not exists t_logs(_id integer primary key autoincrement,date text,level text,source integer,file text,message text)
 * logd.appender.SQLITE.columnsMap.date=%d{yyyy-MM-dd HH:mm:ss}
 * logd.appender.SQLITE.columnsMap.level=%p
 * logd.appender.SQLITE.columnsMap.source=%L
 * logd.appender.SQLITE.columnsMap.file=%f
 * logd.appender.SQLITE.columnsMap.message=%m
 * </pre>
 * 
 * SqliteAppender.java
 * @see StorgeAppender
 * @version 1.0.0
 * @author Liu lihua
 */
public class SqliteAppender extends StorgeAppender
{

    /**
     * Sqlite operation helper
     */
    private SqliteUtils sqlite;

    /**
     * Database name
     */
    private String dbName;

    /**
     * The SQL statement for creating table if table doesn't exists.
     */
    private String createSQL;

    /**
     * The SQL statement for upgrade table if version is difference
     */
    private String updateSQL;

    /**
     * Databases version code
     */
    private int dbVersion;

    public SqliteAppender()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateHandler()
    {
        if (dbName == null || "".equals(dbName))
        {
            return false;
        }
        if (EnvUtils.isAndroidEnv() && Logger.Android.getApplication() == null)
        {
            return false;
        }
        boolean inited = super.activateHandler();
        if (inited)
        {
            sqlite = SqliteUtils.build(dbName, dbVersion, createSQL, updateSQL);
        }
        return inited;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(LogMessage msg, String log) throws Exception
    {
        if (sqlite == null)
        {
            return;
        }
        List<ConvertPattern> converts = getConvertors();
        int len = converts.size();
        String[] values = new String[len];
        for (int i = 0; i < len; i++)
        {
            values[i] = converts.get(i).format(msg);
        }
        sqlite.insert(getSql(), values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needPattern()
    {
        return false;
    }

    public SqliteUtils getSqlite()
    {
        return sqlite;
    }

    public void setSqlite(SqliteUtils sqlite)
    {
        this.sqlite = sqlite;
    }

    public String getDbName()
    {
        return dbName;
    }

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public String getCreateSQL()
    {
        return createSQL;
    }

    public void setCreateSQL(String createSQL)
    {
        this.createSQL = createSQL;
    }

    public String getUpdateSQL()
    {
        return updateSQL;
    }

    public void setUpdateSQL(String updateSQL)
    {
        this.updateSQL = updateSQL;
    }

    public int getDbVersion()
    {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion)
    {
        this.dbVersion = dbVersion;
    }

}
