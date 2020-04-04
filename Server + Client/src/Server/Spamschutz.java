package Server;

import java.time.LocalTime;

public class Spamschutz
{
	private int anzahlNachrichten;
	private LocalTime aktuell;
	private LocalTime grenze;
	
	
	public Spamschutz()
	{
		
	}
	
	public void setzeZeit()
	{
		aktuell = LocalTime.now();
		grenze = aktuell.plusSeconds(5);
		anzahlNachrichten = 0;
	}
	
	public boolean checkErlaubt()
	{
		boolean erlaubt;
		
		if(LocalTime.now().isAfter(grenze))
		{
			setzeZeit();
		}
		if(anzahlNachrichten <= 5 && LocalTime.now().isBefore(grenze))
		{
			erlaubt = true;
		}
		else
		{
			erlaubt = false;
		}
		anzahlNachrichten++;
		
		return erlaubt;
	}
	
}
