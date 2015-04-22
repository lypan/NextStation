package nctucs.nextstation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class LocationService extends Service
        implements LocationListener {
    UserDatabaseHelper mDbHelper;
    Context context;
    ArrayList<LocationInformation> mLocationArray = new ArrayList<>();

    private LocationManager mgr;
    private boolean isIn;
    //Media
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onCreate() {
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, this);
        // Intent intent = new Intent();
        // intent.setClass(LocationService.this,AlarmActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intent);
        context = this;
        isIn = false;
    }

    @Override
    public void onDestroy() {
        mgr.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location current) {
        if (current == null) return;
        Location dest = new Location(current);

        Log.d(Constant.TEST_TAG, "YAYA!");

        //在這裡計算 資料庫裡的點對他的距離
        dest.setLatitude(24.7837263);
        dest.setLongitude(120.9973135);



        float dist = current.distanceTo(dest);
        if (dist < 100.0) {
            if (isIn == false) {
                //CALL ACTIVITY!!ＡＬＲＡＭ
                Intent intent = new Intent();
                intent.setClass(LocationService.this, AlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                isIn = true;
            }
        } else {
            isIn = false; // too far
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
    }


}
