package server;

import java.io.IOException;
import java.net.*;

public class ServerSockets {
	public ServerSockets() throws IOException {
		ServerSocket listener = null;
        try {
            listener = new ServerSocket(7777);
            System.out.println("Server is waiting to accept user...");
            Socket serverSocket = listener.accept();
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            listener.close();
        }
	}
}
