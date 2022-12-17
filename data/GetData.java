package data;

import java.net.*;
import java.io.*;

public class GetData {
    public byte[] dowload(String filename, int i) throws Exception{
        String f = filename + ".DAT00" + i + ";ok";
        System.out.println("kjk");
        Socket sock = new Socket("localhost", 7777 + i + 1);
        DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());
        PrintWriter out = new PrintWriter(sock.getOutputStream());
        dataOutputStream.writeInt(1);
        out.println(f);
        DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
        int bytes = 0;
        long size = dataInputStream.readLong();
        byte[] buffer = new byte[4 * 1024];
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
            bais.write(buffer, 0, bytes);
            size -= bytes;
        }
        byte[] ret = bais.toByteArray();
        dataInputStream.close();
        out.close();
        sock.close();
        return ret;
    }
}
