package websocket.server;

import application.model.Game;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import application.model.MultiPlayerGame;
import websocket.shared.Message;
import websocket.shared.Operation;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/game/")
public class ServerWebSocket {

    // List of all sessions
    private static final List<Session> sessions = new ArrayList<>();

    // Map of each list of sessions that are subscribed to that channel
    private static final Map<String, List<Session>> channels = new HashMap<>();

    // List of all games
    private static final List<MultiPlayerGame> games = new ArrayList<>();

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("new connection: sessionID: " + session.getId());
        sessions.add(session);
        System.out.println("Total number of sessions: " + sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) throws IOException {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    // Incoming Message handling from client
    private void handleMessageFromClient(String s, Session session) throws IOException {
        Gson gson = new Gson();
        Message message;

        try {
            message = gson.fromJson(s, Message.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Cannot parse JSON " + s);
            return;
        }

        // get the operation the message needs to perform
        Operation operation;
        operation = message.getOperation();

        // process based on which operations needs to be handled
        String channel = message.getChannel();
        if (null != operation && null != channel && !"".equals(channel)) {
            switch (operation) {
                case REGISTER:
                    // Register channel if not registered yet
                    if (channels.get(channel) == null) {
                        channels.put(channel, new ArrayList<>());
                    }
                    break;
                case UNREGISTER:
                    break;
                case SUBSCRIBE:
                    //subscribe channel if this has not been done.
                    if (channels.get(channel) != null) {
                        channels.get(channel).add(session);
                    }
                    break;
                case UNSUBSCRIBE:
                    // Unsubscribe if the channel has been subscribed
                    if (channels.get(channel) != null) {
                        channels.get(channel).remove(session);
                    }
                    break;
                case UPDATE:
                    // Send message to all clients subscribed to this channel
                    if (channels.get(channel) != null) {
                        if (channel.equals("Lobby")) {
                            addToGames(s);
                        }
                        System.out.println("[WebSocket send ] " + s + " to:");
                        for (Session sess : channels.get(channel)) {
                            sess.getBasicRemote().sendText(s);
                        }
                    }
                    break;
                case CONNECTED:
                    if (channel.equals("Lobby")) {
                        getAllGames(s);
                    }
                    break;
                case REQUEST:
                    if (channels.get(channel) != null){
                        getGame(s);
                    }
                    break;
                default:
                    System.out.println("Cannot parse JSON " + s);
            }
        }
    }

    private void removeGame(String s) {
        Gson gson = new Gson();

        Message message = gson.fromJson(s, Message.class);

        for (MultiPlayerGame game: games){
            if (game.getGameName().equals(message.getChannel())){
                games.remove(game);
            }
        }
    }

    private void getGame(String s) {
        Gson gson = new Gson();

        Message recievedMessage = gson.fromJson(s, Message.class);

        MultiPlayerGame gameToReturn = null;

        for (MultiPlayerGame game: games){
            if (game.getGameName().equals(recievedMessage.getContent())){
                gameToReturn = game;
            }
        }

        Message toSend = new Message();
        toSend.setContent(gson.toJson(gameToReturn));
        toSend.setOperation(Operation.UPDATE);
        toSend.setChannel(gameToReturn.getGameName());

        if (gameToReturn != null) {
            for (Session session : channels.get(gameToReturn.getGameName())) {
                try{
                    session.getBasicRemote().sendText(gson.toJson(toSend));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addToGames(String s) {
        Gson gson = new Gson();

        Message message = gson.fromJson(s, Message.class);

        MultiPlayerGame game = gson.fromJson(message.getContent(), MultiPlayerGame.class);
        games.add(game);
    }

    private void getAllGames(String s) {
        Gson gson = new Gson();

        Message message = gson.fromJson(s, Message.class);
        message.setContent(gson.toJson(games));

        for (Session session : channels.get("Lobby")) {
            try {
                session.getBasicRemote().sendText(gson.toJson(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
