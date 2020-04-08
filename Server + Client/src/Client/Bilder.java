package Client;

import java.io.Serializable;
import java.awt.Image;

public class Bilder extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private Image bild;
	private String nachricht;

	public Bilder(String name,Image bild,String nachricht)
	{
		super("Bilder");
		this.name = name;
		this.bild = bild;
		this.nachricht = nachricht;
		// TODO Auto-generated constructor stub
	}
	
}
