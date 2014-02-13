package com.kevinchou.stockinformation.StockPage;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.kevinchou.stockinformation.R;
import com.kevinchou.stockinformation.Stock;
import com.kevinchou.stockinformation.YahooFinanceInfo;


public class PriceChartFragment extends Fragment {

    RelativeLayout rlPriceChartContainer;
    LinearLayout llChartProgress;
    ImageView ivPriceChart;

    RadioButton rbTime5d;
    RadioButton rbTime6m;
    RadioButton rbTime5y;
    RadioButton rbTimeMax;

    RadioButton rbPlotTypeLine;
    RadioButton rbPlotTypeBar;
    RadioButton rbPlotTypeCandle;

    Stock stock;


    public PriceChartFragment() {
    }


    public static PriceChartFragment newInstance(Stock stock) {

        PriceChartFragment fragment = new PriceChartFragment();
        fragment.stock = stock;
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pricechart, container, false);

        rlPriceChartContainer = (RelativeLayout) rootView.findViewById(R.id.rlPriceChartContainer);
        ivPriceChart = (ImageView) rootView.findViewById(R.id.ivPriceChart);

        llChartProgress = (LinearLayout) rootView.findViewById(R.id.llChartProgress);

        rbTime5d = (RadioButton) rootView.findViewById(R.id.rbTime5d);
        rbTime6m = (RadioButton) rootView.findViewById(R.id.rbTime6m);
        rbTime5y = (RadioButton) rootView.findViewById(R.id.rbTime5y);
        rbTimeMax = (RadioButton) rootView.findViewById(R.id.rbTimeMax);

        rbTime5d.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime6m.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime5y.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTimeMax.setOnClickListener(new OnTimeIntervalRadioButtonListener());

        rbPlotTypeLine = (RadioButton) rootView.findViewById(R.id.rbPlotTypeLine);
        rbPlotTypeBar = (RadioButton) rootView.findViewById(R.id.rbPlotTypeBar);
        rbPlotTypeCandle = (RadioButton) rootView.findViewById(R.id.rbPlotTypeCandle);

        rbTime5d.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbPlotTypeBar.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbPlotTypeCandle.setOnClickListener(new OnTimeIntervalRadioButtonListener());

        new GetPriceChartAsyncTask().execute(stock.getTicker());

        return rootView;
    }


    private class OnTimeIntervalRadioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new GetPriceChartAsyncTask().execute(stock.getTicker());
        }
    }

    private class GetPriceChartAsyncTask extends AsyncTask<String, Void, Stock> {

        @Override
        protected void onPreExecute() {
            llChartProgress.setVisibility(View.VISIBLE);
            ivPriceChart.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Stock doInBackground(String... arg) {

            String tick = arg[0];
            String timeInterval;
            String plotType;

            if (rbTime5d.isChecked()) {
                timeInterval = "5d";
            } else if (rbTime6m.isChecked()) {
                timeInterval = "6m";
            } else if (rbTime5y.isChecked()) {
                timeInterval = "5y";
            } else if (rbTimeMax.isChecked()) {
                timeInterval = "my";
            } else {
                timeInterval = "5d";
            }

            if (rbPlotTypeBar.isChecked()) {
                plotType = "b";
            } else if (rbPlotTypeCandle.isChecked()) {
                plotType = "c";
            } else if (rbPlotTypeLine.isChecked()) {
                plotType = "l";
            } else {
                plotType = "b";
            }



            try {
                Drawable d = YahooFinanceInfo.getPriceChart(tick, timeInterval, plotType);
                stock.setPriceChart(d);
            }
            catch (Exception e) {
                Log.d("ERROR", e.toString());
            }

            return stock;
        }

        @Override
        protected void onPostExecute(Stock stock) {

            llChartProgress.setVisibility(View.INVISIBLE);
            ivPriceChart.setVisibility(View.VISIBLE);

            ivPriceChart.setImageDrawable(stock.getPriceChart());

        }
    }
}
