package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.core.database.DatabaseUtilsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object {
        // This is when the game is over
        private const val DONE = 0L

        //number of milliseconds in one se
        private const val ONE_SECOND = 1000L

        // total time of the game
        private const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    // the current time the timer holds
    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long>
        get() = _time

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // LivaData that tells when the game is finished
    private val _eventFameFinish =  MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventFameFinish

    // the list of words
    private lateinit var wordList: MutableList<String>

    init  {
        resetList()
        nextWord()
        _score.value = 0
        _time.value = COUNTDOWN_TIME / 1000
        // creates a timer that triggers the end of the game
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = time.value?.minus(1)
            }

            override fun onFinish() {
                if(_time.value == DONE) {
                    _eventFameFinish.value = true
                }
            }
        }
        timer.start()

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFInishComplete() {
        _eventFameFinish.value = false
    }
}