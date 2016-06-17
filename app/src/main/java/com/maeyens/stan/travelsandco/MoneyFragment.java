package com.maeyens.stan.travelsandco;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maeyens.stan.travelsandco.data.DummyDAO;
import com.maeyens.stan.travelsandco.data.TraveloDAO;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoneyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoneyFragment extends Fragment {
    private TraveloDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        if(dao.getTransactions().size()>0){
            View child = view.findViewById(R.id.no_transactions_placeholder);
            ViewGroup parent = (ViewGroup) child.getParent();
            int index = parent.indexOfChild(child);
            parent.removeView(child);
            //child = new ListView();
        }

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.add_transaction);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = DummyDAO.getInstance();
    }
}
