package thread;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import server.ServerPanel;
import java.nio.file.Files;

public class ServerSupp extends Thread{
    ServerPanel p;
    int port;
    DataInputStream dataInputStream = null;
    Socket serverSocket = null;

    public ServerSupp(ServerPanel p, int port){
        this.p = p;
        this.port = port;
    }    

    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(port);
            while (true) {
                serverSocket = listener.accept();
                dataInputStream = new DataInputStream(serverSocket.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                String file = in.readLine();
                try {
                    String path = System.getProperty("user.dir");
                    path = path.replace("\\", "/");
                    path = path+"/thread/files/"+file;
                    int bytes = 0;
                    long size = dataInputStream.readLong();
                    byte[] buffer = new byte[4 * 1024];
                    ByteArrayOutputStream bais = new ByteArrayOutputStream();
                    while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
                        bais.write(buffer, 0, bytes);
                        size -= bytes;
                    }
                    Files.write(new File(path).toPath(), bais.toByteArray());
                    in.close();
                    dataInputStream.close();
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                serverSocket.close();
            }
        }catch (IOException e) {
            System.out.println("client maty");
        }
    }

}
