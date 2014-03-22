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

import java.io.File;

import android.os.Environment;
import android.text.TextUtils;

/**
 * StorageUtils.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class StorageUtils
{
    
    private static String CD_S_SdcardPath = "";
    
    private static String CD_S_SdcardPathAbsolute = "";
    
    public static String getSdcardPath()
    {
        if (TextUtils.isEmpty(CD_S_SdcardPath))
            CD_S_SdcardPath =
                Environment.getExternalStorageDirectory().getPath();
        
        if (CD_S_SdcardPath.contains(CommonType.CT_S_Sdcard_Sign_Storage_emulated))
            CD_S_SdcardPath =
                CD_S_SdcardPath.replace(CommonType.CT_S_Sdcard_Sign_Storage_emulated,
                    CommonType.CT_S_Sdcard_Sign_Storage_sdcard);
        
        return CD_S_SdcardPath;
    }
    
    public static String getAbsoluteSdcardPath()
    {
        if (TextUtils.isEmpty(CD_S_SdcardPathAbsolute))
            CD_S_SdcardPathAbsolute =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        
        if (CD_S_SdcardPathAbsolute.contains(CommonType.CT_S_Sdcard_Sign_Storage_emulated))
            CD_S_SdcardPathAbsolute =
                CD_S_SdcardPathAbsolute.replace(CommonType.CT_S_Sdcard_Sign_Storage_emulated,
                    CommonType.CT_S_Sdcard_Sign_Storage_sdcard);
        
        return CD_S_SdcardPathAbsolute;
    }
    
    public static File getSdcardPathFile()
    {
        return new File(getSdcardPath());
    }
    
    public static String checkAndReplaceEmulatedPath(String strSrc)
    {
        if (strSrc.contains(CommonType.CT_S_Sdcard_Sign_Storage_emulated))
            strSrc =
                strSrc.replace(CommonType.CT_S_Sdcard_Sign_Storage_emulated,
                    CommonType.CT_S_Sdcard_Sign_Storage_sdcard);
        return strSrc;
    }
    
    class CommonType
    {
        public static final String CT_S_Sdcard_Sign_Storage_emulated =
            "storage/emulated/";
        
        public static final String CT_S_Sdcard_Sign_Storage_sdcard =
            "storage/sdcard";
        
    }
}