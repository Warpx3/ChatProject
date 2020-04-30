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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGui extends JFrame
{

	private JPanel contentPane;
	private JButton btnSenden;
	private JTextField textFieldNachricht;
	private JList list;
	private JList list_angemeldeteNutzer;

	private ClientControl client;

	public ClientGui(ClientControl client)
	{
		this.client = client;
		initialize();
		setVisible(true);
		textFieldNachricht.requestFocusInWindow();
	}

	private void initialize()
	{
		setTitle(client.getNickname().getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnSenden());
		contentPane.add(getTextFieldNachricht());
		contentPane.add(getList());
		contentPane.add(getList_angemeldeteNutzer());
	}

	private JButton getBtnSenden()
	{
		if (btnSenden == null)
		{
			btnSenden = new JButton("Senden");
			btnSenden.setBounds(335, 219, 89, 23);
			btnSenden.addActionListener(e -> 
			{
				client.sendeObject(client.createNachricht(getTextFieldNachricht().getText()));
				getTextFieldNachricht().setText("");
			});
		}
		return btnSenden;
	}

	protected JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					client.sendeObject(client.createNachricht(getTextFieldNachricht().getText()));
					getTextFieldNachricht().setText("");
				}
			});
			textFieldNachricht.setBounds(10, 220, 315, 20);
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}

	protected JList getList()
	{
		if (list == null)
		{
			list = new JList();
			list.setBounds(10, 11, 414, 198);
		}
		return list;
	}

	protected JList getList_angemeldeteNutzer()
	{
		if (list_angemeldeteNutzer == null)
		{
			list_angemeldeteNutzer = new JList();
			list_angemeldeteNutzer.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					Nickname nick = new Nickname(((Nickname) list_angemeldeteNutzer.getSelectedValue()).getEmail(),((Nickname) list_angemeldeteNutzer.getSelectedValue()).getName());
					client.clientPrivatOeffnen(nick);
				}
			});
			list_angemeldeteNutzer.setBounds(434, 11, 117, 225);
		}
		return list_angemeldeteNutzer;
	}
}
