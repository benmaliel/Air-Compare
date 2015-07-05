package ftt.aircompare;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ResultList extends ActionBarActivity {

    public static ArrayList<AirConditioner> positiveResults = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ResultCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Input.querySource = "search";

        positiveResults.clear();

        Query receivedQuery = Input.currentQuery;

        for (int i = 0; i < Input.airConditionerArray.size(); i++)
        {
            AirConditioner currentDevice = Input.airConditionerArray.get(i);
            boolean brandEquals = currentDevice.brand.toLowerCase().equals(receivedQuery.manufacturer.toLowerCase());
            boolean installationEquals = currentDevice.installation.toLowerCase().equals(receivedQuery.installation.toLowerCase());
            boolean cycleEquals = currentDevice.type.equals(receivedQuery.cycle);
            boolean inputEquals = currentDevice.coolingInput > receivedQuery.lowerLimit && currentDevice.coolingInput < receivedQuery.upperLimit;
            boolean modelEquals = currentDevice.modelNumber.toLowerCase().contains(receivedQuery.model.toLowerCase()) || currentDevice.modelNumber.equals(receivedQuery.model);

            if (brandEquals && (cycleEquals || receivedQuery.cycle.equals("All")) && inputEquals && (installationEquals || receivedQuery.installation.equals("All")) && modelEquals)
            {
                positiveResults.add(currentDevice);
            }
        }

        Collections.sort(positiveResults, new CustomComparator());

        mRecyclerView = (RecyclerView) findViewById(R.id.resultsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ResultCardAdapter(positiveResults, R.layout.result_card, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_list, menu);
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

    public class CustomComparator implements Comparator<AirConditioner> {
        @Override
        public int compare(AirConditioner o1, AirConditioner o2) {
            double o1Energy = o1.coolingInput*Input.getCoolingHours(Input.userPostCode) + o1.heatingInput*Input.getHeatingHours(Input.userPostCode);
            double o1Cost = o1Energy*0.34;
            if (o1Cost < 0)
            {
                o1Cost *= -1;
            }
            double o2Energy = o2.coolingInput*Input.getCoolingHours(Input.userPostCode) + o2.heatingInput*Input.getHeatingHours(Input.userPostCode);
            double o2Cost = o2Energy*0.34;
            if (o2Cost < 0)
            {
                o2Cost *= -1;
            }
            if (o1Cost < o2Cost) return -1;
            if (o1Cost > o2Cost) return 1;
            return 0;
        }
    }
}
