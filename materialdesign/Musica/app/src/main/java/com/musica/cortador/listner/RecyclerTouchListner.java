package com.musica.cortador.listner;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerTouchListner implements RecyclerView.OnItemTouchListener{

    private static final String LOG=RecyclerTouchListner.class.toString();
    private GestureDetector gestureDetector;

    private ClickListner mClickListner;
    public RecyclerTouchListner(Context context, final RecyclerView recyclerView, final ClickListner clickListner) {
        Log.d(LOG, "constructor is called");
        mClickListner=clickListner;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(LOG,"onSingleTapUp is called");

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(LOG,"onLongPress is called");
                View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clickListner!=null){
                    clickListner.onLongClick(child,recyclerView.getChildPosition(child));
                }
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        Log.d(LOG,"onInterceptTouchEvent is called"+gestureDetector.onTouchEvent(motionEvent)+motionEvent);
        View child=recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if(child!=null && mClickListner!=null && gestureDetector.onTouchEvent(motionEvent)){
            mClickListner.onClick(child, recyclerView.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        Log.d(LOG,"onTouchEvent is called"+gestureDetector.onTouchEvent(motionEvent)+motionEvent);
    }
}