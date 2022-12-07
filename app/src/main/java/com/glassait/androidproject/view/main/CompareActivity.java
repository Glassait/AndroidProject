package com.glassait.androidproject.view.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;

import java.util.ArrayList;
import java.util.Map;

public class CompareActivity extends AppCompatActivity {
    private PieChart piechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Map<Integer, String[]> database = StoreLocalData.getInstance()
                                                        .getDatabase();

        piechart = findViewById(R.id.compare_activity_pie_chart);
        setupPieChart();
        loadPieData();

    }

    private void loadPieData() {
        // Convert purchase data (LoadData) to be compatible with pie-chart library and load data
        // to pie-chart instance
        // just for example - set scanning code to get data of one purchase
        StoreLocalData.getInstance()
                      .setScanningCode("65784");
        // get purchase from scanned receipt
        String[] purchase = StoreLocalData.getInstance()
                                          .getPurchaseByLastSC();

        // calculate total amount of purchase and gather same categories
        float             totalAmount       = 0f;
        ArrayList<String> convertedPurchase = new ArrayList<>();
        for (int i = 1; i < purchase.length; i = i + 3) {
            float price = Float.parseFloat(purchase[i]);
            totalAmount += price;
            if (convertedPurchase.contains(purchase[i + 1])) {
                int   ind         = convertedPurchase.indexOf(purchase[i + 1]);
                float alreadyCost = Float.parseFloat(convertedPurchase.get(ind + 1));
                convertedPurchase.set(
                        ind + 1,
                        String.valueOf(alreadyCost + price)
                );
            } else {
                convertedPurchase.add(purchase[i + 1]);
                convertedPurchase.add(purchase[i]);
            }
        }
        // add items and prices (converted) to pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < convertedPurchase.size(); i = i + 2) {
            float price = Float.parseFloat(convertedPurchase.get(i + 1)) / totalAmount;
            entries.add(new PieEntry(
                    price,
                    convertedPurchase.get(i)
            ));
        }
        // set color
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        // initialize piechart with data and settings
        PieDataSet dataSet = new PieDataSet(
                entries,
                getString(R.string.text_category)
        );
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(piechart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        piechart.setData(data); // transmit all to piechart
        piechart.invalidate(); // data has been updated and needs to refresh on the screen
    }

    private void setupPieChart() {
        piechart.setDrawHoleEnabled(true);
        piechart.setUsePercentValues(true);
        piechart.setEntryLabelTextSize(12);
        piechart.setEntryLabelColor(Color.BLACK);
        piechart.setCenterText(getString(R.string.test_spending_by_category));
        piechart.setCenterTextSize(24);
        piechart.getDescription()
                .setEnabled(false);

        Legend leg = piechart.getLegend();
        leg.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        leg.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        leg.setOrientation(Legend.LegendOrientation.VERTICAL);
        leg.setDrawInside(false);
        leg.setEnabled(true);
    }
}