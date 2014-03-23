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

package darks.log.utils;

import darks.log.Logger;
import darks.log.kernel.Kernel;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SqliteUtils.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class SqliteUtils extends SQLiteOpenHelper
{

    String createSql;

    String updateSql;

    /**
     * Build a sqlite helper
     * 
     * @param dbName Database name
     * @param version Database version for update
     * @param createSql If table does not exists, it will use sql to create
     *            table.
     * @param updateSql If table need to be updated, it will use sql to
     *            update table.
     * @return Sqlite helper
     */
    public static SqliteUtils build(String dbName, int version,
            String createSql, String updateSql)
    {
        return new SqliteUtils(Logger.Android.getApplication(), dbName,
                version, createSql, updateSql);
    }

    private SqliteUtils(Context ctx, String dbName, int version,
            String createSql, String updateSql)
    {
        super(ctx, dbName, null, version);
        this.createSql = createSql;
        this.updateSql = updateSql;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        if (createSql != null && !"".equals(createSql))
        {
            db.execSQL(createSql);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (updateSql != null && !"".equals(updateSql))
        {
            db.execSQL(updateSql);
        }
    }

    /**
     * Insert data to sqlite by SQL
     * 
     * @param sql SQL
     * @param params Bind parameters
     * @return If succeed to insert, return true.
     */
    public boolean insert(String sql, Object[] params)
    {
        try
        {
            getWritableDatabase().execSQL(sql, params);
            return true;
        }
        catch (SQLException e)
        {
            Kernel.logError("Fail to insert message to sqlite. Cause "
                    + e.getMessage());
        }
        return false;
    }
}
