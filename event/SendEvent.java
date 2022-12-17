package event;

import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;
import client.ClientPanel;

public class SendEvent implements ActionListener{
    ClientPanel p;

    public SendEvent(ClientPanel p){
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(p.getSocket() == null){
            JOptionPane.showMessageDialog(
                p,
                "Not Connected!",
                "Alert",
                JOptionPane.WARNING_MESSAGE
              );
        }
        if(p.getFilename() == ""){
            JOptionPane.showMessageDialog(
                p,
                "No File Selected!",
                "Alert",
                JOptionPane.WARNING_MESSAGE
              );
        }
        else{
            try {
                p.getOut().println(p.getFilename());
                p.getOut().flush();
                p.setPhase(p.getPhase() + "<p>Sending " + p.getFilename() + " to the Server...</p>");
                p.getInfo().setText("<html>" + p.getPhase()  + "</html>");
                sendFile(p.getPath().getText());
                p.setPhase(p.getPhase() + "<p>File was send successfuly</p>");
                p.getInfo().setText("<html>" + p.getPhase()  + "</html>");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public void sendFile(String path) throws Exception{
		int bytes = 0;
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		p.getDataOutputStream().writeLong(file.length());
		byte[] buffer = new byte[1024];
		while ((bytes = fileInputStream.read(buffer))!= -1) {
			p.getDataOutputStream().write(buffer, 0, bytes);
			p.getDataOutputStream().flush();
		}
		fileInputStream.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter("client/data.txt", true));
        bw.write(p.getFilename());
        bw.newLine();
        bw.close();
	}

}
