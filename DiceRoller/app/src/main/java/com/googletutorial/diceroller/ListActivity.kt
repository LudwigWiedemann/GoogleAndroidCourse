package com.googletutorial.diceroller

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // The View is assigned to a value and the list is filled with the data from the intent
        val scrollList: TextView = findViewById(R.id.list_TextView)
        val list = intent.extras!!.getIntegerArrayList("listOfNumbers")

        // Every element in the list is added to the View
        for (number in list!!) {
            val numberString = number.toString()
            scrollList.text = scrollList.text.toString().plus("$numberString \n")
        }
    }
}