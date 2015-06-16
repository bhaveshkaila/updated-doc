package com.musica.cortador.listner;


import android.view.View;

public interface ClickListner{

        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }