

public class Entry
{
	public static final String DETACHED = "Detached";
	public static final String SEMI_DETACHED = "Semi - Detached";
	public static final String TERRACED = "Terraced";
	public static final String FLATS_MAISONETTES = "Flats/Maisonettes";
	public static final String OTHER = "Other";
	public static final String[] PROPERTY_TYPES = {DETACHED,SEMI_DETACHED,TERRACED,FLATS_MAISONETTES,OTHER};

	private static final int YEAR_OVERFLOW = 30;

	final int price;

	// look at later
	final String dateOfSale;
	final int day;
	final int month;
	final int year;

	final String postCode;
	final String outwardCode;
	final String inwardCode;

	final String propertyType;
	// if old == false, is new
	final boolean old;

	final String numberOrName;
	final String street;
	final String locality;
	final String town;
	final String district;
	final String county;

	Entry(int price, String dateOfSale, String postCode, char propertyType, char oldNew, String numberOrName,
			String street, String locality, String town, String district, String county)
	{
		this.price = price;
		this.dateOfSale = formatDate(dateOfSale);

		this.day = Integer.parseInt(this.dateOfSale.split("/")[0]);
		this.month = Integer.parseInt(this.dateOfSale.split("/")[1]);
		this.year = Integer.parseInt(this.dateOfSale.split("/")[2])
				+ (Integer.parseInt(this.dateOfSale.split("/")[2]) <= YEAR_OVERFLOW ? 2000 : 1900);

		this.postCode = postCode;
		if (!postCode.isEmpty())
		{
			this.outwardCode = setOutwardCode(postCode);
			this.inwardCode = setInwardCode(postCode);
		} else
		{
			this.outwardCode = null;
			this.inwardCode = null;
		}

		this.propertyType = setHouseType(propertyType);
		this.old = setHouseAge(oldNew);

		this.numberOrName = toProperCase(numberOrName);
		this.street = toProperCase(street);
		this.locality = toProperCase(locality);
		this.town = toProperCase(town);
		this.district = toProperCase(district);
		this.county = toProperCase(county);
	}

	private String formatDate(String date)
	{
		String[] dateTime = date.split(" ");
		return dateTime[0];
	}

	public boolean hasPostcode()
	{
		return !postCode.isEmpty();
	}

	public String toString()
	{
		return ("Price: £" + price + ", Date sold: " + dateOfSale + ", Postcode: " + postCode + ", Property Type: "
				+ propertyType + ", Is " + ((old) ? ("old") : ("new")) + ", Address: " + numberOrName + "|" + street
				+ "|" + locality + "|" + town + "|" + district + "|" + county);
	}


	public static String toProperCase(String input)
	{
		char[] characters = input.toCharArray();
		boolean nextCapitalise = true;
		for(int index = 0; index < characters.length; index++)
		{
			if(nextCapitalise && characters[index] >= 'a' && characters[index] <= 'z')
			{
				characters[index] -= 0x20;
				nextCapitalise = false;
			}
			if(!nextCapitalise && characters[index] >= 'A' && characters[index] <= 'Z')
			{
				characters[index] += 0x20;
				nextCapitalise = false;
			}
			
			if(!isLetter(characters[index]))
				nextCapitalise = true;
			else
				nextCapitalise = false;
		}
		String output = new String(characters);
		return output;
	}
	
	public static boolean isLetter(char input)
	{
		if(input >= 'a' && input <= 'z' || input >= 'A' && input <= 'Z')
			return true;
		else 
			return false;
	}
		
	private boolean setHouseAge(char oldNew)
	{
		boolean old = false;

		if (oldNew == 'Y')
			old = false;
		else if (oldNew == 'N')
			old = true;

		return old;
	}

	private String setHouseType(char type)
	{
		String propertyType = OTHER;

		if (type == 'D')
			propertyType = DETACHED;
		else if (type == 'S')
			propertyType = SEMI_DETACHED;
		else if (type == 'T')
			propertyType = TERRACED;
		else if (type == 'F')
			propertyType = FLATS_MAISONETTES;
		else if (type == 'O')
			propertyType = OTHER;

		return propertyType;
	}

	private String setOutwardCode(String postcode)
	{
		return postcode.split(" ")[0];
	}

	private String setInwardCode(String postcode)
	{
		return postcode.split(" ")[1];
	}

}
