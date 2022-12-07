package thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import server.ServerPanel;

public class ServerSockets extends Thread{
    ServerPanel p;
    int port;
    public ServerSockets(ServerPanel p, int port){
        this.p = p;
        this.port = port;
    }    

    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(port);
            while (true) {
                Socket serverSocket = listener.accept();
                InetSocketAddress socketAddress = (InetSocketAddress) serverSocket.getRemoteSocketAddress();
				String client = socketAddress.getAddress().getHostAddress();
				p.setInfo(p.getInfo() + "<p> " + client + " connected</p>");
                p.getInfoLabel().setText("<html> " + p.getInfo() + " </html>");
                (new ServerThread(serverSocket, client, p)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
