/*
 * Copyright 2007 Rickard Öberg
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
*/
package org.qi4j.library.rmi;

import junit.framework.Assert;
import org.junit.Test;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.library.rmi.remote.RemoteInterface;
import org.qi4j.library.rmi.remote.RemoteInterfaceComposite;
import org.qi4j.library.rmi.remote.RemoteInterfaceImpl;
import org.qi4j.test.AbstractQi4jTest;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * JAVADOC
 */
public class RMIMixinTest
    extends AbstractQi4jTest
{
    public void assemble( ModuleAssembly module )
        throws AssemblyException
    {
        module.addTransients( RemoteInterfaceComposite.class );
    }

    @Test
    public void testRMIMixin()
        throws Exception
    {
        // Instantiate, export, and bind server object
        RemoteInterfaceImpl remoteObject = new RemoteInterfaceImpl();
        RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject( remoteObject, 0 );
        Registry registry = LocateRegistry.createRegistry( 1099 );
        registry.rebind( RemoteInterface.class.getSimpleName(), stub );

        RemoteInterface remote = transientBuilderFactory.newTransient( RemoteInterfaceComposite.class );

        // MethodCallExpression remote interface
        System.out.println( remote.foo( "Bar" ) );
//        System.out.println( remote.foo( "Bar" ) );
//        System.out.println( remote.foo( "Xyz" ) );

        try
        {
            System.out.println( remote.foo( "Zyx" ) );
            Assert.fail( "Should have thrown IOException " );
        }
        catch( IOException e )
        {
            // Ok!
        }
    }
}
