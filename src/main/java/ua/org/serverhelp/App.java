package ua.org.serverhelp;

public class App {
    public static void main(String[] args) {
        try{
            UDPServer udpServer=new UDPServer();
            udpServer.listen();
        }catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
