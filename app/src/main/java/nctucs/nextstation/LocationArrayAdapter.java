package nctucs.nextstation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by liangyupan on 4/21/15.
 */
public class LocationArrayAdapter extends ArrayAdapter<LocationInformation> {
    private Context context;
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
                String name = ((TextView) parent.findViewById(R.id.name)).getText().toString();
                String latitude = ((TextView) parent.findViewById(R.id.latitude)).getText().toString().substring(9);
                String longitude = ((TextView) parent.findViewById(R.id.longitude)).getText().toString().substring(10);
                JSONObject jsonObject = new JSONObject();
                tv.setText("clicked");
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("latitude", latitude);
                    jsonObject.put("longitude", longitude);
                    Log.d(Constant.TESTTAG, jsonObject.toString());
                } catch (JSONException e) {
                    Log.d(Constant.EXCEPTIONTAG, "J1");
                }

//                FileOutputStream fos;
//                try {
//                    fos = context.openFileOutput(Constant.USERLOCATIONFILENAME, Context.MODE_APPEND);
//                }
//                catch (FileNotFoundException e) {
//                    Log.d(Constant.EXCEPTIONTAG, "L1");
//                    return;
//                }
//                try {
//                    fos.write(tv.toString().getBytes());
//                    fos.close();
//
//                }
//                catch (IOException e) {
//                    Log.d(Constant.EXCEPTIONTAG, "L2");
//                }

            }
        });

        return itemlayout;
    }
}

