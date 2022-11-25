package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;

public class Client extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private Socket socket;
	private DataOutputStream dataOutputStream = null;
	private String phase = "";
	private JLabel infoLabel;
	private PrintWriter out;
	private String nameFile = "";

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton findIp = new JButton("Connect");
		
		findIp.setBounds(220, 16, 142, 30);
		contentPane.add(findIp);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 65, 872, 349);
		contentPane.add(panel);
		panel.setLayout(null);   
		
		infoLabel = new JLabel("");
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		infoLabel.setVerticalAlignment(SwingConstants.TOP);
		infoLabel.setBounds(10, 11, 840, 327);
		panel.add(infoLabel);
		
		JLabel path = new JLabel("");
		path.setBounds(37, 303, 707, 35);
		panel.add(path);
		path.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		path.setForeground(new Color(0, 0, 160));
		path.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(10, 419, 686, 35);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Send File");
		
		btnNewButton_1.setBounds(760, 16, 122, 30);
		contentPane.add(btnNewButton_1);
		
		ip = new JTextField();
		ip.setHorizontalAlignment(SwingConstants.LEFT);
		ip.setBounds(10, 16, 200, 30);
		contentPane.add(ip);
		ip.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setBounds(747, 419, 135, 35);
		contentPane.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    JFileChooser choose = new JFileChooser(
			            FileSystemView
			            .getFileSystemView()
			            .getHomeDirectory()
			        );
			        
			    choose.showOpenDialog(null);
			    File file = choose.getSelectedFile();
			    nameFile = file.getName();
			    String n = file.getAbsolutePath();
			    n = n.replace("\\", "/");			
			    path.setText(n);
			    
			}
		});
		
		findIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String serverHost = ip.getText();
			        try {
			            socket = new Socket(serverHost, 7777);
						
			            out = new PrintWriter(socket.getOutputStream());
			            new DataInputStream(socket.getInputStream());
						dataOutputStream = new DataOutputStream(socket.getOutputStream());
			        } 
			        catch(ConnectException co) {
			        	phase = phase + co.getMessage();
			        }
			        catch (Exception ex) {
			           ex.printStackTrace();
			        }
					out.println(nameFile);
					out.flush();
					phase = phase + "<p>Sending " + nameFile + " to the Server...</p>";
					sendFile(path.getText());
					phase = phase + "<p>File was send successfuly</p>";
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		infoLabel.setText("<html> " + phase + " </html>");
		repaint();
	}
	
	public void sendFile(String path) throws Exception{
		int bytes = 0;
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		dataOutputStream.writeLong(file.length());
		byte[] buffer = new byte[4 * 1024];
		while ((bytes = fileInputStream.read(buffer))!= -1) {
			dataOutputStream.write(buffer, 0, bytes);
			dataOutputStream.flush();
		}
		fileInputStream.close();
	}

}
