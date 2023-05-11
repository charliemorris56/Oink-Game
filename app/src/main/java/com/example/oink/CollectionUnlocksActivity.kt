package com.example.oink

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class CollectionUnlocksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_unlocks)

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        animalName = intent.getStringExtra("AnimalName").toString();

        val animalText = findViewById<TextView>(R.id.animalText)
        animalText.text = animalName;

        SetAnimalImage();
        SetAnimalFacts();
    }

    private var animalName = "";

    private fun SetAnimalImage()
    {
        var animalImage = findViewById<ImageView>(R.id.animalImage);

        when (animalName) {
            "Cow" -> {
                animalImage.setImageResource(R.drawable.cow_clean2);
            }
            "Pig" -> {
                animalImage.setImageResource(R.drawable.pig_clean2);
            }
            "Sheep" -> {
                animalImage.setImageResource(R.drawable.sheep_clean2);
            }
        }
    }

    private fun SetAnimalFacts()
    {
        val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);

        var animalFacts = findViewById<TextView>(R.id.animalFacts)

        when (animalName) {
            "Cow" -> {
                animalFacts.text = sharedPref.getString("animal_collection_facts2", "").toString();
            }
            "Pig" -> {
                animalFacts.text = sharedPref.getString("animal_collection_facts1", "").toString();
            }
            "Sheep" -> {
                animalFacts.text = sharedPref.getString("animal_collection_facts3", "").toString();
            }
        }
    }
}