package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientAnmeldung extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_emailadresse;
	private JTextField textField_passwort;
	
	private ClientControl control;

	
	public ClientAnmeldung(ClientControl control)
	{
		this.control = control;
		initialize();
		
	}
	
	public void initialize()
	{
		control.clientStart();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_emailadresse = new JTextField();
		textField_emailadresse.setBounds(10, 36, 194, 20);
		contentPane.add(textField_emailadresse);
		textField_emailadresse.setColumns(10);

		textField_passwort = new JTextField();
		textField_passwort.setBounds(10, 92, 194, 20);
		contentPane.add(textField_passwort);
		textField_passwort.setColumns(10);

		JButton btn_anmelden = new JButton("anmelden");
		btn_anmelden.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				control.clientAnmelden();
			}
		});
		btn_anmelden.setBounds(10, 120, 89, 23);
		contentPane.add(btn_anmelden);

		JButton btn_registrieren = new JButton("registrieren");
		btn_registrieren.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				control.oeffneRegistrierung();
			}
		});
		btn_registrieren.setBounds(10, 154, 89, 23);
		contentPane.add(btn_registrieren);

		JLabel lblNewLabel_emailadresse = new JLabel("Emailadresse");
		lblNewLabel_emailadresse.setBounds(10, 11, 158, 14);
		contentPane.add(lblNewLabel_emailadresse);

		JLabel lblNewLabel_1 = new JLabel("Passwort");
		lblNewLabel_1.setBounds(10, 67, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		setVisible(true);
	}

	public JTextField getTextField_emailadresse()
	{
		return textField_emailadresse;
	}

	public void setTextField_emailadresse(JTextField textField_emailadresse)
	{
		this.textField_emailadresse = textField_emailadresse;
	}

	public JTextField getTextField_passwort()
	{
		return textField_passwort;
	}

	public void setTextField_passwort(JTextField textField_passwort)
	{
		this.textField_passwort = textField_passwort;
	}
}
