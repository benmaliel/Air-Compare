package ftt.aircompare;

/**
 * Created by michaelnguyen-kim on 4/07/15.
 */
public class Query {
    public String manufacturer;
    public String cycle;
    public String installation;
    public double lowerLimit;
    public double upperLimit;
    public String model;

    public Query (String manufacturer, String cycle, String installation, double lowerLimit, double upperLimit, String model)
    {
        this.manufacturer = manufacturer;
        this.cycle = cycle;
        this.installation = installation;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.model = model;
    }
}
