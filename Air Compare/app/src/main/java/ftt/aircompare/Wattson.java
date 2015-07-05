package ftt.aircompare;

import android.app.Application;
import android.content.Context;

/**
 * Created by michaelnguyen-kim on 5/07/15.
 */
public class Wattson extends Application {
    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        Wattson.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return Wattson.context;
    }
}
