package nctucs.nextstation;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmActivity extends Activity {

    //Media
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        player = MediaPlayer.create(this, R.raw.alarm);
        player.setOnCompletionListener(comL);
        /*取得 Button 物件*/
        Toast.makeText(this, "到了RRRRRRRRRR",
                Toast.LENGTH_SHORT).show();
        try {
            player.start();
        } catch (Exception e) {
        }

        Button stopA_btn = (Button) findViewById(R.id.stopA);
        stopA_btn.setOnClickListener(stopAlarm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
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

    OnClickListener stopAlarm = new OnClickListener() {
        public void onClick(View v) {
            try {
                player.stop();
                player.prepare();
            } catch (Exception e) {
            }
            finish();
            //Intent intent = new Intent();
            //intent.setClass(AlarmActivity.this, MainActivity.class);
            //startActivity(intent);
        }
    };

    MediaPlayer.OnCompletionListener comL = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer nouse) {
            try {
                player.stop();
                player.prepare();
            } catch (Exception e) {
            }
        }
    };
}
