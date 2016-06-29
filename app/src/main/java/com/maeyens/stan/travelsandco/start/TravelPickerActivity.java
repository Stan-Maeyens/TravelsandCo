package com.maeyens.stan.travelsandco.start;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.maeyens.stan.travelsandco.NavigationActivity;
import com.maeyens.stan.travelsandco.R;
import com.maeyens.stan.travelsandco.data.DummyDAO;
import com.maeyens.stan.travelsandco.data.NetworkDAO;
import com.maeyens.stan.travelsandco.data.SaveSharedPreference;
import com.maeyens.stan.travelsandco.data.Travel;
import com.maeyens.stan.travelsandco.data.TravelsandCoDAO;

import java.util.List;

public class TravelPickerActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoadTravelsTask mTravelsTask = null;

    private RecyclerView mRecyclerView;
    private TravelListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //TODO: change this:
        mAdapter = new TravelListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mTravelsTask = new LoadTravelsTask();
        mTravelsTask.execute((Void) null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SaveSharedPreference.clearCurrentTravel(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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
            // TODO: bring settings in order
            return true;
        }
        if (id == R.id.action_logout) {
            SaveSharedPreference.clearUserName(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Represents an asynchronous load travels task
     */
    public class LoadTravelsTask extends AsyncTask<Void, Void, List<Travel>> {

        LoadTravelsTask() {
        }

        @Override
        protected List<Travel> doInBackground(Void... params) {
            TravelsandCoDAO dao = NetworkDAO.getInstance(getApplicationContext());
            return dao.getTravels();
        }

        @Override
        protected void onPostExecute(final List<Travel> travels) {
            mTravelsTask = null;
            //showProgress(false);
            int start = mAdapter.getItemCount();
            mAdapter.addItems(travels);
            for(Travel t : travels)
            mAdapter.notifyItemRangeInserted(start-1, travels.size());
        }

        @Override
        protected void onCancelled() {
            mTravelsTask = null;
            //showProgress(false);
        }
    }
}
