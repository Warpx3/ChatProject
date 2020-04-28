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
import java.util.List;

import javax.swing.DefaultListModel;

import Client.AktiveNutzer;
import Client.AktiveNutzerUpdate;
import Client.AnmeldeBestaetigung;
import Client.AnmeldeObjekt;
import Client.Nachricht;
import Client.Nickname;
import Client.PrivateNachricht;
import Client.Registrierung;

public class ServerControl extends Thread implements Serializable
{
	private ArrayList<ClientProxy> liste = new ArrayList<>();
	private ArrayList<Registrierung> registrierungsliste = new ArrayList<>();
	private ClientProxy clientproxy;
	private ServerControl s;
	private int port = 8008;
	private ServerSocket socket;
	
	private ArrayList<Nickname> angemeldeteNutzer;
	
	private ServerGui serverGui;
	private AktiveNutzer aktiveNutzer;
	
	public ServerControl()
	{
		this.serverGui = new ServerGui(this);
		angemeldeteNutzer = new ArrayList<>();
		serverGui.getList_angemeldeteUser().setModel(convertArrayListToDefaultListModel(angemeldeteNutzer));
		aktiveNutzer = new AktiveNutzer();
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
		System.out.println("Server l�uft");
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
				clientproxy = new ClientProxy(this,socket.accept());
				liste.add(clientproxy);
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
	
	public void registrierungPruefen(Registrierung register)
	{	
		boolean flag = false;
	
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			registrierungsliste = (ArrayList<Registrierung>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Registrierung reg : registrierungsliste)
		{
			if(reg.getEmail().equals(register.getEmail()))
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
				registrierungsliste.add(register);
				oos.writeObject(registrierungsliste);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
			anmelden(new AnmeldeObjekt(register.getEmail(), register.getPasswort()));
		}
	}
	
	public void anmelden(AnmeldeObjekt ao)
	{
		//Deserialisierung
		try(FileInputStream fis = new FileInputStream("datei.ser");
				ObjectInputStream ois = new ObjectInputStream(fis))
		{
			registrierungsliste = (ArrayList<Registrierung>) ois.readObject();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Registrierung reg : registrierungsliste)
		{			
			if(reg.getEmail().equals(ao.getEmail()) && reg.getPasswort().equals(ao.getPasswort()))
			{
				Nickname nick = new Nickname(reg.getEmail(), reg.getName());
				AktiveNutzerUpdate anu = new AktiveNutzerUpdate(nick, true);
				
				angemeldeteNutzer.add(nick);
				//Benutzer vorhanden, an Proxy senden
				clientproxy.sendeObject(new AnmeldeBestaetigung(true, nick));
				clientproxy.setNick(nick);
				notifyObserver(anu);
			}
		}
	}
	
	public void notifyObserver(AktiveNutzerUpdate anu)
	{	
		serverGui.getList_angemeldeteUser().setModel(convertArrayListToDefaultListModel(angemeldeteNutzer));
		aktiveNutzer.setBenutzer(angemeldeteNutzer);
		broadcast(anu);
		
		for(ClientProxy c : liste)
		{
			if(c != null && c.getNick().getEmail().equalsIgnoreCase(anu.getNick().getEmail()))
			{
				c.sendeObject(aktiveNutzer);
			}
		}
	}
	
	public void broadcast(Object o) //bearbeiteNachricht wollen wir nicht nur f�r Nachrichten, sondern allgemein als broadcast verwenden
	{	
		if(o != null)
		{
			for(ClientProxy c : liste)
			{
				c.sendeObject(o);
			}
		}
	}
	
	public void aktivenBenutzerEntfernen(String email)
	{
		for(int i = 0; i < angemeldeteNutzer.size(); i++)
		{
			if(angemeldeteNutzer.get(i).getEmail().equalsIgnoreCase(email))
			{
				angemeldeteNutzer.remove(i);
			}
		}
	}
	
	public void privateNachrichtSenden(PrivateNachricht pn)
	{
		for(ClientProxy c : liste)
		{
			if(c != null && c.getNick().getEmail().equalsIgnoreCase(pn.getEmpfaenger().getEmail()))
			{
				c.sendeObject(pn);
			}
		}
	}
	
	public DefaultListModel<Nickname> convertArrayListToDefaultListModel(List<Nickname> arrayList)
	{
		DefaultListModel<Nickname> modelAktiveNicks = new DefaultListModel<>();
		
		for(Nickname nick : arrayList)
		{
			modelAktiveNicks.addElement(nick);
		}
		
		return modelAktiveNicks;
	}
	
	public ArrayList<Nickname> getAngemeldeteNutzer()
	{
		return angemeldeteNutzer;
	}

	public void setAngemeldeteNutzer(ArrayList<Nickname> angemeldeteNutzer)
	{
		this.angemeldeteNutzer = angemeldeteNutzer;
	}

	public ArrayList<ClientProxy> getListe()
	{
		return liste;
	}

	public void setListe(ArrayList<ClientProxy> liste)
	{
		this.liste = liste;
	}	
}
