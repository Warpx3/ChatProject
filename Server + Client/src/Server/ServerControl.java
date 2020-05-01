package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import Client.Nachricht;

public class ServerControl extends Thread implements DOSProtection
{
	private ArrayList<ClientProxy> liste = new ArrayList<>();
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
	public void sendeWarnung(ClientProxy p)
	{
		p.sendeNachricht(new Nachricht("Server","Nicht so schnell!"));
	}
	
	@Override
	public boolean joinCheck (Socket socket)
	{
		if(liste.isEmpty() == false)
		{
			if(liste.get(0).getBenutzer() < 50)
			{
				for (ClientProxy clientProxy : liste)
				{
					if(clientProxy.getaSocket().getInetAddress() == socket.getInetAddress())
					{
						System.out.println(socket.getInetAddress() + " " + clientProxy.getaSocket().getInetAddress());
						return false;
					}
				}
				
				return true;
			}
			
			return false;
		}
		
		return false;
	}	
}
