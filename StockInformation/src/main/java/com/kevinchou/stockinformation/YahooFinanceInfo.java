package com.kevinchou.stockinformation;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class YahooFinanceInfo {

    // Yahoo finance symbols
    final static String yPrice           = "l1";
    final static String yChange          = "c";
    final static String yPrevClose       = "p";
    final static String yOpen            = "o";
    final static String yBid             = "b3";
    final static String yAsk             = "b2";
    final static String yOneYrTarget     = "t8";
    final static String yMarketCap       = "j1";
    final static String yTrailingPE      = "r";
    final static String yForwardPE       = "r7";
    final static String yPegRatio        = "r5";
    final static String yPriceSales      = "p5";
    final static String yPriceBook       = "p6";
    final static String yEbitda          = "j4";
    final static String yTradeDate       = "d1";
    final static String yTradeTime       = "t1";
    final static String yExchange        = "x";
    final static String yName            = "n";
    final static String yDaysRange       = "m0";
    final static String yYearRange       = "w0";
    final static String yAvgDailyVolume  = "a2";
    final static String yVolume          = "v0";



    public static Stock getStockData(String ticker) {

        String[] companyData;

        Stock stock = new Stock();
        stock.setTicker(ticker);

        try {


            URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s="
                    + ticker
                    + "&f="
                    + yPrice
                    + yChange
                    + yPrevClose
                    + yOpen
                    + yBid
                    + yAsk
                    + yOneYrTarget
                    + yMarketCap
                    + yTrailingPE
                    + yForwardPE
                    + yPegRatio
                    + yPriceSales
                    + yPriceBook
                    + yEbitda
                    + yTradeDate
                    + yTradeTime
                    + yExchange
                    + yAvgDailyVolume
                    + yVolume
                    + yDaysRange
                    + yYearRange
                    + yName
            );
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

// Split CSV by comma and quotes
            companyData = in.readLine().split(",");
            in.close();

// Convert price string to float, then round to 2 digits.
            stock.setPrice(String.format("$%.2f", Float.parseFloat(companyData[0])));

// Separate actual change and percent change
            String[] priceChange = companyData[1].replaceAll("\"", "").split(" - ");
            stock.setPriceChange(priceChange[0] + " (" + priceChange[1] + ")");

            stock.setPrevClose(companyData[2]);
            stock.setOpen(companyData[3]);
            stock.setBid(companyData[4]);
            stock.setAsk(companyData[5]);
            stock.setOneYearTarget(companyData[6]);
            stock.setMarketCap(companyData[7]);
            stock.setTrailingPE(companyData[8]);
            stock.setForwardPE(companyData[9]);
            stock.setPegRatio(companyData[10]);
            stock.setPriceSales(companyData[11]);
            stock.setPriceBook(companyData[12]);
            stock.setEbitda(companyData[13]);
            stock.setTradeDate(companyData[14].replaceAll("\"", ""));
            stock.setTradeTime(companyData[15].replaceAll("\"", ""));
            stock.setExchange(companyData[16].replaceAll("\"", ""));
            stock.setAvgDailyVolume(companyData[17]);
            stock.setVolume(companyData[18]);
            stock.setDaysRange(companyData[19].replaceAll("\"", ""));
            stock.setYearRange(companyData[20].replaceAll("\"", ""));

            // Name is always last, since name can contain a comma.
            stock.setName(companyData[companyData.length-1].replaceAll("\"", ""));

        } catch (Exception e) {

            Log.d("ERROR_GETTINGDATA", e.toString());

        }

        return stock;
    }

    // This method will retrieve the price chart from Yahoo and returns it as a drawable
    public static Drawable getPriceChart(String ticker, String timeInterval, String plotType) {

        Drawable d = null;

        try {

            URL url = new URL("http://chart.finance.yahoo.com/z?"
                    + "s=" + ticker
                    + "&t=" + timeInterval  // Time interval, {1d, 5d, 3m, 6m, 1y, 2y, 5y, my}
                    + "&q=" + plotType      // Chart type {l, b, c}
                    + "&l=" + "on"  // Logarithmic scaling {on, off}
                    + "&z=" + "m"   // Size {m, l}
                    + "&a=v" // Volume
                    );

            InputStream content = (InputStream)url.getContent();
            d = Drawable.createFromStream(content, "src");
        } catch (Exception e) {

            Log.d("ERROR_GETTINGCHARTS", e.toString());

        }

        return d;
    }

}
