package thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import server.ServerPanel;
import java.nio.file.Files;

public class ServerSupp extends Thread{
    ServerPanel p;
    int port;
    DataInputStream dataInputStream = null;
    Socket serverSocket = null;
    PrintWriter pri;

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
                pri = new PrintWriter(serverSocket.getOutputStream());
                dataInputStream = new DataInputStream(serverSocket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
                
                BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                String g = in.readLine();
                String file = g;
                if(file.equals("ok")){
                    pri.println("send file");
                    pri.flush();
                    String f = in.readLine();
                    System.out.println(f);  

                    int bytes = 0;
                    String path = System.getProperty("user.dir");
                    path = path.replace("\\", "/");
                    File fi = new File(path + "/thread/files/" + f);
                    FileInputStream fileInputStream = new FileInputStream(fi);
                    pri.println(fi.length());
                    pri.flush();
                    byte[] buffer = new byte[1024];
                    while ((bytes = fileInputStream.read(buffer))!= -1) {
                        dataOutputStream.write(buffer, 0, bytes);
                        dataOutputStream.flush();
                    }
                    fileInputStream.close();

                }else{
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
                
                }
                    
                serverSocket.close();
                
            }
        }catch (IOException e) {
            System.out.println("client maty");
        }
    }

}
