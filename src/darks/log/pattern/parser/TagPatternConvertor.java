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

package darks.log.pattern.parser;

import darks.log.LogMessage;
import darks.log.exceptions.PatternException;
import darks.log.utils.StringUtils;

/**
 * Format class name or nsmaspace/tags
 * 
 * TagPatternConvertor.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class TagPatternConvertor extends PatternConvertor
{

    boolean blClassName;

    public TagPatternConvertor(boolean blClassName)
    {
        this.blClassName = blClassName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean format(StringBuilder buf, LogMessage message)
    {
        if (message == null)
        {
            return false;
        }
        String msg = null;
        if (blClassName)
        {
            msg = message.getClassName();
        }
        else
        {
            msg = message.getNamespace();
        }
        String token = getToken();
        if (token != null && !token.isEmpty())
        {
            try
            {
                int num = Integer.parseInt(token);
                msg = StringUtils.getTagLayer(msg, num);
            }
            catch (Exception e)
            {
                throw new PatternException(
                        "Namespace or class name's layout number cannot be '"
                                + token + "'.");
            }
        }
        buf.append(msg);
        return true;
    }

}
