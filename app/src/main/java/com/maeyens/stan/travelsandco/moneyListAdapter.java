package com.maeyens.stan.travelsandco;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maeyens.stan.travelsandco.data.Transaction;

import java.util.List;

/**
 * Created by Stan on 17-Jun-16.
 */
public class moneyListAdapter extends ArrayAdapter{
    public moneyListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout ret = new RelativeLayout(getContext());
        TextView tv = new TextView(getContext());
        tv.setText(((Transaction) getItem(position)).getDescription());
        ret.addView(tv);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
        params.setMargins(10, 10, 10, 10);
        tv.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        TextView tv2 = new TextView(getContext());
        tv2.setText("" + ((Transaction)getItem(position)).getValue());
        ret.addView(tv2);
        params = (RelativeLayout.LayoutParams)tv2.getLayoutParams();
        params.setMargins(10,10,10,10);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tv2.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv2.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        if(((Transaction)getItem(position)).getValue()>=0)
            tv2.setTextColor(ContextCompat.getColor(getContext(), R.color.positiveValue));
        else
            tv2.setTextColor(ContextCompat.getColor(getContext(), R.color.negativeValue));
        return ret;
    }
}
