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

/**
 * Loader.java
 * 
 * @version 1.0.0
 * @author Liu lihua 2014-2-18
 */
public abstract class Loader
{
    
    protected InputStream inputStream;
    
    public Loader(InputStream ins)
    {
        this.inputStream = ins;
    }

    /**
     * Load config
     * 
     * @return If succeed to load config, return true.
     */
    public abstract boolean loadConfig();

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }
    
    
}
