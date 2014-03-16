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
    TextView tvVolume;
    TextView tvAvgDailyVolume;
    TextView tvDaysRange;
    TextView tvYearRange;
    TextView tvMarketCap;
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

        tvPrevClose      = (TextView) rootView.findViewById(R.id.tvPrevClose);
        tvOpen           = (TextView) rootView.findViewById(R.id.tvOpen);
        tvAvgDailyVolume = (TextView) rootView.findViewById(R.id.tvAvgDailyVolume);
        tvVolume         = (TextView) rootView.findViewById(R.id.tvVolume);
        tvDaysRange      = (TextView) rootView.findViewById(R.id.tvDaysRange);
        tvYearRange      = (TextView) rootView.findViewById(R.id.tvYearRange);
        tvMarketCap      = (TextView) rootView.findViewById(R.id.tvMarketCap);
        tvOneYearTarget  = (TextView) rootView.findViewById(R.id.tvOneYearTarget);

        tvPrevClose.setText(stock.getPrevClose());
        tvOpen.setText(stock.getOpen());
        tvAvgDailyVolume.setText(stock.getAvgDailyVolume());
        tvVolume.setText(stock.getVolume());
        tvDaysRange.setText(stock.getDaysRange());
        tvYearRange.setText(stock.getYearRange());
        tvMarketCap.setText(stock.getMarketCap());
        tvOneYearTarget.setText(stock.getOneYearTarget());



        return rootView;
    }

}
