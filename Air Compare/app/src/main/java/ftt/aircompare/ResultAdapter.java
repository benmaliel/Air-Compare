package ftt.aircompare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by michaelnguyen-kim on 5/07/15.
 */
public class ResultAdapter extends ArrayAdapter<AirConditioner> {
    private final Context context;
    private final ArrayList<AirConditioner> values;

    public ResultAdapter(Context context, ArrayList<AirConditioner> values) {
        super(context, R.layout.result_list_view, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.result_list_view, parent, false);
        AirConditioner placeItem = values.get(position);

        TextView nameView = (TextView) rowView.findViewById(R.id.deviceText);
        TextView costView = (TextView) rowView.findViewById(R.id.costText);

        nameView.setText(placeItem.brand + " " + placeItem.modelNumber);

        double consumedEnergy = placeItem.coolingInput*Input.getCoolingHours(Input.userPostCode) + placeItem.heatingInput*Input.getHeatingHours(Input.userPostCode);
        double costInDollars = consumedEnergy*0.34;
        String displayCost = "$" + String.format("%.2f", costInDollars);

        costView.setText(displayCost);

        if (costInDollars < 0)
        {
            costView.setText("$" + String.format("%.2f", costInDollars*-1) + "(cooling only)");
        }
        return rowView;
    }
}
