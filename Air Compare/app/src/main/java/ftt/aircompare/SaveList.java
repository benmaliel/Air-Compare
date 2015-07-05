package ftt.aircompare;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class SaveList extends ActionBarActivity {

    public static ArrayList<AirConditioner> savedDevices = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ResultCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_list);

        Input.querySource = "save";

        getSaves();

        mRecyclerView = (RecyclerView) findViewById(R.id.resultsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ResultCardAdapter(savedDevices, R.layout.result_card, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_list, menu);
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

    public void getSaves()
    {
        SharedPreferences settings = getSharedPreferences("data", MODE_PRIVATE);

        String value = settings.getString("device_saves", "");

        String[] deviceRaws = value.split("~!");

        for (int i = 0; i < deviceRaws.length; i++)
        {
            String dataLine = deviceRaws[i];

            if (!dataLine.equals("")) {
                String[] dataDecomp = dataLine.split(",");

                String modelNumber = dataDecomp[0];
                String brand = dataDecomp[1];
                double cHumidRate = -1.0;
                if (dataDecomp[2].equals("null") == false) {
                    cHumidRate = Double.parseDouble(dataDecomp[2]);
                }
                String configuration1 = dataDecomp[3];
                String configuration2 = dataDecomp[4];
                String country = dataDecomp[5];
                double cPowerInpRate = -1.0;
                if (dataDecomp[6].equals("null") == false) {
                    cPowerInpRate = Double.parseDouble(dataDecomp[6]);
                }

                double cSensCoolRate = -1.0;
                if (dataDecomp[7].equals("null") == false) {
                    cSensCoolRate = Double.parseDouble(dataDecomp[7]);
                }

                double cTotalCoolRate = -1.0;
                if (dataDecomp[8].equals("null") == false) {
                    cTotalCoolRate = Double.parseDouble(dataDecomp[8]);
                }
                double depth = -1;
                if (dataDecomp[9].equals("null") == false) {
                    depth = Double.parseDouble(dataDecomp[9]);
                }
                double height = -1;
                if (dataDecomp[10].equals("null") == false) {
                    height = Double.parseDouble(dataDecomp[10]);
                }
                double hPowerInpRate = -1.0;
                if (dataDecomp[11].equals("null") == false) {
                    hPowerInpRate = Double.parseDouble(dataDecomp[11]);
                }
                double hTotalHeatRated = -1.0;
                if (dataDecomp[12].equals("null") == false) {
                    hTotalHeatRated = Double.parseDouble(dataDecomp[12]);
                }
                double eerTestAverage = -1.0;
                if (dataDecomp[13].equals("null") == false) {
                    eerTestAverage = Double.parseDouble(dataDecomp[13]);
                }
                double copTestAverage = -1.0;
                if (dataDecomp[14].equals("null") == false) {
                    copTestAverage = Double.parseDouble(dataDecomp[14]);
                }
                String expDate = dataDecomp[15];
                String grandDate = dataDecomp[16];
                String type = dataDecomp[17];
                double width = -1.0;
                if (dataDecomp[18].equals("null") == false) {
                    width = Double.parseDouble(dataDecomp[18]);
                }
                String availability = dataDecomp[19];
                double coolStar = -1.0;
                if (dataDecomp[20].equals("null") == false) {
                    coolStar = Double.parseDouble(dataDecomp[20]);
                }
                double heatStar = -1.0;
                if (dataDecomp[21].equals("null") == false) {
                    heatStar = Double.parseDouble(dataDecomp[21]);
                }

                AirConditioner newConditioner = new AirConditioner(modelNumber, brand, cHumidRate, configuration1, configuration2, country, cPowerInpRate, cSensCoolRate, cTotalCoolRate, depth, height, hPowerInpRate, hTotalHeatRated, eerTestAverage, copTestAverage, expDate, grandDate, type, width, availability, coolStar, heatStar);
                savedDevices.add(newConditioner);
            }
        }
    }
}
