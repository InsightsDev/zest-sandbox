package org.qi4j.osgi;

import org.qi4j.api.property.Property;
import org.qi4j.api.common.Optional;

public interface AComposite
{
    @Optional Property<String> property();

    String sayValue();
}
