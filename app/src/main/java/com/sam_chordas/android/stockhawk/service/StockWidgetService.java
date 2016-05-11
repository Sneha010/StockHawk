package com.sam_chordas.android.stockhawk.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.data.StockWidgetDataProvider;

/**
 * Created by Sneha Khadatare : 587823
 * on 5/11/2016.
 */
public class StockWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new StockWidgetDataProvider(getApplicationContext());
    }
}
