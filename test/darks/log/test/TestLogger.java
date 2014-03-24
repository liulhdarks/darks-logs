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

package darks.log.test;

import darks.log.Logger;

/**
 * 
 * TestLogger.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class TestLogger
{

    private static Logger log = Logger.getLogger(TestLogger.class);

    public TestLogger()
    {
    }

    public static void testException()
    {
        throw new RuntimeException("darks logs test exception");
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            for (int i = 0; i < 1; i++)
            {
                log.info("darks logs test info " + i);
                log.debug("darks logs test debug " + i);
                log.warn("darks logs test warn " + i);
                log.error("darks logs test error " + i);
            }
            testException();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }

}
