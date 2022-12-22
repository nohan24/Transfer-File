package thread;

import java.io.*;
import java.util.*;
import java.net.Socket;
import java.net.SocketException;
import server.ServerPanel;
import data.Spliter;

public class ServerThread extends Thread{
    Socket socket = null;
    DataInputStream dataInputStream = null;
    BufferedReader in;
    String client;
    PrintWriter pri;
    ServerPanel p;
    int[] ports = {7778,7779};
    InputStream iny  = null; 
    
    public ServerThread(Socket socket, String client, ServerPanel p){
        this.socket = socket;
        this.client = client;
        this.p = p;
    }

    @Override
    public void run() {
        try {
            Socket s1 = new Socket("localhost", 7778);
            Socket s2 = new Socket("localhost", 7779);
            dataInputStream = new DataInputStream(socket.getInputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            iny = new DataInputStream(socket.getInputStream());
            pri = new PrintWriter(socket.getOutputStream());
            String ffo; 
            while (true) {
                String file = in.readLine();
                if(file == "ok"){
                    
                }else{
                    int bytes = 0;
                    long size = dataInputStream.readLong();
                    byte[] buffer = new byte[4 * 1024];
                    ByteArrayOutputStream bais = new ByteArrayOutputStream();
                    while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
                        bais.write(buffer, 0, bytes);
                        size -= bytes;
                    }
                    byte[] ret = bais.toByteArray();
                    Spliter sp = new Spliter();
                    List<byte[]> r = sp.splitFile(ret);
                    receiveFile(file,s1,0, r);
                    receiveFile(file,s2,1, r);
                    p.setInfo(p.getInfo() + "<p>" + client + " send " + file + "!</p>");
                    p.getInfoLabel().setText("<html>" + p.getInfo() + "</html>");
                }
            }
        } 
        catch(SocketException so){
            try {
                p.setInfo(p.getInfo() + "<p> " + client + " déconnecté</p>");
                p.getInfoLabel().setText("<html> " + p.getInfo() + " </html>");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFile(String filename, Socket s, int i, List<byte[]> r) throws Exception {
        PrintWriter print = new PrintWriter(s.getOutputStream());
        print.println(filename + ".DAT" + "00" + i);
        print.flush();
        DataOutputStream ou = new DataOutputStream(s.getOutputStream());
        ou.writeLong(r.get(i).length);
        ou.write(r.get(i));
        ou.flush();
        ou.close();
        print.close();
	}
}

