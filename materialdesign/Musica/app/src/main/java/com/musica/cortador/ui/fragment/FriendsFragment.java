package com.musica.cortador.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.musica.cortador.R;
import com.musica.cortador.listner.FloatingBtnClickListner;


public class FriendsFragment extends Fragment implements FloatingBtnClickListner {


    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onSortByName() {
        Toast.makeText(getActivity(), "Sort By Name From Friends tab", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSortByDate() {

        Toast.makeText(getActivity(),"Sort By Date from Friends tab",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSortByRatting() {
        Toast.makeText(getActivity(),"Sort By Ratting from Friends tab",Toast.LENGTH_LONG).show();

    }
}
