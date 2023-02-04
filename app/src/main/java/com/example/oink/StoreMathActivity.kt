package com.example.oink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class StoreMathActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_math)

        var mathQuestion = findViewById<TextView>(R.id.mathQuestionText);
        mathQuestion.text = GetMathQuestion();

        var mathAnswer = findViewById<EditText>(R.id.mathTextNumber);
        mathAnswer.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (mathAnswer.text.toString() == answer.toString())
                    {
                        attempts = 0;

                        Log.v("StoreMath", "Correct");
                        val intent = Intent(this, StoreActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        if (attempts == 3)
                        {
                            Log.v("StoreMath", "4 Wrong attempts, back to home page");
                            attempts = 0;
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            attempts++;

                            Log.v("StoreMath", "Wrong. Attempt: $attempts");
                            Toast.makeText(
                                applicationContext,
                                "Please try again",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                    true;
                }
                else ->
                {
                    Log.v("StoreMath", "False");
                    false
                };
            }
        }

        val debugButtonClick = findViewById<Button>(R.id.debugButton)
        debugButtonClick.setOnClickListener {
            Log.v("StoreMath", mathAnswer.text.toString());
        }

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

    }

    private var attempts = 0;

    private var answer = 0;

    private fun GetMathQuestion() : String {
        val num1 = (5..9).random();
        val num2 = (5..9).random();
        answer = num1 * num2;

        return "$num1 X $num2 = ?"
    }

}