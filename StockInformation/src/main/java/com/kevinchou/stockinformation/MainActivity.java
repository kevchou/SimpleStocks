package com.kevinchou.stockinformation;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ab = getActionBar();
        //ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(102, 153, 0)));

        new GetKeyStatsAsyncTask().execute("GOOG");
        ab.setTitle(getResources().getString(R.string.app_name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        //searchView.setSubmitButtonEnabled(true); // For a submit button

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {

                // this is your adapter that will be filtered
                return true;

            }

            public boolean onQueryTextSubmit(String query) {

                //Here u can get the value "query" which is entered in the search box.
                Log.d("SEARCHTEXT", query);
                new GetKeyStatsAsyncTask().execute(query.toUpperCase());

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
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private class GetKeyStatsAsyncTask extends AsyncTask<String, String, Stock> {

        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.progress_linearlayout);
        FrameLayout fragment_container = (FrameLayout) findViewById(R.id.fragment_container);

        @Override
        protected void onPreExecute() {
            // Sets company info invisible, and progress indicator visible
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            fragment_container.setVisibility(View.INVISIBLE);
        }

        protected Stock doInBackground(String... ticker) {

            Stock stock = null;

            try {

                stock = YahooFinanceInfo.getStockData(ticker[0]);

            } catch (Exception e) {

                Log.d("YAHOO_ERROR", e.toString());

            }

            return stock;
        }

        protected void onPostExecute(Stock stock) {

            // Create a new Fragment to be placed in the activity layout
            StockPageFragment stockPageFragment = StockPageFragment.newInstance(stock);

            if (findViewById(R.id.fragment_container) != null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, stockPageFragment).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, stockPageFragment).commit();
            }

            linlaHeaderProgress.setVisibility(View.GONE);
            fragment_container.setVisibility(View.VISIBLE);

            ab.setTitle(getResources().getString(R.string.app_name) + " - " + stock.getTicker());
        }
    }
}

