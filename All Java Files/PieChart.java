import processing.core.*;

public class PieChart
{
	// main authorship - Seamus Woods
	PApplet display = new PApplet();
	int Y1 = 100;
	int X1 = 500;
	int diameter = 200;
	int[] data;
	int[] colors;
	String[] types;
	float[] radianArray;
	double totalFrequencies;
	float currentAngle;
	float degreePerFrequency;
	float degree;
	float[] currentRadian;
	boolean drawingSegment;
	boolean newPieChart;
	int percentage;

	public PieChart(PApplet disp, int[] data, int[] colors)
	{
		display = disp;
		this.data = data;
		this.colors = colors;
		this.newPieChart = true;
	}

	public PieChart(PApplet disp, int x1, int y1, int diameter, int[] data, int[] colors, String[] types)
	{
		this.display = disp;
		this.Y1 = y1;
		this.X1 = x1;
		this.diameter = diameter;
		this.data = data;
		this.colors = colors;
		this.newPieChart = true;
		this.types = types;
	}
	
	public void standardPieChart()
	{
		radianArray = new float[data.length];
		totalFrequencies = 0;
		currentAngle = 0;
		for(int count = 0; count < data.length; count++)
		{
			totalFrequencies += data[count];
		}
		degreePerFrequency = (float) (360.0 / totalFrequencies);
		for(int dataIndex = 0; dataIndex < data.length; dataIndex++)
		{
			degree = data[dataIndex] * degreePerFrequency;
			radianArray[dataIndex] = (degree * (PConstants.PI / 180));
		}
				
		for(int arc = 0; arc < radianArray.length; arc++)
		{
			display.fill(colors[(arc) % colors.length]);
			if(arc == 0)
			{
				display.rect((X1 + diameter/2) + 10, (Y1 - diameter/2) - 10, 15, 15);
				display.text(types[arc], (X1 + diameter/2) + 90, (Y1 - diameter/2) - 5);
				if(isHover(X1, Y1, diameter, 0, radianArray[arc]))
					display.fill(colors[arc] - 50);
				if(currentRadian[arc] != radianArray[arc])
				{
					currentRadian[arc] += 0.05;
					if(currentRadian[arc] >= radianArray[arc])
					{
						drawingSegment = false;
						currentRadian[arc] = radianArray[arc];
					}
					display.arc(X1, Y1, diameter, diameter, 0, currentRadian[arc]);
					currentAngle += currentRadian[arc];
				}
				else
				{
					display.arc(X1, Y1, diameter, diameter, 0, currentRadian[arc]);
					currentAngle += radianArray[arc];
				}
					
			}
			else
			{
				display.rect((X1 + diameter/2) + 10, (Y1 - diameter/2) - 10 + (20*arc), 15, 15);
				display.text(types[arc], (X1 + diameter/2) + 90, (Y1 - diameter/2) - 5 + (20*arc));
				if(isHover(X1, Y1, diameter, currentAngle, (radianArray[arc] + currentAngle))) {
					display.fill(colors[arc] - 70);

				}
				if(currentRadian[arc] != radianArray[arc])
				{
					currentRadian[arc] += 0.05;
					if(currentRadian[arc] >= radianArray[arc])
					{
						drawingSegment = false;
						currentRadian[arc] = radianArray[arc];
					}
					display.arc(X1, Y1, diameter, diameter, currentAngle, (currentRadian[arc] + currentAngle));
					currentAngle += currentRadian[arc];
				}
				else
				{
					display.arc(X1, Y1, diameter, diameter, currentAngle, (currentRadian[arc] + currentAngle));
					currentAngle += radianArray[arc];
				}
			}	
		}
	}
		
	public boolean isHover(int xPos, int yPos, int diameter, double startAngle, double endAngle)
	{	
		boolean hovering = false;		
		float X = display.mouseX - xPos;
		float Y = display.mouseY - yPos;
		if(Math.sqrt((X*X) + (Y*Y)) < diameter/2 ) 
		{			
			float angle = PApplet.atan2(display.mouseY - yPos, display.mouseX - xPos);
			if(angle < 0)
			{
				angle *= -1;
				angle = PConstants.PI - angle;
				angle += PConstants.PI;
			}
			if(angle >= startAngle && angle <= endAngle)
			{
				hovering = true;
				return hovering;
			}
		} 	
		return hovering;
	}
	
	public void displayDetails()
	{
		currentAngle = 0;
		for(int arc = 0; arc < radianArray.length; arc++)
		{
			if(arc == 0)
			{
				if(isHover(X1, Y1, diameter, 0, radianArray[arc]))
				{
					display.fill(255);
					display.line(display.mouseX, display.mouseY, display.mouseX - 30, display.mouseY - 30);
					display.rect(display.mouseX - 130, display.mouseY - 60, 100, 30);
					display.fill(0);
					percentage = (int)((data[arc] / totalFrequencies) * 100);
					display.text(data[arc] + " (" + percentage + "%)", display.mouseX - 80, display.mouseY - 45);
				}
				currentAngle += currentRadian[arc];
			}
			else
			{
				if(isHover(X1, Y1, diameter, currentAngle, (radianArray[arc] + currentAngle)))
				{
					display.fill(255);
					display.line(display.mouseX, display.mouseY, display.mouseX - 30, display.mouseY - 30);
					display.rect(display.mouseX - 130, display.mouseY - 60, 100, 30);
					display.fill(0);
					percentage = (int)((data[arc] / totalFrequencies) * 100);
					display.text(data[arc] + " (" + percentage + "%)", display.mouseX - 80, display.mouseY - 45);
				}
				currentAngle += currentRadian[arc];
			}
		}
	}
	
	public void changeDataValues(int[] data)
	{
		this.data = data;
		newPieChart = true;
	}
	
	public void draw()
	{
		if(newPieChart)
		{
			currentRadian = new float[data.length];
			for(int i = 0; i < currentRadian.length; i++)
				currentRadian[i] = 0;
			newPieChart = false;
			standardPieChart();
		}
		else
		{
			standardPieChart();
			displayDetails();
		}
			
	}
}