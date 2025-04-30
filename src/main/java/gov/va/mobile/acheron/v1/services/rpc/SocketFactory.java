package gov.va.mobile.acheron.v1.services.rpc;

import gov.va.mobile.acheron.v1.AppProperties;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Factory class used for creating {@link Socket} connections to the external VistA system.
 *
 * @author scott.thompson@apothesource.com
 * @see Socket
 * @since 1.0
 */

@RequiredArgsConstructor
public class SocketFactory {

    /**
     * The application properties containing the "connection" and "read" timeout values used when creating
     * {@link Socket} connections.
     *
     * @see AppProperties
     */
    private final AppProperties appProperties;

    /**
     * Creates a new {@link Socket} connection to a remote VistA instance. The socket is bound to the local host address
     * and a random available local port between 1024 and 65000.
     *
     * @param localhost
     *         the {@link InetAddress} representing the host address of the machine where the Acheron application is
     *         running
     * @param hostname
     *         the host name of the remote VistA instance to connect to
     * @param port
     *         the port of the remote VistA instance to connect to
     *
     * @return the {@link Socket} connected to a remote VistA instance
     *
     * @throws IOException
     *         if the {@link Socket} cannot be created
     * @see Socket
     */
    @SuppressWarnings("java:S2095") // Suppress SonarQube warning
    public Socket create(final InetAddress localhost, final String hostname, final int port) throws IOException {

        final int connectionTimeout = Math.toIntExact(appProperties.getVistaConnectionTimeoutMillis());
        final int readTimeout = Math.toIntExact(appProperties.getVistaReadTimeoutMillis());
        
        final Socket socket = new Socket();
        socket.setKeepAlive(true);
        socket.bind(new InetSocketAddress(0));
        InetSocketAddress target = new InetSocketAddress(hostname, port);
        socket.connect(target, connectionTimeout);
        socket.setSoTimeout(readTimeout);
        return socket;
    }

    /**
     * Returns the {@link InetAddress} representing the host address of the machine where the Acheron application is
     * running.
     *
     * @return the {@link InetAddress} representing the host address
     *
     * @throws UnknownHostException
     *         if the local host address cannot be determined
     * @see InetAddress#getLocalHost()
     */
    public InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }
}