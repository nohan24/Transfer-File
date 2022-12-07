package event;

import java.awt.event.*;
import javax.swing.JButton;
import server.ServerPanel;
import thread.ServerSockets;

public class StartButtonEvent implements ActionListener{
    ServerPanel p;

    public StartButtonEvent(ServerPanel p){
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((JButton)e.getSource()).setEnabled(false);
        p.setInfo(p.getInfo() + "<p>Server Started!</p>");
        p.setInfo(p.getInfo() + "<p>Waiting for client...</p>");
        p.getInfoLabel().setText("<html> " + p.getInfo() + " </html>");
        (new ServerSockets(p, 7777)).start();
    }
    
}
