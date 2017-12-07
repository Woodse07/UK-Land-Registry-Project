import java.util.ArrayList;
import java.util.Arrays;

public class Queries
{
	
	// main authorship - Edvinas Teiserskis
	
	public static int[] generateHistogramValues(Entry[] entries, int splitValue, int numberOfBars)
	{
		int[] values = new int[numberOfBars];
		for (int index = 0; index < values.length; index++)
			values[index] = 0;

		for (int index = 0; index < entries.length; index++)
		{
			int inBar = entries[index].price / splitValue;

			if (inBar < numberOfBars)
				values[inBar]++;
		}

		return values;
	}

	public static int[] generateHistogramValues(Entry[] entries, int numberOfBars)
	{
		int splitValue = (ninetyFifthPercentile(entries) / numberOfBars) + 1;

		return generateHistogramValues(entries, splitValue, numberOfBars);
	}

	public static Entry[] entriesInOutwardArea(Entry[] entries, String outwardCode)
	{
		ArrayList<Entry> entriesInArea = new ArrayList<Entry>();

		for (int index = 0; index < entries.length; index++)
		{
			if (entries[index].outwardCode != null)
			{
				if (entries[index].outwardCode.equals(outwardCode))
					entriesInArea.add(entries[index]);
			}
		}
		
		return entriesInArea.toArray(new Entry[entriesInArea.size()]);
	}

	public static Entry[] entriesInRegion(Entry[] entries, Region region)
	{
		ArrayList<Entry> resultEntries = new ArrayList<Entry>();

		for (int index = 0; index < region.postcodeOutwardAreasInRegion.size(); index++)
			resultEntries.addAll(new ArrayList<Entry>(
					Arrays.asList(entriesInOutwardArea(entries, region.postcodeOutwardAreasInRegion.get(index)))));

		return resultEntries.toArray(new Entry[resultEntries.size()]);
	}

	public static Entry[] entriesInLocation(Entry[] entries, Region[] allRegions, String searchFor)
	{
		ArrayList<Entry> resultEntries = new ArrayList<Entry>();
		boolean isRegion = false;

		for (int region = 0; region < allRegions.length; region++)
		{
			if (allRegions[region].regionName.equalsIgnoreCase(searchFor))
			{
				resultEntries.addAll(new ArrayList<Entry>(Arrays.asList(entriesInRegion(entries, allRegions[region]))));
				isRegion = true;
			}
		}
		if (!isRegion)
		{
			resultEntries.addAll(new ArrayList<Entry>(Arrays.asList(entriesInOutwardArea(entries, searchFor))));
		}
		return resultEntries.toArray(new Entry[resultEntries.size()]);
	}

	public static Entry[] entriesOfPropertyType(Entry[] entries, String propertyType)
	{

		ArrayList<Entry> entriesOfType = new ArrayList<Entry>();

		for (int index = 0; index < entries.length; index++)
			if (entries[index].propertyType == propertyType)
				entriesOfType.add(entries[index]);

		return entriesOfType.toArray(new Entry[entriesOfType.size()]);
	}

	public static int[] generatePieChartValues(Entry[] entries)
	{
		int[] values = new int[5];
		
		values[0] = entriesOfPropertyType(entries,Entry.DETACHED).length;
		values[1] = entriesOfPropertyType(entries,Entry.SEMI_DETACHED).length;
		values[2] = entriesOfPropertyType(entries,Entry.TERRACED).length;
		values[3] = entriesOfPropertyType(entries,Entry.FLATS_MAISONETTES).length;
		values[4] = entriesOfPropertyType(entries,Entry.OTHER).length;
		
		return values;
	}

	public static Entry[] entriesInTimeSlot(Entry[] entries, int startDay, int startMonth, int startYear, int endDay,
			int endMonth, int endYear)
	{

		// time slot is inclusive at both ends.

		ArrayList<Entry> entriesInTimeSlot = new ArrayList<Entry>();

		for (int index = 0; index < entries.length; index++)
		{
			if (entries[index].year >= startYear && entries[index].year <= endYear)
				if (!(entries[index].year == startYear && entries[index].month < startMonth)
						&& !(entries[index].year == endYear && entries[index].month > endMonth))
					if (!(entries[index].year == startYear && entries[index].month == startMonth
							&& entries[index].day < startDay)
							&& !(entries[index].year == endYear && entries[index].month == endMonth
									&& entries[index].day > endDay))
						entriesInTimeSlot.add(entries[index]);
		}

		return entriesInTimeSlot.toArray(new Entry[entriesInTimeSlot.size()]);
	}

	public static Entry[] entriesInPriceRange(Entry[] entries, int lowerRange, int upperRange)
	{
		ArrayList<Entry> entriesInPriceRange = new ArrayList<Entry>();

		for (int index = 0; index < entries.length; index++)
			if (entries[index].price >= lowerRange && entries[index].price <= upperRange)
				entriesInPriceRange.add(entries[index]);

		return entriesInPriceRange.toArray(new Entry[entriesInPriceRange.size()]);
	}

	public static Entry mostExpensiveEntry(Entry[] entries)
	{
		Entry mostExpensive = entries[0];

		for (int index = 1; index < entries.length; index++)
		{
			if (mostExpensive.price < entries[index].price)
				mostExpensive = entries[index];
		}

		return mostExpensive;
	}

	public static Entry leastExpensiveEntry(Entry[] entries)
	{
		Entry leastExpensive = entries[0];

		for (int index = 1; index < entries.length; index++)
		{
			if (leastExpensive.price > entries[index].price)
				leastExpensive = entries[index];
		}

		return leastExpensive;
	}

	public static int averagePriceMean(Entry[] entries)
	{
		int currentAverage = 0;
		int numbersAveraged = 0;
		for (int index = 0; index < entries.length; index++)
			currentAverage += (entries[index].price - currentAverage) / ++numbersAveraged;
		return currentAverage;
	}

	public static int averagePriceMedian(Entry[] entries)
	{
		int[] prices = new int[entries.length];

		for (int index = 0; index < entries.length; index++)
			prices[index] = entries[index].price;

		Arrays.sort(prices);

		return prices[(prices.length - 1) / 2];
	}

	private static int ninetyFifthPercentile(Entry[] entries)
	{
		int[] prices = new int[entries.length];

		for (int index = 0; index < entries.length; index++)
			prices[index] = entries[index].price;

		Arrays.sort(prices);

		return prices[(prices.length / 20) * 19];
	}
}
