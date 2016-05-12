package com.sam_chordas.android.stockhawk.data;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sneha Khadatare : 587823
 * on 5/11/2016.
 */
public class StockWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private List stockList = new ArrayList();

    public StockWidgetDataProvider(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        fetchData();
    }

    @Override
    public void onDataSetChanged() {

        fetchData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mCursor!=null){
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(mCursor != null && mCursor.moveToPosition(position) ){
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName() , R.layout.widget_list_item);
            remoteViews.setTextViewText(R.id.stock_symbol , mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)));
            remoteViews.setTextViewText(R.id.bid_price , mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)));
            remoteViews.setTextColor(R.id.bid_price ,mContext.getResources().getColor(R.color.colorPrimary));

            if(mCursor.getInt(mCursor.getColumnIndex("is_up"))==1)
               remoteViews.setImageViewBitmap(R.id.ivProgressIcon , BitmapFactory.decodeResource(mContext.getResources() ,R.drawable.arrow_up));
            else
                remoteViews.setImageViewBitmap(R.id.ivProgressIcon , BitmapFactory.decodeResource(mContext.getResources() ,R.drawable.arrow_down));

            Intent intent = new Intent(mContext , MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext , 0 ,intent, 0);

            remoteViews.setOnClickPendingIntent(R.id.llStockRow , pendingIntent);
            return remoteViews;

        }else
         return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void fetchData(){
        final long identityToken = Binder.clearCallingIdentity();
        mCursor = mContext.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                new String[]{
                        QuoteColumns._ID,
                        QuoteColumns.SYMBOL,
                        QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE,
                        QuoteColumns.CHANGE,
                        QuoteColumns.ISUP
                },
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);
        Binder.restoreCallingIdentity(identityToken);

    }
}
