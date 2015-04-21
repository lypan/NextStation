package nctucs.nextstation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liangyupan on 4/21/15.
 */
public class LocationCategoryAdapter extends ArrayAdapter<String>{
    private Context context;
    public LocationCategoryAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemlayout;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            itemlayout = (LinearLayout)inflater.inflate(R.layout.grid_item, null);
        } else {
            itemlayout = (LinearLayout)convertView;
        }
        String categoryString = getItem(position);

        TextView gridTextView = (TextView) itemlayout.findViewById(R.id.gridTextView);
        gridTextView.setText(categoryString);

        gridTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView)view;
                String activityName = tv.getText().toString();
                if (activityName.equals("Public\nTransportation")) {
                    Intent intent = new Intent(context, PublicTransportationActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        return itemlayout;
    }
}
