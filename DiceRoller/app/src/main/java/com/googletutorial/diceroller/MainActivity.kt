package com.googletutorial.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    private var index = 0
    private var listOfNumbers = arrayListOf(6,5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        try {
            listOfNumbers = intent.extras?.getIntegerArrayList("listOfNumbers")!!
        } catch (e : Exception) {
            throw e
        }

        rollButton.setOnClickListener {
            rollDice()
            if (index < listOfNumbers!!.size -1) index++
            else index = 0
        }
    }

    private fun rollDice() {
        val randomInt = Random().nextInt(6) + 1
        val drawableResource = when(listOfNumbers!![index]) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val diceImage: ImageView = findViewById(R.id.dice_image)
        diceImage.setImageResource(drawableResource)
    }
}