package com.kevinchou.simplestocks.StockPage;

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

import com.kevinchou.simplestocks.R;
import com.kevinchou.simplestocks.Stock;
import com.kevinchou.simplestocks.YahooFinanceInfo;


/** This fragment retrieves and displays the price chart for the current stock from Yahoo */

public class PriceChartFragment extends Fragment {

    RelativeLayout rlPriceChartContainer;
    LinearLayout llChartProgress;
    ImageView ivPriceChart;

    RadioButton rbTime1d;
    RadioButton rbTime5d;
    RadioButton rbTime3m;
    RadioButton rbTime6m;
    RadioButton rbTime1y;
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

        rbTime1d = (RadioButton) rootView.findViewById(R.id.rbTime1d);
        rbTime5d = (RadioButton) rootView.findViewById(R.id.rbTime5d);
        rbTime3m = (RadioButton) rootView.findViewById(R.id.rbTime3m);
        rbTime6m = (RadioButton) rootView.findViewById(R.id.rbTime6m);
        rbTime1y = (RadioButton) rootView.findViewById(R.id.rbTime1y);
        rbTime5y = (RadioButton) rootView.findViewById(R.id.rbTime5y);
        rbTimeMax = (RadioButton) rootView.findViewById(R.id.rbTimeMax);


        rbTime1d.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime5d.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime3m.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime6m.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime1y.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTime5y.setOnClickListener(new OnTimeIntervalRadioButtonListener());
        rbTimeMax.setOnClickListener(new OnTimeIntervalRadioButtonListener());

        rbPlotTypeLine = (RadioButton) rootView.findViewById(R.id.rbPlotTypeLine);
        rbPlotTypeBar = (RadioButton) rootView.findViewById(R.id.rbPlotTypeBar);
        rbPlotTypeCandle = (RadioButton) rootView.findViewById(R.id.rbPlotTypeCandle);

        rbPlotTypeLine.setOnClickListener(new OnTimeIntervalRadioButtonListener());
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

            // Chosen time interval
            if (rbTime1d.isChecked()) {
                timeInterval = "1d";
            } else if (rbTime5d.isChecked()) {
                timeInterval = "5d";
            } else if (rbTime3m.isChecked()) {
                timeInterval = "3m";
            } else if (rbTime6m.isChecked()) {
                timeInterval = "6m";
            } else if (rbTime1y.isChecked()) {
                timeInterval = "1y";
            } else if (rbTime5y.isChecked()) {
                timeInterval = "5y";
            } else if (rbTimeMax.isChecked()) {
                timeInterval = "my";
            } else {
                timeInterval = "5d";
            }

            // Plot type (bar, candle, line)
            if (rbPlotTypeBar.isChecked()) {
                plotType = "b";
            } else if (rbPlotTypeCandle.isChecked()) {
                plotType = "c";
            } else if (rbPlotTypeLine.isChecked()) {
                plotType = "l";
            } else {
                plotType = "b";
            }

            // Gets Price Chart from Yahoo
            try {
                Drawable d = YahooFinanceInfo.getPriceChart(tick, timeInterval, plotType);
                stock.setPriceChart(d);
            }
            catch (Exception e) {
                Log.d("YAHOO_ERROR", e.toString());
            }

            return stock;
        }

        @Override
        protected void onPostExecute(Stock stock) {

            // Turns off progress view and sets price chart visible
            llChartProgress.setVisibility(View.INVISIBLE);
            ivPriceChart.setVisibility(View.VISIBLE);

            // Sets the imageview to the retrieved drawable
            ivPriceChart.setImageDrawable(stock.getPriceChart());

        }
    }
}
