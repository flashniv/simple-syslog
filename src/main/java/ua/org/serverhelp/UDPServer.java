package ua.org.serverhelp;

import java.io.IOException;
import java.net.*;

public class UDPServer {
    private final int port=15514;
    private final String bindAddress="0.0.0.0";
    private final DatagramSocket socket;
    private final Metrics metrics;

    public UDPServer() throws SocketException {
        this.socket = new DatagramSocket(new InetSocketAddress(bindAddress, port));
        this.metrics = new Metrics();
    }

    public void listen() throws IOException {
        while(true){
            byte[] buffer=new byte[1024];
            DatagramPacket datagramPacket=new DatagramPacket(buffer, buffer.length);
            socket.receive(datagramPacket);
            //process message
            metrics.processNewMessage(new String(datagramPacket.getData(),0,datagramPacket.getLength()));
            //response
            InetAddress address = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            datagramPacket = new DatagramPacket("\n".getBytes(),1, address, port);
            socket.send(datagramPacket);
        }
    }

}
