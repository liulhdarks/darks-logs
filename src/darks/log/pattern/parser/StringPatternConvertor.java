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

/**
 * Format a string directly
 * 
 * StringPatternConvertor.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class StringPatternConvertor extends PatternConvertor
{
    private String data;
    
    public StringPatternConvertor(String data)
    {
        this.data = data;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean format(StringBuilder buf, LogMessage message)
    {
        if (data == null)
        {
        	return false;
        }
        buf.append(data);
        return true;
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public String toString()
	{
		return "StringPatternConvertor [data=" + data + "]";
	}
    
}
