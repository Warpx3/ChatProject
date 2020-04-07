package Client;

import java.io.Serializable;

public class AnmeldeObjekt extends Transport implements Serializable
{
	private String email;
	private String passwort;
	
	public AnmeldeObjekt(String email, String passwort)
	{
		super("AnmeldeObjekt");
		this.email = email;
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

	public String getPasswort()
	{
		return passwort;
	}

	public void setPasswort(String passwort)
	{
		this.passwort = passwort;
	}

	
}
