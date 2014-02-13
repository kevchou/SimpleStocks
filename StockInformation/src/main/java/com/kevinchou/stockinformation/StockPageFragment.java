package com.kevinchou.stockinformation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinchou.stockinformation.StockPage.KeyStatsFragment;
import com.kevinchou.stockinformation.StockPage.PriceChartFragment;
import com.kevinchou.stockinformation.StockPage.PriceInfoFragment;


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

        tvCompany.setText(stock.getName());
        tvCompanyTicker.setText(stock.getTicker());
        tvPrice.setText(stock.getPrice());
        tvPriceChange.setText(stock.getPriceChange());
        tvTradeDate.setText(stock.getTradeDate());
        tvTradeTime.setText("Updated: " + stock.getTradeTime());

        if (stock.getPriceChangeDirection() > 0)
        {
            tvPriceChange.setTextColor(Color.rgb(0, 136,0));
        }
        else if (stock.getPriceChangeDirection() < 0)
        {
            tvPriceChange.setTextColor(Color.rgb(204, 0, 0));
        }
        else
        {
            tvPriceChange.setTextColor(Color.BLACK);
        }

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        mStockInfoPagerAdapter =
                new StockInfoPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mStockInfoPagerAdapter);
        mViewPager.setCurrentItem(1);          // Starting Page
        mViewPager.setOffscreenPageLimit(2);   // Pages to keep loaded

        return rootView;
    }


    public class StockInfoPagerAdapter extends FragmentStatePagerAdapter {

        public StockInfoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            switch (i)
            {
                case 0:  return KeyStatsFragment.newInstance(stock);
                case 1:  return PriceInfoFragment.newInstance(stock);
                case 2:  return PriceChartFragment.newInstance(stock);
                default:  return PriceInfoFragment.newInstance(stock);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position)
            {
                case 0:  return getResources().getString(R.string.key_statistics);
                case 1:  return getResources().getString(R.string.price_info);
                case 2:  return getResources().getString(R.string.charts);
                default: return null;
            }

        }
    }

}