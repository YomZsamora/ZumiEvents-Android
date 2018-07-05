package com.adzumi.zumievents.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.adzumi.zumievents.Constants;
import com.adzumi.zumievents.R;
import com.adzumi.zumievents.adapters.CurrentLocationAdapter;
import com.adzumi.zumievents.models.Event;
import com.adzumi.zumievents.models.Events;
import com.adzumi.zumievents.services.API_Instance;
import com.adzumi.zumievents.services.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventResultsActivity extends AppCompatActivity {

    private TextView mTextMessage;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_results);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        getMyEvents(query);
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
                Intent intent = new Intent(EventResultsActivity.this, EventResultsActivity.class);
                intent.putExtra("query", query);
//                getMyEvents(query);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMyEvents(String query) {
        API_Instance service = RetrofitClient.getClient("https://www.eventbriteapi.com/v3/").create(API_Instance.class);
        Call<Events> call = service.getEvents(query, Constants.EVENTBRITE_OAUTHTOKEN);
        Log.v("MY URL", String.valueOf(call.request().url()));

        progressDialog = new ProgressDialog(EventResultsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                List<Event> events = response.body().getEvents();
                getKeyWordEvents(events);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                Toast.makeText(EventResultsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getKeyWordEvents(List<Event> events) {
        recyclerView = findViewById(R.id.myEventsRecyclerView);
        adapter = new CurrentLocationAdapter(this,events);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EventResultsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(EventResultsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
