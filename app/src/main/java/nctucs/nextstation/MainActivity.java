package nctucs.nextstation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList<String> categoryArray = new ArrayList<>();
    GridView locationCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationCategory = (GridView)findViewById(R.id.locationCategory);

        categoryArray.add("Public\nTransportation");
        categoryArray.add("Custum\nPlace");
        categoryArray.add("test3");
        categoryArray.add("test4");
        categoryArray.add("test5");

        LocationCategoryAdapter categoryAdapter = new LocationCategoryAdapter(this, R.layout.grid_item, categoryArray);
        locationCategory.setNumColumns(2);
        locationCategory.setAdapter(categoryAdapter);

//        publicTransportationReference = (Button) findViewById(R.id.PublicTransportationButton);
//        publicTransportationReference.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
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
//                Intent startPublicTransportaion = new Intent(view.getContext(), PublicTransportationActivity.class);
//                publicTransportationReference.setText("click");
//                startActivity(startPublicTransportaion);
//            }
//        });
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
