import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.*;

public class LandRegistryWeek1 extends PApplet
{
	// warning - bad code, use only for testing
	
	public static void main(String[] args)
	{

		PApplet.main("LandRegistryWeek1");

	}

	public void settings()
	{
		size(1500, 900);
	}

	final int EVENT_BUTTON_1 = 1;
	final int EVENT_BUTTON_2 = 2;
	final int EVENT_BUTTON_3 = 3;
	final int EVENT_NULL = 0;

	Widget averagePrice, highestPrice, lowestPrice;
	ArrayList<Widget> widgetList;

	Entry[] entries;
	Entry[] test;
	Region[] regions;
	SearchBar textInput;
	Histogram hist;
	PieChart pieChart;
	int[] bluh = { 50, 100, 150, 200 };
	int textColour;
	boolean newHistValues = true;
	int[] histValues;
	int[] cloneHistValues;
	boolean next = false;
	boolean[] testArray = new boolean[2];


	public void setup()
	{
		String[] lines = loadStrings("pp-1month.csv");
		regions = Region.initialiseRegions(loadStrings("Postcode districts.csv"));
		entries = new Entry[lines.length];

		String[] values = new String[11];

		for (int line = 0; line < lines.length; line++)
		{
			values = lines[line].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			entries[line] = new Entry(Integer.parseInt(values[0]), values[1], values[2], values[3].charAt(0),
					values[4].charAt(0), values[5].replaceAll("\"", ""), values[6], values[7], values[8], values[9],
					values[10]);
		}
		test = entries;

		hist = new Histogram(this, 50, 100, 1350, 750);
		pieChart = new PieChart(this, 300, 300, 600, 600);

		PFont myFont = createFont(Font.SANS_SERIF, 15);
		textFont(myFont);
		textColour = color(255);

		textInput = new SearchBar(this, 0, 0, 50, 250, myFont);
		
		testArray[0] = true;
		testArray[1] = false;

		// System.out.println(Queries.averagePriceMean(entries));
		// System.out.println(Queries.averagePriceMedian(entries));

		// for (int index = 0; index < regions.length; index++)
		// {
		// System.out.println("\"" + regions[index].regionName + "\"");
		// if (regions[index].regionName.equals("Worcester"))
		// {
		// Entry[] entriesInAberdeenshire = Queries.entriesInRegion(entries,
		// regions[index]);
		// for (int printed = 0; printed < entriesInAberdeenshire.length;
		// printed++)
		// {
		// System.out.println(entriesInAberdeenshire[printed].toString());
		// }
		// }
		// }

		background(0);
		smooth();

		int buttonColour = color(50, 230, 60);

		averagePrice = new Widget(this, 200, 0, 50, 50, "AVG", buttonColour, myFont, EVENT_BUTTON_1);
		highestPrice = new Widget(this, 300, 0, 50, 50, "HGH", buttonColour, myFont, EVENT_BUTTON_2);
		lowestPrice = new Widget(this, 400, 0, 50, 50, "LOW", buttonColour, myFont, EVENT_BUTTON_3);

		// need to set the buttons
		widgetList = new ArrayList<Widget>();
		widgetList.add(averagePrice);
		widgetList.add(highestPrice);
		widgetList.add(lowestPrice);
		
		int[] bluh = Queries.generateHistogramValues(entries, 15000, 50);
		for (int index = 0; index < bluh.length; index++)
			System.out.println(bluh[index]);

	}

	public void draw()
	{
		background(0);
		textInput.draw();

		for (int index = 0; index < widgetList.size(); index++)
		{
			widgetList.get(index).draw();
		}
		
		pieChart.oldNewdPieChart(testArray, bluh);
		
		if(newHistValues)
		{
			histValues = Queries.generateHistogramValues(test, 15000, 50);
			cloneHistValues = histValues.clone();
			hist.standardHistogram(histValues, cloneHistValues, bluh, 15000);
			newHistValues = false;
			next = false;
		}
		else
		{
			int[] currentHistValues = Queries.generateHistogramValues(test, 15000, 50);
			if(next)//!Arrays.equals(histValues, currentHistValues))
			{
				newHistValues = true;
			}
			else
			{
				hist.standardHistogram(histValues, cloneHistValues, bluh, 15000);
				for(int i = 0; i < histValues.length; i++)
				{
					if(histValues[i] != 0 && cloneHistValues[i] <= (2*histValues[i]))
						cloneHistValues[i]+=10;
				}
			}
		}
	}

	public void mousePressed()
	{
		next = true;
		textInput.activateSearchBar(mouseX, mouseY);

		int event = getEvent();
		switch (event)
		{
		case EVENT_BUTTON_1:
			System.out.println(Queries.averagePriceMean(test));
			break;
		case EVENT_BUTTON_2:
			System.out.println(Queries.mostExpensiveEntry(test));
			break;
		case EVENT_BUTTON_3:
			System.out.println(Queries.leastExpensiveEntry(test));
			break;

		}
	}

	public void keyPressed()
	{

		if (key == ENTER)
		{
			test = Queries.entriesInLocation(entries,regions, textInput.displayedText);
		}
		textInput.manipulateText(key);
	}

	public int getEvent()
	{
		int event;
		for (int i = 0; i < widgetList.size(); i++)
		{
			Widget aWidget = (Widget) widgetList.get(i);
			event = aWidget.getEvent(this.mouseX, this.mouseY);
			if (event != EVENT_NULL)
			{
				return event;
			}
		}
		return EVENT_NULL;

	}

}
