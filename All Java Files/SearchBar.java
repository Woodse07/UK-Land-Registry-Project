import processing.core.*;

public class SearchBar 
{
	int xPos;
	int yPos;
	int height;
	int width;
	PFont textSize;
	PApplet display;
	
	boolean selected = false;
	public String displayedText = "";
	
	int ticks = 0;
	final int MAX_COUNT = 20;
	boolean toggle = false;
	//fix this
	
	SearchBar(PApplet display, int xPos, int yPos, int height, int width, PFont textSize)
	{
		this.display = display;
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
		this.width = width;
		this.textSize = textSize;
	}
	
	void manipulateText(char input)
	{
		if(selected)
		{
			display.textSize((float) (height/2.5));

			if(input == PConstants.ENTER)
			{
				selected = false;
			}
			else if(input == 0x8)//delete
			{
				if(displayedText.length() > 1)
					displayedText = displayedText.substring(0, displayedText.length()-1);
				else
					displayedText = "";
			}
			else if(input >= 0x20 && input < 0x80)
				displayedText = displayedText + input;
			if(display.textWidth(displayedText + 'A') > width)
				displayedText = displayedText.substring(0, displayedText.length()-1);
		}
		
	}
	
	void activateSearchBar(int mx, int my)
	{
		if(mx >= xPos && mx <= xPos + width && my >= yPos && my <= yPos + height)
			selected = true;
		else
			selected = false;
	}
	
	void draw()
	{
		display.stroke(0);
		display.fill(display.color(255));
		display.rect(xPos, yPos, width, height);
		display.textAlign(PConstants.LEFT, PConstants.CENTER);
		
		display.textFont(textSize);

		display.textSize((float) (height/2.5));
		
		if(!displayedText.equals(""))
		{
			display.fill(display.color(0));
			if(selected)
				display.text(displayedText + ((toggle)?("|"):("")), xPos + 10, yPos + height/2);
			
			else
				display.text(displayedText, xPos + 10, yPos + height/2);
		}
		else
		{
			display.fill(display.color(100));
			if(selected)
				display.text(((toggle)?("|"):("")), xPos + 10, yPos + height/2);
			
			display.text(" Enter Postcode/Area name", xPos + 10, yPos + height/2);
		}
		
		
		if(ticks >= MAX_COUNT)
		{
			toggle = !toggle;
			ticks = 0;
		}
			
		ticks++;
		
	}
}
