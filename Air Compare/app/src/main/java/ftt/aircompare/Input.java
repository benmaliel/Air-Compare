package ftt.aircompare;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class Input extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static public ArrayList<AirConditioner> airConditionerArray;
    static public ArrayList<ClimateRegion> climateRegionArray;
    static public Query currentQuery;
    static public String querySource;

    static public double userLatitude = -30;
    static public double userLongitude = 140;
    static public String userPostCode = "2000";

    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Query newQuery = new Query("", "", "", 0, 0, "");

        currentQuery = newQuery;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ArrayList<String> manufacturerArray = getManufacturerList();
        ArrayList<String> modelArray = getModelList();

        ArrayAdapter<String> manufacturerAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, manufacturerArray);
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, modelArray);

        AutoCompleteTextView manufacturerView = (AutoCompleteTextView)findViewById(R.id.brandText);
        manufacturerView.setThreshold(1);
        manufacturerView.setAdapter(manufacturerAdapter);

        AutoCompleteTextView modelView = (AutoCompleteTextView)findViewById(R.id.modelText);
        modelView.setThreshold(1);
        modelView.setAdapter(modelAdapter);

        loadData();

        Intent i = new Intent(this, InitInput.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData()
    {
        airConditionerArray = new ArrayList<>();
        climateRegionArray = new ArrayList<>();

        InputStream acStream = getResources().openRawResource(R.raw.aircon_data);
        InputStream crStream = getResources().openRawResource(R.raw.climate_data);

        BufferedReader reader = new BufferedReader(new InputStreamReader(acStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String modelNumber = data[0];
                String brand = data[1];
                double cHumidRate = -1.0;
                if (data[2].equals("null") == false)
                {
                    cHumidRate = Double.parseDouble(data[2]);
                }
                String configuration1 = data[3];
                String configuration2 = data[4];
                String country = data[5];
                double cPowerInpRate = -1.0;
                if (data[6].equals("null") == false)
                {
                    cPowerInpRate = Double.parseDouble(data[6]);
                }

                double cSensCoolRate = -1.0;
                if (data[7].equals("null") == false)
                {
                    cSensCoolRate = Double.parseDouble(data[7]);
                }

                double cTotalCoolRate = -1.0;
                if (data[8].equals("null") == false)
                {
                    cTotalCoolRate = Double.parseDouble(data[8]);
                }
                double depth = -1;
                if (data[9].equals("null") == false)
                {
                    depth = Double.parseDouble(data[9]);
                }
                double height = -1;
                if (data[10].equals("null") == false)
                {
                    height = Double.parseDouble(data[10]);
                }
                double hPowerInpRate = -1.0;
                if (data[11].equals("null") == false)
                {
                    hPowerInpRate = Double.parseDouble(data[11]);
                }
                double hTotalHeatRated = -1.0;
                if (data[12].equals("null") == false)
                {
                    hTotalHeatRated = Double.parseDouble(data[12]);
                }
                double eerTestAverage = -1.0;
                if (data[13].equals("null") == false)
                {
                    eerTestAverage = Double.parseDouble(data[13]);
                }
                double copTestAverage = -1.0;
                if (data[14].equals("null") == false)
                {
                    copTestAverage = Double.parseDouble(data[14]);
                }
                String expDate = data[15];
                String grandDate = data[16];
                String type = data[17];
                double width = -1.0;
                if (data[18].equals("null") == false)
                {
                    width = Double.parseDouble(data[18]);
                }
                String availability = data[19];
                double coolStar = -1.0;
                if (data[20].equals("null") == false)
                {
                    coolStar = Double.parseDouble(data[20]);
                }
                double heatStar = -1.0;
                if (data[21].equals("null") == false)
                {
                    heatStar = Double.parseDouble(data[21]);
                }

                AirConditioner newConditioner = new AirConditioner(modelNumber, brand, cHumidRate, configuration1, configuration2, country, cPowerInpRate, cSensCoolRate, cTotalCoolRate, depth, height, hPowerInpRate, hTotalHeatRated, eerTestAverage, copTestAverage, expDate, grandDate, type, width, availability, coolStar, heatStar);
                airConditionerArray.add(newConditioner);
            }
        }
        catch (IOException ex) {
            // handle exception
        }
        finally {
            try {
                acStream.close();
            }
            catch (IOException e) {
                // handle exception
            }
        }

        reader = new BufferedReader(new InputStreamReader(crStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String postcode = data[0];
                double longitude = Double.parseDouble(data[1]);
                double latitude = Double.parseDouble(data[2]);
                double coolingHours = Double.parseDouble(data[3]);
                double heatingHours = Double.parseDouble(data[4]);

                ClimateRegion newRegion = new ClimateRegion(postcode, longitude, latitude, coolingHours, heatingHours);
                climateRegionArray.add(newRegion);
            }
        }
        catch (IOException ex) {
        }
        finally {
            try {
                crStream.close();
            }
            catch (IOException e) {
            }
        }

        getSupportActionBar().setTitle(Integer.toString(climateRegionArray.size()));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        Location userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        userLatitude = userLocation.getLatitude();
        userLongitude = userLocation.getLongitude();

        Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> address = null;

        if (geoCoder != null) {
            try {
                address = geoCoder.getFromLocation(userLatitude, userLongitude, 1);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (address.size() > 0) {
                userPostCode = address.get(0).getPostalCode();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static ArrayList<String> getModelList()
    {
        ArrayList<String> outputArray = new ArrayList<>();

        InputStream muStream = Wattson.getAppContext().getResources().openRawResource(R.raw.model_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(muStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                outputArray.add(line);
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                muStream.close();
            }
            catch (IOException e) {
            }
        }

        return outputArray;
    }

    public static ArrayList<String> getManufacturerList()
    {
        ArrayList<String> outputArray = new ArrayList<>();

        InputStream muStream = Wattson.getAppContext().getResources().openRawResource(R.raw.manufacturer_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(muStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(" ");

                for (int i = 0; i < lineSplit.length; i++)
                {
                    lineSplit[i] =  lineSplit[i].substring(0, 1).toUpperCase() + lineSplit[i].substring(1).toLowerCase();
                }

                outputArray.add(TextUtils.join(" ", lineSplit));
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                muStream.close();
            }
            catch (IOException e) {
            }
        }

        return outputArray;
    }

    public static double getCoolingHours(String postCode)
    {
        for (int i = 0; i < climateRegionArray.size(); i++)
        {
            if (climateRegionArray.get(i).postcode.equals(postCode))
            {
                return climateRegionArray.get(i).coolingHours;
            }
        }

        return 0;
    }

    public static double getHeatingHours(String postCode)
    {
        for (int i = 0; i < climateRegionArray.size(); i++)
        {
            if (climateRegionArray.get(i).postcode.equals(postCode))
            {
                return climateRegionArray.get(i).heatingHours;
            }
        }

        return 0;
    }
}
