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

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sneha Khadatare on 5/8/2016.
 */
public class StockGraphActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.stockLineChart)
    LineChartView stockLineChart;

    @Bind(R.id.txtStockTitle)
    TextView txtStockTitle;

    private String mSelectedSymbol = "";

    private LineSet mLineSet;

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

        setupUI();

        initialiseLoader();
    }

    private void setupUI() {
        txtStockTitle.setText(mSelectedSymbol);
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

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
