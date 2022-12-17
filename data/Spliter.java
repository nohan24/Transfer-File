package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Spliter {
    public List<byte[]> splitFile(byte[] ar) throws IOException{
        byte[]a = Arrays.copyOfRange(ar, 0, (int)(ar.length / 2));
        byte[]b = Arrays.copyOfRange(ar, (int)(ar.length / 2), ar.length);
        
        // byte[] a1 = new byte[(int)(ar.length/2)];
        // byte[] a2 = new byte[ar.length - a1.length];
        List<byte[]> datas = new ArrayList<>();
        // for (int i = 0; i < a1.length; i++) {
        //     a1[i] = ar[i];
        // }
        // int a = 0;
        // for (int i = a1.length; i < ar.length; i++) {
        //     a2[a] = ar[i];
        //     a++;
        // }
        datas.add(a);
        datas.add(b);
        return datas;  
    }
}
