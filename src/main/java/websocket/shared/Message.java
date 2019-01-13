package websocket.shared;

public class Message {
    private Operation operation;
    private String channel;
    private String content;

    public Message(Operation operation, String channel, String content) {
        this.operation = operation;
        this.channel = channel;
        this.content = content;
    }

    public Message(String channel, String content) {
        this.channel = channel;
        this.content = content;
    }

    public Message() {
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
