package ftt.aircompare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;


public class ModelInput extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_input);

        getSupportActionBar().hide();

        ArrayList<String> modelArray = new ArrayList<String>();

        for (int i = 0; i < Input.airConditionerArray.size(); i++)
        {
            if (Input.airConditionerArray.get(i).brand.toLowerCase().equals(Input.currentQuery.manufacturer.toLowerCase()))
            {
                modelArray.add(Input.airConditionerArray.get(i).modelNumber);
            }
        }

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, modelArray);

        AutoCompleteTextView modelView = (AutoCompleteTextView)findViewById(R.id.modelText);
        modelView.setThreshold(1);
        modelView.setAdapter(modelAdapter);
    }

    public void nextSlide(View v)
    {
        AutoCompleteTextView modelView = (AutoCompleteTextView) findViewById(R.id.modelText);
        Input.currentQuery.model = modelView.getText().toString();

        Intent i = new Intent(this, ResultList.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_model_input, menu);
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
}
