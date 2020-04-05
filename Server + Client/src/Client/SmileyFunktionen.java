package Client;

public class SmileyFunktionen 
{
	public String smileyAutomatischEinfuegen(String s)
	{
		s.replace(" :) ", " \u1F642 ");
		s.replace(" (: ", " \u1F643 ");
		s.replace(" :D ", " \u1F604 ");
		s.replace(" <3 ", " \u2764 ");
		s.replace(" :( ", " \u1F641 ");
		s.replace(" :O ", " \u1F62E ");
		s.replace(" :o ", " \u1F62E ");
		s.replace(" *.* ", " \u1F929 ");
		s.replace(" :* ", " \u1F618 ");
		s.replace(" :') ", " \u1F602 ");
		s.replace(" lol ", " \u1F606 ");
		s.replace(" rofl ", " \u1F923 ");
		s.replace(" ;) ", " \u1F609 ");
		s.replace(" :P ", " \u1F600 ");
		return s;
	}
	
	public String einfuegenPukeSmiley(String s)
	{
		s = s + " \u1F922 ";
		return s;
	}
	
	public String einfuegenSleepSmiley(String s)
	{
		s = s + " \u1F634 ";
		return s;
	}
	
	public String einfuegenHappySmiley(String s)
	{
		s = s + " \u1F604 ";
		return s;
	}
	
	public String einfuegenPoopSmiley(String s)
	{
		s = s + " \u1F4A9 ";
		return s;
	}
	
	public String einfuegenHeartSmiley(String s)
	{
		s = s + " \u1F496 ";
		return s;
	}
	
	public String einfuegenSweaetSmiley(String s)
	{
		s = s + " \u1F605 ";
		return s;
	}
	
	public String einfuegenColdSmiley(String s)
	{
		s = s + " \u1F976 ";
		return s;
	}
}
