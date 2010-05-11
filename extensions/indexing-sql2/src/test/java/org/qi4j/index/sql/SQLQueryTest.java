/*
 * Copyright (c) 2010, Stanislav Muhametsin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package org.qi4j.index.sql;

import org.qi4j.api.common.Visibility;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.AssemblyVisitor;
import org.qi4j.bootstrap.EntityDeclaration;
import org.qi4j.bootstrap.ImportedServiceDeclaration;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.ObjectDeclaration;
import org.qi4j.bootstrap.ServiceDeclaration;
import org.qi4j.bootstrap.TransientDeclaration;
import org.qi4j.bootstrap.ValueDeclaration;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.index.sql.RDBConfiguration;
import org.qi4j.index.sql.assembly.SQLIndexingAssembler;
import org.qi4j.spi.structure.ApplicationSPI;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;
import org.qi4j.test.indexing.AbstractQueryTest;

/**
 *
 * @author Stanislav Muhametsin
 */
public class SQLQueryTest extends AbstractQueryTest
{

   @Override
   protected void setupTest(ModuleAssembly mainModule) throws AssemblyException
   {
      SQLIndexingAssembler ass = new SQLIndexingAssembler();
      ass.assemble(mainModule);
      
      mainModule.addServices(MemoryEntityStoreService.class, UuidIdentityGeneratorService.class).visibleIn(Visibility.application);
   }
   
   @Override
   protected void tearDownTest()
   {
   }

}
