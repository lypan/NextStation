package nctucs.nextstation;

import android.content.Context;
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
public class LocationArrayAdapter extends ArrayAdapter<LocationInformation> {
    Context context;
    public LocationArrayAdapter(Context context, int resource, List<LocationInformation> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemlayout;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            itemlayout = (LinearLayout) inflater.inflate(R.layout.list_item, null);
        } else {
            itemlayout = (LinearLayout) convertView;
        }
        LocationInformation LocationItem = getItem(position);

        TextView name = (TextView) itemlayout.findViewById(R.id.name);
        name.setText(LocationItem.name);

        TextView latitude = (TextView) itemlayout.findViewById(R.id.latitude);
        latitude.setText("Latitude: " + LocationItem.latitude);

        TextView longitude = (TextView) itemlayout.findViewById(R.id.longitude);
        longitude.setText("Longitude: " + LocationItem.longitude);

        TextView addlocation = (TextView) itemlayout.findViewById(R.id.addlocation);
        addlocation.setText("+");

        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parent = (LinearLayout)view.getParent().getParent();
                TextView tv = (TextView)parent.findViewById(R.id.name);
                tv.setText("click");
            }
        });

        return itemlayout;
    }
}

