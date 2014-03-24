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

import android.os.Environment;
import android.text.TextUtils;

/**
 * StorageUtils.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class StorageUtils
{

    public static final String STORAGE_EMULATED = "storage/emulated/";

    public static final String STORAGE_SDCARD = "storage/sdcard";

    private static String sdcardPath = "";

    private static String sdcardPathAbsolute = "";

    public static String getSdcardPath()
    {
        if (TextUtils.isEmpty(sdcardPath))
        {
            sdcardPath = Environment.getExternalStorageDirectory().getPath();
        }

        if (sdcardPath.contains(STORAGE_EMULATED))
        {
            sdcardPath = sdcardPath.replace(STORAGE_EMULATED, STORAGE_SDCARD);
        }
        return sdcardPath;
    }

    public static String getAbsoluteSdcardPath()
    {
        if (TextUtils.isEmpty(sdcardPathAbsolute))
        {
            sdcardPathAbsolute = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        }

        if (sdcardPathAbsolute.contains(STORAGE_EMULATED))
        {
            sdcardPathAbsolute = sdcardPathAbsolute.replace(STORAGE_EMULATED,
                    STORAGE_SDCARD);
        }

        return sdcardPathAbsolute;
    }
}