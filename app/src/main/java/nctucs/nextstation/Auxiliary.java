package nctucs.nextstation;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by liangyupan on 4/21/15.
 */
public class Auxiliary {
    public static String parseJson(InputStream is) {
        String result = null;
        try {
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (IOException e1) {
            Log.d(Constant.EXCEPTION_TAG, "IO1");
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e2) {
                Log.d(Constant.EXCEPTION_TAG, "IO2");
            }
        }
        return result;
    }

}
