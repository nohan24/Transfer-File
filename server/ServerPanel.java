package server;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import event.StartButtonEvent;

public class ServerPanel extends JFrame {

	private JPanel contentPane;
	String info = "";
	JLabel infoLabel;

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
