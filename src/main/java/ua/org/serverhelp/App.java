package ua.org.serverhelp;

import lombok.extern.log4j.Log4j2;
import ua.org.serverhelp.httpserver.WebInterface;

@Log4j2
public class App {
    public static void main(String[] args) {
        try{
            UDPServer udpServer=new UDPServer();
            WebInterface webInterface=new WebInterface(udpServer.getMetrics());

            webInterface.listen();

            udpServer.listen();
        }catch (Exception e) {
            log.error("Error listening", e);
        }
    }
}
