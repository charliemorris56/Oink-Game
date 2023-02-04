package com.example.oink

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        fun buyItem(itemNum: Int) {
            Log.v("buyItem", "itemNum - $itemNum");

            val itemSharedPref = "buy_item$itemNum";
            var itemBuyValue = sharedPref.getInt(itemSharedPref, 99);

            Log.v("buyItem", "itemBuyValue - $itemBuyValue");

            if (itemBuyValue == 99) {
                Toast.makeText(
                    applicationContext,
                    "An error has occurred, please try again later",
                    Toast.LENGTH_SHORT
                ).show();
                Log.v("buyItem", "Item $itemNum Error");
            } else if (itemBuyValue == 1) {
                Toast.makeText(
                    applicationContext,
                    "You already own item: $itemNum",
                    Toast.LENGTH_SHORT
                ).show();
                Log.v("buyItem", "Item $itemNum already brought");
            } else {
                sharedPref.edit().putInt(itemSharedPref, 1).apply();
                Log.v("buyItem", "Item $itemNum brought");
            }
        }

        var storeButtons = arrayOf(
            findViewById<Button>(R.id.storeButton1),
            findViewById<Button>(R.id.storeButton2),
            findViewById<Button>(R.id.storeButton3),
            findViewById<Button>(R.id.storeButton4)
        )

        storeButtons[0].setOnClickListener {
            buyItem(1);
            storeButtons[0].text = "Item 1 Brought";
        }

        storeButtons[1].setOnClickListener {
            buyItem(2);
            storeButtons[1].text = "Item 2 Brought";
        }

        storeButtons[2].setOnClickListener {
            buyItem(3);
            storeButtons[2].text = "Item 3 Brought";
        }

        storeButtons[3].setOnClickListener {
            buyItem(4);
            storeButtons[3].text = "Item 4 Brought";
        }

        val debugButtonClick = findViewById<Button>(R.id.debugButton)
        debugButtonClick.setOnClickListener {
            //RESET
            with(sharedPref.edit())
            {
                putInt("buy_item1", 0);
                putInt("buy_item2", 0);
                putInt("buy_item3", 0);
                putInt("buy_item4", 0);
                apply();
            }
        }

        fun buttonSetup() {
            for (i in 1..4) {
                var itemSharedPref = "buy_item$i";
                var itemBuyValue = sharedPref.getInt(itemSharedPref, 99);

                when (itemBuyValue) {
                    0 -> {
                        storeButtons[i-1].text = "Buy Item $i";
                    }
                    1 -> {
                        storeButtons[i-1].text = "Item $i Brought";
                    }
                    99 -> {
                        storeButtons[i-1].text = "Buy Item $i";
                        with(sharedPref.edit())
                        {
                            putInt(itemSharedPref, 0);
                            apply();
                        }
                        Log.v("buyItem", "First TimeSetup");
                    }
                }
            }
        }

        buttonSetup();
    }
}