package com.example.oink

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

//        val debugButtonClick = findViewById<Button>(R.id.debugButton)
//        debugButtonClick.setOnClickListener {
//            val intent = Intent(this, PlayActivity::class.java);
//            startActivity(intent);
//        }

        val debugButtonClick = findViewById<Button>(R.id.playButton)
        debugButtonClick.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java);
            startActivity(intent);
        }

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        animalName = intent.getStringExtra("Animal").toString();

        SetAnimalFact();
        SetAnimalImage();

        //Timer();
    }

    private var animalName = "";
    private var animalFact = "";

    private val cowFacts: List<String> = listOf(
        "Cows are big, friendly animals that live on farms.",
        "Cows are like big pet cows that farmers take care of.",
        "Cows eat only plants, like grass and hay.",
        "Cows have a special tummy that helps them eat and digest plants that other animals can't eat.",
        "Cows like to be with their cow friends and live in groups called herds.",
        "Cows make friends and help each other by licking each other with their big tongues.",
        "Cows give us important things that we use every day. They give us milk to make yummy things like cheese and butter.",
        "Cows give us meat (beef) from cows.",
        "Cows skin helps make things like shoes and belts."
    );

    private val sheepFacts: List<String> = listOf(
        "Sheep are fluffy animals that live on farms.",
        "Sheep are like big woolly pets that farmers take care of.",
        "Sheep eat plants, just like cows. They like to eat grass and hay.",
        "Sheep have a special tummy that helps them digest the plants and get all the energy they need.",
        "Sheep like to be in groups called flocks.",
        "Sheep stick together and follow their leader, just like friends playing follow-the-leader.",
        "Sheep feel safe when they are all together.",
        "Sheep give us wool, which is their fluffy coat. We use wool to make warm sweaters, hats, and blankets.",
        "Sheep can give us their meat called lamb, which can be very tasty.",
    );

    private val pigFacts: List<String> = listOf(
        "Pigs are cute and chubby animals that live on farms.",
        "Pigs are like big, funny-looking pets that farmers take care of.",
        "Pigs eat a lot of different things. They enjoy eating fruits, vegetables, and even some leftovers from our meals.",
        "Pigs have a strong sense of smell and can find tasty food hidden in the ground.",
        "Pigs wag their tails when they're excited or having fun.",
        "Pigs like to live in groups called herds.",
        "Pigs enjoy playing and rolling around in the mud together.",
        "Mud helps Pigs cool off because they don't sweat like humans do.",
        "Pigs are useful to us in many ways. They provide us with meat called pork, which is used to make things like bacon and sausages.",
        "Some pigs have a special job of finding truffles, which are very expensive mushrooms that grow underground. Pigs have a great nose for sniffing them out!"
        );

    private val timerLengthSeconds = 3;
    private val timerIntervalSeconds = 1;

    private fun SetAnimalImage()
    {
        var animalImage = findViewById<ImageView>(R.id.animalImage);
        val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);
        var currentUnlockedAnimalFacts = "";

        when (animalName) {
            "Cow" -> {
                animalImage.setImageResource(R.drawable.cow_clean2);

                currentUnlockedAnimalFacts = sharedPref.getString("animal_collection_facts2", "").toString();
                if (!currentUnlockedAnimalFacts.contains(animalFact)) {
                    animalFact += "\n" + currentUnlockedAnimalFacts;
                    with(sharedPref.edit())
                    {
                        putString("animal_collection_facts2", animalFact);
                        apply();
                    }
                }
            }
            "Pig" -> {
                animalImage.setImageResource(R.drawable.pig_clean2);

                currentUnlockedAnimalFacts = sharedPref.getString("animal_collection_facts1", "").toString();
                if (!currentUnlockedAnimalFacts.contains(animalFact)) {
                    animalFact += "\n" + currentUnlockedAnimalFacts;
                    with(sharedPref.edit())
                    {
                        putString("animal_collection_facts1", animalFact);
                        apply();
                    }
                }
            }
            "Sheep" -> {
                animalImage.setImageResource(R.drawable.sheep_clean2);

                currentUnlockedAnimalFacts = sharedPref.getString("animal_collection_facts3", "").toString();
                if (!currentUnlockedAnimalFacts.contains(animalFact)) {
                    animalFact += "\n" + currentUnlockedAnimalFacts;
                    with(sharedPref.edit())
                    {
                        putString("animal_collection_facts3", animalFact);
                        apply();
                    }
                }
            }
        }
    }

    private fun SetAnimalFact()
    {
        var animalFactText = findViewById<TextView>(R.id.animalFactText);

        when (animalName) {
            "Cow" -> {
                animalFact = cowFacts.random();
            }
            "Pig" -> {
                animalFact = pigFacts.random();
            }
            "Sheep" -> {
                animalFact = sheepFacts.random();
            }
        }
        animalFactText.text = animalFact;
    }

    private fun Timer()
    {
        Log.v("Timer", "Started");
        object : CountDownTimer(
            (timerLengthSeconds * 1000).toLong(),
            (timerIntervalSeconds * 1000).toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Timer", "Tick");
            }

            override fun onFinish() {
                Log.v("Timer", "Done");
                BackToPlay();
            }
        }.start()
    }

    private fun BackToPlay() {
        val intent = Intent(this, PlayActivity::class.java);
        startActivity(intent);
    }
}