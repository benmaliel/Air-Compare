package ftt.aircompare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class OutputInput extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_input);

        getSupportActionBar().hide();

        SeekBar minSeekBar = (SeekBar) findViewById(R.id.minBar);
        SeekBar maxSeekBar = (SeekBar) findViewById(R.id.maxBar);

        minSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                TextView minValue = (TextView) findViewById(R.id.minValue);
                minValue.setText(Integer.toString(progress));
            }
        });

        maxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                TextView maxValue = (TextView) findViewById(R.id.maxValue);
                maxValue.setText(Integer.toString(progress));
            }
        });

    }

    public void nextSlide(View v)
    {
        SeekBar minSeekBar = (SeekBar) findViewById(R.id.minBar);
        SeekBar maxSeekBar = (SeekBar) findViewById(R.id.maxBar);

        Input.currentQuery.lowerLimit = minSeekBar.getProgress();
        Input.currentQuery.upperLimit = maxSeekBar.getProgress();
        Intent i = new Intent(this, ModelInput.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_output_input, menu);
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
