package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;

public class ClientGui extends JFrame
{

	private JPanel contentPane;
	private JButton btnConnecten;
	private JButton btnSenden;
	private JTextField textFieldNachricht;
	private JList list;
	
	private ClientControl client;
	private JTextField textField;
	private JLabel lblName;

	public ClientGui(ClientControl client)
	{
		initialize();
		this.client = client;
		setVisible(true);
	}
	private void initialize() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnConnecten());
		contentPane.add(getBtnSenden());
		contentPane.add(getTextFieldNachricht());
		contentPane.add(getList());
		contentPane.add(getTextField());
		contentPane.add(getLblName());
	}
	private JButton getBtnConnecten() {
		if (btnConnecten == null) {
			btnConnecten = new JButton("Connecten");
			btnConnecten.setBounds(201, 11, 124, 23);
			btnConnecten.addActionListener(e->{
				client.clientStart();
			});
		}
		return btnConnecten;
	}
	private JButton getBtnSenden() {
		if (btnSenden == null) {
			btnSenden = new JButton("Senden");
			btnSenden.setBounds(335, 219, 89, 23);
			btnSenden.addActionListener(e->{
				client.sendeObject(client.createNachricht(getTextFieldNachricht().getText()));
			});
		}
		return btnSenden;
	}
	protected JTextField getTextFieldNachricht() {
		if (textFieldNachricht == null) {
			textFieldNachricht = new JTextField();
			textFieldNachricht.setBounds(10, 220, 315, 20);
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}
	protected JList getList() {
		if (list == null) {
			list = new JList();
			list.setBounds(10, 45, 315, 164);
		}
		return list;
	}
	protected JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(60, 12, 131, 20);
			textField.setColumns(10);
		}
		return textField;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setBounds(10, 15, 75, 14);
		}
		return lblName;
	}
}
