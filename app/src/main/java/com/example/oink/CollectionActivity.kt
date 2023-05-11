package com.example.oink

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

//        val debugButtonClick = findViewById<Button>(R.id.debugButton)
//        debugButtonClick.setOnClickListener {
//            //RESET
//            with(sharedPref.edit())
//            {
//                putInt("animal_collection1", 0);
//                putInt("animal_collection2", 0);
//                putInt("animal_collection3", 0);
//                apply();
//            }
//        }

        var collectionButtons = arrayOf(
            findViewById<Button>(R.id.animalCollectionButton),
            findViewById<Button>(R.id.animalCollectionButton2),
            findViewById<Button>(R.id.animalCollectionButton3)
        )

        collectionButtons[0].setOnClickListener {
            if (sharedPref.getInt("animal_collection1", 99) == 1) {
                val intent = Intent(this, CollectionUnlocksActivity::class.java);
                intent.putExtra("AnimalName", "Pig")
                startActivity(intent);
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Locked",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        collectionButtons[1].setOnClickListener {
            if (sharedPref.getInt("animal_collection2", 99) == 1) {
                val intent = Intent(this, CollectionUnlocksActivity::class.java);
                intent.putExtra("AnimalName", "Cow")
                startActivity(intent);
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Locked",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        collectionButtons[2].setOnClickListener {
            if (sharedPref.getInt("animal_collection3", 99) == 1) {
                val intent = Intent(this, CollectionUnlocksActivity::class.java);
                intent.putExtra("AnimalName", "Sheep")
                startActivity(intent);
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Locked",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }

        fun buttonSetup() {
            for (i in 1..3) {
                var itemSharedPref = "animal_collection$i";
                var animalCollectionValue = sharedPref.getInt(itemSharedPref, 99);
                var animalNameText = collectionButtons[i-1].text.split(" ")[0];

                when (animalCollectionValue) {
                    0 -> {
                        animalNameText += " Locked";
                    }
                    1 -> {
                        animalNameText += " Unlocked";
                    }
                    99 -> {
                        animalNameText += " Locked";
                        with(sharedPref.edit())
                        {
                            putInt(itemSharedPref, 0);
                            apply();
                        }
                        Log.v("buyItem", "First TimeSetup");
                    }
                }
                collectionButtons[i-1].text = animalNameText;
            }
        }

        buttonSetup();
    }
}