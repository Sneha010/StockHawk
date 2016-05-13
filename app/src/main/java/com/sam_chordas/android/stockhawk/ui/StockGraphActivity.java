package com.sam_chordas.android.stockhawk.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sneha Khadatare on 5/8/2016.
 */
public class StockGraphActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.stockBarChart)
    BarChart stockBarChart;

    @Bind(R.id.txtStockTitle)
    TextView txtStockTitle;

    private String mSelectedSymbol = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_graph_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null && !TextUtils.isEmpty
                (getIntent().getExtras().getString(getString(R.string.symbol)))) {
            mSelectedSymbol = getIntent().getExtras().getString(getString(R.string.symbol));
        }

        txtStockTitle.setText(mSelectedSymbol);

        initialiseLoader();
    }

    private void initialiseLoader() {

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.symbol), mSelectedSymbol);
        getLoaderManager().initLoader(0, bundle, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.BIDPRICE},
                QuoteColumns.SYMBOL + " = ?",
                new String[]{bundle.getString(getResources().getString(R.string.symbol))},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        drawGraph(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void drawGraph(Cursor cursor){
        cursor.moveToFirst();

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < cursor.getCount(); i++) {
            float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(QuoteColumns.BIDPRICE)));
            entries.add(new BarEntry(price , i));
            labels.add(i+"");
            cursor.moveToNext();
        }

        BarDataSet dataset = new BarDataSet(entries, getString(R.string.bar_chart_yAxis_desc));
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data = new BarData(labels, dataset);
        stockBarChart.setData(data);
        data.setGroupSpace(80f);
        stockBarChart.setDescription(getString(R.string.bar_chart_desc));

        data.setGroupSpace(80f);
        stockBarChart.setScaleMinima(3f, 1f);
        stockBarChart.setHovered(false);
        stockBarChart.setDrawGridBackground(false);
        stockBarChart.getAxisRight().setEnabled(false);

        //Plots x-axis lables at bottom
        XAxis xAxis = stockBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);

        YAxis leftAxis = stockBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
