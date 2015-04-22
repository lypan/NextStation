package nctucs.nextstation;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {
    Context context;
    GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastLocation;
    LatLng mLatLng;
    UserDatabaseHelper mDbHelper;
    //    private LocationRequest locationRequest;
//    private Location currentLocation;
//    private Marker currentMarker, itemMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        context = this;

        buildGoogleApiClient();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLatLng = latLng;
//                Log.d(Constant.TEST_TAG, latLng.latitude + "-" + latLng.longitude);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.add_location_title);
                alertDialogBuilder.setMessage(R.string.add_location_message);
                final EditText locationEditText = new EditText(context);
                alertDialogBuilder.setView(locationEditText);
                alertDialogBuilder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mDbHelper = new UserDatabaseHelper(context);
                        SQLiteDatabase dbw = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Constant.TITLE_NAME, locationEditText.getText().toString());
                        values.put(Constant.CONTENT_NAME1, mLatLng.latitude);
                        values.put(Constant.CONTENT_NAME2, mLatLng.longitude);
                        long rowId = dbw.insert(Constant.TABLE_NAME, null, values);
                        Log.d(Constant.TEST_TAG, Integer.toString((int) rowId));
                        if (rowId != -1) {
                            CharSequence text = locationEditText.getText().toString() + " successfully added!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            addMarker(mLatLng, locationEditText.getText().toString());
                        } else {
                            CharSequence text = locationEditText.getText().toString() + " already exists!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        dbw.close();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.delete_location_title);
                alertDialogBuilder.setMessage(R.string.delete_location_message);
                alertDialogBuilder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        marker.remove();
                        String name = marker.getTitle();
                        mDbHelper = new UserDatabaseHelper(context);
                        SQLiteDatabase dbw = mDbHelper.getWritableDatabase();
                        int deteleRow = dbw.delete(Constant.TABLE_NAME, Constant.TITLE_NAME + "='" + name + "'", null);
//                        Log.d(Constant.TEST_TAG,marker.getTitle());
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        updataMapDisplay();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();


            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        LatLng initialPlace = new LatLng(24.786178, 120.996746);
        moveMap(initialPlace);
        addMarker(initialPlace, "NCTU");
    }

    private void moveMap(LatLng place) {
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(place)
                        .zoom(17)
                        .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addMarker(LatLng place, String title) {
        BitmapDescriptor icon =
                BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(place)
                .title(title)
                .icon(icon);

        mMap.addMarker(markerOptions);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void updataMapDisplay() {
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
//                    Log.d(Constant.TEST_TAG, cursor.getString(cursor.getColumnIndex(Key)));
            String name = cursor.getString(cursor.getColumnIndex(nameString));
            String latitude = cursor.getString(cursor.getColumnIndex(latitudeString));
            String longitude = cursor.getString(cursor.getColumnIndex(longitudeString));

            LatLng place = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            addMarker(place, name);

        } while (cursor.moveToNext());
        dbr.close();
    }
}
