package thread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import server.ServerPanel;

public class ServerThread extends Thread{
    Socket socket = null;
    DataInputStream dataInputStream = null;
    BufferedReader in;
    String client;
    ServerPanel p;
    
    public ServerThread(Socket socket, String client, ServerPanel p){
        this.socket = socket;
        this.client = client;
        this.p = p;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String file; 
            while (true) {
                file = in.readLine();
                receiveFile(file);
                p.setInfo(p.getInfo() + "<p>" + client + " send " + file + "!</p>");
                p.getInfoLabel().setText("<html>" + p.getInfo() + "</html>");
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

    public void receiveFile(String filename) throws Exception {
		int bytes = 0;
		FileOutputStream fo = new FileOutputStream(filename);
		long size = dataInputStream.readLong();
		byte[] buffer = new byte[4 * 1024];
		while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
			fo.write(buffer, 0, bytes);
			size -= bytes;
		}
		fo.close();
	}
}
