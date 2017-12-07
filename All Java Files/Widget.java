import processing.core.*;

public class Widget
{
	int x, y, width, height;
	String label;
	int event;
	int widgetColor, labelColor;
	PFont widgetFont;
	PApplet display;

	final int EVENT_NULL = 0;

	Widget(PApplet display, int x, int y, int width, int height, String label, int widgetColor, PFont widgetFont,
			int event)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		this.event = event;
		this.widgetColor = widgetColor;
		this.widgetFont = widgetFont;
		labelColor = display.color(0);
		this.display = display;
	}

	void draw()
	{
		display.textSize(15);
		display.fill(widgetColor);
		display.stroke((getHighlighted(display.mouseX, display.mouseY)) ? (255) : (0));
		display.rect(x, y, width, height, 10);
		display.fill(labelColor);
		display.textAlign(PConstants.CENTER, PConstants.CENTER);
		display.text(label, x + width/2, y + height/2);
		display.stroke(0);

	}

	int getEvent(int mX, int mY)
	{
		if (mX > x && mX < x + width && mY > y && mY < y + height)
		{
			return event;
		}
		return EVENT_NULL;
	}

	boolean getHighlighted(int mX, int mY)
	{
		if (mX > x && mX < x + width && mY > y && mY < y + height)
		{
			return true;
		} 
		else
			return false;
	}

}