package nctucs.nextstation;

/**
 * Created by liangyupan on 4/21/15.
 */
public class Constant {
    public static final String USER_LOCATION_FILENAME = "userLocationFile.json";
    public static final String EXCEPTION_TAG = "EXCEPTION OCCURS!";
    public static final String TEST_TAG = "TEST OCCURS!";
    public static final String INITIAL_DATABASE_NAME = "locationDatabase.db";
    public static final int INITIAL_DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "location";
    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String TITLE_NAME = "name";
    public static final String CONTENT_NAME1 = "latitude";
    public static final String CONTENT_NAME2 = "longitude";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constant.TABLE_NAME + " (" +
                    Constant.TITLE_NAME + " TEXT PRIMARY KEY" + COMMA_SEP +
                    Constant.CONTENT_NAME1 + TEXT_TYPE + COMMA_SEP +
                    Constant.CONTENT_NAME2 + TEXT_TYPE +
                    ")";
}
