package gov.va.mobile.acheron.v1.services.rpc.connection.factory;

import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnection;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionInfo;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

@RequiredArgsConstructor
public abstract class VistaConnectionFactory extends BasePooledObjectFactory<VistaConnection> {

    protected final VistaConnectionInfo connectionInfo;

    /**
     * Validator used to check {@link VistaConnection} status.
     *
     * @see VistaConnectionValidator
     */
    protected final VistaConnectionValidator connectionValidator;

    @Override
    public void destroyObject(final PooledObject<VistaConnection> pooledObject) {
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(final PooledObject<VistaConnection> pooledObject) {
        final boolean valid = connectionValidator.isValid(pooledObject.getObject(), connectionInfo);

        return valid;
    }
}
