package org.apache.maven.doxia.module.confluence.parser;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.util.ByLineSource;

public class FigureBlockParser
    implements BlockParser
{
    public boolean accept( String line, ByLineSource source )
    {
        return line.startsWith( "!" ) && line.lastIndexOf( "!" ) > 1;
    }

    public Block visit( String line, ByLineSource source )
        throws ParseException
    {
        String image = line.substring( 1, line.lastIndexOf( "!" ) );
        line = line.substring( line.lastIndexOf( "!" ) + 1 ).trim();

        if ( line.startsWith( "\\\\" ) )
        {
            // ignore linebreak at start of caption
            line = line.substring( 2 );
        }

        ChildBlocksBuilder builder = new ChildBlocksBuilder();

        String caption = line + builder.appendUntilEmptyLine( source );

        if ( caption.trim().length() > 0 )
        {
            return new FigureBlock( image, caption );
        }

        return new FigureBlock( image );
    }
}