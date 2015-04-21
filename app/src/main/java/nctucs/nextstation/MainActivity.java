package nctucs.nextstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    Button publicTransportationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        publicTransportationReference = (Button) findViewById(R.id.PublicTransportationButton);
        publicTransportationReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent publicTransportationActivity = new Intent();


//                String FILENAME = "hello_file";
//                String string = "hello world!";
//                FileOutputStream fos;
//                try {
//                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//                }
//                catch (FileNotFoundException e) {
//                    return;
//                }
//                try {
//                    fos.write(string.getBytes());
//                    fos.close();
//                }
//                catch (IOException e) {
//
//                }
                Intent startPublicTransportaion = new Intent(view.getContext(), PublicTransportationActivity.class);
                publicTransportationReference.setText("click");
                startActivity(startPublicTransportaion);
            }
        });

//        InputStream ins = getResources().openRawResource(R.raw.test);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
//        StringBuilder out = new StringBuilder();
//        String line;
//        try {
//            line = reader.readLine();
//        }
//        catch (IOException e) {
//            return;
//        }

//        JSONArray jsonArray = null;
//        String r = null;
//        String jsonString = parseJson(R.raw.taipeimrt);
//        try {
//            jsonArray = new JSONArray(jsonString);
//        } catch (JSONException e) {
//            Log.d(EXCEPTIONTAG, "JSON1");
//        }
//        try {
//            r = jsonArray.getJSONObject(0).getString("name");
//        } catch (JSONException e) {
//            Log.d(EXCEPTIONTAG, "JSON2");
//        }
//        publicTransportationReference.setText(r);

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
