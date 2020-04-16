package Client;

import java.io.Serializable;

import javax.swing.DefaultListModel;

public class AktiveNutzer extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Nickname> benutzer;
	
	public AktiveNutzer()
	{
		super("aktiveNutzer");
		benutzer = new DefaultListModel<>();
	}

	public DefaultListModel<Nickname> getBenutzer()
	{
		return benutzer;
	}

	public void setBenutzer(DefaultListModel<Nickname> benutzer)
	{
		this.benutzer = benutzer;
	}
	
	
}
