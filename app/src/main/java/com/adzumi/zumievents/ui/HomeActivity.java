package com.adzumi.zumievents.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.adzumi.zumievents.Constants;
import com.adzumi.zumievents.R;
import com.adzumi.zumievents.adapters.CurrentLocationAdapter;
import com.adzumi.zumievents.models.Event;
import com.adzumi.zumievents.models.Events;
import com.adzumi.zumievents.services.API_Instance;
import com.adzumi.zumievents.services.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private CurrentLocationAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getMyEvents("nairobi");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search City ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                intent.putExtra("query", query);
                Log.v(TAG,"LOCATION NAME: " + query);
                getMyEvents(query);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    public void getMyEvents(String query) {
        API_Instance service = RetrofitClient.getClient("https://www.eventbriteapi.com/v3/").create(API_Instance.class);
        Call<Events> call = service.getEvents(query, Constants.EVENTBRITE_OAUTHTOKEN);
        Log.v("MY URL", String.valueOf(call.request().url()));

        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                List<Event> events = response.body().getEvents();
                getCurrentLocationEvents(events);
                progressDialog.dismiss();
                for (Event e : events) {
                    Log.d(TAG, "Event " + e.getDescription().getText());
                }
//              Gson gson = new Gson();
//              String eventsJson = gson.toJson(events);
//              Log.v(TAG, "MY JSON RESPONSE: " + eventsJson);
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCurrentLocationEvents(List<Event> events) {
        recyclerView = findViewById(R.id.myEventsRecyclerView);
        adapter = new CurrentLocationAdapter(this,events);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
