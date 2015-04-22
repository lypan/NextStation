package nctucs.nextstation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    ArrayList<String> categoryArray = new ArrayList<String>();
    UserDatabaseHelper mDbHelper = new UserDatabaseHelper(this);
    GridView locationCategory;
    ArrayList<LocationInformation> mLocationArray = new ArrayList<>();
    Context context;
    GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastLocation;
    LatLng mLatLng;
    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(Constant.TEST_TAG, "OnConnected!");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d(Constant.TEST_TAG, String.valueOf(mLastLocation.getLatitude()));
            Log.d(Constant.TEST_TAG, String.valueOf(mLastLocation.getLongitude()));
        }
        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(Constant.TEST_TAG, "OnConnectionSuspended!");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(Constant.TEST_TAG, "OnConnectionFailed!");
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.d(Constant.TEST_TAG, "OnLocationChanged!");
        Log.d(Constant.TEST_TAG, String.valueOf(mCurrentLocation.getLatitude()));
        Log.d(Constant.TEST_TAG, String.valueOf(mCurrentLocation.getLongitude()));
        if (location == null) return;


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

            mLocationArray.add(new LocationInformation(name, latitude, longitude));
        } while (cursor.moveToNext());
        dbr.close();

        for (int i = 0; i < mLocationArray.size(); i++) {
            Log.d(Constant.TEST_TAG, mLocationArray.get(i).name);

            Location destination = new Location(location);
            destination.setLatitude(Double.parseDouble(mLocationArray.get(i).latitude));
            destination.setLongitude(Double.parseDouble(mLocationArray.get(i).longitude));
            String name = mLocationArray.get(i).name;
            double distance = location.distanceTo(destination);

            if (distance < 1000.0) {
                mDbHelper = new UserDatabaseHelper(context);
                SQLiteDatabase dbw = mDbHelper.getWritableDatabase();
                int deteleRow = dbw.delete(Constant.TABLE_NAME, Constant.TITLE_NAME + "='" + name + "'", null);
                mLocationArray.remove(i);
                dbw.close();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        locationCategory = (GridView) findViewById(R.id.locationCategory);

        categoryArray.add("Transportation");
        categoryArray.add("Custom");
        categoryArray.add("List");
        categoryArray.add("Category");

        LocationCategoryAdapter categoryAdapter = new LocationCategoryAdapter(this, R.layout.grid_item, categoryArray);
        locationCategory.setNumColumns(2);
        locationCategory.setAdapter(categoryAdapter);

        buildGoogleApiClient();
        createLocationRequest();

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constant.TEST_TAG, "onResume");
//        if (mLastLocation != null) {
//            Log.d(Constant.TEST_TAG, String.valueOf(mLastLocation.getLatitude()));
//            Log.d(Constant.TEST_TAG, String.valueOf(mLastLocation.getLongitude()));
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
