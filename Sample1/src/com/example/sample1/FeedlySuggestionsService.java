package com.example.sample1;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface FeedlySuggestionsService {

	 @GET("/v3/search/feeds")
	    void searchFeeds(@QueryMap Map<String, String> options, Callback<FeedlyResponse> cb);
}
