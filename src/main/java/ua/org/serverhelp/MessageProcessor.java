package ua.org.serverhelp;

import java.util.HashMap;

public class MessageProcessor extends Thread{
    private final HashMap<String,Long> metrics;
    private final String message;

    public MessageProcessor(HashMap<String, Long> metrics, String message) {
        this.metrics=metrics;
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
