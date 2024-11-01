package com.zohair.taskedapp.retrofit;

import java.util.List;

public class QuoteResponse {

    private List<Quote> quotes;
    private int total;
    private int skip;
    private int limit;

    public QuoteResponse(List<Quote> quotes, int total, int skip, int limit) {
        this.quotes = quotes;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    // Getters and Setters
    public List<Quote> getQuotes() { return quotes; }
    public void setQuotes(List<Quote> quotes) { this.quotes = quotes; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getSkip() { return skip; }
    public void setSkip(int skip) { this.skip = skip; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    @Override
    public String toString() {
        return "QuoteResponse{" +
                "quotes=" + quotes +
                ", total=" + total +
                ", skip=" + skip +
                ", limit=" + limit +
                '}';
    }


}
