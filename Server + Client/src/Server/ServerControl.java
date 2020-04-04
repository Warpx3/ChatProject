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
	
	private ServerGui serverGui;
	
	public ServerControl()
	{
		this.serverGui = new ServerGui(this);
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
	
	public void bearbeiteNachricht(Nachricht s)
	{
		if(s != null)
		{
			for(ClientProxy c : liste)
			{
				c.sendeNachricht(s);
			}
		}
	}
	
	public void registrierungPruefen(Nickname n)
	{
		boolean flag = false;
		
		//Serialisierung
		try(FileOutputStream fos = new FileOutputStream("datei.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos))
		{
			oos.writeObject(n);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
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
				//#TODO
				//Abbruch
				flag = true;
				//Benutzer das auch wissen lassen
			}
			
		}
		
		if(flag == false)
		{
			//#TODO
			//Benutzer anmelden
		}
	}
}
