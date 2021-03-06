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
package org.qi4j.entitystore.s3;

import org.junit.Test;
import org.qi4j.api.common.Visibility;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.test.entity.AbstractEntityStoreTest;

/**
 * Amazon S3 EntityStore test
 */
public abstract class S3EntityStoreTest
    extends AbstractEntityStoreTest
{
    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        super.assemble( module );
        module.addServices( S3EntityStoreService.class ).instantiateOnStartup();

        ModuleAssembly config = module.layerAssembly().moduleAssembly( "config" );
        config.addEntities( S3Configuration.class ).visibleIn( Visibility.layer );
        config.addServices( MemoryEntityStoreService.class );
    }

    @Test
    public void dummyTest()
    {
        // All tests are disabled since by default the S3 store doesn't work due to missing account keys!
    }
}