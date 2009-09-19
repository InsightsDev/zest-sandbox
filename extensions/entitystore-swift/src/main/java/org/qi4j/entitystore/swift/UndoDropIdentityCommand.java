/*
 * Copyright 2008 Niclas Hedhman.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.entitystore.swift;


import java.io.IOException;
import java.io.RandomAccessFile;
import org.qi4j.api.entity.EntityReference;

public class UndoDropIdentityCommand
    implements UndoCommand
{
    private EntityReference reference;
    private long position;

    public UndoDropIdentityCommand( EntityReference reference, long position )
    {
        this.reference = reference;
        this.position = position;
    }

    public void undo( RandomAccessFile dataFile, IdentityFile idFile ) throws IOException
    {
        idFile.remember( reference, position );
    }

    public void save( RandomAccessFile undoJournal ) throws IOException
    {
        undoJournal.writeUTF( reference.identity() );
        undoJournal.writeLong( position );
    }

    static UndoDropIdentityCommand load( RandomAccessFile undoJournal )
        throws IOException
    {
        String idString = undoJournal.readUTF();
        EntityReference ref = new EntityReference( idString );
        long pos = undoJournal.readLong();
        return new UndoDropIdentityCommand( ref, pos );
    }
}
