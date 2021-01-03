package model;

public enum Gender {
	Maennlich(1),
	Weiblich(2),
	Divers(3),
	NONE(0);

	private final int GENDERCODE;

	private final String GENDERNAME;

	Gender(int genderCode)
	{
		this.GENDERCODE=genderCode;
		this.GENDERNAME= super.toString();
	}

	public int getStatusCode()
	{
		return GENDERCODE;
	}

	@Override
	public String toString()
	{
		return GENDERNAME;
	}

}
