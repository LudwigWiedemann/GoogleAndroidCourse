package com.googletutorial.diceroller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class CheatScreen : AppCompatActivity() {
    private val listOfNumbers = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat_screen)

        val buttonStart: Button = findViewById(R.id.startCheating_button)
        val buttonAdd: Button = findViewById(R.id.addToList_button)
        val buttonClear : Button = findViewById(R.id.clearList_button)
        val editTextToAdd: EditText = findViewById(R.id.editTextNumberDecimal)

        buttonClear.setOnClickListener {
            listOfNumbers.clear()
            Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show()
        }

        buttonStart.setOnClickListener {
            val intent = Intent(this@CheatScreen, MainActivity::class.java)

            if (listOfNumbers.isEmpty()) {
                repeat(1000) {
                    listOfNumbers.add(Random().nextInt(6) + 1)
                }
            }
            intent.putExtra("listOfNumbers", listOfNumbers)
            startActivity(intent)
        }

        buttonAdd.setOnClickListener {
            if (editTextToAdd.text.toString() == "") {
                Toast.makeText(this, "Du Larry", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val valueToAdd = editTextToAdd.text.toString().toInt()
            print(valueToAdd)
            if (valueToAdd in 1..6) {
                listOfNumbers.add(valueToAdd)
                Toast.makeText(this, "Add $valueToAdd", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Du Larry", Toast.LENGTH_SHORT).show()
            }
            editTextToAdd.text = null

        }
    }

    override fun onPause() {
        super.onPause()
        listOfNumbers.clear()
    }
}