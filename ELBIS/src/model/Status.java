package model;

public enum Status {
	Default (1),
	Submitted(2),
	Declined(3),
	Authorized(4),
	Released(5),
	Archived(6);

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
