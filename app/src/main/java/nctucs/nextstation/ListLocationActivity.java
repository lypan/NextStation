package nctucs.nextstation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class ListLocationActivity extends ActionBarActivity {
    UserDatabaseHelper mDbHelper;
    ArrayList<LocationInformation> locationArray = new ArrayList<>();
    ListView listview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);
        context = this;

        mDbHelper = new UserDatabaseHelper(context);
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


        String nameString = cursor.getColumnName(0);
        String latitudeString = cursor.getColumnName(1);
        String longitudeString = cursor.getColumnName(2);

        cursor.moveToFirst();
        do {
            String name = cursor.getString(cursor.getColumnIndex(nameString));
            String latitude = cursor.getString(cursor.getColumnIndex(latitudeString));
            String longitude = cursor.getString(cursor.getColumnIndex(longitudeString));

            locationArray.add(new LocationInformation(name, latitude, longitude));
        } while (cursor.moveToNext());
        dbr.close();

        ListArrayAdapter locationAdapter = new ListArrayAdapter(this, R.layout.list_item, locationArray);
        listview = (ListView) findViewById(R.id.listView_listLocation);
        listview.setAdapter(locationAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_location, menu);
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
