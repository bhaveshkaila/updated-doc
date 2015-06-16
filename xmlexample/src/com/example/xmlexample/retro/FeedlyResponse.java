package com.example.xmlexample.retro;

import java.util.List;

public class FeedlyResponse {

	public  FeedlyResponse(){
    }
 
    public List<FeedlyResult> getResults() {
        return results;
    }
 
    public void setResults(List<FeedlyResult> results) {
        this.results = results;
    }
 
    private List<FeedlyResult> results;
}
