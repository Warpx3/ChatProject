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
	}
	
	public void clientStart()
	{
		try
		{
			this.name = "";
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
		try
		{
			Object o = oin.readObject();
			Transport t = (Transport) o;
			
			if(t != null)
			{
				//verarbeiteNachricht entfernt, weil unnötig ÄNDERUNG
				switch(t.getIdentifier())
				{
					case "Nachricht":
						Nachricht n = (Nachricht) o;
						nachrichten.addElement(n.getName() + ": " + n.getNachricht());
						break;
					case "AnmeldeBestaetigung": 
						AnmeldeBestaetigung a = (AnmeldeBestaetigung) o;
						name = a.getNickname();
						this.clientGui = new ClientGui(this);
						
						if(registrierung != null)
						{
							registrierung.dispose();
						}
						if(anmeldung != null)
						{
							anmeldung.dispose();
						}
						
						clientGui.getList().setModel(nachrichten);
						break;
					default: break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//vorher sendeNachricht mit übergabe von String(Pfusch) Änderung
	public void sendeObject(Object o)
	{
		try
		{
			oout.writeObject(o);
			oout.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Nachricht createNachricht(String s) //Änderung
	{
		return new Nachricht(name, s);
	}
	
	public void oeffneRegistrierung()
	{
		this.registrierung = new ClientRegistrierung(this);
		anmeldung.dispose();
	}

	public void registrieren()
	{
		String pw = registrierung.getTextField_passwort().getText();
		String pwBestaetigung = registrierung.getTextField_passwortBestaetigung().getText();
		String nickname = registrierung.getTextField_nickname().getText();
		String email = registrierung.getTextField_emailadresse().getText();
		
		if(pw != null && pwBestaetigung != null && nickname != null && email != null)
		{
			if(pw.equals(pwBestaetigung))
			{
				Nickname n = new Nickname("Nickname", email, nickname, pw);

				sendeObject(n);
				
				//#TODO
				//Nickname verschlüsseln && an Datenbank/Server senden zwecks abgleich (Email schon vorhanden?)
				
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
	}
	
	public void clientAnmelden()
	{
		String pw = anmeldung.getTextField_passwort().getText();
		String email = anmeldung.getTextField_emailadresse().getText();
		
		if(pw != null && email != null)
		{
			AnmeldeObjekt ao = new AnmeldeObjekt(email, pw);
			
			sendeObject(ao);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Bitte füllen Sie alle Felder aus.","Fehler", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
