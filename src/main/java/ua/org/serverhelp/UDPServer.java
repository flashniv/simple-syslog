package ua.org.serverhelp;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.*;

@Log4j2
public class UDPServer {
    private final int port=15514;
    private final String bindAddress="0.0.0.0";
    private final DatagramSocket socket;
    @Getter
    private final Metrics metrics;

    public UDPServer() throws SocketException {
        this.socket = new DatagramSocket(new InetSocketAddress(bindAddress, port));
        log.info("Start UDP socket listen");
        this.metrics = new Metrics();
    }

    public void listen() throws IOException {
        log.info("Start UDP datagram receiving");
        while(true){
            byte[] buffer=new byte[1024];
            DatagramPacket datagramPacket=new DatagramPacket(buffer, buffer.length);
            socket.receive(datagramPacket);
            String message=new String(datagramPacket.getData(),0,datagramPacket.getLength());
            log.debug("UDP datagram receive: "+message);
            //process message
            metrics.processNewMessage(message);
            //response
            InetAddress address = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            datagramPacket = new DatagramPacket("\n".getBytes(),1, address, port);
            socket.send(datagramPacket);
        }
    }

}
