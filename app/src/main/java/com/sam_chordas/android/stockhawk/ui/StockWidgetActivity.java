package com.sam_chordas.android.stockhawk.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.StockWidgetService;

/**
 * Created by Sneha Khadatare : 587823
 * on 5/11/2016.
 */
public class StockWidgetActivity extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int widgetId: appWidgetIds) {

            RemoteViews remoteViews = initialiseRemoteViews(context , widgetId);

            Intent intent = new Intent(context , MyStocksActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context , 0 , intent , 0);
            remoteViews.setOnClickPendingIntent(R.id.rlHeader , pendingIntent);

            appWidgetManager.updateAppWidget(widgetId , remoteViews);

        }


    }

    private RemoteViews initialiseRemoteViews(Context context , int widgetId){
        RemoteViews rView = new RemoteViews(context.getPackageName() , R.layout.stock_widget_layout);

        Intent intent = new Intent(context , StockWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID , widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        rView.setRemoteAdapter(R.id.lvWidgetStockList , intent);

        return rView;
    }
}
