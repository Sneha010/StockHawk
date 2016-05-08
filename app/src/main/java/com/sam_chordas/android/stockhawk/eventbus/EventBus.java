package com.sam_chordas.android.stockhawk.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by Sneha Khadatare on 5/8/2016.
 */
public class EventBus {

    private static MainThreadBus bus;

    public static Bus getEventBus(){

        if(bus == null){
            bus = new MainThreadBus();
        }
        return bus;
    }

}
