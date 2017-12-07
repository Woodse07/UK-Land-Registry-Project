import processing.core.*;

public class ImageWidget extends Widget
{
	PImage icon;

	ImageWidget(PApplet display, int x, int y, int width, int height, String label, int widgetColor, PFont widgetFont,
			int event,PImage icon)
	{
		super(display, x, y, width, height, label, widgetColor, widgetFont, event);
		this.icon = icon;
	}
	
	void draw()
	{
		super.draw();
		display.image(icon,x + width / 2 - icon.width / 2 , y + height / 2 - icon.height / 2 ,icon.height ,icon.width );
		
	}
}
