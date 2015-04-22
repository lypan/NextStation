package nctucs.nextstation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
 * Created by liangyupan on 4/21/15.
 */
public class LocationArrayAdapter extends ArrayAdapter<LocationInformation> {
    Context context;
    UserDatabaseHelper mDbHelper;
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
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                String name = ((TextView) parent.findViewById(R.id.name)).getText().toString();
                String latitude = ((TextView) parent.findViewById(R.id.latitude)).getText().toString().substring(9);
                String longitude = ((TextView) parent.findViewById(R.id.longitude)).getText().toString().substring(10);

//                1.Json Method
//                JSONObject jsonValue = new JSONObject();
//                JSONArray jsonData = new JSONArray();
//                try {
//                    jsonValue.put("name", name);
//                    jsonValue.put("latitude", latitude);
//                    jsonValue.put("longitude", longitude);
//                    jsonData.put(jsonValue);
//                    jsonData.put(jsonValue);
//                    jsonData.put(jsonValue);
//                } catch (JSONException e) {
//                    Log.d(Constant.EXCEPTION_TAG, "J1");
//                }
//                JSONArray jsonArray = new JSONArray();
//                FileInputStream fis;
//                FileOutputStream fos;
//                try {
//                    fis = context.openFileInput(Constant.USER_LOCATION_FILENAME);
//                    String jsonString = Auxiliary.parseJson(fis);
//                    Log.d(Constant.TEST_TAG, jsonString);
//                    try {
//                        jsonArray = new JSONArray(jsonString);
//                        jsonArray.put(jsonValue);
//                        Log.d(Constant.TEST_TAG, jsonArray.toString());
//                        Log.d(Constant.TEST_TAG, jsonArray.toString());
//                    } catch (JSONException e) {
//                        Log.d(Constant.EXCEPTION_TAG, "J2");
//
//                    }
//                    fos = context.openFileOutput(Constant.USER_LOCATION_FILENAME, Context.MODE_PRIVATE);
//                    fos.write(jsonArray.toString().getBytes());
//                }
//                catch (FileNotFoundException e) {
//                    Log.d(Constant.EXCEPTION_TAG, "L1");
//                }
//                catch (IOException e) {
//                    Log.d(Constant.EXCEPTION_TAG, "L2");
//                }

                mDbHelper = new UserDatabaseHelper(context);
                SQLiteDatabase dbw = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Constant.TITLE_NAME, name);
                values.put(Constant.CONTENT_NAME1, latitude);
                values.put(Constant.CONTENT_NAME2, longitude);
                long rowId = dbw.insert(Constant.TABLE_NAME, null, values);
                Log.d(Constant.TEST_TAG, Integer.toString((int) rowId));
                if (rowId != -1) {
                    CharSequence text = name + " successfully added!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    CharSequence text = name + " already exists!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                dbw.close();

                SQLiteDatabase dbr = mDbHelper.getReadableDatabase();
                String[] projection = {
                        Constant.TITLE_NAME,
                        Constant.CONTENT_NAME1,
                        Constant.CONTENT_NAME2
                };
//                String sortOrder = " DESC";
                Cursor cursor = dbr.query(
                        Constant.TABLE_NAME,  // The table to query
                        null,                               // The columns to return
                        null,                                // The columns for the WHERE clause
                        null,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                 // The sort order
                );
                String Key = cursor.getColumnName(0);
                cursor.moveToFirst();
                do {
                    Log.d(Constant.TEST_TAG, cursor.getString(cursor.getColumnIndex(Key)));
                } while (cursor.moveToNext());
                dbr.close();
            }
        });
        return itemlayout;
    }
}

