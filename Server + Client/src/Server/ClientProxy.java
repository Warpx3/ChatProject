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

import Client.AnmeldeObjekt;
import Client.Nachricht;
import Client.Nickname;
import Client.Transport;


public class ClientProxy implements Runnable
{

	private ServerControl aServer;
	private Socket aSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	protected Thread t;
	static int benutzer = 0;
	
	
	public ClientProxy(ServerControl control,Socket s)
	{
		this.aServer = control;
		this.aSocket = s;
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
			Object o = (Object) in.readObject();
			Transport t = (Transport) o;
			
			if(t != null)
			{
				switch(t.getIdentifier())
				{
					case "Nachricht":
						Nachricht n = (Nachricht) o;
						aServer.bearbeiteNachricht(n);
						break;
					case "Nickname": 
						Nickname nick = (Nickname) o;
						aServer.registrierungPruefen(nick);
						break;
					case "AnmeldeObjekt":
						AnmeldeObjekt ao = (AnmeldeObjekt) o;
						aServer.anmelden(ao);
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
	
	public void sendeObject(Object o) //Änderung: Vorher SendeNachricht
	{
		try
		{
			out.writeObject(o);
			out.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
