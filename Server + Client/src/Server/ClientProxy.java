package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import Client.Nachricht;


public class ClientProxy implements Runnable
{

	private ServerControl aServer;
	private Socket aSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Spamschutz timer;
	protected Thread t;
	static int benutzer = 0;
	
	
	public ClientProxy(ServerControl control,Socket s)
	{
		this.aServer = control;
		this.aSocket = s;
		this.timer = new Spamschutz();
		timer.setzeZeit();
		benutzer++;
		
		try
		{
			
			this.in = new ObjectInputStream(aSocket.getInputStream());
			this.out = new ObjectOutputStream(aSocket.getOutputStream());
			
			
			t = new Thread(this);
			t.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void run()
	{
		System.out.println("Client wurde angemeldet!");
		
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
	
	private void empfangeNachricht()
	{
		
		try
		{
			Nachricht n = (Nachricht) in.readObject();
			if(n != null)
			{
				if(timer.checkErlaubt())
				{
					verarbeiteNachricht(n);
				}
				else
				{
					aServer.sendeWarnung(this);		
				}		
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
	
	public void verarbeiteNachricht(Nachricht s)
	{
		if(s!=null)
		{
			aServer.bearbeiteNachricht(s);
		}
	}
	
	public void sendeNachricht(Nachricht s)
	{
		try
		{
				out.writeObject(s);
				out.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
