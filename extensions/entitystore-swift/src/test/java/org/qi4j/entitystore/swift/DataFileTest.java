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

import org.junit.Assert;
import org.junit.Test;
import org.qi4j.api.entity.EntityReference;

import java.io.File;

public class DataFileTest
{
    @Test
    public void whenPuttingDataThenExpectSameDataBack()
        throws Exception
    {
        File dir = new File( "swift-store" );
        RecordManager man = new RecordManager( dir, false );
        try
        {
            EntityReference ref = createReference( "12345678" );
            String data = "Hej hopp du glade man!!";
            DataBlock originalData = new DataBlock( ref, data.getBytes(), 81273, 91283 );
            man.putData( originalData );

            DataBlock retrieveData = man.readData( ref );
            Assert.assertEquals( "Incorrect Data retrieved.", originalData, retrieveData );
        }
        finally
        {
            FileUtils.delete( dir );
        }
    }

    @Test
    public void whenPutting100DataWithIndividualCommitsThenExpectSameDataBack()
        throws Exception
    {
        File dir = new File( "swift-store" );
        RecordManager man = new RecordManager( dir, false );
        try
        {
            EntityReference[] ids = new EntityReference[100];
            String[] values = new String[100];
            int[] schemas = new int[100];
            long[] versions = new long[100];
            long t0 = System.currentTimeMillis();
            for( int i = 0; i < 100; i++ )
            {
                ids[ i ] = createReference( "habba" + i );
                values[ i ] = "Hej hopp du glade man!!" + Math.random();
                DataBlock originalData = new DataBlock( ids[ i ], values[ i ].getBytes(), versions[ i ], schemas[ i ] );
                man.putData( originalData );
                man.commit();
            }
            long t1 = System.currentTimeMillis();
            System.out.println( "Thruoughput: " + 100000 / ( t1 - t0 ) + " creations/sec" );
            for( int i = 0; i < 100; i++ )
            {
                DataBlock data = man.readData( ids[ i ] );
                Assert.assertEquals( "Incorrect Data retrieved.", ids[ i ], data.reference );
                Assert.assertEquals( "Incorrect Data retrieved.", versions[ i ], data.instanceVersion );
                Assert.assertEquals( "Incorrect Data retrieved.", values[ i ], new String( data.data ) );
                Assert.assertEquals( "Incorrect Data retrieved.", schemas[ i ], data.schemaVersion );
            }
        }
        finally
        {
            FileUtils.delete( dir );
        }
    }

    @Test
    public void whenPutting100DataWithSingleCommitThenExpectSameDataBack()
        throws Exception
    {
        File dir = new File( "swift-store" );
        RecordManager man = new RecordManager( dir, false );
        try
        {
            EntityReference[] ids = new EntityReference[100];
            String[] values = new String[100];
            int[] schemas = new int[100];
            long[] versions = new long[100];
            long t0 = System.currentTimeMillis();
            for( int i = 0; i < 100; i++ )
            {
                ids[ i ] = createReference( "habba" + i );
                values[ i ] = "Hej hopp du glade man!!" + Math.random();
                DataBlock originalData = new DataBlock( ids[ i ], values[ i ].getBytes(), versions[ i ], schemas[ i ] );
                man.putData( originalData );
            }
            man.commit();
            long t1 = System.currentTimeMillis();
            System.out.println( "Thruoughput: " + 100000 / ( t1 - t0 ) + " creations/sec" );
            for( int i = 0; i < 100; i++ )
            {
                DataBlock data = man.readData( ids[ i ] );
                Assert.assertEquals( "Incorrect Data retrieved.", ids[ i ], data.reference );
                Assert.assertEquals( "Incorrect Data retrieved.", versions[ i ], data.instanceVersion );
                Assert.assertEquals( "Incorrect Data retrieved.", values[ i ], new String( data.data ) );
                Assert.assertEquals( "Incorrect Data retrieved.", schemas[ i ], data.schemaVersion );
            }
        }
        finally
        {
            FileUtils.delete( dir );
        }
    }


    @Test
    public void whenEntityReferenceIsAtMaxThenExpectSameDataBack()
        throws Exception
    {
        File dir = new File( "swift-store" );
        RecordManager man = new RecordManager( dir, false );
        try
        {
            EntityReference ref = createReference( "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678912" );
            String data = "Hej hopp du glade man!!";
            DataBlock originalData = new DataBlock( ref, data.getBytes(), 81273, 91283 );
            man.putData( originalData );
            man.commit();

            DataBlock retrieveData = man.readData( ref );
            Assert.assertEquals( "Incorrect Data retrieved.", originalData, retrieveData );
        }
        finally
        {
            FileUtils.delete( dir );
        }

    }

    @Test
    public void whenTooLongEntityReferenceThenExpectException()
        throws Exception
    {
        File dir = new File( "swift-store" );
        RecordManager man = new RecordManager( dir, false );
        try
        {
            StringBuffer buf = new StringBuffer();
            for( int i=0 ; i < 129 ; i++ )
                buf.append( (i % 10) );
            EntityReference ref = createReference( buf.toString() );
            
            String data = "Hej hopp du glade man!!";
            DataBlock originalData = new DataBlock( ref, data.getBytes(), 81273, 91283 );
            man.putData( originalData );
            Assert.fail( "Exceeeding max length didn't cause Exception." );
        }
        catch( IdentityTooLongException e )
        {
            // Expected.
        }
        finally
        {
            FileUtils.delete( dir );
        }

    }

    private EntityReference createReference( String identity )
    {
        return new EntityReference( identity );
    }
}
