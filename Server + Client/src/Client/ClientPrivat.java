package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientPrivat extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_fluesterNachricht;
	private JList list_fluesterNachricht;
	private JButton btn_Senden;

	private ClientControl control;
	private Nickname empfaenger;
	DefaultListModel<PrivateNachricht> modelPrivateNachrichten;

	public ClientPrivat(ClientControl control, Nickname empfaenger)
	{
		this.control = control;
		this.empfaenger = empfaenger;
		modelPrivateNachrichten = new DefaultListModel<>();
		initialize();
	}

	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		list_fluesterNachricht = new JList(modelPrivateNachrichten);
		list_fluesterNachricht.setBounds(10, 11, 414, 209);
		contentPane.add(list_fluesterNachricht);

		textField_fluesterNachricht = new JTextField();
		textField_fluesterNachricht.setBounds(10, 231, 315, 20);
		contentPane.add(textField_fluesterNachricht);
		textField_fluesterNachricht.setColumns(10);

		btn_Senden = new JButton("Senden");
		btn_Senden.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Nachricht senden
				control.privateNachrichtSenden(empfaenger, ClientPrivat.this);
			}
		});
		btn_Senden.setBounds(335, 230, 89, 23);
		contentPane.add(btn_Senden);
		setVisible(true);
	}

	public JTextField getTextField_fluesterNachricht()
	{
		return textField_fluesterNachricht;
	}

	public void setTextField_fluesterNachricht(JTextField textField_fluesterNachricht)
	{
		this.textField_fluesterNachricht = textField_fluesterNachricht;
	}

	public Nickname getEmpfaenger()
	{
		return empfaenger;
	}

	public void setEmpfaenger(Nickname empfaenger)
	{
		this.empfaenger = empfaenger;
	}

	public JList getList_fluesterNachricht()
	{
		return list_fluesterNachricht;
	}

	public void setList_fluesterNachricht(JList list_fluesterNachricht)
	{
		this.list_fluesterNachricht = list_fluesterNachricht;
	}
	
}
