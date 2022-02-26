package ua.org.serverhelp;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class MessageProcessor extends Thread{
    private final Semaphore semaphore;
    private final HashMap<String,Long> metrics;
    private final String message;

    public MessageProcessor(HashMap<String, Long> metrics, Semaphore semaphore, String message) {
        this.metrics=metrics;
        this.message = message;
        this.semaphore=semaphore;
    }

    @Override
    public void run() {
        Pattern pattern=Pattern.compile(".*nginx:(.*)");
        Matcher matcher= pattern.matcher(message);
        try {
            if (matcher.find()) {
                JSONObject jsonMessage= new JSONObject(matcher.group(1));
                String key= "nginx_access_log."+jsonMessage.getString("path")+"."+jsonMessage.getString("host").replace('.', '_')+
                        "{method=\""+jsonMessage.getString("request_method")+"\",code=\""+jsonMessage.getInt("response_status")+"\"}";
                incMetric(key);
            }else{
                throw new Exception("String not match to access_log");
            }
        }catch (Exception e){
            log.error("JSON parse error", e);
        }
    }

    private void incMetric(String metric){
        try {
            semaphore.acquire();
            Long count = metrics.get(metric);
            if (count == null) {
                count = 0L;
            }
            count++;
            metrics.put(metric, count);
        }catch (InterruptedException e){
            log.error("Error semaphore acquire",e);
        }finally {
            semaphore.release();
        }
    }
}
