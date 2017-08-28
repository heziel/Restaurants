package com.zumper.apps.restaurants.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiObject for getting the Object result from the API.
 */

public class ApiObject {

    private List<Object> htmlAttributions = new ArrayList<Object>();
    private String nextPageToken;
    private List<Restaurant> results = new ArrayList<Restaurant>();
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Restaurant> getResults() {
        return results;
    }

    public void setResults(List<Restaurant> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
