package com.googletutorial.wuerfelabziehapp.screens.start

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.googletutorial.wuerfelabziehapp.R
import com.googletutorial.wuerfelabziehapp.databinding.FragmentStartBinding
import java.util.*

class StartFragment : Fragment() {

    private val listOfNumbers = arrayListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start, container, false
        )
        binding.buttonStartCheating.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_diceFragment)
        )
        binding.buttonShowList.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_listFragment)
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showList() {
//        val intent = Intent(this@CheatScreen, ListActivity::class.java)
//        intent.putExtra("listOfNumbers", listOfNumbers)
//        startActivity(intent)
    }

    // Starts the DiceActivity, passes the listOfNumbers to it and clears the list afterwards
    private fun startCheating() {
//        val intent = Intent(this@CheatScreen, DiceActivity::class.java)

        // If the user hasn't added any numbers, the list should contain 1000 random numbers
        // between 1 and 6
        if (listOfNumbers.isEmpty()) {
            repeat(1000) {
                listOfNumbers.add(Random().nextInt(6) + 1)
            }
        }
        // The listOfNumbers is passed to the intent, which then is passe to the DiceActivity
//        intent.putExtra("listOfNumbers", listOfNumbers)
//        startActivity(intent)

        // Afterwards the listOfNumbers is cleared to provide an empty list when
        // the rolling is started
        listOfNumbers.clear()
    }

    // Clears the listOfNumbers that was filled by the user
    private fun clear() {
        listOfNumbers.clear()
        Toast.makeText(this.activity, "clear", Toast.LENGTH_SHORT).show()
    }

    // Adds a number to the listOfNumbers
    private fun addNumberToList(editTextToAdd: EditText) {
        // Check if the input is empty and returns if so
        if (editTextToAdd.text.toString() == "") {
            Toast.makeText(this.activity, "Du Larry", Toast.LENGTH_SHORT).show()
            return
        }

        val valueToAdd = editTextToAdd.text.toString().toInt()

        // Only if the value is between 1 and 6 it should be added
        if (valueToAdd in 1..6) {
            listOfNumbers.add(valueToAdd)
            Toast.makeText(this.activity, "Add $valueToAdd", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this.activity, "Du Larry", Toast.LENGTH_SHORT).show()
        }

        // Finally the text of the input field should be cleared to be ready for the next input
        editTextToAdd.text = null
    }
}