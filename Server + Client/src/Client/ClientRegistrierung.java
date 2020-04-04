package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientRegistrierung extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_nickname;
	private JTextField textField_passwort;
	private JTextField textField_passwortBestaetigung;
	private JLabel lblNewLabel_emailadresse;

	private ClientControl control;

	private JTextField textField_emailadresse;

	public ClientRegistrierung(ClientControl control)
	{
		initialize();
		this.control = control;
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_nickname = new JLabel("Anzeigename");
		lblNewLabel_nickname.setBounds(10, 11, 96, 14);
		contentPane.add(lblNewLabel_nickname);

		textField_nickname = new JTextField();
		textField_nickname.setBounds(10, 36, 96, 20);
		contentPane.add(textField_nickname);
		textField_nickname.setColumns(10);

		JLabel lblNewLabel_passwort = new JLabel("passwort");
		lblNewLabel_passwort.setBounds(10, 115, 48, 14);
		contentPane.add(lblNewLabel_passwort);

		textField_passwort = new JTextField();
		textField_passwort.setBounds(10, 140, 96, 20);
		contentPane.add(textField_passwort);
		textField_passwort.setColumns(10);

		JLabel lblNewLabel_passwortBestaetigung = new JLabel("passwort nochmal");
		lblNewLabel_passwortBestaetigung.setBounds(10, 171, 142, 14);
		contentPane.add(lblNewLabel_passwortBestaetigung);

		textField_passwortBestaetigung = new JTextField();
		textField_passwortBestaetigung.setBounds(10, 196, 96, 20);
		contentPane.add(textField_passwortBestaetigung);
		textField_passwortBestaetigung.setColumns(10);

		JButton btnNewButton_registieren = new JButton("registrieren");
		btnNewButton_registieren.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				control.registrieren();
			}
		});
		btnNewButton_registieren.setBounds(10, 227, 89, 23);
		contentPane.add(btnNewButton_registieren);
		
		lblNewLabel_emailadresse = new JLabel("Emailadresse");
		lblNewLabel_emailadresse.setBounds(10, 67, 96, 14);
		contentPane.add(lblNewLabel_emailadresse);
		
		textField_emailadresse = new JTextField();
		textField_emailadresse.setBounds(10, 92, 96, 20);
		contentPane.add(textField_emailadresse);
		textField_emailadresse.setColumns(10);
		setVisible(true);
	}

	public JTextField getTextField_nickname()
	{
		return textField_nickname;
	}

	public JTextField getTextField_passwort()
	{
		return textField_passwort;
	}

	public JTextField getTextField_passwortBestaetigung()
	{
		return textField_passwortBestaetigung;
	}

	public JTextField getTextField_emailadresse()
	{
		return textField_emailadresse;
	}
}
