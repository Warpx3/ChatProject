package Client;

import java.io.Serializable;

public class AnmeldeBestaetigung extends Transport implements Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean erfolgreichAngemeldet;
	private String nickname;
	
	public AnmeldeBestaetigung(boolean erfolgreichAngemeldet, String nickname)
	{
		super("AnmeldeBestaetigung");
		this.erfolgreichAngemeldet = erfolgreichAngemeldet;
		this.nickname = nickname;
	}
}
