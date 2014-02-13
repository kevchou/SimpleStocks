package com.kevinchou.stockinformation.StockPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kevinchou.stockinformation.R;
import com.kevinchou.stockinformation.Stock;

public class PriceInfoFragment extends Fragment {

    TextView tvPrevClose;
    TextView tvOpen;
    TextView tvBid;
    TextView tvAsk;
    TextView tvOneYearTarget;

    Stock stock;

    public PriceInfoFragment() {
    }


    public static PriceInfoFragment newInstance(Stock stock) {

        PriceInfoFragment fragment = new PriceInfoFragment();
        fragment.stock = stock;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_priceinfo, container, false);

        tvPrevClose     = (TextView) rootView.findViewById(R.id.tvPrevClose);
        tvOpen          = (TextView) rootView.findViewById(R.id.tvOpen);
        tvBid           = (TextView) rootView.findViewById(R.id.tvBid);
        tvAsk           = (TextView) rootView.findViewById(R.id.tvAsk);
        tvOneYearTarget = (TextView) rootView.findViewById(R.id.tvOneYearTarget);

        tvPrevClose.setText(stock.getPrevClose());
        tvOpen.setText(stock.getOpen());
        tvBid.setText(stock.getBid());
        tvAsk.setText(stock.getAsk());
        tvOneYearTarget.setText(stock.getOneYearTarget());

        return rootView;

    }

}
