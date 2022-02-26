package ua.org.serverhelp.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.log4j.Log4j2;
import ua.org.serverhelp.Metrics;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Log4j2
public class MetricsHttpHandler implements HttpHandler {
    private final Metrics metrics;

    public MetricsHttpHandler(Metrics metrics) {
        this.metrics=metrics;
    }

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is {@code null}
     * @throws IOException          if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            response(exchange,200, metrics.getMetricList());
        }else {
            response(exchange,403,"Access denied");
        }
    }

    private void response(HttpExchange exchange,int code, String body) throws IOException {
        accessLog(exchange,code);
        OutputStream out=exchange.getResponseBody();
        byte[] response=body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, response.length);
        out.write(response);
        out.flush();
        out.close();
    }

    private void accessLog(HttpExchange exchange,int code) {
        log.info("IP:"+exchange.getRemoteAddress().getAddress()+" Method:"+exchange.getRequestMethod()+" URL:"+exchange.getRequestURI()+" Code:"+code);
    }
}
