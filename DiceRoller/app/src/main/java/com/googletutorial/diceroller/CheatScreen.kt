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

        // all Views are assigned to a value that is used in he code
        val buttonStart: Button = findViewById(R.id.startCheating_button)
        val buttonAdd: Button = findViewById(R.id.addToList_button)
        val buttonClear: Button = findViewById(R.id.clearList_button)
        val buttonShowList: Button = findViewById(R.id.showList_button)
        val editTextToAdd: EditText = findViewById(R.id.editTextNumberDecimal)

        buttonClear.setOnClickListener {
            clear()
        }

        buttonAdd.setOnClickListener {
            addNumberToList(editTextToAdd)
        }

        buttonStart.setOnClickListener {
            startCheating()
        }

        buttonShowList.setOnClickListener{
            showList()
        }
    }

    // Starts the ListActivity and passes the listOfNumbers to it without clearing it afterwards
    private fun showList() {
        val intent = Intent(this@CheatScreen, ListActivity::class.java)
        intent.putExtra("listOfNumbers", listOfNumbers)
        startActivity(intent)
    }

    // Starts the DiceActivity, passes the listOfNumbers to it and clears the list afterwards
    private fun startCheating() {
        val intent = Intent(this@CheatScreen, DiceActivity::class.java)

        // If the user hasn't added any numbers, the list should contain 1000 random numbers
        // between 1 and 6
        if (listOfNumbers.isEmpty()) {
            repeat(1000) {
                listOfNumbers.add(Random().nextInt(6) + 1)
            }
        }
        // The listOfNumbers is passed to the intent, which then is passe to the DiceActivity
        intent.putExtra("listOfNumbers", listOfNumbers)
        startActivity(intent)

        // Afterwards the listOfNumbers is cleared to provide an empty list when
        // the rolling is started
        listOfNumbers.clear()
    }

    // Clears the listOfNumbers that was filled by the user
    private fun clear() {
        listOfNumbers.clear()
        Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show()
    }

    // Adds a number to the listOfNumbers
    private fun addNumberToList(editTextToAdd: EditText) {
        // Check if the input is empty and returns if so
        if (editTextToAdd.text.toString() == "") {
            Toast.makeText(this, "Du Larry", Toast.LENGTH_SHORT).show()
            return
        }

        val valueToAdd = editTextToAdd.text.toString().toInt()

        // Only if the value is between 1 and 6 it should be added
        if (valueToAdd in 1..6) {
            listOfNumbers.add(valueToAdd)
            Toast.makeText(this, "Add $valueToAdd", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Du Larry", Toast.LENGTH_SHORT).show()
        }

        // Finally the text of the input field should be cleared to be ready for the next input
        editTextToAdd.text = null
    }
}