import java.util.ArrayList;

public class Region
{
	// main authorship - Edvinas Teiserskis
	
	final String regionName;
	ArrayList<String> postcodeOutwardAreasInRegion = new ArrayList<String>();

	Region(String regionName)
	{
		this.regionName = regionName;
	}
	
	Region(String regionName, String firstPostcode){
		this.regionName = regionName;
		this.postcodeOutwardAreasInRegion.add(firstPostcode);
	}
	
	String currentPostcodes(){
		String output = postcodeOutwardAreasInRegion.get(0);
		
		for(int index = 1; index < postcodeOutwardAreasInRegion.size() ; index++){
			output += ", " + postcodeOutwardAreasInRegion.get(index);
		}
		
		return output;
	}
	
	public static Region[] initialiseRegions(String[] postcodeDistrictsFile)
	{
		//This method currently works with the "Postcode districts.csv" and only it.
		
		ArrayList<Region> regions = new ArrayList<Region>();

		String[] postcodeOutwardArea = new String[12];

		for (int line = 1; line < postcodeDistrictsFile.length; line++)
		{
			postcodeOutwardArea = postcodeDistrictsFile[line].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			int currentRegion = -1;
			for (int index = 0; index < regions.size(); index++)
			{
				if (regions.get(index).regionName.equals(postcodeOutwardArea[7]))
				{
					currentRegion = index;
					break;
				}
			}
			if(currentRegion == -1){
				regions.add(new Region(postcodeOutwardArea[7],postcodeOutwardArea[0]));
			}
			else
			{
				regions.get(currentRegion).postcodeOutwardAreasInRegion.add(postcodeOutwardArea[0]);
			}
			
		}

		return regions.toArray(new Region[regions.size()]);
	}
}
