package ua.org.serverhelp;

import java.util.HashMap;

public class Metrics {
    private final HashMap<String,Long> messages=new HashMap<>();

    public void processNewMessage(String message) {
        new MessageProcessor(messages, message).start();
    }
}
