package ftt.aircompare;

import android.text.TextUtils;

/**
 * Created by michaelnguyen-kim on 4/07/15.
 */
public class AirConditioner {

    public String modelNumber;
    public String brand;
    public double cHumidRate;
    public String circulation;
    public String installation;
    public String country;
    public double coolingInput;
    public double cSensCoolRate;
    public double coolingOutput;
    public double depth;
    public double height;
    public double heatingInput;
    public double heatingOutput;
    public double eerTestAverage;
    public double copTestAverage;
    public String expDate;
    public String grandDate;
    public String type;
    public double width;
    public String availability;
    public double coolStar;
    public double heatStar;

    public AirConditioner(String modelNumber, String brand, double cHumidRate, String circulation, String installation, String country, double coolingInput, double cSensCoolRate, double coolingOutput, double depth, double height, double heatingInput, double heatingOutput, double eerTestAverage, double copTestAverage, String expDate, String grandDate, String type, double width, String availability, double coolStar, double heatStar)
    {
        this.modelNumber = modelNumber;
        this.brand = brand;
        this.cHumidRate = cHumidRate;
        this.circulation = circulation;
        this.installation = installation;
        this.country = country;
        this.coolingInput = coolingInput;
        this.cSensCoolRate = cSensCoolRate;
        this.coolingOutput = coolingOutput;
        this.depth = depth;
        this.height = height;
        this.heatingInput = heatingInput;
        this.heatingOutput = heatingOutput;
        this.eerTestAverage = eerTestAverage;
        this.copTestAverage = copTestAverage;
        this.expDate = expDate;
        this.grandDate = grandDate;
        this.type = type;
        this.width = width;
        this.availability = availability;
        this.coolStar = coolStar;
        this.heatStar = heatStar;
    }

    public String revertToRaw()
    {
        String[] rawOutput = {this.modelNumber, this.brand, Double.toString(this.cHumidRate), this.circulation, this.installation, this.country, Double.toString(this.coolingInput), Double.toString(this.cSensCoolRate), Double.toString(this.coolingOutput), Double.toString(this.depth), Double.toString(this.height), Double.toString(this.heatingInput), Double.toString(this.heatingOutput), Double.toString(this.eerTestAverage), Double.toString(this.copTestAverage), this.expDate, this.grandDate, this.type, Double.toString(this.width), this.availability, Double.toString(this.coolStar), Double.toString(this.heatStar)};
        String outputString = TextUtils.join(",", rawOutput);
        return outputString;
    }
}
