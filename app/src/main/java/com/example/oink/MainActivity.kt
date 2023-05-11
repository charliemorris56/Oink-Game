package com.example.oink

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionCheck();

//        val playButtonClick = findViewById<Button>(R.id.playButton)
//        playButtonClick.setOnClickListener {
//            val intent = Intent(this, PlayActivity::class.java)
//            startActivity(intent)
//        }

        val playButton2Click = findViewById<Button>(R.id.playButton2)
        playButton2Click.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        val storeButtonClick = findViewById<Button>(R.id.storeButton)
        storeButtonClick.setOnClickListener {
            val intent = Intent(this, StoreMathActivity::class.java)
            startActivity(intent)
        }

        val collectionButtonClick = findViewById<Button>(R.id.collectionButton)
        collectionButtonClick.setOnClickListener {
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);
        fun AnimalCollectionSetup() {
            for (i in 1..3) {
                var itemSharedPref = "animal_collection$i";
                var itemSharedPref2 = "animal_collection_facts$i";

                if (sharedPref.getInt("animal_collection1", 99) == 99) {
                    with(sharedPref.edit())
                    {
                        putInt(itemSharedPref, 0);
                        putString(itemSharedPref2, "");
                        apply();
                    }
                }
            }
        }

        AnimalCollectionSetup();
    }

    private fun PermissionCheck()
    {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.RECORD_AUDIO"), 0)
        }
    }
}