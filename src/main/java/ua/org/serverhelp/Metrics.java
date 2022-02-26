package ua.org.serverhelp;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Log4j2
public class Metrics {
    private final Semaphore semaphore=new Semaphore(1);
    private HashMap<String,Long> messages=new HashMap<>();
    @Getter
    private String metricList="";

    public void processNewMessage(String message) {
        new MessageProcessor(messages,semaphore, message).start();
    }

    public void commitMetrics() {
        try {
            semaphore.acquire();
            StringBuilder responseBody=new StringBuilder();
            for (Map.Entry<String, Long> entry:messages.entrySet()){
                responseBody.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
            }
            metricList= responseBody.toString();
            messages=new HashMap<>();
        }catch (InterruptedException e){
            log.error("Error semaphore acquire",e);
        }finally {
            semaphore.release();
        }
    }
}
