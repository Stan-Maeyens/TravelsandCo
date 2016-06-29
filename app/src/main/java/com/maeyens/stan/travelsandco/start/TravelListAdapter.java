package com.maeyens.stan.travelsandco.start;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maeyens.stan.travelsandco.NavigationActivity;
import com.maeyens.stan.travelsandco.R;
import com.maeyens.stan.travelsandco.data.SaveSharedPreference;
import com.maeyens.stan.travelsandco.data.Travel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 20-Jun-16.
 */
public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {
    private static List<Travel> mDataset;

    public TravelListAdapter() {
        mDataset = new ArrayList<>();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView peopleTextView;
        public ViewHolder(LinearLayout l, TextView t, TextView p) {
            super(l);
            l.setClickable(true);
            l.setOnClickListener(this);
            titleTextView = t;
            peopleTextView = p;
        }

        @Override
        public void onClick(View v) {
            System.out.println("--------------------onclick");
            Intent intent = new Intent(v.getContext(), NavigationActivity.class);
            SaveSharedPreference.setCurrentTravel(v.getContext(), mDataset.get(getAdapterPosition()).getID());
            v.getContext().startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TravelListAdapter(List<Travel> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TravelListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travels, parent, false);
        View t = v.findViewById(R.id.title);
        View p = v.findViewById(R.id.people);

        ViewHolder vh = new ViewHolder((LinearLayout)v, (TextView)t, (TextView)p);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleTextView.setText(mDataset.get(position).getTitle());
        String people = "";
        for(String s: mDataset.get(position).getPeople()){
            people += s+", ";
        }
        System.out.println("people : "+people);
        holder.peopleTextView.setText(people.substring(0, people.length()-2));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItems(List<Travel> travels){
        mDataset.addAll(travels);
    }
}
