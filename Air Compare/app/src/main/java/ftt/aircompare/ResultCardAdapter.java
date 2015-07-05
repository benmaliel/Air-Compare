package ftt.aircompare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michael on 27/01/2015.
 */
public class ResultCardAdapter extends RecyclerView.Adapter<ResultCardAdapter.ViewHolder> {

    private ArrayList<AirConditioner> devices;
    private int rowLayout;
    private Context mContext;

    public ResultCardAdapter(ArrayList<AirConditioner> devices, int rowLayout, Context context) {
        this.devices = devices;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        String model = devices.get(i).brand + " " + devices.get(i).modelNumber;
        double consumedEnergy = devices.get(i).coolingInput*Input.getCoolingHours(Input.userPostCode) + devices.get(i).heatingInput*Input.getHeatingHours(Input.userPostCode);
        double costInDollars = consumedEnergy*0.34;

        String displayCost = "$" + String.format("%.2f", costInDollars);

        if (costInDollars < 0)
        {
            costInDollars *= -1;
            displayCost = "$" + String.format("%.2f", costInDollars) + "*";
        }

        if (costInDollars < 200)
        {
            viewHolder.costView.setTextColor(mContext.getResources().getColor(R.color.green));
            viewHolder.suppView.setTextColor(mContext.getResources().getColor(R.color.green));
        }

        else if (costInDollars > 200 && costInDollars < 500)
        {
            viewHolder.costView.setTextColor(mContext.getResources().getColor(R.color.blue));
            viewHolder.suppView.setTextColor(mContext.getResources().getColor(R.color.blue));
        }

        else if (costInDollars > 500 && costInDollars < 900)
        {
            viewHolder.costView.setTextColor(mContext.getResources().getColor(R.color.yellow));
            viewHolder.suppView.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }

        else if (costInDollars > 1000)
        {
            viewHolder.costView.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.suppView.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        viewHolder.deviceName.setText(model);
        viewHolder.costView.setText(displayCost);
        viewHolder.position = i;
    }

    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceName;
        public TextView costView;
        public TextView suppView;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.deviceText);
            costView = (TextView) itemView.findViewById(R.id.costText);
            suppView = (TextView) itemView.findViewById(R.id.suppView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //int index = ReportCardActivity.reportCards.indexOf(cardName.getText().toString());
                    Intent i = new Intent(Wattson.getAppContext(), DeviceDetail.class);
                    i.putExtra("index", position);
                    i.putExtra("source", Input.querySource);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Wattson.getAppContext().startActivity(i);
                }
            });
        }
    }
}
