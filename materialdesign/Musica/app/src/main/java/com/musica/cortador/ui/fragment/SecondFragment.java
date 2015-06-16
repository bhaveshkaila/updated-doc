package com.musica.cortador.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.musica.cortador.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private TextView mPosition;

   public static SecondFragment getInstance(int position){

        SecondFragment fragment=new SecondFragment();

       Bundle args=new Bundle();
       args.putInt("position",position);

       fragment.setArguments(args);

       return fragment;
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_second, container, false);
        mPosition=(TextView)view.findViewById(R.id.text_position);

        Bundle bundle=getArguments();
        if(bundle!=null){
            mPosition.setText("The page currently selected is" +bundle.getInt("position"));
        }else{
            mPosition.setText("The page currently selected is");
        }


        return view;
    }


}
