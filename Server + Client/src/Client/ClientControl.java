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
	
	private Thread t;
	
	
	public ClientControl()
	{
		this.clientGui = new ClientGui(this);
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
}
