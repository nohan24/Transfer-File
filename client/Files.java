package client;

import javax.swing.*;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Files extends JFrame{
    JPanel contentPane;
    Socket cli;
    PrintWriter out;
    BufferedReader in;

    public Files(Object[] obj) throws Exception{
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 206);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

        JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		comboBox.setModel(new DefaultComboBoxModel(obj));
		comboBox.setBounds(10, 58, 338, 42);
		contentPane.add(comboBox);
		
		JButton download = new JButton("Get File");
		download.setBounds(358, 58, 113, 42);
        download.addActionListener(new ActionListener(){
            @Override
                public void actionPerformed(ActionEvent e) {
                    List<byte[]> atb = new ArrayList();
                    try {
                        for(int i = 0; i < 2; i++){
                            int port = 7777 + i + 1;
                            cli = new Socket("localhost", port); 
                            DataInputStream dataInputStream = new DataInputStream(cli.getInputStream());
                            out = new PrintWriter(cli.getOutputStream());
                            in = new BufferedReader(new InputStreamReader(cli.getInputStream()));
                            out.println("ok");
                            out.flush();

                            in.readLine();
                            out.println(comboBox.getSelectedItem() + ".DAT00" + i);
                            out.flush();

                            int bytes = 0;
                            String siz = in.readLine();
                            long size = Long.parseLong(siz);
                            byte[] buffer = new byte[4 * 1024];
                            ByteArrayOutputStream bais = new ByteArrayOutputStream();
                            while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
                                bais.write(buffer, 0, bytes);
                                size -= bytes;
                            }
                            byte[] ret = bais.toByteArray();
                            atb.add(ret);
                            in.close();
                            out.close();
                            cli.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                  
                    byte c[] = joinByteArray(atb.get(0), atb.get(1));
                    String path = System.getProperty("user.dir");
                    path = path.replace("\\", "/");
                    
                    path =path + "/clientData/" + comboBox.getSelectedItem();
                    try(FileOutputStream fos = new FileOutputStream(path)){
                        fos.write(c);
                    }catch(Exception exc){}

                }
        });
		contentPane.add(download);
		
	
	}

    public static byte[] joinByteArray(byte[] byte1, byte[] byte2) {

        return ByteBuffer.allocate(byte1.length + byte2.length)
                .put(byte1)
                .put(byte2)
                .array();
    
    }
}
