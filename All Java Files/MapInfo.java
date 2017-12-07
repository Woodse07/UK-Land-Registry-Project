
import processing.core.*;

public class MapInfo
{
	//Main mapping class, contains information and various functions to make it easy to do mapping, mainly worked on by Marek
	
	PApplet display;
	public String[] LondonCodes = {"WD","EN","HA","IG","SM","BR","DA","RM","KT","TW","UB","AM"};
	public String[] postCodes = {"AB", "AL", "B", "BA", "BB", "BD", "BH", "BL", "BN", "BS", "CA", "CB", "CF", "CH", "CM", "CO", "CT", "CV", "CW", "DD", "DE", "DG", "DH", "DL", "DN", "DT", "DY", "EH", "EX", "FK", "FY", "G", "GL", "GU", "HD", "HG", "HP", "HR", "HU", "HX", "IP", "IV", "KA", "KW", "KY", "L", "LA", "LD", "LE", "LL", "LN", "LS", "LU", "M", "ME", "MK", "ML", "NE", "NG", "NN", "NP", "NR", "OL", "OX", "PA", "PE", "PH", "PL", "PO", "PR", "RG", "RH", "S", "SA", "SG", "SK", "SL", "SN", "SO", "SP", "SR", "SS", "ST", "SY", "TA", "TD", "TF", "TN", "TQ", "TR", "TS", "WA", "WF", "WN", "WR", "WS", "WV", "YO"};
	public String[] postNames = {"Aberdeen", "St Albans", "Birmingham", "Bath", "Blackburn", "Bradford", "Bournemouth", "Bolton", "Brighton", "Bristol", "Carlisle", "Cambridge", "Cardiff", "Chester", "Cchelmsford", "Colchester", "Canterbury", "Coventry", "Crewe", "Dundee", "Derby", "Dumfries", "Durham", "Darlington", "Doncaster", "Dorchester", "Dudley", "Edinburgh", "Exter", "Falkirk", "Blackpool", "Glasgow", "Gloucester", "Guildford", "Huddersfield", "Harrogate", "Hemel Hempstead", "Hereford", "Hull", "Halifax", "Ipswich", "Invrness", "Kilmarnock", "Kirkwall", "Kirkcaldy", "Liverpool", "Lancaster", "Llandrindod Wells", "Leicester", "Llandudno", "Lincoln", "Leeds", "Luton", "Manchester", "Medway", "Milton Keynes", "Motherwell", "Newcastle", "Nottingham", "Northampton", "Newport", "Norwich", "Oldham", "Oxford", "Paisley", "Peterborough", "Perth", "Plymouth", "Portsmouth", "Preston", "Reading", "Redhill", "Sheffield", "Swansea", "Stevenage", "Stockport", "Slough", "Swindon", "Southampton", "Salisbury", "Sunderland", "Southend on Sea", "Stroke on Trent", "Shrewsbury", "Taunton", "Galasheils", "Telford", "Tunbridge Wells", "Torquay", "Truro", "Cleveland", "Warrington", "Wakefield", "Wigan", "Worcester", "Walsall", "Wolverhampton", "York"};
	private String numbers = "0123456789";
	int colorTint;
	PImage mapBackground;
	int x, y;
	float w, h;
	
	public MapInfo(PApplet disp, int xPos, int yPos, float wPos, float hPos, int color, PImage main)
	{
		mapBackground = main;
		colorTint = color;
		display = disp;
		x = xPos;
		y = yPos;
		w = wPos * 672;
		h = hPos * 804;
	}
	
	public String getImg(String postCode)
	{
		return (getPost(postCode) + ".png");
	}
	
	public void drawCurrentImg()
	{
		//display.image(mapBackground, x, y, w, h);
		//display.image(currentIMG, x, y, w, h);
	}
	
	public void changeCurrentImg(PImage newImage)
	{
		//currentIMG = newImage;
	}
	/*
	public void drawPostCode(String postCode)
	{
		display.image(mainBackground, x, y, mainBackground.width*w, mainBackground.height*h);
		String post = getPost(postCode);
		if (post.contains("London"))
		{
			display.tint(colors[0]);
			display.image(london, x, y, london.width*w, london.height*h);
			display.noTint();
		}
		else
		{
			for (int i = 0; i < postCodes.length; i ++)
			{
				if (postCodes[i].contains(post))
				{
					display.tint(colors[i%colors.length]);
					display.image(ukmap[i], x, y, ukmap[i].width*w, ukmap[i].height*h);
					display.noTint();
				}
			}
		}
	}*/
	
	public String getPost(String post)
	{
		String postCodeStart = post.substring(0, 2);
		
		if (isLondon(postCodeStart))
		{
			return "LONDON";
		}
		else if (numbers.contains(postCodeStart.substring(1,2)))
		{
			for (int i = 0; i < postCodes.length; i++)
			{
				if (postCodeStart.substring(0,1).contains(postCodes[i]))
					return postCodes[i];
			}
		}
		else
		{
			for (int i = 0; i < postCodes.length; i++)
			{
				if (postCodeStart.equals(postCodes[i]))
					return postCodes[i];
			}
		}
		return "BLANK";
	}
	
	public String getName(String post)
	{
		String postCodeStart = post.substring(0, 2);
		
		if (isLondon(postCodeStart))
		{
			return "London";
		}
		else if (numbers.contains(postCodeStart.substring(1,2)))
		{
			for (int i = 0; i < postCodes.length; i ++)
			{
				if (postCodeStart.substring(0,1).contains(postCodes[i]))
					return postNames[i];
			}
		}
		else
		{
			for (int i = 0; i < postCodes.length; i ++)
			{
				if (postCodeStart.contains(postCodes[i]))
					return postNames[i];
			}
		}
		return "";
	}
	
	public boolean isLondon(String post)
	{

		String postCodeStart = post;
		if (post.length() > 1)
			postCodeStart = post.substring(0, 2);
		for (int i = 0; i < LondonCodes.length; i++)
		{
			if (postCodeStart.contains(LondonCodes[i]))
			{
				return true;
			}
		}
		return false;
	}
}
