package event;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import client.ClientPanel;

public class ConnectEvent implements ActionListener{

    ClientPanel p;

    public ConnectEvent(ClientPanel p){
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String host = p.getHost().getText();
        String port = p.getPort().getText();
        if(host.equals("")){
            JOptionPane.showMessageDialog(
                p,
                "No Host!",
                "Alert",
                JOptionPane.WARNING_MESSAGE
              );
        }
        if(port.equals("")){
            JOptionPane.showMessageDialog(
                p,
                "No Port!",
                "Alert",
                JOptionPane.WARNING_MESSAGE
              );
        }else{
            try {
                p.setSocket(new Socket(host, Integer.valueOf(port)));
                p.setOut(new PrintWriter(p.getSocket().getOutputStream()));
                p.setDataOutputStream(new DataOutputStream(p.getSocket().getOutputStream()));
                p.setPhase(p.getPhase() + "<p style='color:green; margin-bottom:5px;'>Connected to " + host + " on port : " + port + "</p>");
                p.getInfo().setText("<html>" + p.getPhase() + "</html>");
            } catch (NumberFormatException | IOException e1) {
                p.setPhase(p.getPhase() + "<p style='color:red; margin-bottom:5px;'>Can't find host or port</p>");
                p.getInfo().setText("<html>" + p.getPhase() + "</html>");
            }
        }
    }
    
}
