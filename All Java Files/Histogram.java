import processing.core.*;
import java.awt.Font;
import java.util.Arrays;
import java.util.Random;

public class Histogram
{
	// main authorship - Seamus Woods
	PApplet display = new PApplet();
	Entry[] test;
	Entry[] amountOfEntries;
	Entry[] entriesInBar;
	PFont myFont;
	PFont titleFont;
	Random generator;
	int lastColor;
	int[] colorFound;
	int Y1 = 50;
	int Y2 = 100;
	int X1 = 50;
	int X2 = 50;
	int[] histValues;
	int[] nextHistValues;
	int[] currentHistValues;
	boolean newHistValues;
	int[] colors;
	int textColour;
	int splitValue;
	int barLength;
	int xPos;
	int maxHeight;
	int maxHeightIndex;
	int currentColor;
	int steps;
	int stepNum;
	int currentHeight = 0;
	int currentEntryAmount;
	double currentEntryStep;
	boolean showTitle;
	int amountOfBars;

	public Histogram(PApplet disp, int[] data, Entry[] test, int[] colors, int splitValue, boolean showTitle, int amountOfBars)
	{
		this.display = disp;
		this.colors = colors;
		this.histValues = data.clone();
		this.nextHistValues = data.clone();
		currentHistValues = new int[histValues.length];
		this.test = test;
		this.newHistValues = true;
		this.splitValue = splitValue;
		generator = new Random();
		colorFound = new int[histValues.length];
		this.showTitle = showTitle;
		this.amountOfBars = amountOfBars;
	}

	public Histogram(PApplet disp, int x1, int y1, int x2, int y2, int[] data, Entry[] test, int[] colors,
			int splitValue, boolean showTitle, int amountOfBars)
	{
		this.display = disp;
		this.Y1 = y1;
		this.Y2 = y2;
		this.X1 = x1;
		this.X2 = x2;
		this.colors = colors;
		this.histValues = data.clone();
		this.nextHistValues = histValues;
		currentHistValues = new int[histValues.length];
		this.test = test;
		this.newHistValues = true;
		this.splitValue = splitValue;
		generator = new Random();
		colorFound = new int[histValues.length];
		this.showTitle = showTitle;
		this.amountOfBars = amountOfBars;
	}

	public void standardHistogram()
	{
		myFont = display.createFont(Font.SANS_SERIF, 15);
		textColour = display.color(255);

		display.color(0);
		display.fill(255);
		display.rect(X1, Y1, 2, Y2 - Y1);
		display.rect(X1, Y2, X2 - X1, 2);
		display.textFont(myFont);
		
		xPos = X1 + 2;
		display.pushMatrix();
		display.translate(xPos - 85, ((Y2-Y1)/2) + 250);
		display.rotate(-PConstants.PI/2);
		display.text("Amount Of Entries", 0, 0);
		display.popMatrix();
		display.text("Price Sold(£)", (X2-X1)/2 + 70, Y2 + 70);

		display.color(0);
		display.color(255);

		barLength = Math.abs(X2 - X1) / histValues.length;

		maxHeight = 0;
		maxHeightIndex = 0;

		for (int i = 0; i < histValues.length; i++)
		{
			if (histValues[i] > maxHeight)
			{
				maxHeight = histValues[i];
				maxHeightIndex = i;
			}
		}
		display.stroke(255);
		for (int bar = 0; bar < histValues.length; bar++)
		{
			histValues[bar] *= (Y2 - Y1);
			if (maxHeight != 0)
				histValues[bar] /= maxHeight;
			currentHistValues[bar] *= (Y2 - Y1);
			if (maxHeight != 0)
				currentHistValues[bar] /= maxHeight;
			currentColor = (colors[(bar) % colors.length]);
			// if(bar != 0)
			// {
			// while(colorFound[bar] == 0)
			// {
			// currentColor = generator.nextInt(colors.length);
			// if(currentColor != lastColor)
			// colorFound[bar] = colors[currentColor];
			// }
			// lastColor = currentColor;
			// currentColor = colorFound[bar];
			// }
			// else
			// {
			// if(colorFound[bar] == 0)
			// {
			// currentColor = generator.nextInt(colors.length);
			// lastColor = currentColor;
			// colorFound[bar] = colors[currentColor];
			// currentColor = colorFound[bar];
			// }
			// }

			if (isHover(xPos, ((Y2 - (currentHistValues[bar]))), (xPos + barLength),
					((Y2 - (currentHistValues[bar])) + histValues[bar]) + ((currentHistValues[bar]) - histValues[bar])))
			{
				display.fill(255);
				display.arc(X2 - 300, Y1 + 145, 90, 90, PConstants.PI / 2, PConstants.PI + PConstants.PI / 2);
				display.rect(X2 - 300, Y1 + 100, 200, 90);
				display.arc(X2 - 100, Y1 + 145, 90, 90, -PConstants.PI/2, PConstants.PI / 2);
				display.fill(0);
				display.text("Bar number: " + (bar + 1), X2 - 300, Y1 + 125);
				display.text("Price range: " + (bar * splitValue) + " - " + (bar * splitValue + splitValue), X2 - 300,
						Y1 + 150);
				entriesInBar = Queries.entriesInPriceRange(test, bar * splitValue, bar * splitValue + splitValue);
				display.text("Entries: " + entriesInBar.length, X2 - 300, Y1 + 175);
				display.fill(currentColor - 100);
			} else
				display.fill(currentColor);
			display.rect(xPos, (Y2 - (currentHistValues[bar])), barLength, (currentHistValues[bar]));
			if((bar+1) == histValues.length)
				display.rect(xPos + barLength, Y2, 1, 10);
			display.fill(255);
			display.rect(xPos, Y2, 1, 10);
			xPos += barLength;

		}
		xPos = X1 + 2;
		entriesInBar = Queries.entriesInPriceRange(test, maxHeightIndex * splitValue, maxHeightIndex * splitValue + splitValue);
		steps = (int) Math.floor(Math.log(Math.pow(entriesInBar.length, 2.3)));
		stepNum = maxHeight / steps;
		currentHeight = 0;
		amountOfEntries = Queries.entriesInPriceRange(test, maxHeightIndex * splitValue,
				maxHeightIndex * splitValue + splitValue);
		currentEntryAmount = 0;
		currentEntryStep = amountOfEntries.length / steps;

		for (int yAxis = 0; yAxis <= steps; yAxis++)
		{
			display.fill(255);
			display.text(currentEntryAmount, xPos - 50, (Y2 - currentHeight - 2));
			currentHeight += stepNum;
			currentEntryAmount += currentEntryStep;
		}

		for (int xAxis = 0; xAxis < currentHistValues.length; xAxis++)
		{
			display.fill(textColour);
//			if (xAxis == 0)
//			{
//				display.text("Amount of bars:\n" + currentHistValues.length, xPos + 70, 100);
//				currentHeight = 0;
//			}
			if (xAxis % 2 == 0)
			{
				display.pushMatrix();
				display.translate(xPos, ((Y2 - histValues[xAxis]) + histValues[xAxis]) + 20);
				display.rotate(PConstants.PI / 4);
				display.text((xAxis * splitValue), 0, 0);
				display.popMatrix();
			}
			xPos += barLength + 1;

		}
		display.noStroke();
		if(showTitle)
		{
			titleFont = display.createFont(Font.SANS_SERIF, 48);
			display.textFont(titleFont);
			display.text(test[0].town, (X2-X1)/2 + 70, Y1);
		}
	}

	public boolean isHover(int x1, int y1, int x2, int y2)
	{
		return (display.mouseX >= x1 && display.mouseY >= y1 && display.mouseX <= x2 && display.mouseY <= y2);
	}

	public void draw()
	{

		if (newHistValues)
		{
			histValues = Queries.generateHistogramValues(test, splitValue, amountOfBars);
			for (int i = 0; i < histValues.length; i++)
				currentHistValues[i] = 0;
			standardHistogram();
			nextHistValues = histValues;
			newHistValues = false;
			for (int color = 0; color < colorFound.length; color++)
				colorFound[color] = 0;
		} else
		{
			if (!Arrays.equals(histValues, nextHistValues))
			{
				newHistValues = true;

			} else
			{
				standardHistogram();
				for (int i = 0; i < histValues.length; i++)
				{
					if (histValues[i] != 0 && currentHistValues[i] <= (histValues[i]))
						currentHistValues[i] += 10;
				}
			}
		}

	}

	public void changeHistValues(Entry[] newEntry)
	{
		nextHistValues = Queries.generateHistogramValues(newEntry, splitValue, amountOfBars);
	}
	
	public void titleOn()
	{
		if(showTitle == false)
			showTitle = true;
	}
	
	public void titleOff()
	{
		if(showTitle == true)
			showTitle = false;
	}
}