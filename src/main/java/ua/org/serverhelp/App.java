package ua.org.serverhelp;

import lombok.extern.log4j.Log4j2;
import ua.org.serverhelp.cron.Cron;
import ua.org.serverhelp.cron.MetricsCommitCronTask;
import ua.org.serverhelp.httpserver.WebInterface;

@Log4j2
public class App {
    public static void main(String[] args) {
        try{
            UDPServer udpServer=new UDPServer();
            //Web interface
            WebInterface webInterface=new WebInterface(udpServer.getMetrics());
            webInterface.listen();
            //Cron
            Cron cron=new Cron();
            MetricsCommitCronTask metricsCommitCronTask=new MetricsCommitCronTask(udpServer.getMetrics());
            cron.addCronTask(metricsCommitCronTask);
            cron.start();

            udpServer.listen();
        }catch (Exception e) {
            log.error("Error listening", e);
        }
    }
}
