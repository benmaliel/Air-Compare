package ftt.aircompare;

/**
 * Created by michaelnguyen-kim on 4/07/15.
 */
public class ClimateRegion {
    public String postcode;
    public double longitude;
    public double latitude;
    public double coolingHours;
    public double heatingHours;

    public ClimateRegion(String postcode, double longitude, double latitude, double coolingHours, double heatingHours)
    {
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.coolingHours = coolingHours;
        this.heatingHours = heatingHours;
    }
}
