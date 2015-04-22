package nctucs.nextstation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;


public class PublicTransportationActivity extends ActionBarActivity {
    private ArrayList<LocationInformation> locationArray = new ArrayList<>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transportation);
//        parse json from MRT
        JSONArray jsonArray = null;
        InputStream is = getResources().openRawResource(R.raw.taipeimrt);
        String jsonString = Auxiliary.parseJson(is);
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            Log.d(Constant.EXCEPTION_TAG, "JSON1");
        }
//        fill the list view
        int dataLength = 0;
        try {
            dataLength = jsonArray.length();
        } catch (NullPointerException e) {
            Log.d(Constant.EXCEPTION_TAG, "JSON2");
        } finally {
            if (dataLength != 0) {
                for (int i = 0; i < dataLength; i++) {
                    String name = null, latitude = null, longitude = null;
                    try {
                        name = jsonArray.getJSONObject(i).getString("name");
                        latitude = jsonArray.getJSONObject(i).getString("latitude");
                        longitude = jsonArray.getJSONObject(i).getString("longitude");
                        Log.d("test", name);
                    } catch (JSONException e) {
                        Log.d(Constant.EXCEPTION_TAG, "JSON2");
                    }
                    locationArray.add(new LocationInformation(name, latitude, longitude));
                }
                LocationArrayAdapter locationAdapter = new LocationArrayAdapter(this, R.layout.list_item, locationArray);

                listview = (ListView) findViewById(R.id.listView);
                listview.setAdapter(locationAdapter);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_transportation, menu);
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
