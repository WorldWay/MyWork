
package com.sqkj.znyj.mina;

/**
 * A {@link RuntimeException} which is thrown when a keep-alive response
 * message was not received within a certain timeout.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class KeepAliveRequestTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -1985092764656546558L;

    /**
     * Creates a new instance of a KeepAliveRequestTimeoutException
     */
    public KeepAliveRequestTimeoutException() {
        super();
    }

    /**
     * Creates a new instance of a KeepAliveRequestTimeoutException
     * 
     * @param message The detail message
     * @param cause The Exception's cause
     */
    public KeepAliveRequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of a KeepAliveRequestTimeoutException
     * 
     * @param message The detail message
     */
    public KeepAliveRequestTimeoutException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of a KeepAliveRequestTimeoutException
     * 
     * @param cause The Exception's cause
     */
    public KeepAliveRequestTimeoutException(Throwable cause) {
        super(cause);
    }
}
