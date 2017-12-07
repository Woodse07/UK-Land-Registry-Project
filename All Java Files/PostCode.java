import processing.core.*;

public class PostCode
{
	PApplet display = new PApplet();
	String postCode;
	String postName;
	int color = display.color(255);
	
	public PostCode(String post, String name)
	{
		postCode = post;
		postName = name;
	}
	
	public PostCode(String post, String name, int color)
	{
		postCode = post;
		postName = name;
		this.color = color;
	}
}
