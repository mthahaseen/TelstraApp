package com.telstra.telstraapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telstra.telstraapp.R;
import com.telstra.telstraapp.adapter.ItemListAdapter;
import com.telstra.telstraapp.common.AppConstants;
import com.telstra.telstraapp.common.AppController;
import com.telstra.telstraapp.common.ConnectionDetector;
import com.telstra.telstraapp.model.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ProgressDialog pDialog;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MainActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    ConnectionDetector cd = new ConnectionDetector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(MainActivity.this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        //Check for data connection before making JSON Request
        if(cd.isConnectingToInternet()){
            pDialog.setMessage(AppConstants.MSG_LOADING);
            pDialog.show();
            pDialog.setCancelable(false);
            getItemData();
        }else{
            Toast.makeText(MainActivity.this, AppConstants.MSG_NO_CONNECTION, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        if(cd.isConnectingToInternet()) {
            swipeRefreshLayout.setRefreshing(true);
            getItemData();
        }else{
            Toast.makeText(MainActivity.this, AppConstants.MSG_NO_CONNECTION, Toast.LENGTH_LONG).show();
        }
    }

    /*
    This method GET data from URL and parse it using gson library to a list of items
    */
    public void getItemData(){
        String URL_FEED= AppConstants.URL_TELSTRA_DATA;
        AppController.getInstance().getRequestQueue().getCache().remove(URL_FEED); // Remove cached json response from request queue
        Log.i(LOG_TAG, "URL_FEED=" + URL_FEED);
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        getSupportActionBar().setTitle(response.getString(AppConstants.TAG_TITLE));  //Update ActionBar title from json feed
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        List<Item> lstData = new ArrayList<Item>();
                        lstData = Arrays.asList(gson.fromJson(response.getJSONArray(AppConstants.TAG_ROWS).toString(), Item[].class));
                        if(lstData!=null){
                            populateRecyclerView(lstData);
                        }else{
                            Toast.makeText(MainActivity.this, AppConstants.TOAST_DATA_ERROR, Toast.LENGTH_LONG).show();
                        }
                        pDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();}
                }else {
                    pDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, AppConstants.TOAST_DATA_ERROR, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, AppConstants.TOAST_DATA_ERROR, Toast.LENGTH_LONG).show();
            }
        });
        // Get instance from AppController class and add this request to RequestQueue.
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    /*
    This method populates the recycler with the data set got from URL
    */
    public void populateRecyclerView(List<Item> lstData){
        mAdapter = new ItemListAdapter(lstData, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
