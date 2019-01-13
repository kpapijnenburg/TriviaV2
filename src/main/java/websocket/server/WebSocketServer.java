package websocket.server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

public class WebSocketServer {
    private static final int PORT = 9000;

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {

        // Specify maximum amount of threads to be used.
        QueuedThreadPool threadPool = new QueuedThreadPool(10,1);

        //start the server
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        // Setup context and handlers
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try{
            // initialize websockets
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);

            // Add ws eindpoint
            container.addEndpoint(ServerWebSocket.class);

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
