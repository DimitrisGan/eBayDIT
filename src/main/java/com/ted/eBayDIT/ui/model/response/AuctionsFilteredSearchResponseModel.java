package com.ted.eBayDIT.ui.model.response;


import java.util.List;

public class AuctionsFilteredSearchResponseModel {

    private List<AuctionsResponseModel> auctions;

    private int totalFilteredAuctions;


    public int getTotalFilteredAuctions() {
        return totalFilteredAuctions;
    }

    public void setTotalFilteredAuctions(int totalFilteredAuctions) {
        this.totalFilteredAuctions = totalFilteredAuctions;
    }


    public List<AuctionsResponseModel> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<AuctionsResponseModel> auctions) {
        this.auctions = auctions;
    }



}
