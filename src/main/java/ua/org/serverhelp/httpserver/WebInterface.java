package ua.org.serverhelp.httpserver;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.log4j.Log4j2;
import ua.org.serverhelp.Metrics;

import java.io.IOException;
import java.net.InetSocketAddress;

@Log4j2
public class WebInterface {
    private final String bindAddress="0.0.0.0";
    private final int bindPort=8080;
    private final HttpServer httpServer;
    private final Metrics metrics;

    public WebInterface(Metrics metrics) throws IOException {
        this.metrics=metrics;
        InetSocketAddress inetSocketAddress=new InetSocketAddress(bindAddress,bindPort);
        httpServer=HttpServer.create(inetSocketAddress,0);
        log.info("Start Web interface on "+bindAddress+":"+bindPort);
    }

    public void listen(){
        httpServer.createContext("/metrics", new MetricsHttpHandler(metrics));
        httpServer.start();
    }
}
