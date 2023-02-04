package com.example.oink

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)


        val debugButtonClick = findViewById<Button>(R.id.debugButton)
        debugButtonClick.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java);
            startActivity(intent);
        }

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        var animalFactText = findViewById<TextView>(R.id.animalFactText);

        animalName = intent.getStringExtra("Animal").toString();

        animalFactText.text = "Fact about $animalName";

        Timer();

    }

    private var animalName = "";

    private val timerLengthSeconds = 3;
    private val timerIntervalSeconds = 1;

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