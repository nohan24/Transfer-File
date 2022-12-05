package event;

import java.awt.event.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import client.ClientPanel;

public class BrowseEvent implements ActionListener{

    ClientPanel p;
    JLabel path;

    public BrowseEvent(ClientPanel p, JLabel path){
        this.p = p;
        this.path = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser choose = new JFileChooser(
            FileSystemView
            .getFileSystemView()
            .getHomeDirectory()
        );
        int res = choose.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = choose.getSelectedFile();
            p.setFilename(file.getName());
            String n = file.getAbsolutePath();
            n = n.replace("\\", "/");
            path.setText(n);
        }
    }
    
}
