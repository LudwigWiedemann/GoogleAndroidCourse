package com.googletutorial.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.lang.Exception

class DiceActivity : AppCompatActivity() {

    // Index counts the times, that the user clicked on roll and tells the rollDice() function which
    // index of the listOfNumbers is to be displayed
    private var index = 0

    // listOfNumbers is initialized with values to make sure that there are values o display even if
    // the intent doesn't provide data
    private var listOfNumbers = arrayListOf(6, 5)

    // The diceImage is declared but will be initialized later because it can't be done before the
    // onCreate() function calls setContentView()
    lateinit var diceImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        // Here the diceImage gets initialized and not in the rollButton function to improve
        // performance by only calling once
        diceImage = findViewById(R.id.dice_image)
        val rollButton: Button = findViewById(R.id.roll_button)

        // try to get the date from teh intent into the listOfNumbers
        try {
            listOfNumbers = intent.extras?.getIntegerArrayList("listOfNumbers")!!
        } catch (e: Exception) {
            throw e
        }

        // Set a click listener on the roll button that calls the rollDice() function and resets
        // the index if it points tho th last value of the list
        rollButton.setOnClickListener {
            rollDice()
            if (index < listOfNumbers.size - 1) index++
            else index = 0
        }
    }

    // Takes the number from the listOfNumbers at the index and shows the corresponding image
    private fun rollDice() {
        val drawableResource = when (listOfNumbers[index]) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(drawableResource)
    }
}