package nctucs.nextstation;

import android.content.Context;
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
    ArrayList<String> categoryArray = new ArrayList<>();
    UserDatabaseHelper mDbHelper = new UserDatabaseHelper(this);
    GridView locationCategory;

    public Context context;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Location mLastLocation;
    public LatLng mLatLng;
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
//        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
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
        categoryArray.add("Location");
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
