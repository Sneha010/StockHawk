package com.sam_chordas.android.stockhawk.eventbus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;


/**
 * Created by Sneha Khadatare on 5/8/2016.
 */
public class MainThreadBus extends Bus{

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void post(final Object event){
        if(Looper.myLooper() == Looper.getMainLooper()){
            super.post(event);
        }else{
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
