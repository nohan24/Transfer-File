package server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private int status = 0;
	private String phase = "";
	private DataInputStream dataInputStream = null;
	private Socket serverSocket;
	private String filename;	
	private BufferedReader in;
	ServerSocket listener;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server() throws Exception{
		listener = new ServerSocket(7777);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton start = new JButton("Start Server");
		
		start.setBounds(360, 11, 143, 32);
		contentPane.add(start);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 76, 876, 371);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(21, 11, 845, 349);
		panel.add(lblNewLabel);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = 1;
				phase = "<p>Starting server...</p>";
				lblNewLabel.setText("<html>" + phase + "</html>");
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	
		
		if(status == 1){
				
				try {
					serverSocket = listener.accept();
					in = new BufferedReader (new InputStreamReader (serverSocket.getInputStream()));
					InetSocketAddress socketAddress = (InetSocketAddress) serverSocket.getRemoteSocketAddress();
					String clientIpAddress = socketAddress.getAddress().getHostAddress();
					phase = phase + "<p> " + clientIpAddress + " connected</p>";
					phase = phase + "<p>Waiting for files...</p>"; 
					dataInputStream = new DataInputStream(serverSocket.getInputStream());
					new DataOutputStream(serverSocket.getOutputStream());
					filename = in.readLine();
					receiveFile(filename);
					phase = phase + "<p>Received " + filename + "</p>";
				} catch (Exception exc) {
					System.out.println(exc);
				}
		}
		lblNewLabel.setText("<html>" + phase + "</html>");
		repaint();
	}
	
	public void receiveFile(String filename) throws Exception {
		int bytes = 0;
		FileOutputStream fo = new FileOutputStream(filename);
		long size = dataInputStream.readLong();
		byte[] buffer = new byte[4 * 1024];
		while(size > 0 && (bytes = dataInputStream.read(buffer,0, (int)Math.min(buffer.length, size))) != -1) {
			fo.write(buffer, 0, bytes);
			size -= bytes;
		}
		fo.close();
	}
}
