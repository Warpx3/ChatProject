package Client;

import java.io.Serializable;

public class Nickname extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String email;
	private String name;
	private String passwort;

	public Nickname(String identifier, String email, String name, String passwort)
	{
		super("Nickname");
		this.email = email;
		this.name = name;
		this.passwort = passwort;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPasswort()
	{
		return passwort;
	}

	public void setPasswort(String passwort)
	{
		this.passwort = passwort;
	}

}
