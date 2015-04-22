package nctucs.nextstation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by liangyupan on 4/23/15.
 */
public class ListArrayAdapter extends ArrayAdapter<LocationInformation> {
    Context context;
    UserDatabaseHelper mDbHelper;
    List<LocationInformation> objects;

    public ListArrayAdapter(Context context, int resource, List<LocationInformation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        TextView minuslocation = (TextView) itemlayout.findViewById(R.id.addlocation);
        minuslocation.setText("-");

        minuslocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                String name = ((TextView) parent.findViewById(R.id.name)).getText().toString();
                String latitude = ((TextView) parent.findViewById(R.id.latitude)).getText().toString().substring(9);
                String longitude = ((TextView) parent.findViewById(R.id.longitude)).getText().toString().substring(10);
                Log.d(Constant.TEST_TAG, name);
                mDbHelper = new UserDatabaseHelper(context);
                SQLiteDatabase dbw = mDbHelper.getWritableDatabase();
                int deteleRow = dbw.delete(Constant.TABLE_NAME, Constant.TITLE_NAME + "='" + name + "'", null);
                objects.remove(position);
                notifyDataSetChanged();

                String text = name + " successfully deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Log.d(Constant.TEST_TAG, Integer.toString(deteleRow));
            }
        });


        return itemlayout;
    }
}
