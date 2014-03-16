package com.kevinchou.simplestocks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinchou.simplestocks.StockPage.PriceInfoFragment;
import com.kevinchou.simplestocks.StockPage.PriceChartFragment;


public class StockPageFragment extends Fragment {

    TextView tvCompany;
    TextView tvCompanyTicker;
    TextView tvPrice;
    TextView tvPriceChange;
    TextView tvTradeDate;
    TextView tvTradeTime;

    ViewPager mViewPager;
    StockInfoPagerAdapter mStockInfoPagerAdapter;

    Stock stock;

    public StockPageFragment() {
    }

    public static StockPageFragment newInstance(Stock stock) {

        StockPageFragment fragment = new StockPageFragment();
        fragment.stock = stock;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_stockpage, container, false);

        tvCompany       = (TextView) rootView.findViewById(R.id.tvCompany);
        tvCompanyTicker = (TextView) rootView.findViewById(R.id.tvCompanyTicker);
        tvPrice         = (TextView) rootView.findViewById(R.id.tvPrice);
        tvPriceChange   = (TextView) rootView.findViewById(R.id.tvPriceChange);
        tvTradeDate     = (TextView) rootView.findViewById(R.id.tvTradeDate);
        tvTradeTime     = (TextView) rootView.findViewById(R.id.tvTradeTime);

        // Sets company name, price, etc
        tvCompany.setText(stock.getName());
        tvCompanyTicker.setText(stock.getTicker());
        tvPrice.setText(stock.getPrice());
        tvPriceChange.setText(stock.getPriceChange());
        tvTradeDate.setText(stock.getTradeDate());
        tvTradeTime.setText("Updated: " + stock.getTradeTime());

        // If price change is positive, set TextView to green. Otherwise Red.
        if (stock.getPriceChangeDirection() >= 0)
            tvPriceChange.setTextColor(getResources().getColor(R.color.change_green));
        else
            tvPriceChange.setTextColor(getResources().getColor(R.color.change_red));

        // Sets up the ViewPager
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        mStockInfoPagerAdapter =
                new StockInfoPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mStockInfoPagerAdapter);
        mViewPager.setCurrentItem(0);          // Set default starting page to the 1st item
        mViewPager.setOffscreenPageLimit(2);   // Keep all pages loaded

        return rootView;
    }


    public class StockInfoPagerAdapter extends FragmentStatePagerAdapter {

        public StockInfoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:  return PriceInfoFragment.newInstance(stock);
                case 1:  return PriceChartFragment.newInstance(stock);

                default:  return PriceInfoFragment.newInstance(stock);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:  return getResources().getString(R.string.price_info);
                case 1:  return getResources().getString(R.string.charts);

                default: return "What";
            }
        }
    }

}