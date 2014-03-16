package com.kevinchou.simplestocks;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class MainActivity extends FragmentActivity {

    ActionBar ab;
    MenuItem searchItem;
    MenuItem refreshItem;

    String currentTicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ab = getActionBar();

        // When starting app, search for Google
        currentTicker = "GOOG";
        new GetKeyStatsAsyncTask().execute(currentTicker);

        ab.setTitle(getResources().getString(R.string.app_name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        searchItem  = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Ticker. Eg, GOOG");
        //searchView.setSubmitButtonEnabled(true); // For a submit button

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {

                // this is your adapter that will be filtered
                return true;

            }

            public boolean onQueryTextSubmit(String query) {

                // Here u can get the value "query" which is entered in the search box.
                currentTicker = query.toUpperCase();
                new GetKeyStatsAsyncTask().execute(currentTicker);

                searchItem.collapseActionView();

                return false;
            }

        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.refresh:  refreshItem = item;
                                new GetKeyStatsAsyncTask().execute(currentTicker);
                                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetKeyStatsAsyncTask extends AsyncTask<String, String, Stock> {

        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.progress_linearlayout);
        LinearLayout llNoInternetConnection = (LinearLayout) findViewById(R.id.llNoInternetConnection);

        FrameLayout fragment_container = (FrameLayout) findViewById(R.id.fragment_container);

        @Override
        protected void onPreExecute() {
            // Sets company info invisible, and progress indicator visible
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            fragment_container.setVisibility(View.INVISIBLE);
            llNoInternetConnection.setVisibility(View.GONE);
        }

        protected Stock doInBackground(String... ticker) {

            Stock stock = null;

            if (isNetworkAvailable()) {

                try {

                    stock = YahooFinanceInfo.getStockData(ticker[0]);

                } catch (Exception e) {

                    Log.d("YAHOO_ERROR", e.toString());

                }

            }

            return stock;
        }

        protected void onPostExecute(Stock stock) {

            linlaHeaderProgress.setVisibility(View.GONE);

            if (stock != null) {
                // Create a new Fragment to be placed in the activity layout
                StockPageFragment stockPageFragment = StockPageFragment.newInstance(stock);

                if (findViewById(R.id.fragment_container) != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, stockPageFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, stockPageFragment).commit();
                }

                fragment_container.setVisibility(View.VISIBLE);

            } else {

                llNoInternetConnection.setVisibility(View.VISIBLE);

            }




        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

