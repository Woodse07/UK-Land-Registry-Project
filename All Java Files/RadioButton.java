import java.util.ArrayList;

import processing.core.*;

class RadioButton extends Widget
{
  int segments;
  int segmentActive = 0;
  int segmentWidth;
  int segmentActiveColor;
  
  ArrayList<Widget> buttons = new ArrayList<Widget>();
  
  RadioButton(PApplet display, int x,int y, int width, int height, String[] labels,
  int widgetColor, PFont widgetFont, int event, int segments, int ActiveColor)
  {
    super(display, x, y, width, height, labels[0],
        widgetColor, widgetFont, event);
    this.segments = segments;
    segmentWidth = width/segments;
    this.segmentActiveColor = ActiveColor;//color(0,255,0);
  }
  
  void changeActive(int mX, int mY)
  {
    for(int i = 0; i < segments; i++)
    {
        if(mX > x + segmentWidth*i && mX <= x+segmentWidth*(i+1) && mY >y && mY <y+height)
          segmentActive = i;
    }
  }
  
  void draw()
  {
    
    display.stroke((getHighlighted(display.mouseX, display.mouseY))?(255):(0));
    
    for(int i = 0; i < segments; i++)
    {
        display.fill((i==segmentActive)?(segmentActiveColor):(widgetColor));
        display.rect(x + segmentWidth*i, y,  segmentWidth, height);
    }
    display.fill(labelColor);
    display.text(label, x+10, y+height-10);
  }
}