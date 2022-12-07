package server;

import java.awt.EventQueue;
import java.awt.Font;
import java.net.ServerSocket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import event.StartButtonEvent;
import thread.*;

public class ServerPanel extends JFrame {

	private JPanel contentPane;
	String info = "";
	JLabel infoLabel;
	ServerSockets s1;
	ServerSockets s2;

	public ServerSockets getS1() {
		return s1;
	}

	public void setS1(ServerSockets s1) {
		this.s1 = s1;
	}

	public ServerSockets getS2() {
		return s2;
	}

	public void setS2(ServerSockets s2) {
		this.s2 = s2;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					com.formdev.flatlaf.FlatLightLaf.install();
					ServerPanel frame = new ServerPanel();
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
	public ServerPanel() {
		s1 = new ServerSockets(this, 7778);
		s2 = new ServerSockets(this, 7779);
		s1.start();
		s2.start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton start = new JButton("Start Server");
		start.setFocusPainted(false);
		start.setBounds(341, 9, 135, 23);
		start.addActionListener(new StartButtonEvent(this));
		contentPane.add(start);
		
		infoLabel = new JLabel();
		infoLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		infoLabel.setVerticalAlignment(SwingConstants.TOP);
		infoLabel.setBounds(10, 43, 466, 409);
		infoLabel.setOpaque(true);
		contentPane.add(infoLabel);
	}
}
