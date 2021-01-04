package model;

public enum Status {
	Offen(1),
	Eingereicht(2),
	Abgelehnt(3),
	Autorisiert(4),
	Öffentlich(5),
	Archiviert(6);

	private final int STATUSCODE;

	private final String STATUSNAME;

	Status(int statusCode)
	{
		this.STATUSCODE=statusCode;
		this.STATUSNAME= super.toString();
	}

	public int getStatusCode()
	{
		return STATUSCODE;
	}

	@Override
	public String toString()
	{
		return STATUSNAME;
	}
}
