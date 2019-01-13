package websocket.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import websocket.shared.Message;
import websocket.shared.Operation;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ClientWebSocket extends Communicator {
    // using a singleton to ensure there is only one instance of this client.
    private static ClientWebSocket instance = null;

    private final String uri = "ws://localhost:9000/game/";
    private Session session;
    private String message;

    private Gson gson;

    // status of WS client;
    boolean isRunning = false;

    private ClientWebSocket(){
        gson = new Gson();
    }

    public static ClientWebSocket getInstance(){
        if (instance == null){
            instance = new ClientWebSocket();
        }
        return instance;
    }

    // Start connection
    @Override
    public void start() {
        if (!isRunning){
            isRunning = true;
            startClient();
        }
    }


    @Override
    public void stop() {
        if (isRunning){
            isRunning = false;
            stopClient();
        }
    }

    @OnOpen
    public void onClientConnect(Session session){
        this.session = session;
    }

    @OnMessage
    public void onClientText(String message, Session session){
        this.message = message;
        processMessage(message);
    }

    @OnError
    public void onClientError(Throwable cause){
        System.out.println("Connection error: " + cause.getMessage());
    }

    @OnClose
    public void onClientClose(CloseReason reason){
        System.out.println("Client closed with reason: " + reason);
        session = null;
    }

    @Override
    public void register(String channel) throws IOException {
        Message message = new Message();
        message.setOperation(Operation.REGISTER);
        message.setChannel(channel);

        sendToServer(message);
    }


    @Override
    public void unregister(String channel) throws IOException {
        Message message = new Message();
        message.setOperation(Operation.UNREGISTER);
        message.setChannel(channel);

        sendToServer(message);
    }

    @Override
    public void subscribe(String channel) throws IOException {
        Message message = new Message();
        message.setOperation(Operation.SUBSCRIBE);
        message.setChannel(channel);

        sendToServer(message);
    }

    @Override
    public void unsubscribe(String channel) throws IOException {
        Message message = new Message();
        message.setOperation(Operation.UNSUBSCRIBE);
        message.setChannel(channel);

        sendToServer(message);
    }

    @Override
    public void update(Message message) throws IOException {
        Message wsMessage = new Message();
        wsMessage.setOperation(Operation.UPDATE);
        wsMessage.setChannel(message.getChannel());
        wsMessage.setContent(message.getContent());

        sendToServer(wsMessage);
    }

    @Override
    public void connect(String channel) throws IOException {
        Message wsMessage = new Message();
        wsMessage.setChannel(channel);
        wsMessage.setOperation(Operation.CONNECTED);

        sendToServer(wsMessage);
    }

    private void sendToServer(Message message) throws IOException {
        String s = gson.toJson(message);

        session.getBasicRemote().sendText(s);
    }

    // Start a new websocket client
    private void startClient() {
        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Stop client when running
    private void stopClient() {
        try{
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Process incoming json message
    private void processMessage(String message) {

        // Parse message
        Message wsMessage = null;
        try{
            wsMessage = gson.fromJson(message, Message.class);
        } catch (JsonSyntaxException e){
            System.out.println("Cannot parse JSON in ClientWebSocket" + message);
        }

        // The only operation to be processed will be the Update
        Operation operation;
        operation = wsMessage.getOperation();

        if (operation == null || operation != Operation.UPDATE && operation != Operation.CONNECTED){
            return;
        }

        // Obtain channel from message
        String channel = wsMessage.getChannel();
        if (channel == null || "".equals(channel)){
            return;
        }

        // Obtain content from message
        String content = wsMessage.getContent();
        if (content == null || "".equals(content)){
            return;
        }

        // Obtain operation from message


        // Create object to be send to observers.
        Message observerMessage = new Message();
        observerMessage.setChannel(channel);
        observerMessage.setContent(content);
        observerMessage.setOperation(operation);

        // Notify observers
        this.setChanged();
        this.notifyObservers(observerMessage);

    }

}
