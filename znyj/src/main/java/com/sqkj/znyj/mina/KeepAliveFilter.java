package com.sqkj.znyj.mina;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class KeepAliveFilter extends IoFilterAdapter {

    private final AttributeKey WAITING_FOR_RESPONSE = new AttributeKey(getClass(), "waitingForResponse");

    private final AttributeKey IGNORE_READER_IDLE_ONCE = new AttributeKey(getClass(), "ignoreReaderIdleOnce");

    private final KeepAliveMessageFactory messageFactory;

    private final IdleStatus interestedIdleStatus;

    private volatile KeepAliveRequestTimeoutHandler requestTimeoutHandler;

    private volatile int requestInterval;

    private volatile int requestTimeout;

    private volatile boolean forwardEvent;

    /**
     * 
     * @param messageFactory The message factory to use 
     */
    public KeepAliveFilter(KeepAliveMessageFactory messageFactory) {
        this(messageFactory, IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.CLOSE);
    }

    /**
     * 
     * @param messageFactory The message factory to use 
     * @param interestedIdleStatus The IdleStatus the filter is interested in
     */
    public KeepAliveFilter(KeepAliveMessageFactory messageFactory, IdleStatus interestedIdleStatus) {
        this(messageFactory, interestedIdleStatus, KeepAliveRequestTimeoutHandler.CLOSE, 60, 30);
    }

    /**
     * 
     * @param messageFactory The message factory to use 
     * @param policy The TimeOut handler policy
     */
    public KeepAliveFilter(KeepAliveMessageFactory messageFactory, KeepAliveRequestTimeoutHandler policy) {
        this(messageFactory, IdleStatus.READER_IDLE, policy, 60, 30);
    }

    /**
     * 
     * @param messageFactory The message factory to use 
     * @param interestedIdleStatus The IdleStatus the filter is interested in
     * @param policy The TimeOut handler policy
     */
    public KeepAliveFilter(KeepAliveMessageFactory messageFactory, IdleStatus interestedIdleStatus,
            KeepAliveRequestTimeoutHandler policy) {
        this(messageFactory, interestedIdleStatus, policy, 60, 30);
    }

    /**
     * @param messageFactory The message factory to use 
     * @param interestedIdleStatus The IdleStatus the filter is interested in
     * @param policy The TimeOut handler policy
     * @param keepAliveRequestInterval the interval to use
     * @param keepAliveRequestTimeout The timeout to use
     */
    public KeepAliveFilter(KeepAliveMessageFactory messageFactory, IdleStatus interestedIdleStatus,
            KeepAliveRequestTimeoutHandler policy, int keepAliveRequestInterval, int keepAliveRequestTimeout) {
        if (messageFactory == null) {
            throw new IllegalArgumentException("messageFactory");
        }
        
        if (interestedIdleStatus == null) {
            throw new IllegalArgumentException("interestedIdleStatus");
        }
        
        if (policy == null) {
            throw new IllegalArgumentException("policy");
        }

        this.messageFactory = messageFactory;
        this.interestedIdleStatus = interestedIdleStatus;
        requestTimeoutHandler = policy;

        setRequestInterval(keepAliveRequestInterval);
        setRequestTimeout(keepAliveRequestTimeout);
    }

    /**
     * @return The {@link IdleStatus} 
     */
    public IdleStatus getInterestedIdleStatus() {
        return interestedIdleStatus;
    }

    /**
     * @return The timeout request handler
     */
    public KeepAliveRequestTimeoutHandler getRequestTimeoutHandler() {
        return requestTimeoutHandler;
    }

    /**
     * Set the timeout handler
     * 
     * @param timeoutHandler The instance of {@link KeepAliveRequestTimeoutHandler} to use
     */
    public void setRequestTimeoutHandler(KeepAliveRequestTimeoutHandler timeoutHandler) {
        if (timeoutHandler == null) {
            throw new IllegalArgumentException("timeoutHandler");
        }
        requestTimeoutHandler = timeoutHandler;
    }

    /**
     * @return the interval for keep alive messages
     */
    public int getRequestInterval() {
        return requestInterval;
    }

    /**
     * Sets the interval for keepAlive messages
     * 
     * @param keepAliveRequestInterval the interval to set
     */
    public void setRequestInterval(int keepAliveRequestInterval) {
        if (keepAliveRequestInterval <= 0) {
            throw new IllegalArgumentException("keepAliveRequestInterval must be a positive integer: "
                    + keepAliveRequestInterval);
        }
        
        requestInterval = keepAliveRequestInterval;
    }

    /**
     * @return The timeout
     */
    public int getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the timeout
     * 
     * @param keepAliveRequestTimeout The timeout to set
     */
    public void setRequestTimeout(int keepAliveRequestTimeout) {
        if (keepAliveRequestTimeout <= 0) {
            throw new IllegalArgumentException("keepAliveRequestTimeout must be a positive integer: "
                    + keepAliveRequestTimeout);
        }
        
        requestTimeout = keepAliveRequestTimeout;
    }

    /**
     * @return The message factory
     */
    public KeepAliveMessageFactory getMessageFactory() {
        return messageFactory;
    }

    /**
     * @return <tt>true</tt> if and only if this filter forwards
     * a {@link IoEventType#SESSION_IDLE} event to the next filter.
     * By default, the value of this property is <tt>false</tt>.
     */
    public boolean isForwardEvent() {
        return forwardEvent;
    }

    /**
     * Sets if this filter needs to forward a
     * {@link IoEventType#SESSION_IDLE} event to the next filter.
     * By default, the value of this property is <tt>false</tt>.
     * 
     * @param forwardEvent a flag set to tell if the filter has to forward a {@link IoEventType#SESSION_IDLE} event
     */
    public void setForwardEvent(boolean forwardEvent) {
        this.forwardEvent = forwardEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPreAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        if (parent.contains(this)) {
            throw new IllegalArgumentException("You can't add the same filter instance more than once. "
                    + "Create another instance and add it.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPostAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        resetStatus(parent.getSession());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPostRemove(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        resetStatus(parent.getSession());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        try {
            if (messageFactory.isRequest(session, message)) {
                Object pongMessage = messageFactory.getResponse(session, message);

                if (pongMessage != null) {
                    nextFilter.filterWrite(session, new DefaultWriteRequest(pongMessage));
                }
            }

            if (messageFactory.isResponse(session, message)) {
                resetStatus(session);
            }
        } finally {
            if (!isKeepAliveMessage(session, message)) {
                nextFilter.messageReceived(session, message);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        Object message = writeRequest.getMessage();
        
        if (!isKeepAliveMessage(session, message)) {
            nextFilter.messageSent(session, writeRequest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
        if (status == interestedIdleStatus) {
            if (!session.containsAttribute(WAITING_FOR_RESPONSE)) {
                Object pingMessage = messageFactory.getRequest(session);
                
                if (pingMessage != null) {
                    nextFilter.filterWrite(session, new DefaultWriteRequest(pingMessage));

                    // If policy is OFF, there's no need to wait for
                    // the response.
                    if (getRequestTimeoutHandler() != KeepAliveRequestTimeoutHandler.DEAF_SPEAKER) {
                        markStatus(session);
                        if (interestedIdleStatus == IdleStatus.BOTH_IDLE) {
                            session.setAttribute(IGNORE_READER_IDLE_ONCE);
                        }
                    } else {
                        resetStatus(session);
                    }
                }
            } else {
                handlePingTimeout(session);
            }
        } else if (status == IdleStatus.READER_IDLE) {
            if (session.removeAttribute(IGNORE_READER_IDLE_ONCE) == null) {
                if (session.containsAttribute(WAITING_FOR_RESPONSE)) {
                    handlePingTimeout(session);
                }
            }
        }

        if (forwardEvent) {
            nextFilter.sessionIdle(session, status);
        }
    }

    private void handlePingTimeout(IoSession session) throws Exception {
        resetStatus(session);
        KeepAliveRequestTimeoutHandler handler = getRequestTimeoutHandler();
        if (handler == KeepAliveRequestTimeoutHandler.DEAF_SPEAKER) {
            return;
        }

        handler.keepAliveRequestTimedOut(this, session);
    }

    private void markStatus(IoSession session) {
        session.getConfig().setIdleTime(interestedIdleStatus, 0);
        session.getConfig().setReaderIdleTime(getRequestTimeout());
        session.setAttribute(WAITING_FOR_RESPONSE);
    }

    private void resetStatus(IoSession session) {
        session.getConfig().setReaderIdleTime(0);
        session.getConfig().setWriterIdleTime(0);
        session.getConfig().setIdleTime(interestedIdleStatus, getRequestInterval());
        session.removeAttribute(WAITING_FOR_RESPONSE);
    }

    private boolean isKeepAliveMessage(IoSession session, Object message) {
        return messageFactory.isRequest(session, message) || messageFactory.isResponse(session, message);
    }
}
