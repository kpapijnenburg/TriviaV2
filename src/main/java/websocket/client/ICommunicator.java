package websocket.client;

import websocket.shared.Message;

import java.io.IOException;

public interface ICommunicator {

    /**
     * Start the connection.
     */
    void start();

    /**
     * Stop the connection.
     */
    void stop();

    /**
     * Register a property.
     *
     * @param property
     */
    void register(String property) throws IOException;

    /**
     * Unregister a property.
     *
     * @param property
     */
    void unregister(String property) throws IOException;

    /**
     * Subscribe to a property.
     *
     * @param property
     */
    void subscribe(String property) throws IOException;

    /**
     * Unsubscribe from a property.
     *
     * @param property
     */
    void unsubscribe(String property) throws IOException;

    /**
     * Update a property by sending a message to all clients
     * that are subscribed to the property of the message.
     *
     * @param message the message to be sent
     */
    void update(Message message) throws IOException;

    /**
     * Sends all current game objects connected to the lobby channel
     *
     *
     * @param channel the message to be sent
     */

    void connect(String channel) throws IOException;

}
