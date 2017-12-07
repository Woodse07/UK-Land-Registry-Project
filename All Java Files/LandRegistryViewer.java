import java.awt.Font;

import processing.core.*;

public class LandRegistryViewer extends PApplet
{
	// main authorship - Edvinas Teiserskis
	// Note - this class only for final entries, use the previous mainline for
	// testing.
	// NO CHANGES to this class unless implementing a permanent fixture.

	public static void main(String[] args)
	{
		PApplet.main("LandRegistryViewer");
	}

	public static final int TOP_OFFSET = 0;
	public static final int UPPER_BAR_HEIGHT = 100;
	public static final int BAR_TO_DISPLAY_OFFSET = 0;
	public static final int BOTTOM_OFFSET = 50;
	public static final int BAR_SIDE_OFFSET = 0;
	public static final int DISPLAY_SIDE_OFFSET = 50;
	public static final int MAP_X_POSITION = 1000;
	public static final int MAP_Y_POSITION = 55;
	public static final String[] BUTTON_LABELS = {"","AVERAGE", "MAXIMUM", "MINIMUM", "RESET" , "-" , "+", "QUIT"};

	public void settings()
	{
		size(1500, 900);
	}

	Entry[] allEntries;
	Entry[] currentEntries;
	Histogram mainChart;
	SearchBar textInput;
	Widget[] buttons;
	String currentSelectedEntry;
	PieChart currentPieChart;
	Region[] regions;
	PImage backgroundImage;
	String[] currentText = { "", "" };

	int textColour;
	int[] pieChartColours;
	int[] pieChartValues;
	boolean newEntries;

	String currentPost = "12345";
	MapInfo map;
	PImage baseMap;
	PImage mapCurrentEntry;

	int histogramSplit = 15000;
	int amountOfBars;

	public void setup()
	{
		backgroundImage = loadImage("background.png");
		PFont myFont = createFont(Font.SANS_SERIF, 15);
		textFont(myFont);
		textColour = color(255);
		pieChartColours = new int[5];
		pieChartColours[0] = color(200, 100, 100);
		pieChartColours[1] = color(100, 200, 100);
		pieChartColours[2] = color(100, 100, 200);
		pieChartColours[3] = color(200, 200, 100);
		pieChartColours[4] = color(200, 100, 200);

		String[] lines = loadStrings("entries.csv");
		allEntries = new Entry[lines.length];
		String[] values = new String[11];

		for (int line = 0; line < lines.length; line++)
		{
			values = lines[line].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			allEntries[line] = new Entry(Integer.parseInt(values[0]), values[1], values[2], values[3].charAt(0),
					values[4].charAt(0), values[5].replaceAll("\"", ""), values[6], values[7], values[8], values[9],
					values[10]);
		}

		currentEntries = allEntries;

		regions = Region.initialiseRegions(loadStrings("Postcode districts.csv"));

		amountOfBars = 50;
		mainChart = new Histogram(this, 100, 200, 1000, 800,
				Queries.generateHistogramValues(currentEntries, histogramSplit, amountOfBars), currentEntries, pieChartColours,
				histogramSplit, false, amountOfBars);

		textInput = new SearchBar(this, 0, 0, 50, 300, myFont);

		int buttonColour = color(175,175,175, 150);
		buttons = new Widget[BUTTON_LABELS.length];
		PImage searchIcon = loadImage("searchIcon.png");
		buttons[0] = new ImageWidget(this, BAR_SIDE_OFFSET + 325,TOP_OFFSET,50,50,BUTTON_LABELS[0],buttonColour,myFont,8,searchIcon);
		for (int index = 1; index < BUTTON_LABELS.length; index++)
			buttons[index] = new Widget(this, BAR_SIDE_OFFSET + 400 + 125 * (index - 1), TOP_OFFSET, 100, 50,
					BUTTON_LABELS[index], buttonColour, myFont, index);

		baseMap = loadImage("Main.png");
		mapCurrentEntry = loadImage("BLANK.png");

		map = new MapInfo(this, MAP_X_POSITION, MAP_Y_POSITION, 0.7f, 0.7f, color(255,0,0), baseMap);

		image(backgroundImage, 0, 0);

		image(mapCurrentEntry, map.x, map.y, map.w, map.h);
		image(baseMap, map.x, map.y, map.w, map.h);
		baseMap = get(map.x + 165, map.y, (int) map.w, (int) map.h);

		newEntries = true;
		smooth(/*Jazz*/);
	}

	public void draw()
	{
		regenerateElements(newEntries);

		image(backgroundImage, 0, 0);

		for (int index = 0; index < buttons.length; index++)
			buttons[index].draw();

		currentPieChart.draw();
		textInput.draw();

		if (newEntries)
		{
			String newImg = map.getImg(currentPost);
			mapCurrentEntry = loadImage(newImg);
			image(map.mapBackground, map.x, map.y, map.w, map.h);
			tint(map.colorTint);
			image(mapCurrentEntry, map.x, map.y, map.w, map.h);
			noTint();
			baseMap = get(map.x + 165, map.y, (int) map.w, (int) map.h);
		}

		textFont(createFont(Font.SANS_SERIF, 20));
		fill(color(255));
		text(currentText[0], 25, 100);
		text(currentText[1], 25, 125);

		image(baseMap, map.x + 165, map.y, map.w, map.h);
		mainChart.draw();
		newEntries = false;
	}

	public void mousePressed()
	{
		textInput.activateSearchBar(mouseX, mouseY);

		int event = getEvent();
		switch (event)
		{
		
		case 1:
			if (currentEntries.length > 0)
			{
				currentText[0] = "Average Entry Price: £" + Integer.toString(Queries.averagePriceMean(currentEntries));
				currentText[1] = "";
			}
			break;
		case 2:
			if (currentEntries.length > 0)
				currentText = Queries.mostExpensiveEntry(currentEntries).toString().split(", Address:");
			break;
		case 3:
			if (currentEntries.length > 0)
				currentText = Queries.leastExpensiveEntry(currentEntries).toString().split(", Address:");
			break;
		case 4:
			currentEntries = allEntries;
			currentPost = "12345";
			currentText[0] = "";
			currentText[1] = "";
			textInput.displayedText = "";
			newEntries = true;
			break;
			
			
		case 5:
			if(amountOfBars > 5)
			{
				amountOfBars --;
				newEntries = true;
			}

			break;
		
		case 6:
			if(amountOfBars < 100)
			{
				amountOfBars ++;
				newEntries = true;
			}
			break;

		case 7:
			exit();
			break;
			
		case 8:
			currentEntries = Queries.entriesInLocation(allEntries, regions, textInput.displayedText);
			newEntries = true;
			if (currentEntries.length > 0)
			{
				currentPost = currentEntries[0].outwardCode;
				currentText[0] = "";
				currentText[1] = "";
			}
			break;
		}
		
		
	}

	public void keyPressed()
	{
		if (key == ENTER)
		{
			currentEntries = Queries.entriesInLocation(allEntries, regions, textInput.displayedText);
			newEntries = true;
			if (currentEntries.length > 0)
			{
				currentPost = currentEntries[0].outwardCode;
				currentText[0] = "";
				currentText[1] = "";
			}
		}

		textInput.manipulateText(key);
	}

	final int EVENT_NULL = 0;

	public int getEvent()
	{
		int event;
		for (int i = 0; i < buttons.length; i++)
		{
			Widget aWidget = buttons[i];
			event = aWidget.getEvent(this.mouseX, this.mouseY);
			if (event != EVENT_NULL)
			{
				return event;
			}
		}
		return EVENT_NULL;

	}

	public void regenerateElements(boolean toRegenerate)
	{
		if (!toRegenerate)
			return;

		if (currentEntries.length == 0)
		{
			currentText[0] = "No Entries Found.";
			currentText[1] = "";
			return;
		}

		pieChartValues = Queries.generatePieChartValues(currentEntries);
		currentPieChart = new PieChart(this, 1250, 750, 200, pieChartValues, pieChartColours, Entry.PROPERTY_TYPES);
		mainChart = new Histogram(this, 100, 200, 1000, 800,
				Queries.generateHistogramValues(currentEntries, histogramSplit, amountOfBars), currentEntries, pieChartColours,
				histogramSplit,(currentEntries == allEntries?false:true), amountOfBars);
	}
}
