package gulshansutey.carnotproblem.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import gulshansutey.carnotproblem.MainActivity;

/**
 * Created by asus on 11/4/2017.
 */

public class MyStringRequest implements Response.Listener<String>, Response.ErrorListener {

    private VolleyResponse volleyResponse;
    private String tag;
    private StringRequest stringRequest;


    public MyStringRequest(int method, String url, VolleyResponse volleyResponse) {
        this.volleyResponse = volleyResponse;
        this.tag = url;
        stringRequest = new StringRequest(method, url, this, this);
    }



    @Override
    public void onResponse(String response) {
        volleyResponse.onResponse(response, tag);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        volleyResponse.onError(error, tag);
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }

    public interface VolleyResponse {

        void onResponse(String response, String tag);

        void onError(VolleyError error, String tag);
    }
}