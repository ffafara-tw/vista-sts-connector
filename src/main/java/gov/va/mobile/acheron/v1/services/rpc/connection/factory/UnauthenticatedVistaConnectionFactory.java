package gov.va.mobile.acheron.v1.services.rpc.connection.factory;

import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.acheron.v1.services.rpc.SocketFactory;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnection;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionInfo;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;


/**
 * Factory class for creating new {@link VistaConnection} objects used in the Commons-Pool2 connection pooling
 * framework. This class is responsible for creating, activating, validation, passivation, and destroying pooled
 * {@link VistaConnection} objects.
 * <p>
 * {@link VistaConnection} creation and passivation ensure the external
 * tcp socket is established before the object is added to the connection pool, while the activation logic no longer
 * attempts to establish the tcp socket.
 *
 * @author scott.thompson@apothesource.com
 * @see VistaConnection
 * @since 1.0
 */
@SuppressWarnings("PMD.ShortVariable")
@Slf4j
public class UnauthenticatedVistaConnectionFactory extends VistaConnectionFactory {

    /**
     * The Factory used when creating remote socket connections to the external VistA system.
     *
     * @see SocketFactory
     */
    private final SocketFactory socketFactory;


    public UnauthenticatedVistaConnectionFactory(final VistaConnectionInfo connectionInfo, final VistaConnectionValidator connectionValidator,
                                                 final SocketFactory socketFactory) {
        super(connectionInfo, connectionValidator);
        this.socketFactory = socketFactory;
    }

    @Override
    @SuppressWarnings({"PMD.CloseResource", "java:S2095"})
    public VistaConnection create() throws RPCException {
        final VistaConnection connection = new VistaConnection(
                connectionInfo.getHost(), connectionInfo.getPort(),
                socketFactory, connectionInfo.getSite(), true
        );
        connection.connect();
        connection.signOnSetup();
        log.info("Created Unauthenticated Connection");
        return connection;
    }

    @Override
    public PooledObject<VistaConnection> wrap(final VistaConnection obj) {
        return new DefaultPooledObject<>(obj);
    }
}