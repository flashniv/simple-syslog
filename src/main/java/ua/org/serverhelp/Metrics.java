package ua.org.serverhelp;

import lombok.Getter;

import java.util.HashMap;

public class Metrics {
    @Getter
    private final HashMap<String,Long> messages=new HashMap<>();

    public void processNewMessage(String message) {
        new MessageProcessor(messages, message).start();
    }
}
