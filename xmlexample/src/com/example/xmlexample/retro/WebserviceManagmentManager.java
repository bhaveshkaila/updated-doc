package com.example.xmlexample.retro;

import retrofit.RestAdapter;

public class WebserviceManagmentManager {
	
	public static WebserviceManagmentManager getInstance() {
        return new WebserviceManagmentManager();
    }

    private RestAdapter getRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(HttpConstants.BASE_URL)
               /* .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader(LibConstant.API_KEYS.KEY_API_TOKEN, HadwigerApp.getApiToken());
                    }
                })*/
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }

    /*HashMap queryMap=new HashMap();
    queryMap.put("query","bhavesh");
    
	WebserviceManagmentManager.getInstance().getFeedlySuggestionsService().searchFeeds(queryMap, this);*/
    public FeedlySuggestionsService getFeedlySuggestionsService() {
        return getRestAdapter().create(FeedlySuggestionsService.class);
    }
}
