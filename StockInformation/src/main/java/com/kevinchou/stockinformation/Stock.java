package com.kevinchou.stockinformation;

import android.graphics.drawable.Drawable;

public class Stock
{
    public Stock() {}

    private String prevClose;
    private String open;
    private String bid;
    private String ask;
    private String oneYearTarget;
    private String price;
    private String priceChange;
    private String marketCap;
    private String trailingPE;
    private String forwardPE;
    private String pegRatio;
    private String priceSales;
    private String priceBook;
    private String ebitda;
    private String tradeDate;


    private String tradeTime;
    private String name;
    private String ticker;
    private String exchange;


    private Drawable priceChart;

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    // Handles the price change info
    public int getPriceChangeDirection() {
        String d = priceChange.substring(0, 1);

        if (d.equals("-"))
        {
            return -1;
        }
        else if (d.equals("+"))
        {
            return 1;
        }

        return 0;
    }



    // Getters and setters

    public Drawable getPriceChart() {
        return priceChart;
    }

    public void setPriceChart(Drawable priceChart) {
        this.priceChart = priceChart;
    }

    public String getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getOneYearTarget() {
        return oneYearTarget;
    }

    public void setOneYearTarget(String oneYearTarget) {
        this.oneYearTarget = oneYearTarget;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getTrailingPE() {
        return trailingPE;
    }

    public void setTrailingPE(String trailingPE) {
        this.trailingPE = trailingPE;
    }

    public String getForwardPE() {
        return forwardPE;
    }

    public void setForwardPE(String forwardPE) {
        this.forwardPE = forwardPE;
    }

    public String getPegRatio() {
        return pegRatio;
    }

    public void setPegRatio(String pegRatio) {
        this.pegRatio = pegRatio;
    }

    public String getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(String priceSales) {
        this.priceSales = priceSales;
    }

    public String getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(String priceBook) {
        this.priceBook = priceBook;
    }

    public String getEbitda() {
        return ebitda;
    }

    public void setEbitda(String ebitda) {
        this.ebitda = ebitda;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
    return exchange;
}

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
