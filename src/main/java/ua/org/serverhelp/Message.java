package ua.org.serverhelp;

import lombok.Data;

import java.time.Instant;

@Data
public class Message {
    private Instant instant=Instant.now();
    private String subPath;
    private int data;
}
