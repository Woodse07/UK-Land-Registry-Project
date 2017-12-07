
import processing.core.*;

public class Label 
{
	int xPos;
	int yPos;
	int height;
	int width;
	PFont textSize;
	PApplet display;
	
	boolean selected = false;
	public String displayedText = "";
	//fix this
	
	Label(PApplet display, int xPos, int yPos, int height, int width, PFont textSize, String displayedText)
	{
		this.display = display;
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
		this.width = width;
		this.textSize = textSize;
		this.displayedText = displayedText;
	}
	

	
	void draw()
	{
		display.noStroke();
		display.fill(display.color(255));
		display.rect(xPos, yPos, width, height);
		display.fill(display.color(0));
		display.textFont(textSize);
		display.text(displayedText, xPos + 10, yPos + height/2);
	}
}
