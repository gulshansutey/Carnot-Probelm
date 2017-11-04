package gulshansutey.carnotproblem.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by asus on 11/4/2017.
 */

public class MyApplication extends com.activeandroid.app.Application {


    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static MyApplication volleySingletonInstance;

    /**
     * Global request queue for Volley
     */
    private static RequestQueue requestQueue;

    /**
     * @return MyApplication singleton instance
     */
    public static synchronized MyApplication getInstance() {
        return volleySingletonInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        volleySingletonInstance = this;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds the request to the global queue
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
