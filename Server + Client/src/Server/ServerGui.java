package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;

public class ServerGui extends JFrame
{

	private JPanel contentPane;
	private JButton btnStartServer;
	private JButton btnStopServer;
	private JList list_angemeldeteUser;

	private ServerControl server;
	
	public ServerGui(ServerControl server)
	{
		initialize();
		this.server = server;
		setVisible(true);
	}
	private void initialize() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnStartServer());
		contentPane.add(getBtnStopServer());
		
		list_angemeldeteUser = new JList();
		list_angemeldeteUser.setBounds(136, 33, 288, 217);
		contentPane.add(list_angemeldeteUser);
		
		JLabel lblAngemeldeteUser = new JLabel("angemeldete User:");
		lblAngemeldeteUser.setBounds(137, 15, 138, 14);
		contentPane.add(lblAngemeldeteUser);
	}
	private JButton getBtnStartServer() {
		if (btnStartServer == null) {
			btnStartServer = new JButton("Start Server");
			btnStartServer.setBounds(10, 11, 115, 23);
			btnStartServer.addActionListener(e->{
				server.starteServer();
			});
		}
		return btnStartServer;
	}
	private JButton getBtnStopServer() {
		if (btnStopServer == null) {
			btnStopServer = new JButton("Stop Server");
			btnStopServer.setBounds(10, 45, 115, 23);
			btnStopServer.addActionListener(e->{
				server.beenden();
			});
		}
		return btnStopServer;
	}
	public JList getList_angemeldeteUser()
	{
		return list_angemeldeteUser;
	}
	public void setList_angemeldeteUser(JList list_angemeldeteUser)
	{
		this.list_angemeldeteUser = list_angemeldeteUser;
	}
	
	
}
