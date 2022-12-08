package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Spliter {
    public List<byte[]> splitFile(byte[] ar) throws IOException{
        byte[] a1 = new byte[(int)(ar.length/2)];
        byte[] a2 = new byte[ar.length - a1.length];
        List<byte[]> datas = new ArrayList<>();
        for (int i = 0; i < a1.length; i++) {
            a1[i] = ar[i];
        }
        for (int i = 0; i < a2.length; i++) {
            a2[i] = ar[i];
        }
        datas.add(a1);
        datas.add(a2);
        return datas;  
    }
}
