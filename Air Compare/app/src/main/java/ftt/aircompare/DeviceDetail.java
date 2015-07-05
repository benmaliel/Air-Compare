package ftt.aircompare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class DeviceDetail extends ActionBarActivity {

    AirConditioner subjectDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        Intent intent = getIntent();

        int index = intent.getIntExtra("index", 0);
        String source = intent.getStringExtra("source");

        if (source.equals("search")) {
            subjectDevice = ResultList.positiveResults.get(index);
        }

        else if (source.equals("save"))
        {
            subjectDevice = SaveList.savedDevices.get(index);
        }

        TextView brandDisplay = (TextView) findViewById(R.id.manufacturerDisplay);
        TextView costDisplay = (TextView) findViewById(R.id.costDisplay);
        TextView suppView = (TextView) findViewById(R.id.suppView);
        TextView installationDisplay = (TextView) findViewById(R.id.installationDisplay);
        TextView functionDisplay = (TextView) findViewById(R.id.functionDisplay);
        TextView eerDisplay = (TextView) findViewById(R.id.eerDisplay);
        TextView dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        TextView countryDisplay = (TextView) findViewById(R.id.countryDisplay);
        TextView modelDisplay = (TextView) findViewById(R.id.modelDisplay);
        TextView availabilityDisplay = (TextView) findViewById(R.id.availabilityDisplay);
        View backdrop = (View) findViewById(R.id.backdrop);

        double consumedEnergy = subjectDevice.coolingInput*Input.getCoolingHours(Input.userPostCode) + subjectDevice.heatingInput*Input.getHeatingHours(Input.userPostCode);
        double costInDollars = consumedEnergy*0.34;
        String displayCost = "$" + String.format("%.2f", costInDollars);

        if (costInDollars < 0)
        {
            costInDollars *= -1;
            displayCost = "$" + String.format("%.2f", costInDollars) + "*";
        }

        if (costInDollars < 200)
        {
            costDisplay.setTextColor(this.getResources().getColor(R.color.green));
            suppView.setTextColor(this.getResources().getColor(R.color.green));
            backdrop.setBackgroundColor(this.getResources().getColor(R.color.green));
        }

        else if (costInDollars > 200 && costInDollars < 500)
        {
            costDisplay.setTextColor(this.getResources().getColor(R.color.blue));
            suppView.setTextColor(this.getResources().getColor(R.color.blue));
            backdrop.setBackgroundColor(this.getResources().getColor(R.color.blue));
        }

        else if (costInDollars > 500 && costInDollars < 900)
        {
            costDisplay.setTextColor(this.getResources().getColor(R.color.yellow));
            suppView.setTextColor(this.getResources().getColor(R.color.yellow));
            backdrop.setBackgroundColor(this.getResources().getColor(R.color.yellow));
        }

        else if (costInDollars > 1000)
        {
            costDisplay.setTextColor(this.getResources().getColor(R.color.red));
            suppView.setTextColor(this.getResources().getColor(R.color.red));
            backdrop.setBackgroundColor(this.getResources().getColor(R.color.red));
        }

        brandDisplay.setText(subjectDevice.brand);
        installationDisplay.setText(subjectDevice.installation);
        functionDisplay.setText(subjectDevice.type);
        eerDisplay.setText(Double.toString(subjectDevice.eerTestAverage));
        dateDisplay.setText(subjectDevice.grandDate);
        modelDisplay.setText(subjectDevice.modelNumber);
        countryDisplay.setText(subjectDevice.country);
        availabilityDisplay.setText(subjectDevice.availability);
        costDisplay.setText(displayCost);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveDevice();
            checkSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveDevice()
    {
        SharedPreferences settings = getSharedPreferences("data", MODE_PRIVATE);

        String value = settings.getString("device_saves", "");

        String outputValue;

        if (!value.equals("")) {
            outputValue = value + "~!" + subjectDevice.revertToRaw();
        }

        else
        {
            outputValue = subjectDevice.revertToRaw();
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("device_saves", outputValue);
        editor.commit();
    }

    public void checkSave()
    {
        SharedPreferences settings = getSharedPreferences("data", MODE_PRIVATE);

        String value = settings.getString("device_saves", "");

        getSupportActionBar().setTitle(value);
    }
}
