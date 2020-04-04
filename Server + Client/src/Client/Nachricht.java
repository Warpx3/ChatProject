package Client;

import java.io.Serializable;

public class Nachricht implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String nachricht;
	

	public Nachricht(String name, String nachricht)
	{
		this.name = name;
		this.nachricht = nachricht;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNachricht()
	{
		return nachricht;
	}

	public void setNachricht(String nachricht)
	{
		this.nachricht = nachricht;
	}

	
	@Override
	public String toString()
	{
		return nachricht;
	}
	
}