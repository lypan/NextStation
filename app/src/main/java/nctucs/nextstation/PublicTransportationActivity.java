package nctucs.nextstation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        String jsonString = parseJson(R.raw.taipeimrt);
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            Log.d(Constant.EXCEPTIONTAG, "JSON1");
        }

//        fill the list view
        int dataLength = 0;
        try {
            dataLength = jsonArray.length();
        } catch (NullPointerException e) {
            Log.d(Constant.EXCEPTIONTAG, "JSON2");
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
                        Log.d(Constant.EXCEPTIONTAG, "JSON2");
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

    public String parseJson(int resourceID) {
        InputStream is = null;
        String result = null;
        try {
            is = getResources().openRawResource(resourceID);
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (IOException e1) {
            Log.d(Constant.EXCEPTIONTAG, "IO1");
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e2) {
                Log.d(Constant.EXCEPTIONTAG, "IO2");
            }
        }
        return result;
    }

}
