package com.musica.cortador.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.musica.cortador.R;
import com.musica.cortador.adapter.AdapterMovies;
import com.musica.cortador.application.MusicaApplication;
import com.musica.cortador.listner.FloatingBtnClickListner;
import com.musica.cortador.model.Movie;
import com.musica.cortador.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_ID;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.musica.cortador.model.Keys.EndpointBoxOffice.KEY_TITLE;


public class FromMeFragment extends Fragment implements FloatingBtnClickListner, SwipeRefreshLayout.OnRefreshListener {

    private static final String BOX_OFFICE="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey="+ MusicaApplication.API_KEY;
    private static final String SAVED_STATE ="Saved_Movie_list" ;

    private VolleySingleton mVolleySingleton;

    private RequestQueue mRequestQueue;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<Movie> mListMovies = new ArrayList<>();

    private ImageLoader mImageLoader;

    private SimpleDateFormat dateFormat=null;

    private RecyclerView mListMovieHits;

    private AdapterMovies mAdapterBoxOffice;

    public FromMeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVolleySingleton=VolleySingleton.getInstance();
        mRequestQueue=mVolleySingleton.getRequestQueue();
        dateFormat=new SimpleDateFormat("yyyy-MM-dd");


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_from_me, container, false);

        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeMovieHits);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListMovieHits= (RecyclerView) rootView.findViewById(R.id.listMovieHits);
        mListMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterBoxOffice=new AdapterMovies(getActivity());
        mListMovieHits.setAdapter(mAdapterBoxOffice);
        ScaleInAnimator animator=new ScaleInAnimator();
        animator.setRemoveDuration(1000);
        animator.setAddDuration(1000);
        mListMovieHits.setItemAnimator(animator);
        // Inflate the layout for this fragment
        if(savedInstanceState!=null){

            mListMovies=savedInstanceState.getParcelableArrayList(SAVED_STATE);
            mAdapterBoxOffice.setMovies(mListMovies);

        }else{
            sendJsonRequest();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_STATE,mListMovies);
    }


    public void sendJsonRequest(){
        Toast.makeText(getActivity(),"Json Request Send",Toast.LENGTH_LONG).show();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,BOX_OFFICE,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(getActivity(), "RESPONSE " + response, Toast.LENGTH_SHORT).show();
                mListMovies=parseJsonResponse(response);
                mAdapterBoxOffice.setMovies(mListMovies);
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);

    }
    public ArrayList<Movie> parseJsonResponse(JSONObject jsonObject){
        ArrayList<Movie> listMovie=new ArrayList<>();
        if(jsonObject!=null && jsonObject.length()!=0) {


            try {
                if (jsonObject.has(KEY_MOVIES)) {
                    StringBuilder data = new StringBuilder();
                    JSONArray arrayMovie = jsonObject.getJSONArray(KEY_MOVIES);
                    for (int i = 0; i < arrayMovie.length(); i++) {
                        JSONObject current = arrayMovie.getJSONObject(i);
                        long id = current.getLong(KEY_ID);
                        String title = current.getString(KEY_TITLE);
                        JSONObject releaseDataObject = current.getJSONObject(KEY_RELEASE_DATES);
                        String releaseDate = null;
                        if (releaseDataObject.has(KEY_THEATER)) {
                            releaseDate = releaseDataObject.getString(KEY_THEATER);
                        } else {
                            releaseDate = "Not Available";
                        }
                        String audienceScore = "70";
                        String synopsis = current.getString(KEY_SYNOPSIS);

                        JSONObject posterDataObject = current.getJSONObject(KEY_POSTERS);
                        String thumbnail = null;
                        if (posterDataObject.has(KEY_THUMBNAIL)) {
                            thumbnail = posterDataObject.getString(KEY_THUMBNAIL);
                        } else {
                            thumbnail = "Not Available";
                        }

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setReleaseDateTheater(dateFormat.parse(releaseDate));
                        movie.setSynopsis(synopsis);
                        movie.setUrlThumbnail(thumbnail);
                        movie.setAudienceScore(Integer.parseInt(audienceScore));
                        listMovie.add(movie);
                    }
//                    Toast.makeText(getActivity(), "RESPONSE " + mListMovies.toString(), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return listMovie;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSortByName() {
        Toast.makeText(getActivity(),"Sort By Name From from Me tab",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSortByDate() {

        Toast.makeText(getActivity(),"Sort By Date fr0m From Me tab",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSortByRatting() {
        Toast.makeText(getActivity(),"Sort By Ratting from From Me tab",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"List Refreshed",Toast.LENGTH_LONG).show();
        sendJsonRequest();
    }
}
