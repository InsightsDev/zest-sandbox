/*  Copyright 2008 Rickard Öberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.entitystore.rmi;

import org.qi4j.api.entity.EntityReference;
import org.qi4j.io.Input;
import org.qi4j.io.Output;
import org.qi4j.spi.entitystore.EntityStoreException;

import java.io.IOException;
import java.io.Reader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.qi4j.api.service.ServiceActivation;
import org.qi4j.spi.entitystore.helpers.MapEntityStore;

/**
 * RMI client implementation of Entity
 */
public class ClientRmiEntityStoreMixin
    implements ServiceActivation, MapEntityStore
{
    private RemoteEntityStore remote;

    // Activatable implementation
    public void activateService()
        throws Exception
    {
        Registry registry = LocateRegistry.getRegistry( "localhost" );
        remote = (RemoteEntityStore) registry.lookup( ServerRmiEntityStoreService.class.getSimpleName() );
    }

    public void passivateService()
        throws Exception
    {
        remote = null;
    }

    public Reader get( EntityReference entityReference )
        throws EntityStoreException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Input<Reader, IOException> entityStates()
    {
        return new Input<Reader, IOException>()
        {
            public <ReceiverThrowableType extends Throwable> void transferTo( Output<? super Reader, ReceiverThrowableType> output )
                throws IOException, ReceiverThrowableType
            {
                // TODO Implement this
                throw new UnsupportedOperationException( "Not supported yet." );
            }
        };
    }

    public void applyChanges( MapChanges changes )
        throws IOException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}