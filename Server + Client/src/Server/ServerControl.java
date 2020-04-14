package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Client.AnmeldeBestaetigung;
import Client.AnmeldeObjekt;
import Client.Nachricht;
import Client.Nickname;

public class ServerControl extends Thread implements Serializable
{
	private ArrayList<ClientProxy> liste = new ArrayList<>();
	private ArrayList<Nickname> nicknameListe = new ArrayList<>();
	private ClientProxy p;
	private ServerControl s;
	private int port = 8008;
	private ServerSocket socket;
	
	private DefaultListModel<String> angemeldeteNutzer;
	
	private ServerGui serverGui;
	
	public ServerControl()
	{
		this.serverGui = new ServerGui(this);
		angemeldeteNutzer = new DefaultListModel<>();
		serverGui.getList_angemeldeteUser().setModel(angemeldeteNutzer);
	}
	
	public void starteServer()
	{
		this.start();
	}
	
	public void beenden()
	{
		if(this != null)
		{
			this.interrupt();
		}
	}
	
	
	public void run()
	{
		System.out.println("Server läuft");
		try
		{
			socket = new ServerSocket(port);
			socket.setSoTimeout(100);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		while(!isInterrupted() && socket != null)
		{
			try
			{
				sleep(100);
				p = new ClientProxy(this,socket.accept());
				liste.add(p);
			}
			catch(SocketTimeoutException e)
			{
				
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(InterruptedException e)
			{
				interrupt();
				try
				{
					liste.forEach(proxys->{
						proxys.t.interrupt();
					});
					socket.close();
					System.out.println("Server wird beendet!");
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		try
		{
			liste.forEach(proxys->{
				proxys.t.interrupt();
			});
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		socket = null;
	}
	
	public void bearbeiteNachricht(Nachricht n)
	{	
		if(n != null)
		{
			for(ClientProxy c : liste)
			{
				c.sendeObject(n);
			}
		}
	}
	
	public void registrierungPruefen(Nickname n)
	{	
		boolean flag = false;
	
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			nicknameListe = (ArrayList<Nickname>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Nickname nick : nicknameListe)
		{
			if(nick.getEmail().equals(n.getEmail()))
			{
				flag = true;
				//TODO
				//Benutzer das auch wissen lassen
			}
			
		}
		
		if(flag == false)
		{
			//Benutzer registrieren
			//Serialisierung
			try(FileOutputStream fos = new FileOutputStream("datei.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos))
			{
				nicknameListe.add(n);
				oos.writeObject(nicknameListe);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
			anmelden(new AnmeldeObjekt(n.getEmail(), n.getPasswort()));
		}
	}
	
	public void anmelden(AnmeldeObjekt n)
	{
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			nicknameListe = (ArrayList<Nickname>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Nickname nick : nicknameListe)
		{			
			if(nick.getEmail().equals(n.getEmail()) && nick.getPasswort().equals(n.getPasswort()))
			{
				angemeldeteNutzer.addElement(nick.getEmail());
				//Benutzer vorhanden, an Proxy senden
				p.sendeObject(new AnmeldeBestaetigung(true, nick.getName()));
			}
			
		}
	}
	
	public DefaultListModel<String> getAngemeldeteNutzer()
	{
		return angemeldeteNutzer;
	}

	public void setAngemeldeteNutzer(DefaultListModel<String> angemeldeteNutzer)
	{
		this.angemeldeteNutzer = angemeldeteNutzer;
	}
	
	
}
