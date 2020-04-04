package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ClientControl implements Runnable
{

	private Socket aSocket;
	private InputStream inStream;
	private OutputStream outStream;
	private ObjectInputStream oin = null;
	private ObjectOutputStream oout = null;
	
	private String ip = "localhost";
	private String name = "";
	private int port = 8008;
	
	private DefaultListModel<String> nachrichten = new DefaultListModel<String>();
	
	private ClientGui clientGui;
	private ClientAnmeldung anmeldung;
	private ClientRegistrierung registrierung;
	
	private Thread t;
	
	
	public ClientControl()
	{
		this.anmeldung = new ClientAnmeldung(this);
		clientGui.getList().setModel(nachrichten);
	}
	
	public void clientStart()
	{
		try
		{
			this.name = clientGui.getTextField().getText();
			verbinden(ip,port);
		}
		catch(UnknownHostException e)
		{
			System.out.println("Unbekannte IP-Adresse!");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("Fehler beim Verbindungsaufbau!");
			e.printStackTrace();
		}
	}
	
	public void verbinden(String ip, int port) throws UnknownHostException,IOException
	{
		aSocket = new Socket(ip,port);
		if(aSocket != null)
		{
			
			oout = new ObjectOutputStream(aSocket.getOutputStream());
			oin = new ObjectInputStream(aSocket.getInputStream());
			System.out.println("Verbindung mit dem Server wurde hergestellt!");
			
			t = new Thread(this);
			t.start();
		}
	}
	
	@Override
	public void run()
	{
		while(!t.isInterrupted())
		{
			try
			{
				Thread.sleep(100);
				empfangeNachricht();
			}
			catch(InterruptedException e)
			{
				t.interrupt();
			}
		}
	}
	
	public void empfangeNachricht()
	{
		Nachricht n = null;
		try
		{
			if((n =(Nachricht) oin.readObject()) != null)
			{
				nachrichten.addElement(n.getName() +": " + n.getNachricht());
			}
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendeNachricht(String s)
	{
		try
		{
			oout.writeObject(new Nachricht(name,clientGui.getTextFieldNachricht().getText()));
			oout.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void oeffneRegistrierung()
	{
		this.registrierung = new ClientRegistrierung(this);
	}

	public void registrieren()
	{
		//Serialisierung
		if(registrierung.getTextField_passwort().getText() != null && registrierung.getTextField_passwortBestaetigung().getText() != null && registrierung.getTextField_nickname().getText() != null && registrierung.getTextField_emailadresse().getText() != null)
		{
			if(registrierung.getTextField_passwort().getText().equals(registrierung.getTextField_passwortBestaetigung().getText()))
			{
				Nickname n = new Nickname(registrierung.getTextField_emailadresse().getText(), registrierung.getTextField_nickname().getText(), registrierung.getTextField_passwort().getText());
				
				//#TODO
				//Nickname verschlüsseln && an Datenbank/Server senden zwecks abgleich (Email schon vorhanden)
				
				//über anderen Port verschicken? (verbinden methode)
				
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Passwörter stimmen nicht überein.","Fehler", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Bitte füllen Sie alle Felder aus.","Fehler", JOptionPane.PLAIN_MESSAGE);
		}

		
		
		this.clientGui = new ClientGui(this);
	}
}
