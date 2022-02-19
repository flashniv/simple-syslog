package ua.org.serverhelp;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageProcessor extends Thread{
    private final HashMap<String,Long> metrics;
    private final String message;

    public MessageProcessor(HashMap<String, Long> metrics, String message) {
        this.metrics=metrics;
        this.message = message;
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
                System.out.println(key);
            }else{
                throw new Exception("String not match to access_log");
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    private void incMetric(String metric){
        Long count=metrics.get(metric);
        if(count==null){
            count=0L;
        }
        count++;
        metrics.put(metric, count);
    }
}
