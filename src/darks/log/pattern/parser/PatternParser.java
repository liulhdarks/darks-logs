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

import darks.log.exceptions.PatternException;
import darks.log.utils.StringUtils;

/**
 * PatternParser.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class PatternParser
{

    private String pattern;

    private PatternConvertor head;

    private PatternConvertor tail;

    public PatternParser()
    {

    }

    public PatternParser(String pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Validate whether pattern is available
     * 
     * @return if available, return true
     */
    public boolean validate()
    {
        if (pattern == null)
        {
            return false;
        }
        pattern = pattern.trim();
        if ("".equals(pattern))
        {
            return false;
        }
        return true;
    }

    /**
     * Get {@linkplain darks.log.pattern.parser.PatternConvertor
     * PatternConvertor} object
     * 
     * @return {@linkplain darks.log.pattern.parser.PatternConvertor
     *         PatternConvertor}
     */
    public PatternConvertor parseConvertor()
    {
        StringBuilder buf = new StringBuilder(32);
        int index = 0;
        while (index < pattern.length())
        {
            char ch = pattern.charAt(index++);

            switch (ch)
            {
            case '%':
                index = handleParams(buf, index);
                break;
            default:
                buf.append(ch);
                break;
            }
        }
        if (buf.length() > 0)
        {
            addConvertor(new StringPatternConvertor(buf.toString()));
            buf.setLength(0);
        }
        return head;
    }

    private int handleParams(StringBuilder buf, int index)
    {
        char ch = pattern.charAt(index++);
        switch (ch)
        {
        case '%': // print '%'
            buf.append(ch);
            break;
        case 'N':
        case 'n': // return character
            buf.append(StringUtils.LINE_RETURN);
            break;
        default:
            addConvertor(new StringPatternConvertor(buf.toString()));
            buf.setLength(0);
            index = handleOptions(--index);
            break;
        }
        return index;
    }

    private void addConvertor(PatternConvertor convertor)
    {
        if (head == null)
        {
            head = convertor;
            tail = convertor;
        }
        else
        {
            tail.setNext(convertor);
            tail = convertor;
        }
    }

    private int handleOptions(int index)
    {
        PatternConvertor convert = null;
        char ch = pattern.charAt(index++);
        switch (ch)
        {
        case 'M':
        case 'm': // log message
            convert = new MessagePatternConvertor();
            break;
        case 'D':
        case 'd': // date
            convert = new DatePatternConvertor();
            break;
        case 'C':
            convert = new TagPatternConvertor(true);
            break;
        case 'c': // namespace
            convert = new TagPatternConvertor(false);
            break;
        case 'F':
        case 'f': // code source file name
            convert = new FilenamePatternConvertor();
            break;
        case 'L': // source line position
            convert = new SourcePatternConvertor();
            break;
        case 'l': // log event position detail information
            convert = new EventDetailPatternConvertor();
            break;
        case 'P':
        case 'p': // log level
            convert = new LogLevelPatternConvertor();
            break;
        case 'R':
        case 'r': // the milliseconds from startup
            convert = new DuringTimePatternConvertor();
            break;
        case 'T':
        case 't': // thread name
            convert = new ThreadPatternConvertor();
            break;
        default:
            throw new PatternException("Invalid pattern character '" + ch
                    + "\'");
        }
        if (convert != null)
        {
            StringBuilder buf = new StringBuilder();
            index = handleToken(buf, index);
            convert.setToken(buf.toString());
            addConvertor(convert);
        }
        return index;
    }

    private int handleToken(StringBuilder buf, int index)
    {
        boolean started = false;
        boolean finish = false;
        while (index < pattern.length() && !finish)
        {
            char ch = pattern.charAt(index++);
            switch (ch)
            {
            case '{':
                started = true;
                finish = false;
                break;
            case '}':
                started = false;
                finish = true;
                break;
            default:
                if (started)
                {
                    buf.append(ch);
                }
                else if (ch != ' ')
                {
                    index--;
                    finish = true;
                }
                break;
            }
        }
        return index;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

}
