package gov.va.mobile.acheron.v1.services.rpc.connection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Contains information needed to establish a connection to an external VistA system.
 *
 * @author scott.thompson@apothesource.com
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VistaConnectionInfo {

    /**
     * The name of the VistA site instance (optional).
     */
    private String instance;
    
    private boolean useForDefault;

    /**
     * The VistA site identifier.
     */
    private String site;

    /**
     * The hostname of the VistA instance.
     */
    private String host;

    /**
     * The port of the VistA instance.
     */
    private Integer port;

    /**
     * The optional timeout duration of a VistA connection
     */
    private Duration timeoutDuration;

    /**
     * Overrides for connection pooling behavior.
     */
    private final Pool pool = new Pool();

    /**
     * Configuration properties for connection pooling overrides.
     */
    @Data
    public static class Pool {

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This setting only has an effect if
         * both it and time between eviction runs are positive.
         */
        private Integer minIdle;

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to indicate an unlimited number of
         * idle connections.
         */
        private Integer maxIdle;

        /**
         * Maximum number of connections that can be allocated by the pool at a given time. Range is limited between 1
         * and 100 connections.
         */
        private Integer maxTotal;

        /**
         * Set and verify the provided {@code minIdle} value.
         *
         * @param minIdle
         *         the {@link #minIdle} value to set
         *
         * @see #minIdle
         */
        public void setMinIdle(final Integer minIdle) {
            this.minIdle = minIdle;
        }

        /**
         * Set and verify the provided {@code maxIdle} value.
         *
         * @param maxIdle
         *         the {@link #maxIdle} value to set
         *
         * @see #maxIdle
         */
        public void setMaxIdle(final Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        /**
         * Set and verify the provided {@code maxTotal} value.
         *
         * @param maxTotal
         *         the {@link #maxTotal} value to set
         *
         * @see #maxTotal
         */
        public void setMaxTotal(final Integer maxTotal) {
            this.maxTotal = maxTotal;
        }
    }
}