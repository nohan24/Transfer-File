package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import event.BrowseEvent;
import event.ConnectEvent;
import event.SendEvent;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClientPanel extends JFrame {

	private JPanel contentPane;
	String phase = "";
	JTextField host;
	JTextField port;
	JLabel info;
	String filename = "";
	Socket socket = null;
	PrintWriter out;
	DataOutputStream dataOutputStream = null;
	JLabel path;

	public JLabel getPath() {
		return path;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPhase() {
		return phase;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public DataOutputStream getDataOutputStream() {
		return dataOutputStream;
	}

	public void setDataOutputStream(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public JTextField getHost() {
		return host;
	}

	public void setHost(JTextField host) {
		this.host = host;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public JLabel getInfo() {
		return info;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientPanel frame = new ClientPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientPanel() throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		host = new JTextField();
		host.setBounds(51, 11, 149, 20);
		contentPane.add(host);
		host.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Host : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 60, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Port : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(210, 11, 49, 20);
		contentPane.add(lblNewLabel_1);
		
		port = new JTextField();
		port.setHorizontalAlignment(SwingConstants.CENTER);
		port.setBounds(254, 11, 60, 20);
		contentPane.add(port);
		port.setColumns(10);
		
		JButton send = new JButton("Send File");
		send.setBounds(617, 10, 89, 23);
		send.setFocusPainted(false);
		send.addActionListener(new SendEvent(this));
		contentPane.add(send);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 696, 410);
		contentPane.add(panel);
		panel.setLayout(null);
		
		info = new JLabel(phase);
		info.setVerticalAlignment(SwingConstants.TOP);
		info.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		info.setBounds(10, 11, 676, 367);
		panel.add(info);
		
		path = new JLabel();
		path.setForeground(new Color(0, 64, 0));
		path.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		path.setHorizontalAlignment(SwingConstants.CENTER);
		path.setBounds(10, 385, 676, 25);
		panel.add(path);
		
		JButton chooseFile = new JButton("Browse");
		chooseFile.setBounds(410, 10, 89, 20);
		chooseFile.setFocusPainted(false);
		chooseFile.addActionListener(new BrowseEvent(this, path));
		contentPane.add(chooseFile);

		JButton getfile = new JButton("Get File");
		getfile.setBounds(500, 10, 89, 20);
		getfile.setFocusPainted(false);
		getfile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedReader reader;

				try {
					reader = new BufferedReader(new FileReader("client/data.txt"));
					String line = reader.readLine();
					List<String> l = new ArrayList();
					while (line != null) {
						l.add(line);
						line = reader.readLine();
					}
					reader.close();
					Files ff = new Files(l.toArray());
					ff.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
		contentPane.add(getfile);
		
		JButton connect = new JButton("Connect");
		connect.setBounds(318, 10, 89, 20);
		connect.addActionListener(new ConnectEvent(this));
		contentPane.add(connect);
	}
}
