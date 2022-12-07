package com.glassait.androidproject.view.main;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;

import java.util.Arrays;

public class DisplayListActivity extends AppCompatActivity {

    ListView itemList, priceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        // just for example - set scanning code to get data of one purchase
        StoreLocalData.getInstance()
                      .setScanningCode("65784");
        // get purchase from scanned receipt
        String[] purchase = StoreLocalData.getInstance()
                                          .getPurchaseByLastSC();
        System.out.println(Arrays.toString(purchase));

        String[] itemsName  = new String[purchase.length / 3];
        String[] itemsPrice = new String[purchase.length / 3];

        for (int i = 0; i < purchase.length - 1; i += 3) {
            itemsName[i / 3] = purchase[i];
            itemsPrice[i / 3] = purchase[i + 1];
        }

        itemList = findViewById(R.id.item_list_view);
        ArrayAdapter<String> arrayAdapterItems = new ArrayAdapter<>(
                this,
                R.layout.activity_list_view,
                R.id.textView,
                itemsName
        );
        itemList.setAdapter(arrayAdapterItems);

        priceList = findViewById(R.id.price_list_View);
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<>(
                this,
                R.layout.activity_list_view,
                R.id.textView,
                itemsPrice
        );
        priceList.setAdapter(arrayAdapterPrice);
    }
}
