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


public class BrandInput extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_input);


        getSupportActionBar().hide();

        ArrayList<String> manufacturerArray = Input.getManufacturerList();

        ArrayAdapter<String> manufacturerAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, manufacturerArray);

        AutoCompleteTextView manufacturerView = (AutoCompleteTextView)findViewById(R.id.brandText);
        manufacturerView.setThreshold(1);
        manufacturerView.setAdapter(manufacturerAdapter);
    }

    public void nextSlide(View v)
    {
        AutoCompleteTextView brandView = (AutoCompleteTextView) findViewById(R.id.brandText);
        Input.currentQuery.manufacturer = brandView.getText().toString();
        Intent i = new Intent(this, CycleInput.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brand_input, menu);
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
