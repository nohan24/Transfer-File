package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Spliter {
    public List<File> splitFile(File file, int n) throws IOException{
        List<File> ret = new ArrayList<>();
        byte[] b = Files.readAllBytes(file.toPath());
        byte[] c = new byte[1024];
        int l = b.length / n;
        FileInputStream in = new FileInputStream(file);
        int j = 0;
        int i = 1;
        String no = file.getName();
        while (in.available() != 0) {
            j = 0;
            String name = no + i + ".png";
            File g = new File(name);
            FileOutputStream out = new FileOutputStream(g);
            while (in.available() != 0 && j < l) { 
                j += in.read(c, 0, l);
            }
            out.write(c, 0, j);
            ret.add(g);
        }
        return ret;
    }
}
